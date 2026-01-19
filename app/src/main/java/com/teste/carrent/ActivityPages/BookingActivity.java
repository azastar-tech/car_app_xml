package com.teste.carrent.ActivityPages;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.teste.carrent.DataBase.BookingDao;
import com.teste.carrent.DataBase.CustomerDao;
import com.teste.carrent.DataBase.Project_Database;
import com.teste.carrent.Models.Customer;
import com.teste.carrent.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class BookingActivity extends AppCompatActivity {
    private TextView pickupDate, returnDate;
    private TextView pickupTime, returnTime;
    private Calendar _pickup;
    private Calendar _return;
    private EditText firstName, lastName, email, phoneNumber;
    private RadioGroup customerTitle;
    private BookingDao bookingDao;
    private CustomerDao customerDao;

    String mrMs = "mr";
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM, d yyyy", Locale.ENGLISH);
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
    private Button back, continueBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        initComponents();
        listenHandler();
    }
    private void initComponents() {
        back = findViewById(R.id.back);

        continueBooking = findViewById(R.id.continueBooking);

        pickupDate = findViewById(R.id.pickupDate);
        pickupTime = findViewById(R.id.pickupTime);

        returnDate = findViewById(R.id.returnDate);
        returnTime = findViewById(R.id.returnTime);

        customerTitle = findViewById(R.id.mrMsTitle);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phoneNumber);

        _pickup = Calendar.getInstance();
        _return = Calendar.getInstance();

        pickupDate.setText(dateFormat.format(_pickup.getTime()));
        pickupTime.setText(timeFormat.format(_pickup.getTime()));

        returnDate.setText(dateFormat.format(_return.getTime()));
        returnTime.setText(timeFormat.format(_return.getTime()));


        customerDao = Room.databaseBuilder(getApplicationContext(), Project_Database.class, "car_rental_db").allowMainThreadQueries()
                .build()
                .customerDao();
        bookingDao = Room.databaseBuilder(getApplicationContext(), Project_Database.class, "car_rental_db").allowMainThreadQueries()
                .build()
                .bookingDao();
    }

    private void listenHandler() {

        back.setOnClickListener(v -> finish());

        continueBooking.setOnClickListener(v -> {
            Intent bookingSummaryPage = new Intent(BookingActivity.this, BookingCompleteActivity.class);
            startActivity(bookingSummaryPage);
        });

        pickupDate.setOnClickListener(v -> openCalendar(_pickup,pickupDate));
        pickupTime.setOnClickListener(v -> openTimePicker(_pickup, pickupTime));

        returnDate.setOnClickListener(v -> openCalendar(_return,returnDate));
        returnTime.setOnClickListener(v -> openTimePicker(_return, returnTime));

        continueBooking.setOnClickListener(v -> validate());
    }

    private void validate() {
        customerTitle.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton title = findViewById(checkedId);
            mrMs = title.getText().toString().toLowerCase();
        });

        String _firstName = firstName.getText().toString().toLowerCase();
        String _lastName = lastName.getText().toString().toLowerCase();
        String _email= email.getText().toString().toLowerCase();
        String _phoneNumber = phoneNumber.getText().toString();

        if(!fieldCheck(_firstName,_lastName,_email,_phoneNumber)) {
            toast("Incomplete Form");
            return;
        }

        Customer customer = customerDao.findUser(_firstName,_lastName,_email);

        if(customer == null){
            toast("Customer Do Not Exist");
            return;
        }

        customerDao.setTitle(mrMs,customer.getCustomerID());

        int bookingID = generateID();
        while(bookingDao.exist(bookingID)){
            bookingID = generateID();
        }


        Intent bookingSummary = new Intent(BookingActivity.this,BookingCompleteActivity.class);

        startActivity(bookingSummary);

    }

    private boolean fieldCheck(String _firstName, String _lastName, String _email, String _phoneNumber) {
        return  !_firstName.isEmpty() && !_lastName.isEmpty() &&
                !_email.isEmpty() && !_phoneNumber.isEmpty();
    }

    private void openCalendar(final Calendar rentalDate, final TextView rentalDateText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this);

        datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
            rentalDate.set(year,month,dayOfMonth);
            rentalDateText.setText(dateFormat.format(rentalDate.getTime()));
        });

        datePickerDialog.show();
    }

    private void openTimePicker(final Calendar rentalTime, final TextView rentalTimeText){
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);



        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            rentalTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
            rentalTime.set(Calendar.MINUTE,minute);

            rentalTimeText.setText(timeFormat.format(rentalTime.getTime()));
        },hour,min,false);

        timePickerDialog.show();

        calendar.getTime();
    }

    private void toast(String txt){
        Toast toast = Toast.makeText(getApplicationContext(),txt,Toast.LENGTH_SHORT);
        toast.show();
    }
    private int generateID(){
        Random rnd = new Random();
        int bound = 499 %100;
        return rnd.nextInt(bound)+ 400;
    }

}
