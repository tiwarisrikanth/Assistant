package com.srikanth.android.assistant;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.provider.Telephony;
import android.speech.RecognizerIntent;
import android.text.Layout;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView text;
    Button auto, mTTS, textView;
    LinearLayout ll;
    Random r1 = new Random();

        int[] images = {
                R.drawable.shot,
                R.drawable.csgo,
                R.drawable.bullet,
                R.drawable.csgoo,
                R.drawable.csgooo,
                R.drawable.gun,
                R.drawable.cslogo
        };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll = (LinearLayout) findViewById(R.id.ll);

        textView = (Button) findViewById(R.id.tv);
        auto = findViewById(R.id.auto);
        mTTS = findViewById(R.id.mTTs);
        text = (TextView) findViewById(R.id.text);

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView.setBackgroundColor(getResources().getColor(R.color.white));
                Toast.makeText(MainActivity.this, "You have been navigated to Speech to Text Activity", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, Speech.class);
                startActivity(i);

            }
        });

        mTTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.setBackgroundColor(getResources().getColor(R.color.white));
                Intent nav = new Intent(MainActivity.this, TexttoSpeech.class);
                startActivity(nav);
            }
        });

        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auto.setBackgroundColor(getResources().getColor(R.color.white));

                Intent voice = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());
                voice.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        "Speak to open");
                startActivityForResult(voice, 1);

            }
        });

    }

    public int getRandomCard(){
        return this.images[r1.nextInt(6)];
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

            ArrayList<String> arrayList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            assert arrayList != null;
            if (arrayList.get(0).equals("open camera")) {

                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(camera);

            }

            if (arrayList.get(0).equals("open spotify")) {

                Intent spotify = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);
                startActivity(spotify);

            }

            if (arrayList.get(0).equals("video")) {

                Intent video = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivity(video);

            }

            if (arrayList.get(0).equals("settings")) {

                Intent video = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(video);

            }
            if (arrayList.get(0).equals("change")) {

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), getRandomCard());
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                try {
                    wallpaperManager.setBitmap(bitmap);
                    Toast.makeText(this, "Done, Check your Wallpaper", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }

            }

        }

    }

}

