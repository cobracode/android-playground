package ned.playground;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class DeviceInfo extends AppCompatActivity {
    private static final String TAG = "DeviceInfo Activity";

    private TextView txtStatus;
    private BroadcastManager broadcastManager = new BroadcastManager();

    /**
     * Creating/initialize app resources
     * at the start of the Activity lifecycle
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Log.i(TAG, "Creating");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);

        initResources();
    }


    /**
     * Called after onStop() when user navigates back to Activity
     */
    @Override
    protected void onRestart() {
        Log.i(TAG, "Restarting");
        super.onRestart();
    }


    /**
     * Called after onCreate() and onRestart()
     *
     * Dispatch onStart() to all fragments.  Ensure any created loaders are
     * now started.
     */
    @Override
    protected void onStart() {
        Log.i(TAG, "Starting");
        super.onStart();
    }



    private void mapXmlIds() {
        txtStatus = (TextView) findViewById(R.id.status_textView);
        Log.i(TAG, "XML IDs mapped");
    }


    /**
     * Called after onStart()
     *
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        Log.i(TAG, "Resuming");
        GlobalVoice.getInstance(getApplicationContext()).say("Resuming activity");
        super.onResume();
    }


    /**
     * Called after onResume()
     */
    @Override
    protected void onPostResume() {
        Log.i(TAG, "Post Resuming");
        super.onPostResume();
    }

    /**
     * Called when Activity loses foreground focus
     *
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        Log.i(TAG, "Pausing");
        super.onPause();
    }

    /**
     * Called when Activity is no longer visible on screen
     * After onPause()
     */
    @Override
    protected void onStop() {
        GlobalVoice.getInstance(getApplicationContext()).say("Stopping activity");
        Log.i(TAG, "Stopping");
        super.onStop();
    }

    /**
     * Called at the very end of the Activity lifecycle.
     * After onStop()
     *
     * Release resources here.
     */
    @Override
    protected void onDestroy() {
        Log.i(TAG, "Destroying Activity. This should be the final call, once.");
        //globalVoice.close();
        GlobalVoice.getInstance(getApplicationContext()).shutdownTts();
        super.onDestroy();
    }

    @Override
    public void onContentChanged() {
        Log.i(TAG, "Content has changed");
        super.onContentChanged();
    }

    private void initResources() {
        Log.i(TAG, "Initializing Activity resources");
        mapXmlIds();

        //globalVoice = GlobalVoice.getInstance(getApplicationContext());
        getVoice().say("Activity has been initialized");

//        // Activity is now initialized. Do stuff
//        globalVoice = GlobalVoice.getInstance(getApplicationContext());
//        broadcastManager.addBroadcaster(new LogBroadcaster(TAG));
//
//        broadcastManager.addBroadcaster(new TextViewBroadcaster(txtStatus));
//        //globalVoice = new GlobalVoice(getApplicationContext(), broadcastManager);
//        //GlobalVoice.getInstance(getApplicationContext()).say("hi there");
//        //globalVoice.initialize();
//        globalVoice.say("hey there ned man");
//
//        broadcastManager.broadcast("Created");
    }

    private GlobalVoice getVoice() {
        return GlobalVoice.getInstance(getApplicationContext());
    }
}
