package ned.playground;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

public class DeviceInfo extends AppCompatActivity {
    private static final String TAG = "DeviceInfo Activity";

    // UI
    private TextView txtStatus;

    // Data



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
        status("Restarting activity");
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
        status("Starting activity");
        super.onStart();
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        status("Configuration changed: " + newConfig);
        super.onConfigurationChanged(newConfig);
    }

    private void mapXmlIds() {
        txtStatus = (TextView) findViewById(R.id.status_textView);
        Log.d(TAG, "XML IDs mapped");
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
        status("Resuming activity");
        //registerReceivers();
        super.onResume();
    }

    /**
     * Called after onResume()
     */
    @Override
    protected void onPostResume() {
        status("Post Resuming activity");
        super.onPostResume();
    }

    /**
     * Called when Activity loses foreground focus
     *
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        status("Pausing activity");
        //unregisterReceivers();
        super.onPause();
    }

    /**
     * Called when Activity is no longer visible on screen
     * After onPause()
     */
    @Override
    protected void onStop() {
        status("Stopping activity");
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
        status("Destroying Activity");
        getVoice().shutdownTts();
        getBroadcastManager().removeAll();

        super.onDestroy();
    }

    @Override
    public void onContentChanged() {
        status("Activity content has changed");
        super.onContentChanged();
    }

    private void initResources() {
        Log.i(TAG, "Initializing Activity resources");
        mapXmlIds();

        // Enable scrolling for the text view
        txtStatus.setMovementMethod(new ScrollingMovementMethod());

        initBroadcasters();

        status("Broadcasters have been initialized");
    }

    private void initBroadcasters() {
        final BroadcastManager bm = getBroadcastManager();
        bm.addBroadcaster(new LogBroadcaster());
        bm.addBroadcaster(new TextViewBroadcaster(txtStatus));
        bm.addBroadcaster(new VoiceBroadcaster(getApplicationContext()));
    }

    private void registerReceivers() {
        Log.d(TAG, "Registering BroadcastReceivers");
        //registerReceiver(smsListener, IntentFilter.x);
    }

    private void unregisterReceivers() {
        Log.d(TAG, "Unregistering BroadcastReceivers");
        //unregisterReceiver(smsListener);
    }

    private GlobalVoice getVoice() {
        return GlobalVoice.getInstance(getApplicationContext());
    }

    private BroadcastManager getBroadcastManager() {
        return BroadcastManager.getInstance();
    }

    private void status(final String text) {
        getBroadcastManager().broadcast(text);
    }
}
