package br.edu.green.web.entity.enumerate;

/**
 * <p>
 * <b>This enumerate defines all identifiers of email templates .</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 17/01/2017 (creation date)
 */

public enum EmailTemplateIdentifierEnum {
	USER_LOGIN("USER_LOGIN"), //
	EXECUTE_SUBMISSION_APPROVAL_EXPERIMENT_SCHEDULE("EXECUTE_SUBMISSION_APPROVAL_EXPERIMENT_SCHEDULE"), //
	EXECUTE_APPROVAL_EXPERIMENT_SCHEDULE("EXECUTE_APPROVAL_EXPERIMENT_SCHEDULE"), //
	EXECUTE_REJECTION_EXPERIMENT_SCHEDULE("EXECUTE_REJECTION_EXPERIMENT_SCHEDULE"), //
	;

	private String emailTemplateIdentifier;

	public String getEmailTemplateIdentifier() {
		return emailTemplateIdentifier;
	}

	public void setEmailTemplateIdentifier(String emailTemplateIdentifier) {
		this.emailTemplateIdentifier = emailTemplateIdentifier;
	}

	/**
	 * @param emailTemplateIdentifier
	 */
	private EmailTemplateIdentifierEnum(String emailTemplateIdentifier) {
		this.emailTemplateIdentifier = emailTemplateIdentifier;
	}
}
