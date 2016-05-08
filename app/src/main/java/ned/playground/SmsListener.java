package ned.playground;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SmsListener extends BroadcastReceiver {
    private static final String TAG = "SmsListener";

    public SmsListener() {
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.d(TAG, "Received one or more text messages");

        // Screen intent
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            readTexts(getTexts(intent));
        } else {
            Log.d(TAG, "Intent didn't match SMS_RECEIVED_ACTION. Instead: " + intent.getAction());
        }
    }

    private void readTexts(final List<String> texts) {
        final BroadcastManager bm = BroadcastManager.getInstance();

        for (final String text : texts) {
            bm.broadcast(text);
        }
    }


    private List<String> getTexts(final Intent intent) {
        List<String> texts = new ArrayList<String>();

        for (final SmsMessage message : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
            if (null != message) {
                final String phoneNumber = message.getDisplayOriginatingAddress();
                final String body = message.getDisplayMessageBody();

                final String text = phoneNumber + " says " + body;

                Log.d(TAG, text);

                texts.add(text);
            } else {
                Log.d(TAG, "Skipping null SmsMessage");
            }
        }

        return texts;
    }
}
