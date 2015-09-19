package com.educationet.k12.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.ibm.watson.developer_cloud.language_translation.v2.LanguageTranslation;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;

import org.apache.commons.codec.digest.DigestUtils;

/**
 *  TTS Manager Class
 *  @author K12-Dev-Team
 *  @version 0.1
 */
public class TTSManager {
    private boolean ttsenable;
    private Context context;
    private TextToSpeech wtts= new TextToSpeech();
    private MediaPlayer mp = null;
    private Object pink=null;
    private String language=null;
    private Voice voice;
    private LanguageTranslation langTrans=new LanguageTranslation();
    private enum Langs {
        en,es,fr;
    }
    /**
     * Costructor
     * @param baseContext Caller Activity
     */
    public TTSManager(Context baseContext)
    {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(baseContext);
        ttsenable=SP.getBoolean("tts.enable", false);
        language=SP.getString("tts.lang", "en");
        this.context = baseContext;
        Log.d("TTS", "Status:" + ttsenable);
        wtts.setUsernameAndPassword("22feab3b-67b7-42bc-85e6-1e125a9d772e", "yGvVPqm9xL3u");
        langTrans.setUsernameAndPassword("f911c3c1-5603-431b-aa04-e1f81669993e","nHb0YFr2jZ4a");
        mp=new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        Langs lang=Langs.valueOf(language);
        switch (lang){
            case en: voice=Voice.EN_LISA;
                break;
            case es: voice=Voice.ES_ENRIQUE;
                break;
            case fr: voice=Voice.FR_RENEE;
                break;
            default: voice=Voice.EN_MICHAEL;
        }
        Log.d(this.getClass().getName(),voice.toString());
    }


    /**
     * Destroy TTS
     */
    public void destroy(){
        if(pink!=null){
            ((TTSSpeaker)pink).cancel(true);
        }
        Log.d("Activity", "TTS Destroyed");
    }

    /**
     * Speak the text
     * @param text Text
     */
    public void speak(String text){
        if(ttsenable) {
            if(pink!=null){
                ((TTSSpeaker)pink).cancel(true);
            }
            if(!text.isEmpty())
                pink=new TTSSpeaker().execute(text);
        }
    }
    private class TTSSpeaker extends AsyncTask<String,Void,Void> {
        public void takeInputStream(InputStream stream) {


        }

        @Override
        protected Void doInBackground(String... texts) {
            if(!language.equals("en"))
                texts[0]=langTrans.translate(texts[0],"en",language).getTranslations().get(0).getTranslation();
            String myquestionhash = DigestUtils.shaHex(texts[0]+language);
            File file = new File(context.getCacheDir(), myquestionhash+".flac");
            Log.d(this.getClass().getName(), texts[0]+myquestionhash);
            if(!file.exists()) {
                Log.d("TTS Manager","Download data from watson");
                InputStream stream = wtts.synthesize(texts[0], voice, "audio/flac");
                try {
                        OutputStream output = new FileOutputStream(file);
                        byte[] buffer = new byte[16 * 1024]; // or other buffer size
                        int read;

                        while ((read = stream.read(buffer)) != -1) {
                            output.write(buffer, 0, read);
                        }
                        output.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try{
                mp.reset();
                File outFile = new File(context.getCacheDir(), myquestionhash+".flac");
                mp.setDataSource(outFile.getAbsolutePath());
                mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer player) {
                        mp.start();
                    }
                });
                mp.prepareAsync();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}