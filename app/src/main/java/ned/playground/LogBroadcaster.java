package ned.playground;

import android.util.Log;

/**
 * Created by ned on 5/6/16.
 */
public class LogBroadcaster implements IStatusBroadcaster {
    private String TAG = "LogBroadcaster";

    public LogBroadcaster() {
        Log.v(TAG, "Creating");
    }

    @Override
    public boolean broadcast(final String message) {
        boolean result = false;

        Log.i(TAG, message);
        result = true;

        return result;
    }
}
