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

    private EditText task;
    private EditText date;
    private Button add;
    private Button second_activity;
    private ItemsAdapter adapter;


    static class Item {
        String task;
        String date;

        public Item(String task, String date) {
            this.task = task;
            this.date = date;
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        task = (EditText) findViewById(R.id.task);
        date = (EditText) findViewById(R.id.data);
        add = (Button) findViewById(R.id.add);
        second_activity = (Button) findViewById(R.id.secondA);
        adapter = new ItemsAdapter();
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putText();
            }
        };
        add.setOnClickListener(listener);

        View.OnClickListener swap = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MActivity.this, secondActivity.class);
                startActivity(intent);

            }

        };
        second_activity.setOnClickListener(swap);

    }
    private void putText() {
        if (task.getText().length() == 0) {
            return;
        } else {
            ListView items = (ListView) findViewById(R.id.items);
            items.setAdapter(adapter);
                adapter.add(new Item(task.getText().toString(), date.getText().toString()));
        }
    }

    private class ItemsAdapter extends ArrayAdapter<Item> {

        public ItemsAdapter() {
            super(MActivity.this, R.layout.item);
        }

        @Override
        @NonNull
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final View view = getLayoutInflater().inflate(R.layout.item, null);
            final Item item = getItem(position);
            if (item.task.length() == 0) {
                return view;
            }
            ((TextView) view.findViewById(R.id.task)).setText(item.task);
            ((TextView) view.findViewById(R.id.data)).setText(item.date);
            return view;
        }
    }
}
