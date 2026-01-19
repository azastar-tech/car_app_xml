package com.teste.carrent.ActivityPages;

import android.app.DatePickerDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.room.Room;


import com.teste.carrent.DataBase.CustomerDao;
import com.teste.carrent.DataBase.Project_Database;
import com.teste.carrent.Models.Customer;
import com.teste.carrent.R;

import java.util.Random;


public class RegistrationActivity extends AppCompatActivity{
    private Button register;
    private TextView login;

    private TextView expiryDate;
    private TextView dob;
    private CustomerDao customerDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initComponents();

        clickListenHandler();
    }

    private void initComponents(){
        register = findViewById(R.id.register);

        login = findViewById(R.id.login);

        expiryDate = findViewById(R.id.expiryDate);

        dob = findViewById(R.id.dob);

        customerDao = Room.databaseBuilder(getApplicationContext(), Project_Database.class, "car_rental_db").allowMainThreadQueries()
                .build()
                .customerDao();

    }
    private void clickListenHandler(){

        expiryDate.setOnClickListener(v -> openCalendar(expiryDate));

        dob.setOnClickListener(v -> openCalendar(dob));


        login.setOnClickListener(v -> {
            Intent registerPage = new Intent(RegistrationActivity.this,LoginActivity.class);
            startActivity(registerPage);
        });


        register.setOnClickListener(v -> {
            Customer customer = createCustomerObject();

            if(customerDao != null) {

                if(customer != null) {
                    customerDao.insert(customer);
                    toast("Registration Successful");
                    finish();
                }
            }
        });
    }

    private void openCalendar(final TextView dateFieldButton) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this);

        datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
            String date = year + "-" + month + "-" + dayOfMonth;
            dateFieldButton.setText(date);
        });

        datePickerDialog.show();
    }


    private Customer createCustomerObject(){

        String firstName = ((EditText)findViewById(R.id.firstName)).getText().toString();
        String lastName = ((EditText)findViewById(R.id.lastName)).getText().toString();

        String email = ((EditText)findViewById(R.id.email)).getText().toString();

        String driverLicense = ((EditText)findViewById(R.id.license)).getText().toString();
        String expiry = expiryDate.getText().toString();
        String dateOfBirth = dob.getText().toString();

        String phoneNumber = ((EditText)findViewById(R.id.phoneNumber)).getText().toString();

        String street = ((EditText)findViewById(R.id.street)).getText().toString();
        String city = ((EditText)findViewById(R.id.city)).getText().toString();
        String postalCode = ((EditText)findViewById(R.id.postalCode)).getText().toString();

        String password = ((EditText)findViewById(R.id.password)).getText().toString();
        String confirm_password = ((EditText)findViewById(R.id.confirmPassword)).getText().toString();

        boolean success = fieldRequiredCheck(firstName,lastName,email,driverLicense,expiry,dateOfBirth,phoneNumber,street,city,postalCode);

        int id = generateID();

        while(customerDao.exist(id)){
            id = generateID();
        }

        if(success){
            if(!password.isEmpty() && password.equals(confirm_password)) {
                return new Customer(id,firstName, lastName,
                        email, driverLicense, expiry,
                        dateOfBirth, phoneNumber, street,
                        city, postalCode, password
                );
            }else{
                toast("Password do not match");
            }
        }else{
            toast("Incomplete Form");
        }

        return null;
    }

    private boolean fieldRequiredCheck(String firstName,String lastName, String email, String driverLicense, String expiry, String dateOfBirth, String phoneNumber, String street, String city, String postalCode) {
        return  !firstName.isEmpty() && !lastName.isEmpty() &&
                !email.isEmpty() && !driverLicense.isEmpty() && !expiry.isEmpty() &&
                !dateOfBirth.isEmpty() && !phoneNumber.isEmpty() && !street.isEmpty() &&
                !city.isEmpty() && !postalCode.isEmpty();
    }

    private void toast(String txt){
        Toast toast = Toast.makeText(getApplicationContext(),txt,Toast.LENGTH_SHORT);
        toast.show();
    }

    private int generateID(){
        Random rnd = new Random();
        return 202000 + rnd.nextInt(65)+10;
    }

}
