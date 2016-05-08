package ned.playground;

import android.content.Context;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Singleton in charge of text-to-speech
 *
 * Created by ned on 5/6/16.
 */
public class GlobalVoice implements TextToSpeech.OnInitListener {
    public static final String TAG = "GlobalVoice";

    private static GlobalVoice instance;
    private static Context applicationContext;
    private static TextToSpeech tts;
    private static boolean ttsReady;
    private static final Queue<String> spokenTextQueue =  new LinkedList<String>();

    private GlobalVoice(final Context applicationContext) {
        Log.v(TAG, "Creating");

        this.applicationContext = applicationContext;
        initializeTts();
    }

    public static synchronized GlobalVoice getInstance(final Context applicationContext) {
        if (null == instance) {
            Log.d(TAG, "Not created yet; creating");
            instance = new GlobalVoice(applicationContext);
        } else {
            Log.v(TAG, "Returning existing instance");
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
            ttsReady = true;
            say("Voice Activated");

            // Say queued messages
            for (final String message : spokenTextQueue) {
                say(message);
            }

            spokenTextQueue.clear();
        } else {
            Log.e(TAG, "Error initializing text to speech. Status: " + status);
        }
    }

    public void say(final String text) {
        Log.d(TAG, "Saying: " + text);

        if (ttsReady) {
            getTts().speak(text, TextToSpeech.QUEUE_ADD, null, "");
        } else {
            Log.w(TAG, "Text-to-speech engine not ready; adding message to queue");
            spokenTextQueue.add(text);

            // tts may or may not be initializing;
            // start initialization if not
            if (null == tts) {
                initializeTts();
            }
        }
    }

    public void shutdownTts() {
        if (null == tts || !ttsReady) {
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
            ttsReady = false;
        }
    }
}
