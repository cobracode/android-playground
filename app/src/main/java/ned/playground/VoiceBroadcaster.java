package ned.playground;

import android.content.Context;
import android.util.Log;

/**
 * Created by ned on 5/6/16.
 */
public class VoiceBroadcaster implements IStatusBroadcaster {
    private static final String TAG = "VoiceBroadcaster";

    private Context applicationContext;

    public VoiceBroadcaster(final Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public boolean broadcast(final String message) {
        Log.v(TAG, "Broadcasting: " + message);

        boolean result = false;

        GlobalVoice.getInstance(applicationContext).say(message);
        result = true;

        return result;
    }
}
