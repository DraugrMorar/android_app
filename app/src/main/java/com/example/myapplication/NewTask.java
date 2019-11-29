package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import network.ServiceGenerator;
import network.UsersApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewTask extends AppCompatActivity implements View.OnClickListener {


    private EditText title;
    private EditText description;
    private Spinner status;
    private Spinner priority;
    private Button save;
    private Button delete;
    private int stat = -1;
    private int prior = 0;
    private int userId;

    Calendar date = Calendar.getInstance();
    String bDateText;
    Button bdate;
    SimpleDateFormat newDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_description);

        description = (EditText) findViewById(R.id.createdesc);
        title = (EditText) findViewById(R.id.task);
        status = (Spinner) findViewById(R.id.status);
        priority = (Spinner) findViewById(R.id.priority);
        save = (Button) findViewById(R.id.bsave);
        delete = (Button) findViewById(R.id.bdelete);
        userId = getIntent().getIntExtra("userId", 0);
        bdate = (Button) findViewById(R.id.bdate);
        setInitialDateTime();


        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition,
                                       long selectedId) {
                stat = selectedItemPosition;
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        priority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition,
                                       long selectedId) {
                prior = selectedItemPosition;
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        save.setOnClickListener(this);
        delete.setOnClickListener(this);
    }


    public void setDate(View v) {
        new DatePickerDialog(NewTask.this, d,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void setInitialDateTime() {
        newDate = new SimpleDateFormat("yyyy-MM-dd");
        newDate.setTimeZone(date.getTimeZone());
        bDateText = newDate.format(date.getTime());
        bdate.setText(bDateText);
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            date.set(Calendar.YEAR, year);
            date.set(Calendar.MONTH, monthOfYear);
            date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bsave:
                newTask();
                break;
            case R.id.bdelete:
                finish();
                System.exit(0);
                break;
        }
    }

    private void newTask() {
        if (title.getText().length() == 0) {
            Toast.makeText(this, "Create Title!", Toast.LENGTH_SHORT).show();
        } else {
            if (description.getText().length() == 0)
                description.setText("description");
            UsersApi usersApi = ServiceGenerator.createService(UsersApi.class);
            Call<List<network.ToDoList>> call = usersApi.addTask(userId,
                    title.getText().toString(),
                    description.getText().toString(),
                    bDateText,
                    stat, prior);
            call.enqueue(new Callback<List<network.ToDoList>>() {
                @Override
                public void onResponse(Call<List<network.ToDoList>> call, Response<List<network.ToDoList>> response) {
                    if (response.code() == 200) {
                        Toast toast = Toast.makeText(NewTask.this, "New task was created", Toast.LENGTH_SHORT);
                        toast.show();
                        Intent intent = new Intent(NewTask.this, ToDoList.class);
                        intent.putExtra("id", userId);
                        startActivity(intent);
                    } else if (response.code() == 403) {
                        Toast toast = Toast.makeText(NewTask.this, "Error", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(NewTask.this, "Another error", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

                @Override
                public void onFailure(Call<List<network.ToDoList>> call, Throwable throwable) {
                    Toast toast = Toast.makeText(NewTask.this, call.toString(), Toast.LENGTH_SHORT);
                    toast.show();

                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 3, "Back to list");
        menu.add(1, 2, 4, "Exit");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 2) {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        } else if (item.getItemId() == 1) {
            finish();
            System.exit(0);
        }
        return super.onOptionsItemSelected(item);
    }

}
