package com.collegegroup.personaldiary.emailHelper;

import java.util.Random;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmailHelper {

	// generate verification code
	public String getRandom() {
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		return String.format("%06d", number);
	}

	// send email to the user email
	public String sendEmail(EmailRequest emailRequest) {
		
		final String senderEmail = "misbahulhaque2001@gmail.com";
		final String password = "";

		StringBuffer sb = new StringBuffer();

		String code = getRandom();
		
//		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

		try {
			
//			mimeMessageHelper.setFrom(senderEmail, senderName);
//			mimeMessageHelper.setTo(emailRequest.getRecipientEmail());
			
			// your host email smtp server details
            Properties pr = new Properties();
            pr.setProperty("mail.smtp.host", "smtp.gmail.com");
            pr.setProperty("mail.smtp.port", "587");
            pr.setProperty("mail.smtp.auth", "true");
            pr.setProperty("mail.smtp.starttls.enable", "true");
            pr.put("mail.smtp.socketFactory.port", "587");
            pr.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            //get session to authenticate the host email address and password
            Session session = Session.getInstance(pr, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, password);
                }
            });

            //set email message details
            Message message = new MimeMessage(session);

            //set from email address
            message.setFrom(new InternetAddress(senderEmail));

            //set to email address or destination email address
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailRequest.getRecipientEmail()));

			switch (emailRequest.getEmailType()) {

			case 0:
				sb.append("Your OTP (One Time Password) to register at Personal Diary is :-  <strong>").append(code)
						.append("</strong>.");

				sb.append("<br/>");
				sb.append("It is a system generated email. Please do not reply.");

				sb.append("<br/>");
				sb.append("<br/>");
				sb.append("Thank you.");

				sb.append("<br/>");
				sb.append("<br/>");
				sb.append("Regards,");
				sb.append("<br/>");
				sb.append("Team Personal Diary");
				
				//set email subject
                message.setSubject("Personal Diary - User Verification");
                
                break;

//				mimeMessageHelper.setSubject("Personal Diary - User Verification");
//				mimeMessageHelper.setText(sb.toString());

			case 1:
				sb.append("Your OTP (One Time Password) to reset password at Personal Diary is :-  <strong>")
						.append(code).append("</strong>.");

				sb.append("<br/>");
				sb.append("It is a system generated email. Please do not reply.");

				sb.append("<br/>");
				sb.append("<br/>");
				sb.append("Thank you.");

				sb.append("<br/>");
				sb.append("<br/>");
				sb.append("Regards,");
				sb.append("<br/>");
				sb.append("Team Personal Diary");
				
				//set email subject
                message.setSubject("Personal Diary - Reset Password");
                
                break;

//				mimeMessageHelper.setSubject("Personal Diary - Reset Password");
//				mimeMessageHelper.setText(sb.toString());

			}
			
			//set message text
            message.setContent(sb.toString(), "text/html;charset=UTF-8");
            
            Transport.send(message);
            
            return code;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";

	}

}
