package com.rajasree.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.auth.phone}")
    private String twilioPhoneNumber;

    public String generateOTP() {
        Random rand = new Random();
        return String.format("%06d", rand.nextInt(1000000));
    }

    public void sendOTP(String mobileNumber, String otp) {
        Twilio.init(accountSid, authToken);

        Message.creator(
                        new PhoneNumber(mobileNumber),
                        new PhoneNumber(twilioPhoneNumber),
                        "Your OTP is: " + otp)
                .create();
    }
}
