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

import java.util.List;

import network.ServiceGenerator;
import network.Users;
import network.UsersApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener {

    private EditText login;
    private EditText oldPwd;
    private EditText newPwd;
    private Button bEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        login = (EditText) findViewById(R.id.cpLogin);
        newPwd = (EditText) findViewById(R.id.cpNewPwd);
        oldPwd = (EditText) findViewById(R.id.cpOldPwd);
        bEdit = (Button) findViewById(R.id.cpEdit);
        bEdit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        UsersApi usersApi = ServiceGenerator.createService(UsersApi.class);
        Call<List<Users>> call = usersApi.changePass(login.getText().toString(), oldPwd.getText().toString(), newPwd.getText().toString());
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                if (response.code() == 200) {
                    if (response.body().get(0).getId() != 0) {
                        Toast toast = Toast.makeText(ChangePassword.this, ("Hello, " + response.body().get(0).getName()), Toast.LENGTH_SHORT);
                        toast.show();
                        editPass(response.body().get(0).getId());
                    } else {
                        Toast toast = Toast.makeText(ChangePassword.this, "Incorrect password", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else if (response.code() == 403) {
                    Toast toast = Toast.makeText(ChangePassword.this, "Error", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(ChangePassword.this, "Another error", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable throwable) {
                Toast toast = Toast.makeText(ChangePassword.this, call.toString(), Toast.LENGTH_SHORT);
                toast.show();

            }
        });
    }

    private void editPass(int id) {
        Toast toast = Toast.makeText(ChangePassword.this, "Sign in", Toast.LENGTH_SHORT);
        toast.show();
        Intent intent = new Intent(this, ToDoList.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 3, "Back");
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
