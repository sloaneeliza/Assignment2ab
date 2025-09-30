package com.example.assignment2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus != null) {
                for (Object pdu : pdus) {
                    SmsMessage message = SmsMessage.createFromPdu((byte[]) pdu);
                    String body = message.getMessageBody();

                    Pattern pattern = Pattern.compile("\\b[A-Z]{1,5}\\b");
                    Matcher matcher = pattern.matcher(body);

                    if (matcher.find()) {
                        String ticker = matcher.group();
                        if (context instanceof MainActivity) {
                            ((MainActivity) context).addTickerFromSMS(ticker);
                        }
                    }
                }
            }
        }
    }
}

