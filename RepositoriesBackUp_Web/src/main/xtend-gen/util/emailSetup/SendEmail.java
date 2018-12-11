package util.emailSetup;

import constants.EmailSetup;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class SendEmail {
  public static void attachTheAttachment(final String host, final String port, final String userName, final String password, final String toAddress, final String subject, final String message, final String[] attachFiles) throws AddressException, MessagingException {
    Properties properties = new Properties();
    properties.put("mail.smtp.host", host);
    properties.put("mail.smtp.port", port);
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.user", userName);
    properties.put("mail.password", password);
    Authenticator auth = new Authenticator() {
      @Override
      public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
      }
    };
    Session session = Session.getInstance(properties, auth);
    Message msg = new MimeMessage(session);
    InternetAddress _internetAddress = new InternetAddress(userName);
    msg.setFrom(_internetAddress);
    InternetAddress _internetAddress_1 = new InternetAddress(toAddress);
    InternetAddress[] toAddresses = new InternetAddress[] { _internetAddress_1 };
    msg.setRecipients(Message.RecipientType.TO, toAddresses);
    msg.setSubject(subject);
    Date _date = new Date();
    msg.setSentDate(_date);
    MimeBodyPart messageBodyPart = new MimeBodyPart();
    messageBodyPart.setContent(message, "text/html");
    Multipart multipart = new MimeMultipart();
    multipart.addBodyPart(messageBodyPart);
    if (((attachFiles != null) && (attachFiles.length > 0))) {
      for (final String filePath : attachFiles) {
        {
          MimeBodyPart attachPart = new MimeBodyPart();
          try {
            attachPart.attachFile(filePath);
          } catch (final Throwable _t) {
            if (_t instanceof IOException) {
              final IOException ex = (IOException)_t;
              ex.printStackTrace();
            } else {
              throw Exceptions.sneakyThrow(_t);
            }
          }
          multipart.addBodyPart(attachPart);
        }
      }
    }
    msg.setContent(multipart);
    Transport.send(msg);
  }
  
  /**
   * Test sending e-mail with attachments
   */
  public static void sendMailWithAttachment(final String reportName) {
    String host = "smtp.gmail.com";
    String port = "587";
    String mailFrom = EmailSetup.MAIL_FROM.value();
    String password = EmailSetup.MAIL_FROM_PASSWORD.value();
    String mailTo = EmailSetup.TO_EMAIL_ADDRESS.value();
    String subject = "Test Regression Report";
    String message = (("<h1>Test Regression Report</h1><br>Here i have attach the test regression reports.Please check on this.</br><br>Thank you</br><br>Best Regards</br><br>" + mailFrom) + "</br>");
    String[] attachFiles = new String[1];
    {
      final String[] _wrVal_attachFiles = attachFiles;
      String _value = EmailSetup.OUTPUT_ZIP_FILE.value();
      String _plus = (_value + reportName);
      _wrVal_attachFiles[0] = _plus;
    }
    try {
      SendEmail.attachTheAttachment(host, port, mailFrom, password, mailTo, subject, message, attachFiles);
      System.out.println("Email sent.");
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception ex = (Exception)_t;
        System.out.println("Could not send email.");
        ex.printStackTrace();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
}
