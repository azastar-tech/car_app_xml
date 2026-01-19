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

import com.teste.carrent.ActivityPages.CarDetailsActivity;
import com.teste.carrent.Adapter.CarAdapter;
import com.teste.carrent.R;

import java.util.ArrayList;

public class CarFragment extends Fragment implements CarAdapter.onVehicleListener{
    private ArrayList<com.teste.carrent.Models.Vehicle> list;
    public void VehicleFragment() {
        //Take All You can
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vehicle, container, false);
        initComponents(view);
        
        return view;
    }

    private void initComponents(View view) {

        String selectVehicleCategory = getArguments().getString("CATEGORY");

        com.teste.carrent.DataBase.VehicleDao vehicleDao = Room.databaseBuilder(getContext(), com.teste.carrent.DataBase.Project_Database.class, "car_rental_db").allowMainThreadQueries()
                .build()
                .vehicleDao();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = (ArrayList<com.teste.carrent.Models.Vehicle>) vehicleDao.getCategoryVehicle(selectVehicleCategory);
        CarAdapter adapter = new CarAdapter(getContext(), list, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(int position) {
        Intent vehicleInfoPage = new Intent(getActivity(), CarDetailsActivity.class);
        vehicleInfoPage.putExtra("VEHICLE",list.get(position));
        startActivity(vehicleInfoPage);
    }

    private void toast(String txt){
        Toast toast = Toast.makeText(getContext(),txt,Toast.LENGTH_SHORT);
        toast.show();
    }
}
