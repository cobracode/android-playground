package ned.playground;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class DeviceInfo extends AppCompatActivity {
    private static final String TAG = "DeviceInfo Activity";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Log.i(TAG, "Created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, "Restarting");
        super.onRestart();
    }

    /**
     * Dispatch onStart() to all fragments.  Ensure any created loaders are
     * now started.
     */
    @Override
    protected void onStart() {
        Log.i(TAG, "Starting");
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
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "Stopping");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "Destroying");
        super.onDestroy();
    }

    @Override
    public void onContentChanged() {
        Log.i(TAG, "Content has changed");
        super.onContentChanged();
    }

    @Override
    protected void onPostResume() {
        Log.i(TAG, "Post Resuming");
        super.onPostResume();
    }

    /**
     * This method is called whenever the user chooses to navigate Up within your application's
     * activity hierarchy from the action bar.
     * <p/>
     * <p>If a parent was specified in the manifest for this activity or an activity-alias to it,
     * default Up navigation will be handled automatically. See
     * {@link #getSupportParentActivityIntent()} for how to specify the parent. If any activity
     * along the parent chain requires extra Intent arguments, the Activity subclass
     * should override the method {@link #onPrepareSupportNavigateUpTaskStack(TaskStackBuilder)}
     * to supply those arguments.</p>
     * <p/>
     * <p>See <a href="{@docRoot}guide/topics/fundamentals/tasks-and-back-stack.html">Tasks and
     * Back Stack</a> from the developer guide and
     * <a href="{@docRoot}design/patterns/navigation.html">Navigation</a> from the design guide
     * for more information about navigating within your app.</p>
     * <p/>
     * <p>See the {@link TaskStackBuilder} class and the Activity methods
     * {@link #getSupportParentActivityIntent()}, {@link #supportShouldUpRecreateTask(Intent)}, and
     * {@link #supportNavigateUpTo(Intent)} for help implementing custom Up navigation.</p>
     *
     * @return true if Up navigation completed successfully and this Activity was finished,
     * false otherwise.
     */
    @Override
    public boolean onSupportNavigateUp() {
        Log.i(TAG, "Support Navigation Up");
        return super.onSupportNavigateUp();
    }
}
