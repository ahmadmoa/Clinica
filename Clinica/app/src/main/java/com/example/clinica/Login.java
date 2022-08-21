package com.example.clinica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {
    TextInputLayout Email,Password;
Button Log_btn,Register_btn;
String email,password,temp;
    PeopleDatabase DataBaseObject = new PeopleDatabase(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String Client = getIntent().getStringExtra("Client");
        temp=Client;

        Email = findViewById(R.id.LoginEmail);
        Password = findViewById(R.id.LoginPassward);

        Log_btn = (Button) findViewById(R.id.Login_btn);
        Log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = Email.getEditText().getText().toString();
                password = Password.getEditText().getText().toString();
                checkUser();
            }
        });
        Register_btn=(Button) findViewById(R.id.Register_btn);
        Register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if(Client.equals("patients"))
                    intent=new Intent(getApplicationContext(),CreatePatient.class);
                else
                    intent = new Intent(getApplicationContext(), CreateDoctor.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private Boolean validateUsername(){
        if(email.isEmpty()){
            Email.setError("Field cannot be empty");
            return false;
        } else{
            Email.setError(null);
            Email.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword(){
        if(password.isEmpty()){
            Password.setError("Field cannot be empty");
            return false;
        } else{
            Password.setError(null);
            Password.setErrorEnabled(false);
            return true;
        }
    }
    public void checkUser(){
        int type;
        if(temp.equals("patients"))
            type=1;
        else
            type=2;

        if(validatePassword()&&validateUsername()){

            if(Email.getEditText().getText().toString().equals("admin") && Password.getEditText().getText().toString().equals("admin")){
                Email.getEditText().getText().clear();
                Password.getEditText().getText().clear();
                Intent intent = new Intent(getApplicationContext(), AdminPage.class);
                startActivity(intent);}

            {
                int IsLogin=DataBaseObject.loginCheck(Email.getEditText().getText().toString(),Password.getEditText().getText().toString(),type);
                if(IsLogin==1){
                    Toast.makeText(Login.this, "Login done", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), HomePage.class);
                    startActivity(intent);
                    Email.getEditText().getText().clear();
                    Password.getEditText().getText().clear();}
                else if(IsLogin==2){
                    Intent intent = new Intent(getApplicationContext(), DoctorHome.class);
                    startActivity(intent);
                    Email.getEditText().getText().clear();
                    Password.getEditText().getText().clear();}
                }
            }

        }
        }

