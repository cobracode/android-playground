package ned.playground;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class DeviceInfo extends AppCompatActivity {
    private static final String TAG = "DeviceInfo";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);
        Log.i(TAG, "constructed");
    }
}
