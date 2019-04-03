package gov.va.ocp.framework.exception;

import org.springframework.http.HttpStatus;

import gov.va.ocp.framework.messages.MessageKey;
import gov.va.ocp.framework.messages.MessageSeverity;

/**
 * The root OCP class for managing <b>runtime</b> exceptions.
 * <p>
 * To support the requirements of consumer responses, OCP Exception classes that need
 * to immediately bubble back to the provider controller should extend this class.
 *
 * @see OcpExceptionExtender
 * @see RuntimeException
 *
 * @author aburkholder
 */
public class OcpRuntimeException extends RuntimeException implements OcpExceptionExtender {
	private static final long serialVersionUID = 4717771104509731434L;

	/** The consumer facing identity key */
	private final MessageKey key;
	/** Any values needed to fill in params (e.g. value for {0}) in the MessageKey message */
	private final Object[] params;
	/** The severity of the event: FATAL (500 series), ERROR (400 series), WARN (200 series), or INFO/DEBUG/TRACE */
	private final MessageSeverity severity;
	/** The best-fit HTTP Status, see <a href="https://tools.ietf.org/html/rfc7231">https://tools.ietf.org/html/rfc7231</a> */
	private final HttpStatus status;

	/**
	 * Constructs a new RuntimeException with the specified detail key, message, severity, and status.
	 * The cause is not initialized, and may subsequently be initialized by
	 * a call to {@link #initCause}.
	 *
	 * @see RuntimeException#RuntimeException(String)
	 *
	 * @param key - the consumer-facing key that can uniquely identify the nature of the exception
	 * @param severity - the severity of the event: FATAL (500 series), ERROR (400 series), WARN (200 series), or INFO/DEBUG/TRACE
	 * @param status - the HTTP Status code that applies best to the encountered problem, see
	 *            <a href="https://tools.ietf.org/html/rfc7231">https://tools.ietf.org/html/rfc7231</a>
	 * @param params - arguments to fill in any params in the MessageKey message (e.g. value for {0})
	 */
	public OcpRuntimeException(final MessageKey key, final MessageSeverity severity, final HttpStatus status, Object... params) {
		this(key, severity, status, null, params);
	}

	/**
	 * Constructs a new RuntimeException with the specified detail key, message, severity, status, and cause.
	 *
	 * @see RuntimeException#RuntimeException(String, Throwable)
	 *
	 * @param key - the consumer-facing key that can uniquely identify the nature of the exception
	 * @param severity - the severity of the event: FATAL (500 series), ERROR (400 series), WARN (200 series), or INFO/DEBUG/TRACE
	 * @param status - the HTTP Status code that applies best to the encountered problem, see
	 *            <a href="https://tools.ietf.org/html/rfc7231">https://tools.ietf.org/html/rfc7231</a>
	 * @param cause - the throwable that caused this throwable
	 * @param params - arguments to fill in any params in the MessageKey message (e.g. value for {0})
	 */
	public OcpRuntimeException(final MessageKey key, final MessageSeverity severity, final HttpStatus status,
			final Throwable cause, Object... params) {
		super((key == null ? "" : key.getMessage(params)), cause);
		this.key = key;
		this.params = params;
		this.severity = severity;
		this.status = status;
	}

	@Override
	public MessageKey getMessageKey() {
		return this.key;
	}

	@Override
	public String getKey() {
		return key.getKey();
	}

	@Override
	public Object[] getParams() {
		return this.params;
	}

	@Override
	public HttpStatus getStatus() {
		return status;
	}

	@Override
	public MessageSeverity getSeverity() {
		return severity;
	}

	@Override
	public String getServerName() {
		return System.getProperty("server.name");
	}
}
