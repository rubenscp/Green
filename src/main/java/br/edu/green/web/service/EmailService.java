package br.edu.green.web.service;

//import org.apache.commons.mail.HtmlEmail;

/**
 * <p>
 * <b> This class defines operations to send an email by SITIS Web.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 12/02/2016 (creation date)
 */

// http://www.codejava.net/java-ee/javamail/send-e-mail-in-html-format-using-javamail-api

public class EmailService {

	private ApplicationConfigurationService applicationConfiguration;
	private ApplicationMessageService applicationMessage;
	// private HtmlEmail htmlEmail;

	private String hostName;
	private int smtpPort;
	private String charSet;
	private String fontFamily;
	private String fromUserEmail;
	private String fromUserName;

	// private String emailUser;
	// private String password;
	// private boolean ssl;

	/***
	 * Default constructor
	 * 
	 * @throws GeneralException
	 *             The exception thrown when occurring any failure.
	 */
	// public EmailService() throws GeneralException {
	// super();
	// try {
	// // recovering the parameters of configuration of the system loading current messages file
	// this.applicationConfiguration = ApplicationConfigurationService.getInstanceof();
	// this.applicationMessage = ApplicationMessageService.getInstanceof();
	//
	// // configuring initials parameters of email service
	// this.htmlEmail = new HtmlEmail();
	// this.hostName = this.applicationConfiguration.getStringValue("sitisweb.email.host.name").trim();
	// this.smtpPort = this.applicationConfiguration.getIntegerValue("sitisweb.email.smtp.port");
	// this.charSet = this.applicationConfiguration.getStringValue("sitisweb.email.charset");
	// this.fontFamily = this.applicationConfiguration.getStringValue("sitisweb.email.font.family");
	// this.fromUserEmail = this.applicationConfiguration.getStringValue("sitisweb.email.from.user.email").trim();
	// this.fromUserName = this.applicationConfiguration.getStringValue("sitisweb.email.from.user.name").trim();
	//
	// } catch (Exception e) {
	// // configuring and throwing details of the actual exception
	// GeneralException ge = new GeneralException();
	// ge.setProcessingResult(new ProcessingResultEntity(Code.EMAIL_SERVICE_ERROR_INITIALIZING,
	// this.applicationMessage.getMessage(Code.EMAIL_SERVICE_ERROR_INITIALIZING.name())));
	// ge.setClassName(this.getClass().getName());
	// ge.setMethodName("EmailService");
	// ge.setExceptionOriginalMessage(e.getMessage()); // the original exception message
	//
	// // writing in the message log
	// System.out.println(ge.getProcessingResult().toString());
	//
	// // throws general exception
	// throw ge;
	// }
	// }
	//
	// /**
	// * Send an email
	// *
	// * @throws GeneralException
	// */
	// public void send(String subject, String body, String toEmail, String toUserName) throws GeneralException {
	// try {
	// this.htmlEmail.setHostName(this.hostName);
	// this.htmlEmail.setSmtpPort(this.smtpPort);
	// // this.htmlEmail.setSslSmtpPort(Integer.toString(this.smtpPort));
	// // this.htmlEmail.setAuthenticator(new DefaultAuthenticator(this.emailUser, this.password));
	// // this.htmlEmail.setSSL(true);
	// this.htmlEmail.setFrom(this.fromUserEmail, this.fromUserName);
	// this.htmlEmail.setCharset(this.charSet);
	//
	// this.htmlEmail.setSubject(subject);
	// // this.htmlEmail.setMsg(body);
	// this.htmlEmail.setHtmlMsg(body);
	// this.htmlEmail.addTo(toEmail, toUserName);
	// this.htmlEmail.send();
	//
	// } catch (Exception e) {
	// // configuring and throwing details of the actual exception
	// GeneralException ge = new GeneralException();
	// ge.setProcessingResult(new ProcessingResultEntity(Code.EMAIL_SERVICE_ERROR_SEND,
	// this.applicationMessage.getMessage(Code.EMAIL_SERVICE_ERROR_SEND.name())));
	// ge.setClassName(this.getClass().getName());
	// ge.setMethodName("send");
	// ge.setExceptionOriginalMessage(e.getMessage()); // the original exception message
	//
	// // writing in the message log
	// System.out.println(ge.getProcessingResult().toString());
	//
	// // throws general exception
	// throw ge;
	// }
	// }
}
