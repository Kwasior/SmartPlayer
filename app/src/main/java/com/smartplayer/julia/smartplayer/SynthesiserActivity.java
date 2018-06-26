package com.smartplayer.julia.smartplayer;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class SynthesiserActivity extends AppCompatActivity {


    private final int REQ_CODE_SPEECH_OUTPUT = 143;
    private TextToSpeech ttobj;

    AudioManager audio;

    String pierwszaStrona = "This is first page ";
    String drugaStrona = "This is second page";
    String trzeciaStrona = "This is third page";
    private int i = 0;

    private String[] Strony = {pierwszaStrona, drugaStrona, trzeciaStrona};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synthesiser);

        configureButton3();
        ttobj = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
            }
        });
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        audio = (AudioManager) getSystemService(AUDIO_SERVICE);

    }

    private void configureButton3() {


        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnToOpenMic();
            }
        });
    }

    private void btnToOpenMic() {
        float maxVolume = (float)audio.getStreamMaxVolume((AudioManager.STREAM_MUSIC));
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Mów teraz...");
        if (ttobj.isSpeaking())
        audio.setStreamVolume(AudioManager.STREAM_MUSIC,(int)(maxVolume * 0.1),0);
        else audio.setStreamVolume(AudioManager.STREAM_MUSIC,100,0);
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_OUTPUT);

        } catch (ActivityNotFoundException tim) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        ttobj.setLanguage(Locale.UK);

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_OUTPUT: {


                ArrayList<String> voiceInText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                if (voiceInText.get(0).equals("Main") || voiceInText.get(0).equals("main") || voiceInText.get(0).equals("Siedem") || voiceInText.get(0).equals("siedem") || voiceInText.get(0).equals("7"))
                    startActivity(new Intent(SynthesiserActivity.this, MainActivity.class));
                else if (voiceInText.get(0).equals("Start") || voiceInText.get(0).equals("start") || voiceInText.get(0).equals("dwa") || voiceInText.get(0).equals("Dwa") || voiceInText.get(0).equals("2")) {

                    Toast.makeText(getApplicationContext(), Strony[i], Toast.LENGTH_SHORT).show();
                    ttobj.speak(Strony[i], TextToSpeech.QUEUE_ADD, null);
                } else if ((voiceInText.get(0).equals("Next") || voiceInText.get(0).equals("next") || voiceInText.get(0).equals("pięć") || voiceInText.get(0).equals("Pięć") || voiceInText.get(0).equals("5")) && i<2) {
                    i++;
                    Toast.makeText(getApplicationContext(), Strony[i], Toast.LENGTH_SHORT).show();
                    ttobj.speak(Strony[i], TextToSpeech.QUEUE_ADD, null);
                } else if ((voiceInText.get(0).equals("Back") || voiceInText.get(0).equals("cztery") || voiceInText.get(0).equals("Cztery") || voiceInText.get(0).equals("4")) && i > 0) {
                    i--;
                    Toast.makeText(getApplicationContext(), Strony[i], Toast.LENGTH_SHORT).show();
                    ttobj.speak(Strony[i], TextToSpeech.QUEUE_ADD, null);
                } else if (voiceInText.get(0).equals("Stop") || voiceInText.get(0).equals("stop") || voiceInText.get(0).equals("trzy") || voiceInText.get(0).equals("Trzy") || voiceInText.get(0).equals("3")) {

                    Toast.makeText(getApplicationContext(), Strony[i], Toast.LENGTH_SHORT).show();
                    ttobj.stop();
                }


                break;
            }
        }
    }
}

