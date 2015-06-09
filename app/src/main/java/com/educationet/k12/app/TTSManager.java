package com.educationet.k12.app;

import java.util.Locale;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;

/**
 *  TTS Manager Class
 *  @author K12-Dev-Team
 *  @version 0.1
 */
public class TTSManager{
    private boolean ttsenable;
    private TextToSpeech myTTS;
    private boolean readyToSpeak = false;
    private Context context;

    /**
     * Costructor
     * @param baseContext Caller Activity
     */
    public TTSManager(Context baseContext)
    {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(baseContext);
        ttsenable=SP.getBoolean("tts.enable",false);
        this.context = baseContext;
        Log.d("TTS","Status:"+ttsenable);
    }

    /**
     * Init the TTS system
     */
    public void initOrInstallTTS(){
        if(ttsenable) {
            myTTS = new TextToSpeech(context, new OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        myTTS.setLanguage(Locale.US);
                        readyToSpeak = true;
                    } else
                        installTTS();
                }
            });
        }
        else {myTTS=null;}
    }

    /**
     * Install the TTS system
     */
    private void installTTS()
    {
        Intent installIntent = new Intent();
        installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
        context.startActivity(installIntent);
    }

    /**
     * Destroy TTS
     */
    public void destroy(){
        if(myTTS != null) {
            myTTS.stop();
            myTTS.shutdown();
            Log.d("Activity", "TTS Destroyed");
        }
    }

    /**
     * Speak the text
     * @param text Text
     */
    public void speak(String text){
        if ((readyToSpeak)&&(ttsenable=true))
            myTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }


}