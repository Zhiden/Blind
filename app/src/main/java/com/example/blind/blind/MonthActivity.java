package com.example.blind.blind;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MonthActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private final String np = "ВЫБЕРИТЕ ДЕНЬ";
    private int mCount = 0;
    private List<String> month = new ArrayList<>();
    private TextToSpeech mTTS;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.month);

        month.add(0, "Январь");
        month.add(1, "Февраль");
        month.add(2, "Март");
        month.add(3, "Апрель");
        month.add(4, "Май");
        month.add(5, "Июнь");
        month.add(6, "Июль");
        month.add(7, "Август");
        month.add(8, "Сентябрь");
        month.add(9, "Октябрь");
        month.add(10, "Ноябрь");
        month.add(11, "Декабрь");

        final TextView mon = findViewById(R.id.textView3);
        mTTS = new TextToSpeech(this, this);

        mon.setText(month.get(0));


        RelativeLayout lay = findViewById(R.id.q11);

        lay.setOnTouchListener(new OnSwipeTouchListener(MonthActivity.this) {
            public void onSwipeRight() {
                if (mCount >= 0) {
                    String text = month.get(--mCount);
                    mon.setText(text);
                    mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }

            }

            public void onSwipeLeft() {
                if (mCount <= 11) {
                    String text = month.get(++mCount);
                    mon.setText(text);
                    mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }
            }

            public void onSwipeTop() {
                Intent sog = new Intent(MonthActivity.this, DayActivity.class);
                sog.putExtra("month", mon.getText());
                startActivity(sog);
                mTTS.speak(np, TextToSpeech.QUEUE_FLUSH, null);

            }

            public void onSwipeBottom() {
                Intent ver = new Intent(MonthActivity.this, MainActivity.class);
                startActivity(ver);
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
        mTTS.speak(month.get(0), TextToSpeech.QUEUE_FLUSH, null);
    }
}

