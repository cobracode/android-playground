package ned.playground;

import android.content.Context;

/**
 * Created by ned on 5/14/16.
 */
final class AppContext {
    private static Context appContext;

    private AppContext() {
        // Should not be instantiated
    }

    public static void setAppContext(final Context appContext) {
        // Should only be set once
        if (null == AppContext.appContext) {
            AppContext.appContext = appContext;
        }
    }

    public static Context get() {
        if (null == appContext) {
            throw new RuntimeException("App Context has not been set.");
        }

        return appContext;
    }
}
