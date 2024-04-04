package tn.esprit.util;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsService {

    public static final String ACCOUNT_SID = "AC90d7491497b0fb786a9922f7975b6318";
    public static final String AUTH_TOKEN = "1bd640336ca7487bc42e33eb2f74ca9c";
    private static final String TWILIO_NUMBER = "+19497103991";

    static {
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