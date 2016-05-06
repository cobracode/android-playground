package ned.playground;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class DeviceInfo extends AppCompatActivity {
    private static final String TAG = "DeviceInfo Activity";
    private TextView txtStatus;
    private IStatusBroadcaster logBroadcaster = new LogBroadcaster();
    private IStatusBroadcaster textBroadcaster = new TextViewBroadcaster();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Log.i(TAG, "Created");
        logBroadcaster.broadcast("Created");
        textBroadcaster.broadcast("Created");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);

        mapXmlIds();
        textBroadcaster.setTextView(txtStatus);

        txtStatus.append("\nfinished creating!!!!!!!!!!!");
        //txtStatus.setInputType(InputType.TYPE_NULL);
    }

    private void mapXmlIds() {
        txtStatus = (TextView) findViewById(R.id.status_textView);
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, "Restarting");
        logBroadcaster.broadcast("Restarting");
        textBroadcaster.broadcast("Restarting");

        super.onRestart();
    }

    /**
     * Dispatch onStart() to all fragments.  Ensure any created loaders are
     * now started.
     */
    @Override
    protected void onStart() {
        Log.i(TAG, "Starting");
        logBroadcaster.broadcast("Starting");
        textBroadcaster.broadcast("Starting");
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
        Log.i(TAG, "Resuming");
        logBroadcaster.broadcast("Resuming");
        textBroadcaster.broadcast("Resuming");

        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "Stopping");
        logBroadcaster.broadcast("Stopping");
        textBroadcaster.broadcast("Stopping");

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "Destroying");
        logBroadcaster.broadcast("Destroying");
        textBroadcaster.broadcast("Destroying");

        super.onDestroy();
    }

    @Override
    public void onContentChanged() {
        Log.i(TAG, "Content has changed");
        logBroadcaster.broadcast("Content has changed");
        textBroadcaster.broadcast("Content has changed");

        super.onContentChanged();
    }

    @Override
    protected void onPostResume() {
        Log.i(TAG, "Post Resuming");
        logBroadcaster.broadcast("Post Resuming");
        textBroadcaster.broadcast("Post Resuming");

        super.onPostResume();
    }
}
