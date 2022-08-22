package com.example.clinica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminPage extends AppCompatActivity {
Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        initialize_the_design();
b1 =findViewById(R.id.pendingBtn);
b1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(getApplicationContext(),PendingDoctors.class);
        startActivity(intent);
    }
});

    }
    public void initialize_the_design(){
        //initializing the variables
        final  PeopleDatabase DataBaseObject = new PeopleDatabase(this);
        TextView users=(TextView)findViewById(R.id.usersNum);
        TextView doctors=(TextView)findViewById(R.id.doctorsNum);


        //StatusBar set color
        getWindow().setStatusBarColor(ContextCompat.getColor(AdminPage.this,R.color.AdminHomeColor));
        getWindow().setNavigationBarColor(ContextCompat.getColor(AdminPage.this, R.color.AdminNavColor));

        //set values
        //Toast.makeText(AdminPage.this, "Login done, Doctor Type", Toast.LENGTH_LONG).show();
        users.setText(String.valueOf(DataBaseObject.getAdminStatictics()[0]));
        doctors.setText(String.valueOf(DataBaseObject.getAdminStatictics()[1]));

    }
}