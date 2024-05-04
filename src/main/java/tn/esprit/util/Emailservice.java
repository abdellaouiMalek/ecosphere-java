package tn.esprit.util;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.Recipient;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.emails.Email;

public class Emailservice {

    private static final String API_KEY = "mlsn.c76d4f9c86d6fd3a0dda268cd4064cf0325aec20a180dd60264afbf4883af51b";
    private static final String SENDER_EMAIL = "ecosphere@trial-jy7zpl93yp3l5vx6.mlsender.net";
    public void sendEmail(String toEmail, String subject, String body) {
        System.out.println("Sending email to: " + toEmail);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);

        if (toEmail == null || toEmail.isEmpty()) {
            System.err.println("Failed to send email: recipient email address is null or empty");
            return;
        }

        Email email = new Email();
        email.setFrom("Ecosphere", SENDER_EMAIL);
        Recipient r = new Recipient("test", toEmail);
        email.AddRecipient(r);
        email.setSubject(subject);
        email.setHtml(body);

        MailerSend ms = new MailerSend();
        ms.setToken(API_KEY);
        try {

            MailerSendResponse response = ms.emails().send(email);
            System.out.println(response.messageId);
        } catch (MailerSendException e) {
            e.printStackTrace();
        }
    }

}