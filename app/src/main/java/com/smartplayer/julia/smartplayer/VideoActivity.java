package com.smartplayer.julia.smartplayer;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.Locale;

public class VideoActivity extends AppCompatActivity {


    private final int REQ_CODE_SPEECH_OUTPUT = 143;
    private TextToSpeech ttobj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video2);
        VideoView videoView = findViewById(R.id.videoView);
        configureButton4();

        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        videoView.start();

        ttobj = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
            }
        });

    }
        private void configureButton4() {


            Button button4 = findViewById(R.id.button4);
            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    btnToOpenMic();
                }
            });
        }

        private void btnToOpenMic() {

            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Mów teraz...");

            try {
                startActivityForResult(intent, REQ_CODE_SPEECH_OUTPUT);

            } catch (ActivityNotFoundException tim) {

            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            ttobj.setLanguage(Locale.UK);
            VideoView videoView = findViewById(R.id.videoView);
            super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode) {
                case REQ_CODE_SPEECH_OUTPUT: {


                    ArrayList<String> voiceInText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    if (voiceInText.get(0).equals("Back") || voiceInText.get(0).equals("back"))
                        startActivity(new Intent(VideoActivity.this, PlayerActivity.class));
                    else if (voiceInText.get(0).equals("Stop") || voiceInText.get(0).equals("stop"))
                        videoView.stopPlayback();
                    else if (voiceInText.get(0).equals("Start") || voiceInText.get(0).equals("start"))
                        videoView.start();



                    break;
                }
            }
        }

}
