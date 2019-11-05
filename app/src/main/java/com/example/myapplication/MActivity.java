package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MActivity extends Activity {
    private Button second_activity;


//    static class Item {
//        String task;
//        String date;
//
//        public Item(String task, String date) {
//            this.task = task;
//            this.date = date;
//        }
//
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        task = (EditText) findViewById(R.id.task);
//        date = (EditText) findViewById(R.id.data);
//        add = (Button) findViewById(R.id.add);
        second_activity = (Button) findViewById(R.id.secondA);
//        adapter = new ItemsAdapter();
//        View.OnClickListener listener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                putText();
//            }
//        };
//        add.setOnClickListener(listener);

        View.OnClickListener swap = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MActivity.this, secondActivity.class);
                startActivity(intent);

            }

        };
        second_activity.setOnClickListener(swap);

    }
}
