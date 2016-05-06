package ned.playground;

import android.util.Log;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ned on 5/6/16.
 */
public class BroadcastManager {
    private static final String TAG = "BroadcastManager";

    private Set<IStatusBroadcaster> broadcasters = new HashSet<>();

    public void addBroadcaster(final IStatusBroadcaster broadcaster) {
        if (null == broadcaster) {
            Log.w(TAG, "Cannot add null broadcaster");
        } else {
            broadcasters.add(broadcaster);
        }
    }

    public void removeBroadcaster(final IStatusBroadcaster broadcaster) {
        broadcasters.remove(broadcaster);
    }

    public void broadcast(final String message) {
        for (final IStatusBroadcaster broadcaster: broadcasters) {
            broadcaster.broadcast(message);
        }
    }
}
