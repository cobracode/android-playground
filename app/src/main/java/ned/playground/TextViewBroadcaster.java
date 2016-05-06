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

    }

    public TextViewBroadcaster(final TextView view) {
        this.view = view;
    }

    public void setTextView(final TextView view) {
        this.view = view;
    }

    @Override
    public boolean broadcast(final String message) {
        boolean result = false;

        if (null != view) {
            view.append(message);
            result = true;
        } else {
            Log.w(TAG, "null TextView; cannot broadcast message: " + message);
        }

        return result;
    }
}
