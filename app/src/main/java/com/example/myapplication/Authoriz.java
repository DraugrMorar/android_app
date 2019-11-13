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

import network.DataManager;
import network.UsersApi;
import retrofit2.Call;

public class Authoriz extends AppCompatActivity implements View.OnClickListener{


    private Button signIn;
    private EditText login;
    private EditText password;
    private Button createUser;




//    private DataManager dataManager;




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in:
                showTodoList();
                break;
            case R.id.create:
                break;
        }
    }

    private void showTodoList()
    {
        Call<UsersApi>
        Toast toast =Toast.makeText(Authoriz.this, "Sign in", Toast.LENGTH_SHORT);
        toast.show();
        Intent intent = new Intent(this, MActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authoriz);



//        dataManager = DataManager.getInstance();




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
