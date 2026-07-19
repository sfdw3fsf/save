package vn.vnptit.sapi.oneapp.ep;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.util.Base64;
import java.util.Properties;

@Component
public class SendEmail {

	@Value("${spring.httpservices.mail.host}")
	String mailServerHost;
	@Value("${spring.httpservices.mail.port}")
	String mailServerPort;
	@Value("${spring.httpservices.mail.protocol}")
	String protocol;
	@Value("${spring.httpservices.mail.from}")
	String from;
	@Value("${spring.httpservices.mail.username}")
	String username;
	@Value("${spring.httpservices.mail.password}")
	String password;
	public static String defaultSubject = "VNPT-ThongBao";
	public static String name = "VNPT";

	public String sendMailNotFile(String subject, String content, String mailTo, String mailCc, String mailBcc) {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", mailServerHost);
		props.put("mail.smtp.port", String.valueOf(mailServerPort));
		props.put("mail.transport.protocol", protocol);
		props.put("mail.smtp.starttls.enable", "false");
		props.put("mail.smtp.socketFactory.fallback", "true");
		props.put("mail.smtp.socketFactory.port", String.valueOf(mailServerPort));
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.ssl.checkserveridentity", "true");
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from, name));
			message.setSubject(subject != null && subject.trim().length() > 0 ? subject : defaultSubject);
			message.setText(content);

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
			if (mailCc != null && mailCc.trim().length() > 0) {
				message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(mailCc));
			}
			if (mailBcc != null && mailBcc.trim().length() > 0) {
				message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(mailBcc));
			}
			Transport.send(message);
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public String sendMailAttachFile(String subject, String content, String mailTo, String mailCc, String mailBcc, String base64, String fileName, String contentType) {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", mailServerHost);
		props.put("mail.smtp.port", String.valueOf(mailServerPort));
		props.put("mail.transport.protocol", protocol);
		props.put("mail.smtp.starttls.enable", "false");
		props.put("mail.smtp.socketFactory.fallback", "true");
		props.put("mail.smtp.socketFactory.port", String.valueOf(mailServerPort));
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.ssl.checkserveridentity", "true");
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from, name));
			message.setSubject(subject != null && subject.trim().length() > 0 ? subject : defaultSubject);

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
			if (mailCc != null && mailCc.trim().length() > 0) {
				message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(mailCc));
			}
			if (mailBcc != null && mailBcc.trim().length() > 0) {
				message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(mailBcc));
			}

			Multipart multipart = new MimeMultipart();

			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText(content);

			MimeBodyPart attachmentBodyPart = new MimeBodyPart();
			DataSource source = new ByteArrayDataSource(Base64.getDecoder().decode(base64), contentType);
			attachmentBodyPart.setDataHandler(new DataHandler(source));
			attachmentBodyPart.setFileName(fileName);

			multipart.addBodyPart(textBodyPart);
			multipart.addBodyPart(attachmentBodyPart);

			message.setContent(multipart);

			Transport.send(message);
			return "";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public String sendMailAttachFile(String subject, String content, String mailTo, String mailCc, String mailBcc, String base64, String fileName, String contentType,
                                     String imailServerHost, String imailServerPort, String iusername, String ipassword, String ifrom) {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", imailServerHost);
		props.put("mail.smtp.port", String.valueOf(imailServerPort));
		props.put("mail.transport.protocol", protocol);
        if (imailServerPort.equals("465")) {
            props.put("mail.smtp.starttls.enable", "false");
            props.put("mail.smtp.socketFactory.fallback", "false");
            props.put("mail.smtp.socketFactory.port", String.valueOf(imailServerPort));
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.ssl.checkserveridentity", "true");
            props.put("mail.smtp.ssl.enable", "false");
        }
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(iusername, ipassword);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(ifrom));
			message.setSubject(subject != null && subject.trim().length() > 0 ? subject : defaultSubject);

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
			if (mailCc != null && mailCc.trim().length() > 0) {
				message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(mailCc));
			}
			if (mailBcc != null && mailBcc.trim().length() > 0) {
				message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(mailBcc));
			}

			Multipart multipart = new MimeMultipart();

			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText(content);
			textBodyPart.setHeader("Content-Type", "text/html");
			MimeBodyPart attachmentBodyPart = new MimeBodyPart();
			DataSource source = new ByteArrayDataSource(Base64.getDecoder().decode(base64), contentType);
			attachmentBodyPart.setDataHandler(new DataHandler(source));
			attachmentBodyPart.setFileName(fileName);

			multipart.addBodyPart(textBodyPart);
			multipart.addBodyPart(attachmentBodyPart);

			message.setContent(multipart);

			Transport.send(message);
			return "";
		} catch (Exception e) {
            return e.toString();
		}
	}

	public String sendMailNotFile(String subject, String content, String mailTo, String mailCc, String mailBcc,
                                  String imailServerHost, String imailServerPort, String iusername, String ipassword, String ifrom) {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", imailServerHost);
		props.put("mail.smtp.port", String.valueOf(imailServerPort));
		props.put("mail.transport.protocol", protocol);
        if (imailServerPort.equals("465")) {
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.socketFactory.fallback", "true");
            props.put("mail.smtp.socketFactory.port", String.valueOf(imailServerPort));
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.ssl.checkserveridentity", "true");
        }

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(iusername, ipassword);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(ifrom));
			message.setSubject(subject != null && subject.trim().length() > 0 ? subject : defaultSubject);
			message.setText(content);
			message.setHeader("Content-Type", "text/html");

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
			if (mailCc != null && mailCc.trim().length() > 0) {
				message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(mailCc));
			}
			if (mailBcc != null && mailBcc.trim().length() > 0) {
				message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(mailBcc));
			}
			Transport.send(message);
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}

	public String sendMailHTML(String subject, String content, String mailTo, String mailCc, String mailBcc,
                              String imailServerHost, String imailServerPort, String iusername, String ipassword, String ifrom) {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", imailServerHost);
		props.put("mail.smtp.port", String.valueOf(imailServerPort));
		props.put("mail.transport.protocol", "smtp");
		if (imailServerPort.equals("465")) {
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.socketFactory.fallback", "true");
			props.put("mail.smtp.socketFactory.port", String.valueOf(imailServerPort));
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.ssl.checkserveridentity", "true");
		}

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(iusername, ipassword);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(ifrom));
			message.setSubject(subject != null && !subject.trim().isEmpty() ? subject : "No Subject");

			// Tạo phần nội dung HTML
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(content, "text/html; charset=utf-8");

			// Đóng gói vào Multipart
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);

			// Gán nội dung vào message
			message.setContent(multipart);

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
			if (mailCc != null && !mailCc.trim().isEmpty()) {
				message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(mailCc));
			}
			if (mailBcc != null && !mailBcc.trim().isEmpty()) {
				message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(mailBcc));
			}

			Transport.send(message);
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}

	public static void main(String[] args) {
		try {
			String content = "<p>a</p><p style=\"font-weight:bold;\"></p><table id=\"DS_NO\" style=\"width:100%;border-collapse:collapse;border:1px solid #bdbdbd;; border-collapse: collapse; border:1px solid #000; width: 100%; ; padding: 8px\" style=\"border-collapse: collapse; border:1px solid #000; width: 100%; padding: 8px\"><thead style=\"background-color:#035fcc;color:#fff;text-align:center;; border:1px solid #000; padding:8px;\" style=\"border:1px solid #000; padding:8px;\"><tr><th style=\"border:1px solid #bdbdbd;padding:8px;font-weight:bold;; border:1px solid #000; padding:8px;\" style=\"border:1px solid #000; padding:8px;\">THANHTOAN_ID</th><th style=\"border:1px solid #bdbdbd;padding:8px;font-weight:bold;; border:1px solid #000; padding:8px;\" style=\"border:1px solid #000; padding:8px;\">KY_CUOC</th></tr></thead><tbody><tr><td style=\"border:1px solid #bdbdbd;padding:6px;text-align:center;; border:1px solid #000; padding:8px;\" style=\"border:1px solid #000; padding:8px;\">...</td><td style=\"border:1px solid #bdbdbd;padding:6px;text-align:center;; border:1px solid #000; padding:8px;\" style=\"border:1px solid #000; padding:8px;\">...</td></tr></tbody></table><p><br>c</p><p style=\"font-weight:bold;\"></p><table id=\"DS_NHACNO\" style=\"width:100%;border-collapse:collapse;border:1px solid #bdbdbd;; border-collapse: collapse; border:1px solid #000; width: 100%; ; padding: 8px\" style=\"border-collapse: collapse; border:1px solid #000; width: 100%; padding: 8px\"><thead style=\"background-color:#035fcc;color:#fff;text-align:center;; border:1px solid #000; padding:8px;\" style=\"border:1px solid #000; padding:8px;\"><tr><th style=\"border:1px solid #bdbdbd;padding:8px;font-weight:bold;; border:1px solid #000; padding:8px;\" style=\"border:1px solid #000; padding:8px;\">TIEN_NO</th><th style=\"border:1px solid #bdbdbd;padding:8px;font-weight:bold;; border:1px solid #000; padding:8px;\" style=\"border:1px solid #000; padding:8px;\">NGAY_60</th></tr></thead><tbody><tr><td style=\"border:1px solid #bdbdbd;padding:6px;text-align:center;; border:1px solid #000; padding:8px;\" style=\"border:1px solid #000; padding:8px;\">...</td><td style=\"border:1px solid #bdbdbd;padding:6px;text-align:center;; border:1px solid #000; padding:8px;\" style=\"border:1px solid #000; padding:8px;\">...</td></tr></tbody></table><p><br>b</p>\"";
			new SendEmail().sendMailHTML("Test", content, "", null, null,
					"email.vnpt.vn", "465", "", "",
					"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}