package org.euro.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.twilio.Twilio;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendSMS {

    public void sendSMStoUser(String userPhone, String messageInSMS){
        final  String AUTH_SID= "ACe469777f1e1ed9a6e802701decd594b0";
        final  String AUTH_TOKEN = "6f434524bda46608341706d130fb32a1";
        Twilio.init(AUTH_SID,AUTH_TOKEN);
        Message.creator(new PhoneNumber(userPhone), new PhoneNumber("+12345754133"), messageInSMS).create();
    }


}
