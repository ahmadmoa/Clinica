package com.example.clinica;

import static java.lang.Integer.parseInt;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.time.*;

public class PeopleDatabase extends SQLiteOpenHelper {
    private static String databaseName = "allUsersDatabase";

    //private static String createUsersTable = "create table users(email text primary key, username text, password text not null, typeIndicator integer)";

    private static String createPatientsTable = "create table patients(patientEmail text primary key, username text not null, " +
            "password text not null, age text, phone text unique, gender text)";

    private static String createDoctorsTable = "create table doctors(doctorEmail text primary key, username text not null," +
            "password text not null, age integer,gender text,booking_price integer,phone text, workplace_address text," +
            "specialization text, city text,fees interger, starting_hours text, closing_hours text, slot_time text,MembershipType interger)";

    private static String createAppointmentsTable = "create table appointments(patient_email text not null references patients(patientEmail), " +
            "doctor_email text not null references doctors(doctorEmail), dateAndTime text,Status text, " +
            "primary key (patient_email, doctor_email))";

    private static String createSlotsTable = "create table slots(doctor text, slot text)";

    private static SQLiteDatabase AppDataBase;

    public PeopleDatabase(Context context) {
        super(context, databaseName, null, 1);
        this.AppDataBase = AppDataBase;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createPatientsTable);
        sqLiteDatabase.execSQL(createDoctorsTable);
        sqLiteDatabase.execSQL(createAppointmentsTable);
        sqLiteDatabase.execSQL(createSlotsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists patients");
        sqLiteDatabase.execSQL("drop table if exists doctors");
        sqLiteDatabase.execSQL("drop table if exists appointments");
        onCreate(sqLiteDatabase);
    }

    public void addPatient(String username, String email, String password, String age, String phoneNo, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        /*ContentValues newUser = new ContentValues();
        newUser.put("email", email);
        newUser.put("username", username);
        newUser.put("password", password);
        newUser.put("typeIndicator", 1);
        db.insert("users", null, newUser);*/
        ContentValues newPatient = new ContentValues();
        newPatient.put("username", username);
        newPatient.put("patientEmail", email);
        newPatient.put("password", password);
        newPatient.put("age", age);
        newPatient.put("phone", phoneNo);
        newPatient.put("gender", gender);
        db.insert("patients", null, newPatient);
        Log.i("My Activity", "Record inserted");
        db.close();
    }

    public boolean exist(String EMail, String Tablename) {
        AppDataBase = getReadableDatabase();
        if (Tablename.equals("patients")) {
            Cursor cursor = AppDataBase.rawQuery("select * from patients where patientEmail =?  ", new String[]{EMail});
            if (cursor.getCount() > 0) {
                return true;
            }
        } else if (Tablename.equals("doctors")) {
            Cursor cursor = AppDataBase.rawQuery("select * from doctors where doctorEmail =? ", new String[]{EMail});
            if (cursor.getCount() > 0) {
                return true;
            }
        }

        return false;
    }
    public int[] getAdminStatictics(){
        AppDataBase=getReadableDatabase();
        Cursor usersCur=AppDataBase.rawQuery("select patientEmail from patients",null);
        Cursor doctorsCur=AppDataBase.rawQuery("select doctorEmail from doctors",null);

        int[] activeNumber=new int[2];
        activeNumber[0]=usersCur.getCount();
        activeNumber[1]=doctorsCur.getCount();
        return activeNumber;
    }

    public void addDoctor(String username, String email, String password, String age, String phoneNo, String gender,
                          int price, String start, String finish, int slot, String workplace, String specialty, String city,int MembershipType) {
        SQLiteDatabase db = this.getWritableDatabase();
        /*ContentValues newUser = new ContentValues();
        newUser.put("email", email);
        newUser.put("username", username);
        newUser.put("password", password);
        newUser.put("typeIndicator", 2);
        db.insert("users", null, newUser);*/
        ContentValues newDoctor = new ContentValues();
        newDoctor.put("username", username);
        newDoctor.put("doctorEmail", email);
        newDoctor.put("password", password);
        newDoctor.put("age", age);
        newDoctor.put("phone", phoneNo);
        newDoctor.put("gender", gender);
        newDoctor.put("starting_hours", start);
        newDoctor.put("closing_hours", finish);
        newDoctor.put("slot_time", slot);
        newDoctor.put("booking_price", price);
        newDoctor.put("workplace_address", workplace);
        newDoctor.put("specialization", specialty);
        newDoctor.put("city", city);
        newDoctor.put("MembershipType",MembershipType);
        db.insert("doctors", null, newDoctor);
        Log.i("Doctor", "Record inserted");
        db.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void calcAppointment(String docEmail, String start, String end, double slot) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues s = new ContentValues();

        /*SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date d = df.parse(start);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, 10);
        String newTime = df.format(cal.getTime());*/
        double slotInHours = slot / 60;
        DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
        int iterations = (int) (((Integer.parseInt(end.substring(0, 2))) - (Integer.parseInt(start.substring(0, 2)))) / slotInHours);
        //Log.i("itertaions", String.valueOf(iterations));
        String temp;
        for (int i = 0; i < iterations; ++i) {
            temp = start;
            LocalTime lt = LocalTime.parse(start);
            start = df.format(lt.plusMinutes((long) slot));
            Log.i("new slot", start);
            s.put("doctor", docEmail);
            s.put("slot", temp + " - " + start);
            db.insert("slots", null, s);
        }
    }

    public void deletePatient(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete("users", "email = ?", new String[]{String.valueOf(email)});
        db.delete("patients", "patientEmail = ?", new String[]{String.valueOf(email)});
    }

    public void deleteDoctor(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete("users", "email = ?", new String[]{String.valueOf(email)});
        db.delete("doctors", "doctorEmail = ?", new String[]{String.valueOf(email)});
    }

    public void deleteAppointment(String patientEmail, String drEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("appointments", "patient_email = ? and doctor_email = ?", new String[]{String.valueOf(patientEmail), String.valueOf(drEmail)});
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void changingWorkingHours(String drEmail, String startHours, String closingHours, int slot) {
        SQLiteDatabase db = getReadableDatabase();
        db.delete("slots", "doctor = ?", new String[]{String.valueOf(drEmail)});
        calcAppointment(drEmail, startHours, closingHours, slot);
    }

    public int loginCheck(String email, String password, int type) {
        AppDataBase = getReadableDatabase();
        if (type == 1) {
            Cursor cursor = AppDataBase.rawQuery("select * from patients where patientEmail =? and Password =?", new String[]{email, password});
            if (cursor.getCount() > 0)
                return 1;
            else
                return 0;
        }
        else if (type == 2) {
            Cursor cursor = AppDataBase.rawQuery("select * from doctors where doctorEmail =? and Password =?", new String[]{email, password});
            if (cursor.getCount() > 0) {
                return 2;

            }
            else
                return 0;
        }
        else
            return 0;
    }
    public ArrayList<Doctors> fetchDoctorsData(Cursor cursor){
        ArrayList<Doctors> doctorsList = new ArrayList<>();
        while(!cursor.isAfterLast()){
            Doctors tmp = new Doctors();
            tmp.setUserEmail(cursor.getString(0));
            tmp.setName(cursor.getString(1));
            tmp.setSpecialty(cursor.getString(8));
            tmp.setLocation(cursor.getString(9));
            tmp.setWorkingHoursStart(parseInt(cursor.getString(10)));
            tmp.setWorkingHoursEnd(parseInt(cursor.getString(11)));
            doctorsList.add(tmp);
            cursor.moveToNext();
        }
        return doctorsList;
    }

    public Cursor getAllPendingDoctors(){
        AppDataBase=getReadableDatabase();
        //Cursor[] data=new Cursor[2];
        //getting Doctor data
        Cursor data=AppDataBase.rawQuery("select doctor.username,doctors.specialization ,doctors.starting_hours,doctors.starting_hours,doctors.closing_hours,doctors.city from doctors where users.MembershipType = 2 AND  doctors.doctorEmail ", null);
        if (data!=null){
            data.moveToFirst();
        }

        AppDataBase.close();

        return data;
    }
    @SuppressLint("Recycle")
    public void refuseDoctor(String email){
        AppDataBase= getWritableDatabase();
        AppDataBase.delete("doctors","doctorEmail = ?",new String[] {email});

        AppDataBase.close();
    }
    @SuppressLint("Recycle")
    public void acceptDoctor(String email){
        AppDataBase= getWritableDatabase();
        AppDataBase.execSQL("update doctors set MembershipType = 1 where email =?",new String[] {email});
        AppDataBase.close();
    }
}