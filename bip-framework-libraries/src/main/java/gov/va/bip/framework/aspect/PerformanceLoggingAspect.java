package gov.va.bip.framework.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import gov.va.bip.framework.log.BipLogger;
import gov.va.bip.framework.log.BipLoggerFactory;

/**
 * Performance logging (elapsed execution time).
 * This class should only be invoked from {@code @Aspect} annotated classes,
 * and only from {@code @Around} advice.
 *
 * Developers note: this class cannot be converted to use {@code @Before}
 * and {@code @After} advice. It would be necessary to maintain state between
 * the advice calls for the startMillis value. Spring can only maintain
 * threadsafety if injected (state) values are proxied, and a Long is not proxied.
 *
 * @author aburkholder
 */
public class PerformanceLoggingAspect {
	/** Class logger */
	private static final BipLogger LOGGER = BipLoggerFactory.getLogger(PerformanceLoggingAspect.class);

	/** number of milliseconds in a second */
	private static final int NUMBER_OF_MILLIS_N_A_SECOND = 1000;

	/** The Constant IN_ELAPSED_TIME. */
	private static final String IN_ELAPSED_TIME = "] in elapsed time [";

	/** The Constant ENTER. */
	private static final String ENTER = "enter ";

	/** The Constant EXIT. */
	private static final String EXIT = "exit ";

	/** The Constant SECS. */
	private static final String SECS = " secs";

	/** The Constant OPEN_BRACKET. */
	private static final String OPEN_BRACKET = "[";

	/** The Constant CLOSE_BRACKET. */
	private static final String CLOSE_BRACKET = "]";

	/** The Constant DOT. */
	private static final String DOT = ".";

	/**
	 * Do not instantiate this class.
	 */
	private PerformanceLoggingAspect() {
		throw new IllegalAccessError("PerformanceLoggingAspect is a static class. Do not instantiate it.");
	}

	/**
	 *
	 * This method should only be invoked from {@code @Aspect} annotated classes,
	 * and only from {@code @Around} advice.
	 *
	 * Developers note: this class cannot be converted to use {@code @Before}
	 * and {@code @After} advice. It would be necessary to maintain state between
	 * the advice calls for the startMillis value. Spring can only maintain
	 * threadsafety if injected (state) values are proxied, and a Long is not proxied.
	 *
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	public static final Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

		LOGGER.debug("PerformanceLoggingAspect executing around method:" + joinPoint.toLongString());

		Object returnObject = null;
		Method method = null;
		BipLogger methodLog = null;
		final long startTime = System.currentTimeMillis();

		try {
			method = ((MethodSignature) joinPoint.getStaticPart().getSignature()).getMethod();
			methodLog = BipLoggerFactory.getLogger(method.getDeclaringClass());

			// only log entry at the debug level
			if (methodLog.isDebugEnabled()) {
				methodLog.debug(ENTER + OPEN_BRACKET + method.getDeclaringClass().getSimpleName() + DOT
						+ method.getName() + CLOSE_BRACKET);
			}

			returnObject = joinPoint.proceed();

		} finally {
			LOGGER.debug("PerformanceLoggingAspect after method was called.");
			final long elapsedTime = System.currentTimeMillis() - startTime;
			final String callingClassAndMethod =
					method == null ? "null"
							: method.getDeclaringClass().getSimpleName() + DOT + method.getName();
			if (methodLog != null && methodLog.isInfoEnabled()) {
				methodLog.info(EXIT + OPEN_BRACKET + callingClassAndMethod + IN_ELAPSED_TIME
						+ elapsedTime / NUMBER_OF_MILLIS_N_A_SECOND
						+ DOT + elapsedTime % NUMBER_OF_MILLIS_N_A_SECOND + SECS + CLOSE_BRACKET);
			}
		}
		return returnObject;
	}
}
