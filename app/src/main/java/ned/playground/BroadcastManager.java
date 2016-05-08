package ned.playground;

import android.util.Log;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Singleton in charge of broadcasters
 *
 * Created by ned on 5/6/16.
 */
public class BroadcastManager {
    private static final String TAG = "BroadcastManager";

    private static BroadcastManager instance;
    private static final Set<IStatusBroadcaster> broadcasters = new HashSet<IStatusBroadcaster>();

    private BroadcastManager() {
        Log.d(TAG, "Creating");
    }

    public static synchronized BroadcastManager getInstance() {
        if (null == instance) {
            Log.d(TAG, "Creating new instance");
            instance = new BroadcastManager();
        } else {
            Log.v(TAG, "Returning existing instance");
        }

        return instance;
    }

    public void addBroadcaster(final IStatusBroadcaster broadcaster) {
        if (null == broadcaster) {
            Log.w(TAG, "Cannot add null broadcaster");
        } else {
            Log.d(TAG, "Adding broadcaster: " + broadcaster);
            broadcasters.add(broadcaster);
        }
    }

    public void addBroadcasters(final Collection<IStatusBroadcaster> broadcasters) {
        if (null == broadcasters) {
            Log.w(TAG, "Cannot add null broadcaster collection");
        } else {
            Log.d(TAG, "Adding " + broadcasters.size() + " broadcasters");
            broadcasters.addAll(broadcasters);
        }
    }

    public void removeBroadcaster(final IStatusBroadcaster broadcaster) {
        Log.d(TAG, "Removing broadcaster: " + broadcaster);
        broadcasters.remove(broadcaster);
    }

    public void removeAll() {
        Log.d(TAG, "Removing all broadcasters");
        broadcasters.clear();
    }

    public void broadcast(final String message) {
        Log.v(TAG, "Broadcasting: " + message);

        for (final IStatusBroadcaster broadcaster: broadcasters) {
            broadcaster.broadcast(message);
        }
    }
}
