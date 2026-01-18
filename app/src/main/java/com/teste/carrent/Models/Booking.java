package com.teste.carrent.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity(primaryKeys = {"bookingID","customerID"})
public class Booking implements Serializable {

    private int bookingID;

    private Calendar pickupDate;
    private Calendar returnDate;

    private String bookingStatus;

    private int customerID;

    private int vehicleID;


    public Booking(int bookingID, Calendar pickupDate, Calendar returnDate, String bookingStatus, int customerID, int administratorID, int billingID, int vehicleID, String insuranceID) {
        this.bookingID = bookingID;
        this.pickupDate = pickupDate;
        this.returnDate = returnDate;
        this.bookingStatus = bookingStatus;
        this.customerID = customerID;
        this.vehicleID = vehicleID;
    }

@NonNull
    public String toString(){
        SimpleDateFormat format = new SimpleDateFormat("MMMM, d yyyy hh:mm a");
        return  "\n" +
                "BookingID:         " + bookingID + "\n" +
                "Pickup Date:       " + format.format(pickupDate.getTime()) + "\n" +
                "Return Date:       " + format.format(returnDate.getTime()) + "\n" +
                "Status:            " + bookingStatus + "\n" +
                "CustomerID:        " + customerID ;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public Calendar getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Calendar pickupDate) {
        this.pickupDate = pickupDate;
    }

    public Calendar getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Calendar returnDate) {
        this.returnDate = returnDate;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getPickupTime(){
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a MMMM, d yyyy");
        return format.format(pickupDate.getTime());
    }

    public String getReturnTime(){
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a MMMM, d yyyy");
        return format.format(returnDate.getTime());
    }
}

