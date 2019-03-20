package com.cyspan.tap.appconfig;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.cyspan.tap.user.model.UsersModel;

@Configuration
@PropertySource("classpath:mail.properties")
public class EmailSend {

	@Autowired
	Environment environment;

	static Logger logger = Logger.getLogger(EmailSend.class.getName());

	public static final String USERNAME = "devadmin@letztap.com";
	public static final String PASSWORD = "letZtapAdmin1.";
	public static final String SMTP_HOST = "smtp.office365.com";
	public static final String SMTP_PORT = "587";
	public static final String SMTP_AUTH = "true";
	public static final String SMTP_SSL_TRUST = "smtp.office365.com";
	public static final String SMTP_STARTTLS_ENABLE = "true";
	public static final String SMTP_DEBUG = "true";

	public Boolean mailSend(UsersModel user, String otp) throws IOException {

		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", SMTP_STARTTLS_ENABLE);
		props.put("mail.smtp.host", SMTP_HOST);
		props.put("mail.smtp.ssl.trust", SMTP_SSL_TRUST);
		props.put("mail.smtp.port", SMTP_PORT);
		props.put("mail.smtp.auth", SMTP_AUTH);
		props.put("mail.debug", SMTP_DEBUG);

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(USERNAME));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
			message.setSubject("OTP Generated");
			// message.setText("OTP,"+ otp);
			message.setContent("<p>Hi there! Welcome aboard!</p>" +

					"<p>We got a request to create a new TAP account with you mobile number " + user.getPhone()
					+ "  and email ID " + user.getEmail()
					+ ". Please use below OTP within 24 hours to complete the account creation.</p>" +

					"<p>Your one time password is:" + otp + "</p>" +

					"<p>TAP Care Team</p>", "text/html");
			Transport.send(message);
			return Boolean.TRUE;
		} catch (MessagingException e) {
			return Boolean.TRUE;
		}
	}

	public Boolean mailResend(String email, String otp) throws IOException {

		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", SMTP_STARTTLS_ENABLE);
		props.put("mail.smtp.host", SMTP_HOST);
		props.put("mail.smtp.ssl.trust", SMTP_SSL_TRUST);
		props.put("mail.smtp.port", SMTP_PORT);
		props.put("mail.smtp.auth", SMTP_AUTH);
		props.put("mail.debug", SMTP_DEBUG);

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(USERNAME));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setContent("<p>Hi there! Welcome aboard!</p>" +

					"<p>We got a request to create a new TAP account with  email ID " + email
					+ ". Please use below OTP within 24 hours to complete the account creation.</p>" +

					"<p>Your one time password is:" + otp + "</p>" +

					"<p>TAP Care Team</p>", "text/html");
			message.setSubject("OTP Generated");
			Transport.send(message);
			return Boolean.TRUE;

		} catch (MessagingException e) {
			e.printStackTrace();
			return Boolean.TRUE;
		}

	}

	public Boolean forgotPassword(String email, String otp) throws IOException {

		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", SMTP_STARTTLS_ENABLE);
		props.put("mail.smtp.host", SMTP_HOST);
		props.put("mail.smtp.ssl.trust", SMTP_SSL_TRUST);
		props.put("mail.smtp.port", SMTP_PORT);
		props.put("mail.smtp.auth", SMTP_AUTH);
		props.put("mail.debug", SMTP_DEBUG);

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(USERNAME));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setContent(
					"<p>Dear User</p>" + "<p>We received a request to reset the password of your TAP account " + email
							+ "  through your email address. </p>" +

							"<p>Your verification code is:" + otp + "</p>" +

							"<p>If you didn’t make this request then ignore the email; no changes have been made.</p>" +

							"<p>Sincerely Yours,</p>" + "<p>TAP account team</p>",
					"text/html");
			message.setSubject("Forgot Password");
			Transport.send(message);
			return Boolean.TRUE;
		} catch (MessagingException e) {
			return Boolean.TRUE;
		}
	}

	public void subscriptionAdminCredetials(String email, String organization, String username, String password)
			throws IOException {

		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", SMTP_STARTTLS_ENABLE);
		props.put("mail.smtp.host", SMTP_HOST);
		props.put("mail.smtp.ssl.trust", SMTP_SSL_TRUST);
		props.put("mail.smtp.port", SMTP_PORT);
		props.put("mail.smtp.auth", SMTP_AUTH);
		props.put("mail.debug", SMTP_DEBUG);

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(USERNAME));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setContent(this.getMessage(username, password), "text/html");
			message.setSubject("Tap Subscription");
			Transport.send(message);
			logger.info("email send success to : " + email);
		} catch (MessagingException e) {
			logger.error("email send failed");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public void sendCredentialsToSuperAdmin(String email, String username, String password) throws IOException {

		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", SMTP_STARTTLS_ENABLE);
		props.put("mail.smtp.host", SMTP_HOST);
		props.put("mail.smtp.ssl.trust", SMTP_SSL_TRUST);
		props.put("mail.smtp.port", SMTP_PORT);
		props.put("mail.smtp.auth", SMTP_AUTH);
		props.put("mail.debug", SMTP_DEBUG);

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(USERNAME));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setContent(this.getMessage(username, password), "text/html");
			message.setSubject("Tap Credentials");
			Transport.send(message);
			logger.info("email send success to : " + email);
		} catch (MessagingException e) {
			logger.error("email send failed");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	private String getMessage(String username, String password) {
		return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/> <title>[SUBJECT]</title> <style type=\"text/css\"> body{padding-top: 0 !important; padding-bottom: 0 !important; padding-top: 0 !important; padding-bottom: 0 !important; margin: 0 !important; width: 100% !important; -webkit-text-size-adjust: 100% !important; -ms-text-size-adjust: 100% !important; -webkit-font-smoothing: antialiased !important;}.tableContent img{border: 0 !important; display: block !important; outline: none !important;}a{color: #382F2E;}p, h1{color: #382F2E; margin: 0;}p{text-align: left; color: #999999; font-size: 14px; font-weight: normal; line-height: 19px;}a.link1{color: #382F2E;}a.link2{font-size: 16px; text-decoration: none; color: #ffffff;}h2{text-align: left; color: #222222; font-size: 19px; font-weight: normal;}div, p, ul, h1{margin: 0;}.bgBody{background: #ffffff;}.bgItem{background: #ffffff;}@media only screen and (max-width:480px){table[class=\"MainContainer\"], td[class=\"cell\"]{width: 100% !important; height: auto !important;}td[class=\"specbundle\"]{width: 100% !important; float: left !important; font-size: 13px !important; line-height: 17px !important; display: block !important; padding-bottom: 15px !important;}td[class=\"spechide\"]{display: none !important;}img[class=\"banner\"]{width: 100% !important; height: auto !important;}td[class=\"left_pad\"]{padding-left: 15px !important; padding-right: 15px !important;}}@media only screen and (max-width:540px){table[class=\"MainContainer\"], td[class=\"cell\"]{width: 100% !important; height: auto !important;}td[class=\"specbundle\"]{width: 100% !important; float: left !important; font-size: 13px !important; line-height: 17px !important; display: block !important; padding-bottom: 15px !important;}td[class=\"spechide\"]{display: none !important;}img[class=\"banner\"]{width: 100% !important; height: auto !important;}.font{font-size: 18px !important; line-height: 22px !important;}.font1{font-size: 18px !important; line-height: 22px !important;}}</style> <script type=\"colorScheme\" class=\"swatch active\">{\"name\":\"Default\",\"bgBody\":\"ffffff\",\"link\":\"382F2E\",\"color\":\"999999\",\"bgItem\":\"ffffff\",\"title\":\"222222\"}</script></head><body paddingwidth=\"0\" paddingheight=\"0\" style=\"padding-top: 0; padding-bottom: 0; padding-top: 0; padding-bottom: 0; background-repeat: repeat; width: 100% !important; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; -webkit-font-smoothing: antialiased;\" offset=\"0\" toppadding=\"0\" leftpadding=\"0\"> <table bgcolor=\"#ffffff\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"tableContent\" align=\"center\" style='font-family:Helvetica, Arial,serif;'> <tbody> <tr> <td> <table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" bgcolor=\"#ffffff\" class=\"MainContainer\"> <tbody> <tr> <td> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> <tbody> <tr> <td valign=\"top\" width=\"40\">&nbsp;</td><td> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> <tbody> <tr> <td height='75' class=\"spechide\"></td></tr><tr> <td class='movableContentContainer ' valign='top'> <div class=\"movableContent\" style=\"border: 0px; padding-top: 0px; position: relative;\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> <tbody> <tr> <td height=\"35\"></td></tr><tr><img src=\"http://letztap.com/img/logo.png\"></img></tr><tr> <td> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> <tbody> <tr> <td valign=\"top\" align=\"center\" class=\"specbundle\"> <div class=\"contentEditableContainer contentTextEditable\"> <div class=\"contentEditable\"> <p style='text-align:left;margin:0;font-family:Georgia,Time,sans-serif;font-size:26px;color:#222222;'><span class=\"specbundle2\"><span class=\"font1\">Welcome to&nbsp;</span></span><span style='text-align:left;margin:0;font-family:Georgia,Time,sans-serif;font-size:26px;color:#4286f4;'><span class=\"font\">letztap</span> </span></p></div></div></td><td valign=\"top\" class=\"specbundle\"> </td></tr></tbody> </table> </td></tr></tbody> </table> </div><div class=\"movableContent\" style=\"border: 0px; padding-top: 0px; position: relative;\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"> <tr> <td valign='top' align='center'> <div class=\"contentEditableContainer contentImageEditable\"> <div class=\"contentEditable\"> </div></div></td></tr></table> </div><div class=\"movableContent\" style=\"border: 0px; padding-top: 0px; position: relative;\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\"> <tr> <td height='55'></td></tr><tr> <td align='left'> <div class=\"contentEditableContainer contentTextEditable\"> <div class=\"contentEditable\" align='center'> <h2>username/password for login</h2> </div></div></td></tr><tr> <td height='15'> </td></tr><tr> <td align='left'> <div class=\"contentEditableContainer contentTextEditable\"> <div class=\"contentEditable\" align='center'> <p> The only app you will need to take and create any kind of Poll or Survey. Create custom Polls Share on your groups- Take part in Polls. Control and set all parameters of polling. \"A fun and interactive polling App.\" <a target='_blank' href='http://letztap.com/' class='link1'>Getting Started</a> section to get the most out of your new account. <br><br>Have questions? Get in touch with us via Facebook or Twitter, or email our support team. <br><br>Cheers, <br><span style='color:#222222;'>Letztap Administrator</span> </p></div></div></td></tr><tr> <td height='55'></td></tr><tr> <td align='center'> <table> <tr> <td align='center' bgcolor='#1A54BA' style='background:#69C374; padding:15px 18px;-webkit-border-radius: 4px; -moz-border-radius: 4px; border-radius: 4px;'> <div class=\"contentEditableContainer contentTextEditable\"> <div class=\"contentEditable\" align='center'> <a target='_blank' href='#' class='link2' style='color:#ffffff;'>"
				+ username
				+ "</a> </div></div></td><td align='center' bgcolor='#1A54BA' style='background:#69C374; padding:15px 18px;-webkit-border-radius: 4px; -moz-border-radius: 4px; border-radius: 4px;'> <div class=\"contentEditableContainer contentTextEditable\"> <div class=\"contentEditable\" align='center'> <a target='_blank' href='#' class='link2' style='color:#ffffff;'>"
				+ password
				+ "</a> </div></div></td></tr></table> </td></tr><tr> <td height='20'></td></tr></table> </div><div class=\"movableContent\" style=\"border: 0px; padding-top: 0px; position: relative;\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> <tbody> <tr> <td height='65'> </tr><tr> <td style='border-bottom:1px solid #DDDDDD;'></td></tr><tr> <td height='25'></td></tr><tr> <td> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> <tbody> <tr> <td valign=\"top\" class=\"specbundle\"> <div class=\"contentEditableContainer contentTextEditable\"> <div class=\"contentEditable\" align='center'> <p style='text-align:left;color:#CCCCCC;font-size:12px;font-weight:normal;line-height:20px;'> <span style='font-weight:bold;'>LETZTAP TECHNOLOGIES PRIVATE LIMITED</span> <br>Jeerak Builders and Developers 33/1833 A, Edappally Road Vennala Ernakulam Kerala 682028 <br><a target='_blank' href=\"[FORWARD]\">Forward to a friend</a><br><a target=\"_blank\" class='link1' class='color:#382F2E;' href=\"[UNSUBSCRIBE]\">Unsubscribe</a> <br><a target='_blank' class='link1' class='color:#382F2E;' href=\"[SHOWEMAIL]\">Show this email in your browser</a> </p></div></div></td><td valign=\"top\" width=\"30\" class=\"specbundle\">&nbsp;</td><td valign=\"top\" class=\"specbundle\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"> <tbody> <tr> <td valign='top' width='52'> <div class=\"contentEditableContainer contentFacebookEditable\"> <div class=\"contentEditable\"> <a target='_blank' href=\"#\"><img src=\"https://cdn4.iconfinder.com/data/icons/social-media-icons-the-circle-set/48/facebook_circle-512.png\" width='52' height='53' alt='facebook icon' data-default=\"placeholder\" data-max-width=\"52\" data-customIcon=\"true\"></a> </div></div></td><td valign=\"top\" width=\"16\">&nbsp;</td><td valign='top' width='52'> <div class=\"contentEditableContainer contentTwitterEditable\"> <div class=\"contentEditable\"> <a target='_blank' href=\"#\"><img src=\"https://cdn2.iconfinder.com/data/icons/minimalism/512/twitter.png\" width='52' height='53' alt='twitter icon' data-default=\"placeholder\" data-max-width=\"52\" data-customIcon=\"true\"></a> </div></div></td></tr></tbody> </table> </td></tr></tbody> </table> </td></tr><tr> <td height='88'></td></tr></tbody> </table> </div></td></tr></tbody> </table> </td><td valign=\"top\" width=\"40\">&nbsp;</td></tr></tbody> </table> </td></tr></tbody> </table> </td></tr></tbody> </table></body></html>";
	}
}
