package gov.va.ocp.framework.security.util;

import static gov.va.ocp.framework.security.jwt.JwtAuthenticationProvider.isPersonTraitsValid;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.HttpStatus;

import gov.va.ocp.framework.messages.MessageKeys;
import gov.va.ocp.framework.messages.MessageSeverity;
import gov.va.ocp.framework.security.PersonTraits;
import gov.va.ocp.framework.security.jwt.JwtAuthenticationException;
import gov.va.ocp.framework.security.jwt.correlation.CorrelationIdsParser;
import gov.va.ocp.framework.security.model.Person;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Created by vgadda on 5/5/17.
 */
public class GenerateToken {

	private static String secret = "secret";
	private static String issuer = "Vets.gov";

	/**
	 * Do not instantiate
	 */
	private GenerateToken() {
	}

	public static String generateJwt() {
		return generateJwt(person(), 900, secret, issuer, null);
	}

	public static String generateJwt(final Person person, final String secret, final String issuer) {
		return generateJwt(person, 900, secret, issuer, null);
	}

	public static String generateJwt(final Person person) {
		return generateJwt(person, 900, secret, issuer, null);
	}

	public static String generateJwt(final int expireInsec) {
		return generateJwt(person(), expireInsec, secret, issuer, null);
	}

	public static String generateJwt(final Person person, final int expireInsec, final String secret, final String issuer,
			final String[] jwtTokenRequiredParameterList) {
		final Calendar currentTime = GregorianCalendar.getInstance();
		final Calendar expiration = GregorianCalendar.getInstance();
		expiration.setTime(currentTime.getTime());
		expiration.add(Calendar.SECOND, expireInsec);

		// The JWT signature algorithm we will be using to sign the token
		final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		// We will sign our JWT with our ApiKey secret
		final Key signingKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), signatureAlgorithm.getJcaName());

		final PersonTraits personTraits = populatePersonTraits(person);
		try {
			List<String> list = person.getCorrelationIds();
			CorrelationIdsParser.parseCorrelationIds(list, personTraits);

		} catch (Exception e) { // NOSONAR intentionally wide, errors are already logged
			// if there is any detected issue with the correlation ids
			throw new JwtAuthenticationException(MessageKeys.OCP_SECURITY_TOKEN_INVALID,
					MessageSeverity.ERROR, HttpStatus.BAD_REQUEST);
		}

		if (!isPersonTraitsValid(personTraits, jwtTokenRequiredParameterList)) {
			throw new JwtAuthenticationException(MessageKeys.OCP_SECURITY_TOKEN_INVALID,
					MessageSeverity.ERROR, HttpStatus.BAD_REQUEST);
		}

		return Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.setIssuer(issuer)
				.setIssuedAt(currentTime.getTime())
				.setId(UUID.randomUUID().toString())
				.setExpiration(expiration.getTime())
				.claim("firstName", person.getFirstName())
				.claim("middleName", person.getMiddleName())
				.claim("lastName", person.getLastName())
				.claim("prefix", person.getPrefix())
				.claim("suffix", person.getSuffix())
				.claim("birthDate", person.getBirthDate())
				.claim("gender", person.getGender())
				.claim("assuranceLevel", person.getAssuranceLevel())
				.claim("email", person.getEmail())
				.claim("correlationIds", person.getCorrelationIds())
				.signWith(signatureAlgorithm, signingKey).compact();
	}

	private static PersonTraits populatePersonTraits(final Person person) {
		PersonTraits personTraits = new PersonTraits();

		personTraits.setFirstName(person.getFirstName());
		personTraits.setLastName(person.getLastName());
		personTraits.setPrefix(person.getPrefix());
		personTraits.setMiddleName(person.getMiddleName());
		personTraits.setSuffix(person.getSuffix());
		personTraits.setBirthDate(person.getBirthDate());
		personTraits.setGender(person.getGender());
		personTraits.setAssuranceLevel(person.getAssuranceLevel());
		personTraits.setEmail(person.getEmail());

		return personTraits;
	}

	public static Person person() {
		final Person person = new Person();
		person.setFirstName("JANE");
		person.setLastName("DOE");
		person.setPrefix("Ms");
		person.setMiddleName("M");
		person.setSuffix("S");
		person.setBirthDate("1955-01-01");
		person.setGender("FEMALE");
		person.setAssuranceLevel(2);
		person.setEmail("jane.doe@va.gov");

		final List<String> strArray = Arrays.asList("77779102^NI^200M^USVHA^P", "912444689^PI^200BRLS^USVBA^A",
				"6666345^PI^200CORP^USVBA^A", "1105051936^NI^200DOD^USDOD^A", "912444689^SS");
		person.setCorrelationIds(strArray);
		return person;
	}
}
