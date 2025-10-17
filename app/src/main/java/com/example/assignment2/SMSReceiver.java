package com.example.assignment2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMSReceiver extends BroadcastReceiver {

    private static final Pattern TICKER_PATTERN =
            Pattern.compile("Ticker:\\s*<<([A-Za-z]+)>>", Pattern.CASE_INSENSITIVE);

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String fullMessage = "";

        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            String format = bundle.containsKey("format") ? bundle.getString("format") : null;

            if (pdus != null) {
                for (Object pdu : pdus) {
                    try {
                        SmsMessage sms;
                        if (format != null) {
                            sms = SmsMessage.createFromPdu((byte[]) pdu, format);
                        } else {
                            sms = SmsMessage.createFromPdu((byte[]) pdu);
                        }
                        fullMessage += sms.getMessageBody();
                    } catch (Exception e) {
                        try {
                            SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdu);
                            fullMessage += sms.getMessageBody();
                        } catch (Exception ex) {
                            // ignore
                        }
                    }
                }
            }
        }

        Matcher matcher = TICKER_PATTERN.matcher(fullMessage);
        Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if (!matcher.find()) {
            mainIntent.putExtra("sms_action", "no_valid_format");
            context.startActivity(mainIntent);
            Toast.makeText(context, "No valid watchlist entry found", Toast.LENGTH_LONG).show();
            return;
        }

        String ticker = matcher.group(1).toUpperCase();

        if (!ticker.matches("^[A-Z]+$")) {
            mainIntent.putExtra("sms_action", "invalid_ticker");
            mainIntent.putExtra("received_ticker", ticker);
            context.startActivity(mainIntent);
            Toast.makeText(context, "Invalid ticker received: " + ticker, Toast.LENGTH_LONG).show();
            return;
        }

        mainIntent.putExtra("sms_action", "valid_ticker");
        mainIntent.putExtra("new_ticker", ticker);
        context.startActivity(mainIntent);
        Toast.makeText(context, "Received ticker: " + ticker, Toast.LENGTH_SHORT).show();
    }
}