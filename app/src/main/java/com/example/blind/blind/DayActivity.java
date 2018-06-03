package com.example.blind.blind;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

public class DayActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    int dayscounter = 0;
    int sad = 1;
    private TextToSpeech mTTS;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.days);

        String mon = getIntent().getStringExtra("month");
        TextView as = findViewById(R.id.textView5);
        as.setText(mon);

        switch (mon) {
            case "Январь":
                dayscounter = 31;
                break;
            case "Февраль":
                dayscounter = 28;
                break;
            case "Март":
                dayscounter = 31;
                break;
            case "Апрель":
                dayscounter = 30;
                break;
            case "Май":
                dayscounter = 31;
                break;
            case "Июнь":
                dayscounter = 30;
                break;
            case "Июль":
                dayscounter = 31;
                break;
            case "Август":
                dayscounter = 31;
                break;
            case "Сентябрь":
                dayscounter = 30;
                break;
            case "Октябрь":
                dayscounter = 31;
                break;
            case "Ноябрь":
                dayscounter = 30;
                break;
            case "Декабрь":
                dayscounter = 31;
                break;
        }


        final TextView day = findViewById(R.id.textView4);
        mTTS = new TextToSpeech(this, this);
        day.setText(String.valueOf(sad));


        RelativeLayout lay2 = findViewById(R.id.w22);

        lay2.setOnTouchListener(new OnSwipeTouchListener(DayActivity.this) {
            public void onSwipeRight() {
                if (sad > 1) {
                    int buf = --sad;
                    day.setText(String.valueOf(buf));
                    mTTS.speak(String.valueOf(buf) + "ое", TextToSpeech.QUEUE_FLUSH, null);
                }

            }

            public void onSwipeLeft() {
                if (sad < dayscounter) {
                    int buf1 = ++sad;
                    day.setText(String.valueOf(buf1));
                    mTTS.speak(String.valueOf(buf1) + "ое", TextToSpeech.QUEUE_FLUSH, null);
                }
            }

            public void onSwipeTop() {
                Intent sog1 = new Intent(DayActivity.this, Zadaji.class);
                sog1.putExtra("sad", sad);
                sog1.putExtra("month", getIntent().getStringExtra("month"));
                startActivity(sog1);

            }

            public void onSwipeBottom() {
                Intent ver1 = new Intent(DayActivity.this, MonthActivity.class);
                startActivity(ver1);
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
        mTTS.speak(String.valueOf(sad) + "ое", TextToSpeech.QUEUE_FLUSH, null);
    }
}

