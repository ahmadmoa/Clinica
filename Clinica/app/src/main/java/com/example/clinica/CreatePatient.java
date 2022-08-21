package com.example.clinica;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;




import com.google.android.material.textfield.TextInputLayout;

public class CreatePatient extends AppCompatActivity  {

    TextInputLayout age, username, Email, password, repassword,mobile_Number,gender;
    Button Register;
    PeopleDatabase DataBaseObject = new PeopleDatabase(this);
    DatePickerDialog.OnDateSetListener setListener;
    RadioButton male,female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_patient);

        username = findViewById(R.id.Username);
        Email = findViewById(R.id.LoginEmail);
        mobile_Number = findViewById(R.id.lmobileNo);

        password = findViewById(R.id.password_reg);
        repassword = findViewById(R.id.re_password_reg);
        Register = (Button) findViewById(R.id.lgo);
        male = (RadioButton) findViewById(R.id.Male);
        female = (RadioButton) findViewById(R.id.Female);
        age = findViewById(R.id.lage);
        DataBaseObject = new PeopleDatabase(this);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               registerUser("patients");
            }
        });
    }

        public Boolean validateUsername() {
            String val = username.getEditText().getText().toString();

            if (val.isEmpty()) {
                username.setError("Field cannot be empty");
                return false;
            } else {
                username.setError(null);
                return true;
            }
        }
        public Boolean validateMobileNo(){
            String val = mobile_Number.getEditText().getText().toString();

            if(val.isEmpty()){
                mobile_Number.setError("Field cannot be empty");
                return false;
            }
            else{
                mobile_Number.setError(null);
                return true;
            }
        }
        public Boolean validateEmail(String Tablename){
            String val = Email.getEditText().getText().toString();
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(DataBaseObject.exist(Email.getEditText().getText().toString(),Tablename)){
            Email.setError("Field cannot be empty");
            return false;
        }

             if(val.isEmpty()){
                Email.setError("Field cannot be empty");
                return false;
            } else if(!val.matches(emailPattern)){
                Email.setError("Invalid email address");
                return false;
            }
            else{
                Email.setError(null);
                return true;
            }
        }
        private Boolean validatePassword(){
            String val = password.getEditText().getText().toString();

            if(val.isEmpty()){
                password.setError("Field cannot be empty");
                return false;
            }
            else{
                password.setError(null);
                return true;
            }
        }

        public Boolean confirmPassword () {
            String val1 = repassword.getEditText().getText().toString();
            String val2 = password.getEditText().getText().toString();
            if (val1.equals(val2)) {
                repassword.setError(null);
                return true;
            } else {
                repassword.setError("password doesn't match");
                return false;
            }
        }

        public Boolean validateAge () {
            String val = age.getEditText().getText().toString();
        //String agePattern = "[0-100]";

            if (val.isEmpty()) {
                age.setError("Field cannot be empty");
                return false;
            }
      // else if(!val.matches(agePattern)){
         //   age.setError("Invalid age");
         //   return false;
       // }
            else {
                age.setError(null);
                return true;
            }
        }
        public String validGender(){
        String gender;
            if (male.isChecked()) {
                gender = "M";
            }
            else if (female.isChecked()) {
                gender = "F";
            }
            else{
                gender = "X";
            }
                return gender;
    }
    public void registerUser(String user){

        if(  !validateEmail(user)||!validateMobileNo() || !validateUsername()|| !validatePassword() || !confirmPassword()){
            return;
        }
            String Name = username.getEditText().getText().toString();

            String email =Email.getEditText().getText().toString();
            String Mobile = mobile_Number.getEditText().getText().toString();
            String Password = password.getEditText().getText().toString();
            String re_Password = repassword.getEditText().getText().toString();
            String Age = age.getEditText().getText().toString();
            String gender=validGender();
        DataBaseObject.addPatient(Name,email,Password,Age,Mobile,gender);
        Intent intent=new Intent(getApplicationContext(),HomePage.class);
        startActivity(intent);
    }
}




