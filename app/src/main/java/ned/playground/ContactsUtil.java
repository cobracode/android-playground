package ned.playground;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by ned on 5/9/16.
 */
public class ContactsUtil {
    private static final String TAG = "ContactsUtil";
    private static final Map<String, String> contacts = new HashMap<String, String>();


    public static void getContactNames(final Set<String> contactNumbers, final ContactListener contactListener) {
        // Call ContactResolver to get the contact names
        // That will call ContactListener.receiveContactNames() with the results
        Log.d(TAG, "Creating async task to resolve contacts: " + contactNumbers);
        new ContactResolver(contactListener).execute(contactNumbers);
    }

    private static class ContactResolver extends AsyncTask<Set<String>, Void, Map<String, String>> {
        private static final String TAG = "ContactResolver";
        private ContactListener listener;
        private String uid;

        public ContactResolver(final ContactListener listener) {
            uid = ":" + this;
            Log.v(TAG + uid, "Constructing task with ContactListener " + listener);
            this.listener = listener;
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p/>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param resolvedContacts The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(final Map<String, String> resolvedContacts) {
            Log.d(TAG + uid, "Task finished. Result: " + resolvedContacts);
            listener.receiveContactNames(resolvedContacts);
        }

        /**
         * Runs on the UI thread before {@link #doInBackground}.
         *
         * @see #onPostExecute
         * @see #doInBackground
         */
        @Override
        protected void onPreExecute() {
            Log.d(TAG + uid, "Pre-executing task");
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param numbers The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Map<String, String> doInBackground(final Set<String>... numbers) {
            Log.d(TAG + uid, "Beginning execution with params: " + numbers);

            final Map<String, String> resolvedContacts = new HashMap<String, String>();

            if (0 < numbers.length) {
                for (final String number : numbers[0]) {
                    resolvedContacts.put(number, resolveContact(number));
                }
            }

            return resolvedContacts;
        }

        private String resolveContact(final String number) {
            Log.d(TAG + uid, "Resolving number: " + number);

            String name = "Someone";

            if (!number.isEmpty()) {
                // Check if contact is already stored
                if (contacts.containsKey(number)) {
                    name = contacts.get(number);
                    Log.i(TAG + uid, "Cached number " + number + " found in contact map: '" + name + "'");
                } else {
                    // Check for email address
                    if (number.contains("@")) {
                        name = "Email";
                    } else {
                        // Define the columns I want the query to return
                        final String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};

                        // Encode the phone number and build the filter URI
                        final Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));

                        Log.d(TAG + uid, "Contact Lookup URI: " + contactUri);

                        // Query for the URI
                        final Cursor cursor = AppContext.get().getContentResolver().query(contactUri, projection, null, null, null);

                        if (null != cursor) {
                            if (cursor.moveToFirst()) {
                                name = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
                                Log.d(TAG + uid, number + " resolved to contact \"" + name + "\".");
                            } else {
                                Log.w(TAG + uid, "Contact not found for number " + number);
                            }

                            cursor.close();

                        } else {
                            Log.w(TAG + uid, "Cursor returned null for number " + number);
                        }
                    }

                    contacts.put(number, name);
                }
            } else {
                Log.w(TAG + uid, "Skipping blank phone number");
            }

            return name;
        }
    }
}
