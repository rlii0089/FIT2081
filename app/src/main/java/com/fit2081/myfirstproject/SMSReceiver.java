package com.fit2081.myfirstproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {
    // Declare constants for the filter and key
    public static final String SMS_FILTER = "com.fit2081.myfirstproject.filter";
    public static final String SMS_MESSAGE_KEY = "com.fit2081.myfirstproject.key";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get the messages from the intent
        SmsMessage[] messages= Telephony.Sms.Intents.getMessagesFromIntent(intent);

        // Loop through all the messages
        for (int i = 0; i <messages.length; i++) {
            SmsMessage currentMessage = messages[i];

            // Get the message body
            String message = currentMessage.getDisplayMessageBody();

            // Create a new intent to send to MainActivity
            Intent msgIntent = new Intent();

            // Set the action to be the same as the filter in MainActivity
            msgIntent.setAction(SMS_FILTER);

            // Add the message to the intent
            msgIntent.putExtra(SMS_MESSAGE_KEY, message);

            // Send the broadcast to MainActivity
            context.sendBroadcast(msgIntent);
        }
    }
}
