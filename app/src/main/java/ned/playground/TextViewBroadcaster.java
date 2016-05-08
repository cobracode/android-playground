package ned.playground;

import android.util.Log;
import android.widget.TextView;

/**
 * Created by ned on 5/6/16.
 */
public class TextViewBroadcaster implements IStatusBroadcaster {
    private static final String TAG = "TextViewBroadcaster";
    private TextView view;

    public TextViewBroadcaster() {
        Log.v(TAG, "Creating");
    }

    public TextViewBroadcaster(final TextView view) {
        Log.v(TAG, "Creating with TextView: " + view);
        this.view = view;
    }

    @Override
    public boolean broadcast(final String message) {
        boolean result = false;

        Log.d(TAG, "Appending to TextView: " + message);

        if (null != view) {
            view.append(message + "\n");
            result = true;
        } else {
            Log.w(TAG, "null TextView; cannot broadcast message: " + message);
        }

        return result;
    }
}
