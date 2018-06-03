package com.example.blind.blind;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {


    private final String info = "ИНСТРУКЦИЯ";
    private final String np = "ВЫБЕРИТЕ МЕСЯЦ";
    private TextToSpeech mTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mTTS = new TextToSpeech(this, this);

        Button q1 = findViewById(R.id.start);
        q1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent q11 = new Intent(MainActivity.this, MonthActivity.class);
                startActivity(q11);
                mTTS.speak(np, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        Button q2 = findViewById(R.id.inst);
        q2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent q12 = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(q12);*/
                mTTS.speak(info, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    @Override
    public void onInit(int i) {
        if (i == TextToSpeech.SUCCESS) {


            Locale locale = new Locale("ru");

            int result = mTTS.setLanguage(locale);
            //int result = mTTS.setLanguage(Locale.getDefault());

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Извините, этот язык не поддерживается");
            } else {

            }
        } else {
            Log.e("TTS", "Ошибка!");
        }
        String start = "Здравствуйте, для начала использвоания нажмите в врехнюю часть экрана, для получения инструкции в нижнюю";
        mTTS.speak(start, TextToSpeech.QUEUE_FLUSH, null);
    }
}
