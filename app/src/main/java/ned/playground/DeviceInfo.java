package ned.playground;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DeviceInfo extends AppCompatActivity {
    private static final String TAG = "DeviceInfo Activity";
    private TextView txtStatus;
    private BroadcastManager broadcastManager = new BroadcastManager();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        broadcastManager.addBroadcaster(new LogBroadcaster(TAG));
        broadcastManager.broadcast("Created");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);

        mapXmlIds();
        broadcastManager.addBroadcaster(new TextViewBroadcaster(txtStatus));
    }

    private void mapXmlIds() {
        txtStatus = (TextView) findViewById(R.id.status_textView);
    }

    @Override
    protected void onRestart() {
        broadcastManager.broadcast("Restarting");
        super.onRestart();
    }

    /**
     * Dispatch onStart() to all fragments.  Ensure any created loaders are
     * now started.
     */
    @Override
    protected void onStart() {
        broadcastManager.broadcast("Starting");
        super.onStart();
    }

    /**
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
        broadcastManager.broadcast("Resuming");
        super.onResume();
    }

    @Override
    protected void onStop() {
        broadcastManager.broadcast("Stopping");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        broadcastManager.broadcast("Destroying");
        super.onDestroy();
    }

    @Override
    public void onContentChanged() {
        broadcastManager.broadcast("Content has changed");
        super.onContentChanged();
    }

    @Override
    protected void onPostResume() {
        broadcastManager.broadcast("Post Resuming");
        super.onPostResume();
    }
}
