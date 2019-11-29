package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import network.ServiceGenerator;
import network.Users;
import network.UsersApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Authoriz extends AppCompatActivity implements View.OnClickListener {


    private Button signIn;
    private EditText login;
    private EditText password;
    private Button createUser;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in:
                showTodoList();
                break;
            case R.id.create:
                createNewUser();
                break;
        }
    }

    private void createNewUser() {
        UsersApi usersApi = ServiceGenerator.createService(UsersApi.class);
        Call<Users> call = usersApi.addUser(login.getText().toString(), password.getText().toString());
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.code() == 200) {
                    if (response.body().getId() != 0) {
                        Toast toast = Toast.makeText(Authoriz.this, ("Hello, " + response.body().getName()), Toast.LENGTH_SHORT);
                        toast.show();
                        loginSuccess(response.body().getId());
                    } else {
                        Toast toast = Toast.makeText(Authoriz.this, "Name is already exist", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else if (response.code() == 403) {
                    Toast toast = Toast.makeText(Authoriz.this, "Error", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(Authoriz.this, "Another error", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable throwable) {
                Toast toast = Toast.makeText(Authoriz.this, call.toString(), Toast.LENGTH_SHORT);
                toast.show();

            }
        });

    }

    private void showTodoList() {
        UsersApi usersApi = ServiceGenerator.createService(UsersApi.class);
        Call<Users> call = usersApi.getUser(login.getText().toString(), password.getText().toString());
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.code() == 200) {
                    if (response.body().getId() != 0) {
                        Toast toast = Toast.makeText(Authoriz.this, ("Hello, " + response.body().getName()), Toast.LENGTH_SHORT);
                        toast.show();
                        loginSuccess(response.body().getId());
                    } else {
                        Toast toast = Toast.makeText(Authoriz.this, "Wrong login or password", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else if (response.code() == 403) {
                    Toast toast = Toast.makeText(Authoriz.this, "Error", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(Authoriz.this, "Another error", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable throwable) {
                Toast toast = Toast.makeText(Authoriz.this, call.toString(), Toast.LENGTH_SHORT);
                toast.show();

            }
        });
    }

    private void loginSuccess(int userId) {
        Intent intent = new Intent(this, ToDoList.class);
        intent.putExtra("id", userId);
        intent.putExtra("sort by", R.id.m_sort_date);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authoriz);

        signIn = (Button) findViewById(R.id.sign_in);
        login = (EditText) findViewById(R.id.usersLogin);
        password = (EditText) findViewById(R.id.usersPsw);
        createUser = (Button) findViewById(R.id.create);
        createUser.setOnClickListener(this);
        signIn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, "Edit password");
        menu.add(1, 2, 2, "Exit");


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 2) {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        } else if (item.getItemId() == 1) {
            Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ChangePassword.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
