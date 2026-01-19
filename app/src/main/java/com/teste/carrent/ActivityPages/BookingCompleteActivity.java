package com.teste.carrent.ActivityPages;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.teste.carrent.DataBase.CustomerDao;
import com.teste.carrent.DataBase.Project_Database;
import com.teste.carrent.DataBase.VehicleDao;
import com.teste.carrent.Models.Booking;
import com.teste.carrent.Models.Customer;
import com.teste.carrent.Models.Vehicle;
import com.teste.carrent.R;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class BookingCompleteActivity extends AppCompatActivity {
    private Button back;
    private TextView name, email, phoneNumber;
    private TextView bookingID, vehicleName, rate, totalDays, _pickup, _return, totalCost;
    private CustomerDao customerDao;
    private Booking booking;


    private Vehicle vehicle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_complete);

        initComponents();
        listenHandler();
        displayCustomerInformation();
        displaySummary();
        displayTotalCost();
    }

    private void initComponents() {
        back = findViewById(R.id.back);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phoneNumber);


        vehicleName = findViewById(R.id.vehicleName);
        rate = findViewById(R.id.rate);
        totalDays = findViewById(R.id.totalDays);
        _pickup = findViewById(R.id.pickup);
        _return = findViewById(R.id.dropoff);

        totalCost = findViewById(R.id.totalCost);


        customerDao = Room.databaseBuilder(getApplicationContext(), Project_Database.class, "car_rental_db").allowMainThreadQueries()
                .build()
                .customerDao();
        VehicleDao vehicleDao = Room.databaseBuilder(getApplicationContext(), Project_Database.class, "car_rental_db").allowMainThreadQueries()
                .build()
                .vehicleDao();


        booking = (Booking) getIntent().getSerializableExtra("BOOKING");

        assert booking != null;
        vehicle = vehicleDao.findVehicle(booking.getVehicleID());

        bookingID = findViewById(R.id.bookingID);
    }

    private void listenHandler() {
        back.setOnClickListener(v -> {
            Intent homePage = new Intent(BookingCompleteActivity.this,MainActivity.class);
            startActivity(homePage);
//                finish();
        });
    }

    private void displayCustomerInformation() {
        Customer customer = customerDao.findUser(booking.getCustomerID());

        name.setText(customer.getFullName());
        email.setText(customer.getEmail());
        phoneNumber.setText(customer.getPhoneNumber());

        bookingID.setText("BookingID: " + booking.getBookingID());
    }

    private void displaySummary(){

        vehicleName.setText(vehicle.fullTitle());
        rate.setText("XAF "+vehicle.getPrice()+"/Day");
        totalDays.setText(getDayDifference(booking.getPickupDate(),booking.getReturnDate())+" Days");
        _pickup.setText(booking.getPickupTime());
        _return.setText(booking.getReturnTime());
    }

    private void displayTotalCost(){
        double cost = calculateTotalCost();
        totalCost.setText("XAF "+cost);
    }

    private long getDayDifference(Calendar start, Calendar end){
        return ChronoUnit.DAYS.between(start.toInstant(), end.toInstant())+2;
    }

    private double calculateTotalCost(){
        long _days = getDayDifference(booking.getPickupDate(),booking.getReturnDate());
        double _vehicleRate = vehicle.getPrice();
        return (_days*_vehicleRate) + 2000;
    }

    public void onBackPressed(){
        super.onBackPressed();
        Intent homepage = new Intent(getApplicationContext(), MainActivity.class);
        homepage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homepage);
    }
}
