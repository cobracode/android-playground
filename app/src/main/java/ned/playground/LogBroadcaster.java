package ned.playground;

import android.util.Log;

/**
 * Created by ned on 5/6/16.
 */
public class LogBroadcaster implements IStatusBroadcaster {
    private static final String TAG = "LogBroadcaster";

    @Override
    public boolean broadcast(final String message) {
        boolean result = false;

        Log.i(TAG, message);
        result = true;

        return result;
    }
}
