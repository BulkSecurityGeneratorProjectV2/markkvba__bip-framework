package gov.va.bip.framework.audit.model;

import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The purpose of this class is to collect the audit data for a response before serializing it to the logs.
 */
@JsonInclude(Include.NON_NULL)
public class HttpResponseAuditData extends ResponseAuditData {

	private static final long serialVersionUID = 3362363966640647082L;

	/* A map of the http headers on the response. */
	private Map<String, String> headers;

	/**
	 * Gets the http headers.
	 *
	 * @return the headers
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}

	/**
	 * Sets the http headers.
	 *
	 * @param headers
	 */
	public void setHeaders(final Map<String, String> headers) {
		this.headers = headers;
	}

	/**
	 * Manually formatted JSON-like string of key/value pairs.
	 */
	@Override
	public String toString() {
		return "HttpResponseAuditData{" + "headers=" + (headers == null ? "" : ReflectionToStringBuilder.toString(headers)) + ", uri="
				+ ", response=" + (getResponse() == null ? "" : getResponse().toString()) + '}';
	}
}
