package ned.playground;

import android.content.Context;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.util.Log;

/**
 * Created by ned on 5/6/16.
 */
public class Voice implements TextToSpeech.OnInitListener {
    public static final String TAG = "Voice";

    private BroadcastManager broadcastManager;

    private static TextToSpeech tts;

    public Voice(final Context context, final BroadcastManager bm) {
        broadcastManager = bm;
        broadcastManager.broadcast("Initializing text-to-speech engine");

        tts = new TextToSpeech(context, this);
    }

    /**
     * Called to signal the completion of the TextToSpeech engine initialization.
     *
     * @param status {@link TextToSpeech#SUCCESS} or {@link TextToSpeech#ERROR}.
     */
    @Override
    public void onInit(final int status) {
        if (TextToSpeech.SUCCESS == status) {
            final String msg = "Text-to-speech engine initialized";
            broadcastManager.broadcast(msg);
            say(msg);
        } else {
            Log.e(TAG, "Error initializing text to speech. Status: " + status);
        }
    }

    public void say(final String text) {
        tts.speak(text, TextToSpeech.QUEUE_ADD, null, "");
    }

    public void stop() {
        final String msg = "Stopping text-to-speech engine";
        Log.i(TAG, msg);
        say(msg);

        while (tts.isSpeaking()) {
            SystemClock.sleep(500);
        }

        tts.stop();
        tts.shutdown();

        broadcastManager.broadcast("Text-to-speech engine stopped");

        resetMembers();
    }

    private void resetMembers() {
        //broadcastManager = null;
        tts = null;
    }
}
