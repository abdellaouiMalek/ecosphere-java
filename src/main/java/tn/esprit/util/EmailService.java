package tn.esprit.util;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.Recipient;
import com.mailersend.sdk.exceptions.MailerSendException;
import com.mailersend.sdk.emails.Email;

public class EmailService {

    private static final String API_KEY = "mlsn.bf3b48b41c74c58e4cb0b749c130d9b610175432fd81f4dbcc4878b0e9b88580";
    private static final String SENDER_EMAIL = "ecosphere@trial-vywj2lpz9mjg7oqz.mlsender.net";
    public void sendEmail(String toEmail, String subject, String body) {
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
