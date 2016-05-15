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
    private static final Map<String, String> contacts = new HashMap<String, String>();


    public static void getContactNames(final Set<String> contactNumbers, final ContactListener contactListener) {
        // Call ContactResolver to get the contact names
        // That will call ContactListener.receiveContactNames() with the results
        BroadcastManager.getInstance().broadcast("Creating async task");
        new ContactResolver(contactListener).execute(contactNumbers);
    }

    private static class ContactResolver extends AsyncTask<Set<String>, Void, Map<String, String>> {
        private static final String TAG = "ContactResolver";
        private ContactListener listener;

        public ContactResolver(final ContactListener listener) {
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
            BroadcastManager.getInstance().broadcast("Done executing. Result: " + resolvedContacts);
            super.onPostExecute(resolvedContacts);

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
            BroadcastManager.getInstance().broadcast("Pre-executing");
            super.onPreExecute();
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
            BroadcastManager.getInstance().broadcast("Beginning execution with params: " + numbers);

            final Map<String, String> resolvedContacts = new HashMap<String, String>();

            for (final String number : numbers[0]) {
                resolvedContacts.put(number, resolveContact(number));
            }

            return resolvedContacts;
        }

        private String resolveContact(final String number) {
            String name = "Someone";

            if (!number.isEmpty()) {
                // Check if contact is already stored
                if (contacts.containsKey(number)) {
                    name = contacts.get(number);
                    Log.d(TAG, "getContactName(): name \"" + name + "\" found in contact map.");
                } else {
                    // Define the columns I want the query to return
                    final String[] projection = new String[] { ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID };

                    // Encode the phone number and build the filter URI
                    final Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));

                    // Query for the URI
                    final Cursor cursor = AppContext.get().getContentResolver().query(contactUri, projection, null, null, null);

                    if (null != cursor) {
                        if (cursor.moveToFirst()) {
                            name = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
                            Log.d(TAG, "getContactName(): " + number + " resolved to contact \"" + name + "\".");
                        } else {
                            Log.w(TAG, "getContactName(): contact not found for number " + number + ".");
                        }

                        cursor.close();
                    }

                    contacts.put(number, name);
                }
            }

            return name;
        }
    }
}
