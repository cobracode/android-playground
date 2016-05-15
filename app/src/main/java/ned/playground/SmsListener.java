package ned.playground;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SmsListener extends BroadcastReceiver implements ContactListener {
    private static final String TAG = "SmsListener";

    private static final String listenIntent = Telephony.Sms.Intents.SMS_RECEIVED_ACTION;

    private static List<Text> latestTexts;

    public SmsListener() {
    }

    /**
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.i(TAG, "Received one or more text messages");

        // Screen intent
        if (listenIntent.equals(intent.getAction())) {
            // Pass texts (intent) to text processor
            processTexts(intent);
        } else {
            Log.d(TAG, "Intent didn't match '" + listenIntent + "'. Instead: " + intent.getAction());
        }
    }


    /**
     *  texts come in
         get the list of texts
         #, text string


         send the list of unique #s to ContactsUtil
         when all are resolved, it will send a list of pairs <#, name> back


         when get list of <#, name>,
         associate them to their texts:
         replace # with name in text, and push new string to broadcaster

     * @param intent
     */
    private void processTexts(final Intent intent) {
        // Get list of texts
        latestTexts = getTexts(intent);

        // Get unique phone numbers from texts
        final Set<String> phoneNumbers = getPhoneNumbers(latestTexts);

        // Kick off the task to get the contact names
        ContactsUtil.getContactNames(phoneNumbers, this);
    }

    private static Set<String> getPhoneNumbers(final List<Text> texts) {
        final Set<String> numbers = new HashSet<String>();

        for (final Text text : texts) {
            numbers.add(text.getPhoneNumber());
        }

        return numbers;
    }

    private static List<Text> getTexts(final Intent intent) {
        List<Text> texts = new LinkedList<Text>();

        for (final SmsMessage message : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
            if (null != message) {
                final String body = message.getDisplayMessageBody()
                        .replaceAll("\\t", "")
                        .replaceAll("\\n", "");

                texts.add(new Text(message.getDisplayOriginatingAddress(), body));
            } else {
                Log.d(TAG, "Skipping null SmsMessage");
            }
        }

        return texts;
    }

//    @Override
//    public void processContact(final String contactNumber, final String contactName) {
//
//    }

    @Override
    public void receiveContactNames(final Map<String, String> contacts) {
        Log.d(TAG, "Received contacts: " + contacts);

        // Set contact names in texts
        updateTextContacts(contacts);

        // Read 'em
        readTexts();
    }

    private void updateTextContacts(final Map<String, String> contacts) {
        for (final Text text : latestTexts) {
            text.setName(contacts.get(text.getPhoneNumber()));
        }
    }

    private void readTexts() {
        final BroadcastManager bm = BroadcastManager.getInstance();

        for (final Text text : latestTexts) {
            String prefix = text.getName() + " says ";

            if (text.getName().equals("Email")) {
                prefix = "Email: ";
            }

            bm.broadcast(prefix + text.getMessage());
        }
    }
}
