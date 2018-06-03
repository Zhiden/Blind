package com.example.blind.blind;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by drcov on 03.06.2018.
 */

public class NewTaskDialog extends DialogFragment implements TextView.OnEditorActionListener {
    private EditText etNewTask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newtask_dialog, container);
        etNewTask = view.findViewById(R.id.etNewTask);
        getDialog().setTitle("Введите новую задачу");

        etNewTask.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        etNewTask.setOnEditorActionListener(this);
        return view;
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (EditorInfo.IME_ACTION_DONE == i) {
            // Return input text to activity
            EditNameDialogListener activity = (EditNameDialogListener) getActivity();
            activity.onFinishEditDialog(etNewTask.getText().toString());
            this.dismiss();
            return true;
        }
        return false;
    }
}
