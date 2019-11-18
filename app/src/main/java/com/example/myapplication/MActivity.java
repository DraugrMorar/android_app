package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import network.ServiceGenerator;
import network.ToDoList;
import network.UsersApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView title;
    private TextView description;
    private TextView status;
    private TextView priority;
    private TextView date;
    private ItemsAdapter adapter;
    private Integer usersId;

    @Override
    public void onClick(View v) {

    }


    static class Item{
        String title;
        String description;
        String status;
        String priority;
        String date;

        public Item(String title, String description, String status, String priority, String date) {
            this.title = title;
            this.description = description;
            this.status = status;
            this.priority = priority;
            this.date = date;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todilist);
        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        status = (TextView) findViewById(R.id.status);
        priority = (TextView) findViewById(R.id.priority);
        date = (TextView) findViewById(R.id.date);
        usersId = getIntent().getIntExtra("id", 0);
        adapter = new ItemsAdapter();
        toDoListViewer(usersId);
    }

    private void toDoListViewer(Integer usersId) {
        UsersApi usersApi = ServiceGenerator.createService(UsersApi.class);
        Call<List<ToDoList>> call = usersApi.getListForUser(usersId);
        call.enqueue(new Callback<List<ToDoList>>() {
            @Override
            public void onResponse(Call<List<ToDoList>> call, Response<List<ToDoList>> response) {
                if(response.code() == 200){
                    fillToDoList(response.body());
                } else if(response.code() == 403){
                    Toast toast =Toast.makeText(MActivity.this, "Error", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    Toast toast =Toast.makeText(MActivity.this, "Another error", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            @Override
            public void onFailure(Call<List<ToDoList>> call, Throwable throwable) {
                Toast toast =Toast.makeText(MActivity.this, call.toString(), Toast.LENGTH_SHORT);
                toast.show();

            }
        });
    }

    private void fillToDoList(List<ToDoList> responses) {
            ListView items = findViewById(R.id.items);
            items.setAdapter(adapter);
        for (ToDoList response :responses) {
            adapter.add(new Item(response.getTitle(),
            response.getDescription(),
           ("" + response.getStatus()),
            ("" + response.getPriority()),
            response.getDate().toString()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, "Create task");
        menu.add(1, 1, 2, "Sort by");
        menu.add(1, 2, 3,"Change user");
        menu.add(1, 2, 4,"Exit");
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals("Exit")){
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
        else if(item.getTitle().equals("Create task")){
            Toast.makeText(this, "new task", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Description.class);
//            intent.putExtra("id", response.body().get(userId).getId());
            startActivity(intent);
        }
        else if(item.getTitle().equals("Sort by")){
            Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        else if(item.getTitle().equals("Change user")){
            finish();
            System.exit(0);}
        return super.onOptionsItemSelected(item);
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
            ((TextView)view.findViewById(R.id.title)).setText(item.title);
            ((TextView)view.findViewById(R.id.description)).setText(item.description);
            ((TextView)view.findViewById(R.id.status)).setText(item.status);
            ((TextView)view.findViewById(R.id.priority)).setText(item.priority);
            ((TextView)view.findViewById(R.id.date)).setText(item.date);
            return view;
        }
    }
}
