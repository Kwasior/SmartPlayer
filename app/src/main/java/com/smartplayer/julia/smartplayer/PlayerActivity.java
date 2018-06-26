package com.smartplayer.julia.smartplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.*;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import 	java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import yogesh.firzen.filelister.FileListerDialog;
import yogesh.firzen.filelister.OnFileSelectedListener;

public class PlayerActivity extends AppCompatActivity {

    private final int REQ_CODE_SPEECH_OUTPUT = 143;
    private TextToSpeech ttobj;
    private TextView pokazTekst;

   // FileListerDialog fileListerDialog = FileListerDialog.createFileListerDialog(getApplicationContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        configureMicButton();
        ttobj = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
            }
        });

    }


    private void configureMicButton() {


       ImageButton micButton = (ImageButton) findViewById(R.id.micButton);
        micButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnToOpenMic();
            }
        });
    }



    private void btnToOpenMic(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Mów teraz...");

        try{
            startActivityForResult(intent, REQ_CODE_SPEECH_OUTPUT);
        }
        catch (ActivityNotFoundException tim)
        {

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
     ttobj.setLanguage(Locale.UK);

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
        case REQ_CODE_SPEECH_OUTPUT: {


            ArrayList<String> voiceInText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            if (voiceInText.get(0).equals("Main") || voiceInText.get(0).equals("main") || voiceInText.get(0).equals("Pięć") || voiceInText.get(0).equals("pięć") || voiceInText.get(0).equals("5"))
                startActivity(new Intent(PlayerActivity.this, MainActivity.class));
            else if(voiceInText.get(0).equals("Start") || voiceInText.get(0).equals("start") || voiceInText.get(0).equals("dwa") || voiceInText.get(0).equals("Dwa") || voiceInText.get(0).equals("2"))
                startActivity(new Intent(PlayerActivity.this,VideoActivity.class ));

            break;
        }
    }
}
}



