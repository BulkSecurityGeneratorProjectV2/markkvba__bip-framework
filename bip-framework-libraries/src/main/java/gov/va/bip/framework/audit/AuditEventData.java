package gov.va.bip.framework.audit;

import org.apache.commons.lang3.StringUtils;

import gov.va.bip.framework.security.SecurityUtils;

/**
 *
 * Used as a data transfer object from the Auditable annotation for writing to the audit log.
 *
 * @author npaulus
 */
public final class AuditEventData {

	/** Replacement for {@code null} parameters to the constructor that cannot be null or empty */
	private static final String UNKNOWN = "Unknown";
	/** Replacement for {@code null} parameters to the constructor that cannot be null */
	private static final String EMPTY = "";

	/**
	 * The event type.
	 */
	private final AuditEvents event;

	/**
	 * The activity (or method name).
	 */
	private final String activity;

	/**
	 * The class being audited.
	 */
	private final String auditClass;

	/**
	 * The user from person traits.
	 */
	private String user = StringUtils.EMPTY;

	/**
	 * The tokenId from person traits.
	 */
	private String tokenId = StringUtils.EMPTY;

	/**
	 * Constructs a new AuditEventData object.
	 *
	 * @param event the event type.
	 * @param activity the activity or method name.
	 * @param auditClass the class name for class under audit.
	 */
	public AuditEventData(final AuditEvents event, final String activity, final String auditClass) {
		this.event = event == null ? AuditEvents.UNKNOWN : event;
		this.activity = StringUtils.isBlank(activity) ? UNKNOWN : activity;
		this.auditClass = StringUtils.isBlank(auditClass) ? UNKNOWN : auditClass;

		if (SecurityUtils.getPersonTraits() != null) {
			this.user = StringUtils.isBlank(SecurityUtils.getPersonTraits().getUser())
					? UNKNOWN
					: SecurityUtils.getPersonTraits().getUser();
			this.tokenId = StringUtils.isBlank(SecurityUtils.getPersonTraits().getTokenId())
					? EMPTY
					: SecurityUtils.getPersonTraits().getTokenId();
		} else {
			this.user = UNKNOWN;
			this.tokenId = EMPTY;
		}
	}

	/**
	 * Gets the event.
	 *
	 * @return the event.
	 */
	public AuditEvents getEvent() {
		return event;
	}

	/**
	 * Gets the activity.
	 *
	 * @return the activity
	 */
	public String getActivity() {
		return activity;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user.
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Gets the tokenId.
	 *
	 * @return the tokenId.
	 */
	public String getTokenId() {
		return tokenId;
	}

	/**
	 * Gets the audited class name.
	 *
	 * @return the audited class name.
	 */
	public String getAuditClass() {
		return auditClass;
	}

	@Override
	public String toString() {
		return "AuditEventData{" +
				"event=" + event +
				", activity='" + activity + '\'' +
				", auditClass='" + auditClass + '\'' +
				", tokenId=" + tokenId +
				", user=" + user +
				'}';
	}
}