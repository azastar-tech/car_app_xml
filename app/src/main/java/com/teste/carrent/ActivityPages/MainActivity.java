package com.teste.carrent.ActivityPages;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.teste.carrent.FragmentPages.AccountFragment;
import com.teste.carrent.FragmentPages.BookingFragment;
import com.teste.carrent.FragmentPages.CarFragment;
import com.teste.carrent.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private BookingFragment bookingFragment;
    private AccountFragment accountFragment;
    private CarFragment carFragment;
    private String loggedInCustomerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        setupBottomNavigation();

        setFragment(carFragment, loggedInCustomerID);
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_vehicle) {
                setFragment(carFragment, loggedInCustomerID);
            } else if (id == R.id.nav_booking) {
                setFragment(bookingFragment, loggedInCustomerID);

            } else if (id == R.id.nav_account) {
                setFragment(accountFragment, loggedInCustomerID);
            }
            return true;
        });

    }
    private void setFragment(Fragment fragment, String data) {
        Bundle bundle = new Bundle();
        bundle.putString("CUSTOMERID", data);
        fragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framelayout, fragment)
                .commit();
    }

    private void initComponents() {
        bottomNavigationView = findViewById(R.id.bottom_nav);

        bookingFragment = new BookingFragment();
        accountFragment = new AccountFragment();
        carFragment = new CarFragment();

        loggedInCustomerID = getIntent().getStringExtra("CUSTOMERID");
    }
}
