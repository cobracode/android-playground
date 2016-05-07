package ned.playground;

import android.content.Context;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.util.Log;

/**
 * Created by ned on 5/6/16.
 */
public class GlobalVoice implements TextToSpeech.OnInitListener {
    public static final String TAG = "GlobalVoice";

    private static GlobalVoice instance;
    private static Context applicationContext;
    private static TextToSpeech tts;

    private GlobalVoice(final Context applicationContext) {
        Log.i(TAG, "Creating");

        this.applicationContext = applicationContext;
        initializeTts();
    }

    public static synchronized GlobalVoice getInstance(final Context applicationContext) {
        if (null == instance) {
            Log.i(TAG, "Not created yet; creating");
            instance = new GlobalVoice(applicationContext);
        } else {
            Log.i(TAG, "Returning existing instance");
        }

        return instance;
    }

    private TextToSpeech getTts() {
        if (null == tts) {
            initializeTts();
        }

        return tts;
    }

    private void initializeTts() {
        Log.i(TAG, "Initializing text-to-speech");
        tts = new TextToSpeech(applicationContext, this);
    }

    /**
     * Called to signal the completion of the TextToSpeech engine initialization.
     *
     * @param status {@link TextToSpeech#SUCCESS} or {@link TextToSpeech#ERROR}.
     */
    @Override
    public void onInit(final int status) {
        if (TextToSpeech.SUCCESS == status) {
            say("Voice Activated");
        } else {
            Log.e(TAG, "Error initializing text to speech. Status: " + status);
        }
    }

    public void say(final String text) {
        Log.d(TAG, "Saying: " + text);
        getTts().speak(text, TextToSpeech.QUEUE_ADD, null, "");
    }

    public void shutdownTts() {
        if (null == tts) {
            Log.w(TAG, "Text-to-speech engine already shutdown");
        } else {
            final String msg = "Deactivating voice";
            Log.i(TAG, msg);
            say(msg);

            while (tts.isSpeaking()) {
                SystemClock.sleep(250);
            }

            tts.stop();
            tts.shutdown();

            tts = null;
        }
    }
}
