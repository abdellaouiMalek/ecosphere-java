package tn.esprit.util;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import io.github.cdimascio.dotenv.Dotenv;

public class SmsService {

    public static String ACCOUNT_SID;
    public static String AUTH_TOKEN;
    public static String TWILIO_NUMBER;

    static {
        Dotenv dotenv = Dotenv.configure().load();

        ACCOUNT_SID = dotenv.get("TWILIO_ACCOUNT_SID");
        AUTH_TOKEN = dotenv.get("TWILIO_AUTH_TOKEN");
        TWILIO_NUMBER = dotenv.get("TWILIO_PHONE_NUMBER");

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public static void sendReservationConfirmationSMS(String toPhoneNumber, String messageBody) {
        Message message = Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(TWILIO_NUMBER),
                messageBody
        ).create();
    }
}