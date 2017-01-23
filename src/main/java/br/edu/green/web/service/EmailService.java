package br.edu.green.web.service;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.edu.green.web.entity.EmailTemplateEntity;
import br.edu.green.web.entity.ProcessingResultEntity;
import br.edu.green.web.entity.ProcessingResultEntity.Code;
import br.edu.green.web.exception.GeneralException;

/**
 * <p>
 * <b> This class defines operations to send an email by SITIS Web.</b>
 * </p>
 * 
 * @author Rubens de Castro Pereira
 * @version 0.1
 * @since 17/01/2017 (creation date)
 */

// http://www.codejava.net/java-ee/javamail/send-e-mail-in-html-format-using-javamail-api

public class EmailService {

	protected ApplicationConfigurationService applicationConfiguration;
	protected ApplicationMessageService applicationMessage;
	private Session session;

	private String hostName;
	private int smtpPort;
	private String fromUserEmail;

	/***
	 * Default constructor
	 * 
	 * @throws GeneralException
	 *             The exception thrown when occurring any failure.
	 */
	public EmailService() throws GeneralException {
		super();
		try {
			// initializing services
			this.applicationConfiguration = ApplicationConfigurationService.getInstanceof();
			this.applicationMessage = ApplicationMessageService.getInstanceof();

			// getting properties values
			this.hostName = this.applicationConfiguration.getStringValue("sitisweb.email.host.name");
			this.smtpPort = this.applicationConfiguration.getIntegerValue("sitisweb.email.smtp.port");
			this.fromUserEmail = this.applicationConfiguration.getStringValue("sitisweb.email.from.user.email");

			// configuring properties of email service
			Properties props = new Properties();
			props.put("mail.smtp.host", this.hostName);
			props.put("mail.smtp.port", this.smtpPort);
			// props.put("mail.smtp.socketFactory.port", this.applicationConfiguration.getIntegerValue("sitisweb.email.smtp.port"));
			// props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			// props.put("mail.smtp.auth", "true");

			// configuring email session
			this.session = Session.getDefaultInstance(props);

		} catch (Exception e) {
			// configuring and throwing details of the actual exception
			GeneralException ge = new GeneralException();
			ge.setProcessingResult(new ProcessingResultEntity(Code.EMAIL_SERVICE_ERROR_INITIALIZING, this.applicationMessage.getMessage(Code.EMAIL_SERVICE_ERROR_INITIALIZING.name())));
			ge.setClassName(this.getClass().getName());
			ge.setMethodName("EmailService");
			ge.setExceptionOriginalMessage(e.getMessage()); // the original exception message

			// writing in the message log
			System.out.println(ge.getProcessingResult().toString());

			// throws general exception
			throw ge;
		}
	}

	/**
	 * Send an email
	 * 
	 * @throws GeneralException
	 */
	public void send(EmailTemplateEntity emailTemplate) throws GeneralException {
		try {
			// configuring message object to send email
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(this.fromUserEmail));
			for (String toEmail : emailTemplate.getToEmails()) {
				message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(toEmail));
			}
			message.setSubject(emailTemplate.getSubject());
			message.setContent(emailTemplate.getBody() + emailTemplate.getFooter(), "text/html; charset=utf-8");
			message.setSentDate(new Date());

			// sending email
			Transport.send(message);

		} catch (MessagingException e) {
			// configuring and throwing details of the actual exception
			GeneralException ge = new GeneralException();
			ge.setProcessingResult(new ProcessingResultEntity(Code.EMAIL_SERVICE_ERROR_SEND, this.applicationMessage.getMessage(Code.EMAIL_SERVICE_ERROR_SEND.name())));
			ge.setClassName(this.getClass().getName());
			ge.setMethodName("send");
			ge.setExceptionOriginalMessage(e.getMessage()); // the original exception message

			// throws general exception
			throw ge;
		}
	}
}
