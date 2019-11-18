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

public class Authoriz extends AppCompatActivity implements View.OnClickListener{


    private Button signIn;
    private EditText login;
    private EditText password;
    private Button createUser;


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in:
                showTodoList();
                break;
            case R.id.create:
                createNewUser();
                break;
        }
    }

    private void createNewUser() {

    }

    private void showTodoList()
    {
        UsersApi usersApi = ServiceGenerator.createService(UsersApi.class);
        Call<List<Users>> call = usersApi.getAll();
//        Toast toast =Toast.makeText(Authoriz.this, call.request().toString(), Toast.LENGTH_SHORT);
//        toast.show();
        call.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                if(response.code() == 200){
                    int userId = checkLoginPassword(response.body(), login, password);
                    if(userId != -1)
                    {
                    Toast toast =Toast.makeText(Authoriz.this, ("Hello, " + response.body().get(userId).getName()), Toast.LENGTH_SHORT);
                    toast.show();
                    loginSuccess(response, userId);
                    }
                    else
                    {
                        Toast toast =Toast.makeText(Authoriz.this, "Wrong login or password", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else if(response.code() == 403){
                    Toast toast =Toast.makeText(Authoriz.this, "Error", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    Toast toast =Toast.makeText(Authoriz.this, "Another error", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            @Override
            public void onFailure(Call<List<Users>> call, Throwable throwable) {
                Toast toast =Toast.makeText(Authoriz.this, call.toString(), Toast.LENGTH_SHORT);
                toast.show();

            }
        });
    }

    private int checkLoginPassword(List<Users> responses, EditText login, EditText password) {
        int res = -1;
        for (Users response:responses)
        {
            if(response.getName().equals(login.getText().toString()) && response.getPassword().equals(password.getText().toString())) {
                res = response.getId();
            }
        }
        return res;
    }

    private void loginSuccess(Response<List<Users>> response, int userId) {
        Toast toast =Toast.makeText(Authoriz.this, "Sign in", Toast.LENGTH_SHORT);
        toast.show();
        Intent intent = new Intent(this, MActivity.class);
        intent.putExtra("id", response.body().get(userId).getId());
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authoriz);

        signIn = (Button)findViewById(R.id.sign_in);
        login = (EditText)findViewById(R.id.usersLogin);
        password = (EditText)findViewById(R.id.usersPsw);
        createUser = (Button)findViewById(R.id.create);
        createUser.setOnClickListener(this);
        signIn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, "Edit password");
        menu.add(1, 2, 2,"Exit");


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals("Exit")){
        finish();
        System.exit(0);}
        else if(item.getTitle().equals("Edit password")){
            Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
