package com.example.myapplication;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MainActivity extends Activity {
        static class Item{
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
        final EditText task = (EditText) findViewById(R.id.task);
        final EditText date = (EditText) findViewById(R.id.data);
        final Button add = (Button) findViewById(R.id.add);
//        if (task.getText().length() == 0) {
//            add.setEnabled(false);
//        }
//        else
//        {
            add.setEnabled(true);
            final ListView items = (ListView) findViewById(R.id.items);
            final ItemsAdapter adapter = new ItemsAdapter();
            items.setAdapter(adapter);
            add.setOnClickListener((v) -> {
                adapter.add(new Item(task.getText().toString(), date.getText().toString()));
            });
//        }
    }
    private class ItemsAdapter extends ArrayAdapter<Item> {

        public ItemsAdapter() {
            super(MainActivity.this, R.layout.item);
        }

        @Override
        @NonNull
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                final View view = getLayoutInflater().inflate(R.layout.item, null);
                final Item item = getItem(position);
                    ((TextView) view.findViewById(R.id.task)).setText(item.task);
                    ((TextView) view.findViewById(R.id.data)).setText(item.date);
            return view;
        }
    }
}
