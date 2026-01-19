package com.teste.carrent.FragmentPages;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.teste.carrent.DataBase.BookingDao;
import com.teste.carrent.DataBase.Project_Database;
import com.teste.carrent.Models.Booking;
import com.teste.carrent.R;
import com.teste.carrent.Session.Session;

import java.util.ArrayList;


public class BookingFragment extends Fragment implements com.example.carrentalapp.Adapter.BookingAdapter.onBookingListener{
    private ArrayList<Booking> bookings;

    public BookingFragment() {
        // Take All You Can
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        BookingDao bookingDao = Room.databaseBuilder(getContext(), Project_Database.class, "car_rental_db").allowMainThreadQueries()
                .build()
                .bookingDao();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        int customerID = Integer.parseInt(Session.read(getContext(), "customerID", "-1"));

        bookings = (ArrayList<Booking>) bookingDao.getAllCustomerBookings(customerID);
        com.example.carrentalapp.Adapter.BookingAdapter bookingAdapter = new com.example.carrentalapp.Adapter.BookingAdapter(getContext(), bookings, this);
        recyclerView.setAdapter(bookingAdapter);
    }

    @Override
    public void onClick(int position) {
        int bookingID = bookings.get(position).getBookingID();
        Intent viewBooking = new Intent(getContext(), ViewBookingActivity.class);
        viewBooking.putExtra("BOOKING_ID",""+bookingID);
        startActivity(viewBooking);
    }
    private void toast(String txt){
        Toast toast = Toast.makeText(getContext(),txt,Toast.LENGTH_SHORT);
        toast.show();
    }
}
