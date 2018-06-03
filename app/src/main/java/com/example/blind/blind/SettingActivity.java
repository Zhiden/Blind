package com.example.blind.blind;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class SettingActivity extends AppCompatActivity {

    private int mCount = 0;
    private List<String> month = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
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
        TextView chet = findViewById(R.id.textViewS);
        chet.setText(month.get(0));


        FloatingActionButton sSchetCl = findViewById(R.id.floatingActionButton3);
        sSchetCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView chet = findViewById(R.id.textViewS);
                chet.setText(month.get(++mCount));
            }
        });
    }

    public void onSwipeLeft() {


    }
}

