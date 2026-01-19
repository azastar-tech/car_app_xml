package com.teste.carrent.ActivityPages;

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
import com.teste.carrent.Session.Session;
import com.teste.carrent.R;


public class LoginActivity extends AppCompatActivity {
    private TextView register;
    private Button login;
    private EditText email;
    private EditText password;

    private Project_Database db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        boolean isLoggedIn = Boolean.parseBoolean(Session.read(LoginActivity.this, "isLoggedIn", "false"));
        if(isLoggedIn){
            Intent homePage = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(homePage);
        }

        initComponents();
        clickListenHandler();
    }

    private void initComponents(){
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        db = Room.databaseBuilder(getApplicationContext(), Project_Database.class, "car_rental_db").allowMainThreadQueries().build();
    }

    private void clickListenHandler(){

        register.setOnClickListener(v -> {
            Intent registerPage = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(registerPage);
        });

        login.setOnClickListener(v -> {
            CustomerDao customerDao = db.customerDao();
            Customer check = customerDao.findUser(email.getText().toString(),password.getText().toString());

            if(check != null){
                Session.save(LoginActivity.this,"customerID",check.getCustomerID()+"");
                Session.save(LoginActivity.this,"isLoggedIn","true");

                Intent homePage = new Intent(LoginActivity.this,MainActivity.class);
                homePage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(homePage);
            }else{
                toast();
            }
        });

    }

    private void toast(){
        Toast toast = Toast.makeText(getApplicationContext(), "Unsuccessful",Toast.LENGTH_SHORT);
        toast.show();
    }
}
