package com.teste.carrent.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.teste.carrent.Converter.Converter;
import com.teste.carrent.Models.Booking;
import com.teste.carrent.Models.Customer;
import com.teste.carrent.Models.Vehicle;


@Database(entities = {Customer.class,  Vehicle.class,Booking.class,}, version = 1)
@TypeConverters({Converter.class})
public abstract class Project_Database extends RoomDatabase {
    public abstract CustomerDao customerDao();
    public abstract VehicleDao vehicleDao();
    public abstract BookingDao bookingDao();
}

