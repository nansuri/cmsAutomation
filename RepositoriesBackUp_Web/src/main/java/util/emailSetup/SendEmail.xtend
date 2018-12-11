package util.emailSetup

import constants.EmailSetup
import java.io.IOException
import java.util.Date
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.Multipart
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.AddressException
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart

class SendEmail {

	def static void attachTheAttachment(String host, String port, String userName, String password, String toAddress,
		String subject, String message, String[] attachFiles) throws AddressException, MessagingException {

		// sets SMTP server properties
		var Properties properties = new Properties()
		properties.put("mail.smtp.host", host)
		properties.put("mail.smtp.port", port)
		properties.put("mail.smtp.auth", "true")
		properties.put("mail.smtp.starttls.enable", "true")
		properties.put("mail.user", userName)
		properties.put("mail.password", password)
		// creates a new session with an authenticator
		var Authenticator auth = new Authenticator() {
			def override PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password)
			}
		}
		var Session session = Session.getInstance(properties, auth)

		// creates a new e-mail message
		var Message msg = new MimeMessage(session)
		msg.setFrom(new InternetAddress(userName))
		var InternetAddress[] toAddresses = #[new InternetAddress(toAddress)]
		msg.setRecipients(Message.RecipientType.TO, toAddresses)
		msg.setSubject(subject)
		msg.setSentDate(new Date())

		// creates message part
		var MimeBodyPart messageBodyPart = new MimeBodyPart()
		messageBodyPart.setContent(message, "text/html")

		// creates multi-part
		var Multipart multipart = new MimeMultipart()
		multipart.addBodyPart(messageBodyPart)

		// adds attachments
		if (attachFiles !== null && attachFiles.length > 0) {
			for (String filePath : attachFiles) {
				var MimeBodyPart attachPart = new MimeBodyPart()
				try {
					attachPart.attachFile(filePath)
				} catch (IOException ex) {
					ex.printStackTrace()
				}

				multipart.addBodyPart(attachPart)
			}
		}
		// sets the multi-part as e-mail's content
		msg.setContent(multipart)
		// sends the e-mail
		Transport.send(msg)
	}

	/** 
	 * Test sending e-mail with attachments
	 */
	/** 
	 * Test sending e-mail with attachments
	 */
	def static void sendMailWithAttachment(String reportName) {
		// SMTP info
		var String host = "smtp.gmail.com"
		var String port = "587"
		var String mailFrom = EmailSetup.MAIL_FROM.value
		var String password = EmailSetup.MAIL_FROM_PASSWORD.value

		// message info
		var String mailTo = EmailSetup.TO_EMAIL_ADDRESS.value
		var String subject = "Test Regression Report"
		var String message = "<h1>Test Regression Report</h1><br>Here i have attach the test regression reports.Please check on this.</br><br>Thank you</br><br>Best Regards</br><br>"+mailFrom+"</br>"

		// attachments
		var String[] attachFiles = newArrayOfSize(1)
		{
			val _wrVal_attachFiles = attachFiles
			_wrVal_attachFiles.set(0, EmailSetup.OUTPUT_ZIP_FILE.value+reportName)
		}

		try {
			attachTheAttachment(host, port, mailFrom, password, mailTo, subject, message, attachFiles)
			System.out.println("Email sent.")
		} catch (Exception ex) {
			System.out.println("Could not send email.")
			ex.printStackTrace()
		}

	}
}
