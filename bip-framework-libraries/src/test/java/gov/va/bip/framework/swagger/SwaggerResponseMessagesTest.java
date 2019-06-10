package gov.va.bip.framework.swagger;

import org.junit.Assert;
import org.junit.Test;

import gov.va.bip.framework.swagger.SwaggerResponseMessages;

public class SwaggerResponseMessagesTest {

	private static final String RESPONSE_200_MESSAGE = "A Response which indicates a successful Request.  "
			+ "Response may contain \"messages\" that could describe warnings or further information.";

	private static final String RESPONSE_400_MESSAGE = "There was an error encountered processing the Request.  "
			+ "Response will contain \"messages\" element with additional information on the error.  "
			+ "This request shouldn't be retried until corrected.";

	private static final String RESPONSE_401_MESSAGE = "The request is not authorized.  "
			+ "Please verify credentials in the request. "
			+ "Response will contain \"messages\" element with additional information on the error.";

	private static final String RESPONSE_403_MESSAGE = "Access to the resource is Forbidden.  "
			+ "Please verify if you have permission to access this resource. "
			+ "Response will contain \"messages\" element with additional information on the error.";

	private static final String RESPONSE_500_MESSAGE = "There was an error encountered processing the Request. "
			+ "Response will contain \"messages\" element with additional information on the error. Please retry. "
			+ "If problem persists, please contact support with a copy of the Response.";

	@Test
	public void response200MessageTextTest() throws Exception {
		Assert.assertEquals(SwaggerResponseMessages.MESSAGE_200, RESPONSE_200_MESSAGE);
	}

	@Test
	public void response401MessageTextTest() throws Exception {
		Assert.assertEquals(SwaggerResponseMessages.MESSAGE_401, RESPONSE_401_MESSAGE);
	}

	@Test
	public void response403MessageTextTest() throws Exception {
		Assert.assertEquals(SwaggerResponseMessages.MESSAGE_403, RESPONSE_403_MESSAGE);
	}

	@Test
	public void response400MessageTextTest() throws Exception {
		Assert.assertEquals(SwaggerResponseMessages.MESSAGE_400, RESPONSE_400_MESSAGE);
	}

	@Test
	public void response500MessageTextTest() throws Exception {
		Assert.assertEquals(SwaggerResponseMessages.MESSAGE_500, RESPONSE_500_MESSAGE);
	}

}
