package ned.playground;

import android.content.Context;

/**
 * Created by ned on 5/6/16.
 */
public class VoiceBroadcaster implements IStatusBroadcaster {
    private GlobalVoice globalVoice;

    public VoiceBroadcaster(final Context context) {

    }

    @Override
    public boolean broadcast(String message) {
        return false;
    }
}
