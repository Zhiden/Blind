package com.example.blind.blind;

import android.app.FragmentManager;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by drcov on 03.06.2018.
 */

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements TextToSpeech.OnInitListener {
    private List<Task> tasks = new ArrayList<>();
    private Context context;
    private FragmentManager fm;
    private TextToSpeech mTTS;


    public MyAdapter(Context context, FragmentManager fm) {
        this.fm = fm;
        this.context = context;
        Task main = new Task();
        main.setText("Список задач:");
        tasks.add(main);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.taskitem, parent, false);
        final ViewHolder h = new ViewHolder(v);
        mTTS = new TextToSpeech(context, this);
        v.setOnTouchListener(new OnSwipeTopAndBot(context) {
            @Override
            public void onSwipeTop() {
                mTTS.speak("Добавление задачи", TextToSpeech.QUEUE_FLUSH, null);

                showEditDialog();
            }

            @Override
            public void onClick() {
                int pos = h.getAdapterPosition();

                mTTS.speak(tasks.get(pos).getText(), TextToSpeech.QUEUE_FLUSH, null);
            }

            @Override
            public void onSwipeBottom() {
                if (tasks.size() > 1) {
                    int pos = h.getAdapterPosition();
                    mTTS.speak("Удаление задачи " + tasks.get(pos).getText(), TextToSpeech.QUEUE_FLUSH, null);
                    delete(tasks.get(pos), pos);
                } else if (tasks.size() == 1)
                    mTTS.speak("Удаление невозможно", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        return h;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task tas;
        tas = tasks.get(position);

        holder.setTv(tas.getText());
    }


    public void delete(Task t, int pos) {
        tasks.remove(t);
        App.getDbHelper().delete(t);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, tasks.size());

    }


    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void add(Task t) {
        tasks.add(t);
        notifyDataSetChanged();
    }

    public int getLastId() {
        if (tasks.size() > 0)
            return tasks.get(tasks.size() - 1).getId();
        return 0;
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
    }

    public void showEditDialog() {

        NewTaskDialog editNameDialog = new NewTaskDialog();
        editNameDialog.show(fm, "edittext");
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;


        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.taitem);

        }

        public void setTv(String tv) {
            this.tv.setText(tv);
        }
    }
}
