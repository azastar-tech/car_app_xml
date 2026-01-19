package com.teste.carrent.ActivityPages;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;
import com.teste.carrent.Models.Vehicle;
import com.teste.carrent.R;

public class CarDetailsActivity extends AppCompatActivity {
    private Vehicle vehicle;

    private TextView vehicleTitle;
    private ImageView vehicleImage;
    private TextView vehiclePrice;
    private ConstraintLayout available;
    private ConstraintLayout notAvailable;
    private Button back;
    private Button book;
    private TextView year, manufacturer, model, mileage, seats, type;
    private RadioGroup insuranceOption;
    private String chosenInsurance = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        initComponents();
        listenHandler();
        displayVehicleInfo();
    }

    private void initComponents() {
        vehicle = (Vehicle) getIntent().getSerializableExtra("VEHICLE");
        back = findViewById(R.id.back);
        vehicleTitle = findViewById(R.id.vehicleTitle);
        vehicleImage = findViewById(R.id.vehicleImage);

        available = findViewById(R.id.available);
        notAvailable = findViewById(R.id.notAvailable);

        year = findViewById(R.id.year);
        manufacturer = findViewById(R.id.manufacturer);
        model = findViewById(R.id.model);
        mileage = findViewById(R.id.mileage);
        seats = findViewById(R.id.seats);
        type = findViewById(R.id.type);

        vehiclePrice = findViewById(R.id.vehiclePrice);

        insuranceOption = findViewById(R.id.insuranceOption);

        book = findViewById(R.id.book_this_car);

    }

    private void listenHandler() {

        back.setOnClickListener(v -> finish());

        book.setOnClickListener(v -> {
            Intent informationPage = new Intent(CarDetailsActivity.this, BookingActivity.class);
            informationPage.putExtra("INSURANCEID","");
            informationPage.putExtra("VEHICLEID",vehicle.getVehicleID()+"");
            startActivity(informationPage);
        });

        insuranceOption.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton option = findViewById(checkedId);
            chosenInsurance = option.getText().toString().toLowerCase();
        });
    }
    private void displayVehicleInfo() {
        vehicleTitle.setText(vehicle.fullTitle());

        Picasso.get().load(vehicle.getVehicleImageURL()).into(vehicleImage);

        if(vehicle.isAvailability()){
            available.setVisibility(ConstraintLayout.VISIBLE);
            notAvailable.setVisibility(ConstraintLayout.INVISIBLE);
            book.setEnabled(true);
            book.setBackground(ContextCompat.getDrawable(CarDetailsActivity.this,R.drawable.round_button));
            book.setText("Book This Car");
        }else{
            available.setVisibility(ConstraintLayout.INVISIBLE);
            notAvailable.setVisibility(ConstraintLayout.VISIBLE);
            book.setEnabled(false);
            book.setBackground(ContextCompat.getDrawable(CarDetailsActivity.this,R.drawable.disable_button));
            book.setText("Vehicle Not Available");
        }

        year.setText(vehicle.getYear()+"");
        manufacturer.setText(vehicle.getManufacturer());
        model.setText(vehicle.getModel());
        mileage.setText(vehicle.getMileage()+"");
        seats.setText(vehicle.getSeats()+"");
        type.setText(vehicle.getCategory());

        vehiclePrice.setText("$" + vehicle.getPrice()+"/Day");

    }
}
