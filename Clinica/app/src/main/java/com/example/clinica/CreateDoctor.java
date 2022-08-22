package com.example.clinica;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;

public class CreateDoctor extends AppCompatActivity {

    TextInputLayout age, username, Email, password, repassword,mobile_Number,fees,workplace;
    Button Register;
    PeopleDatabase DataBaseObject = new PeopleDatabase(this);
    DatePickerDialog.OnDateSetListener setListener;
    RadioButton male,female;
    Spinner special,city,fromTime,toTime,slot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_doctor);
        username = findViewById(R.id.doctor_Username);
        Email = findViewById(R.id.doctor_LoginEmail);
        mobile_Number = findViewById(R.id.doctor_mobileNo);
        password = findViewById(R.id.doctor_password_reg);
        repassword = findViewById(R.id.doctor_re_password_reg);
        Register = (Button) findViewById(R.id.doctor_reg_btn);
        male = (RadioButton) findViewById(R.id.doctor_Male);
        female = (RadioButton) findViewById(R.id.doctor_Female);
        age = findViewById(R.id.doctor_age);
        fees=findViewById(R.id.price);
        workplace=findViewById(R.id.Work_plce);
        special=(Spinner)findViewById(R.id.spinneup_specialization);
        city=(Spinner)findViewById(R.id.city);
        fromTime=(Spinner)findViewById(R.id.begin);
        toTime=(Spinner)findViewById(R.id.end);
        slot=(Spinner)findViewById(R.id.slot);
        DataBaseObject = new PeopleDatabase(this);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateDoctor("doctors");
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
        String agePattern = "[0-100]";

        if (val.isEmpty()) {
            age.setError("Field cannot be empty");
            return false;
        }
        else if(!val.matches(agePattern)){
           age.setError("Invalid age");
          return false;
         }
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
    public void CreateDoctor (String user){

        if(!validateEmail(user)||!validateMobileNo() || !validateUsername()|| !validatePassword() || !confirmPassword()){
            return;
        }
        String Name = username.getEditText().getText().toString();
        String fromTimeDate = fromTime.getSelectedItem().toString().replaceAll("\\D+","");
        String toTimeDate = toTime.getSelectedItem().toString().replaceAll("\\D+","");
        String email =Email.getEditText().getText().toString();
        String Mobile = mobile_Number.getEditText().getText().toString();
        String Password = password.getEditText().getText().toString();
        String re_Password = repassword.getEditText().getText().toString();
        String Age = age.getEditText().getText().toString();
        String gender=validGender();
        String Fees=fees.getEditText().getText().toString();
        String Special=special.getSelectedItem().toString();

        String Slot=slot.getSelectedItem().toString();
        String City=city.getSelectedItem().toString();
        String Workplace=workplace.getEditText().getText().toString();

        DataBaseObject.addDoctor(Name,email,Password,Age,Mobile,gender,Integer.parseInt(Fees),fromTimeDate,toTimeDate,Integer.parseInt(Slot),Workplace,Special,City,2);
       // Intent intent=new Intent(getApplicationContext(),DoctorHome.class);
        //startActivity(intent);
        openDialog();
        fromTime.setSelection(0);
        special.setSelection(0);
        toTime.setSelection(0);
        slot.setSelection(0);
        username.getEditText().getText().clear();
        fees.getEditText().getText().clear();
        Email.getEditText().getText().clear();
        mobile_Number.getEditText().getText().clear();
        password.getEditText().getText().clear();
        workplace.getEditText().getText().clear();
        repassword.getEditText().getText().clear();
        age.getEditText().getText().clear();
        male.setChecked(false);
        female.setChecked(false);

    }

    public void openDialog(){
        PopUpmessage dialog =new PopUpmessage("Registration Done","You are pending now until the Admin Approve your request","Home",getApplicationContext());
        dialog.show(getSupportFragmentManager(),"Ahmad");
    }
}