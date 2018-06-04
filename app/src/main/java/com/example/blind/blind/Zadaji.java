package com.example.blind.blind;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;


public class Zadaji extends AppCompatActivity implements EditNameDialogListener {
    final MyAdapter adapter = new MyAdapter(this, getFragmentManager());
    private RecyclerView rv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zada);

        String mon1 = getIntent().getStringExtra("month");
        TextView as1 = findViewById(R.id.textView2);
        as1.setText(mon1);

        findViewById(R.id.uprel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent q11 = new Intent(Zadaji.this, MonthActivity.class);
                startActivity(q11);
            }
        });

        int day1 = getIntent().getIntExtra("sad", -1);
        TextView as2 = findViewById(R.id.textView);
        as2.setText(String.valueOf(day1) + "ое");

        rv = findViewById(R.id.recview);

        rv.setAdapter(adapter);

        rv.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        PagerSnapHelper snapHelper = new PagerSnapHelper();

        snapHelper.attachToRecyclerView(rv);

        List<Task> tasks = App.getDbHelper().getTasks(day1, mon1);

        for (Task t :
                tasks) {
            adapter.add(t);
        }
        if (tasks.size() > 0)
            rv.smoothScrollToPosition(1);
    }


    @Override
    public void onFinishEditDialog(String inputText) {
        Task t = new Task();

        t.setId(adapter.getLastId() + 1);
        t.setDay(getIntent().getIntExtra("sad", -1));
        t.setMonth(getIntent().getStringExtra("month"));
        t.setText(inputText);
        App.getDbHelper().addTask(t);
        adapter.add(t);
        rv.smoothScrollToPosition(adapter.getItemCount() - 1);
    }
}
