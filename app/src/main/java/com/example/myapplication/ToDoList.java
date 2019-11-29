package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import java.util.ArrayList;
import java.util.List;

import network.ServiceGenerator;
import network.UsersApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ToDoList extends AppCompatActivity implements View.OnClickListener {

    private TextView title;
    private TextView status;
    private int userId;
    private ItemsAdapter adapter;
    private List<Item> itemList = new ArrayList<Item>();
    private int listId;
    private int sort;


    @Override
    public void onClick(View v) {

    }

    static class Item {
        String title;
        int status;
        int priority;
        int itemId;

        public Item(String title, int status, int priority, int itemId) {
            this.title = title;
            this.status = status;
            this.priority = priority;
            this.itemId = itemId;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist);
        userId = getIntent().getIntExtra("id", 0);
        sort = getIntent().getIntExtra("sort by", 0);
        adapter = new ItemsAdapter();
        toDoListViewer();
        ListView items = findViewById(R.id.items);
        items.setAdapter(new ItemsAdapter(itemList, this));
        items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = items.getItemAtPosition(position);
                Item item = (Item) o;
                Intent intent = new Intent(ToDoList.this, Description.class);
                intent.putExtra("itemId", ((Item) o).itemId);
                intent.putExtra("list_id", listId);
                intent.putExtra("user_id", userId);
                startActivity(intent);
            }
        });
    }

    private void toDoListViewer() {
        UsersApi usersApi = ServiceGenerator.createService(UsersApi.class);
        Call<List<network.ToDoList>> call = usersApi.getListForUser(userId);
        switch (sort) {
            case R.id.m_sort_title:
                call = usersApi.getListForUserbyTitle(userId);
                break;
            case R.id.m_sort_priority:
                call = usersApi.getListForUserbyPriority(userId);
                break;
            case R.id.m_sort_status:
                call = usersApi.getListForUserbyStatus(userId);
                break;
            case R.id.m_sort_date:
                call = usersApi.getListForUser(userId);
                break;
        }
        call.enqueue(new Callback<List<network.ToDoList>>() {
            @Override
            public void onResponse(Call<List<network.ToDoList>> call, Response<List<network.ToDoList>> response) {
                if (response.code() == 200) {
                    fillToDoList(response.body());
                } else if (response.code() == 403) {
                    Toast toast = Toast.makeText(ToDoList.this, "Error", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(ToDoList.this, "Another error", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<List<network.ToDoList>> call, Throwable throwable) {
                Toast toast = Toast.makeText(ToDoList.this, call.toString(), Toast.LENGTH_SHORT);
                toast.show();

            }
        });
    }

    private void fillToDoList(List<network.ToDoList> responses) {
        if (!responses.isEmpty())
            listId = responses.get(0).getList_id();
        for (network.ToDoList response : responses) {
            itemList.add(new Item(response.getTitle(),
                    response.getStatus(),
                    response.getPriority(), response.getItem_id()));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.m_exit) {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        } else if (item.getItemId() == R.id.m_add) {
            Toast.makeText(this, "new task", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, NewTask.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        } else if (item.getGroupId() == R.id.m_sort_date) {
            Intent intent = new Intent(this, ToDoList.class);
            intent.putExtra("id", userId);
            intent.putExtra("sort by", item.getItemId());
            startActivity(intent);
        } else if (item.getItemId() == R.id.m_ch_u) {
            Intent intent = new Intent(this, Authoriz.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    public class ItemsAdapter extends BaseAdapter {
        private List<Item> listData;
        private LayoutInflater layoutInflater;
        private Context context;


        public ItemsAdapter(List<Item> listData, Context context) {
            this.listData = listData;
            layoutInflater = LayoutInflater.from(context);
            this.context = context;
        }

        public ItemsAdapter() {
            super();
        }

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public Object getItem(int position) {
            return listData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item, null);
                holder = new ViewHolder();
                holder.title = (TextView) convertView.findViewById(R.id.title);
                holder.stat = (AppCompatCheckBox) convertView.findViewById(R.id.stat);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Item item = this.listData.get(position);
            holder.title.setText(item.title);
            if (item.status == 2) {
                holder.stat.setChecked(true);
                holder.stat.setSupportButtonTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
            } else if (item.status == 1) {
                holder.stat.setChecked(true);
                holder.stat.setSupportButtonTintList(ColorStateList.valueOf(Color.parseColor("#008000")));
            } else
                holder.stat.setChecked(false);
            if (item.priority == 2)
                holder.title.setBackgroundColor(Color.parseColor("#A70000FF"));
            else if (item.priority == 1)
                holder.title.setBackgroundColor(Color.parseColor("#A71E90FF"));
            else
                holder.title.setBackgroundColor(Color.parseColor("#A7AFEEEE"));
            return convertView;
        }

        class ViewHolder {
            TextView title;
            AppCompatCheckBox stat;
        }
    }
}