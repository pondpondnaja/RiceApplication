package com.example.riceapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginScreen extends AppCompatActivity {

    EditText userE;
    EditText passE;
    String user,defaultUser = "admin";
    String pass,defaultPass = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        userE = findViewById(R.id.user_edit);
        passE = findViewById(R.id.password_edit);
    }

    public void login_btn(View view){
        user = userE.getText().toString();
        pass = passE.getText().toString();

        if(user.equals(defaultUser) && pass.equals(defaultPass)){
            Intent intent = new Intent(LoginScreen.this,MainActivity.class);
            intent.putExtra("username",user);
            startActivity(intent);
        }
    }
}
