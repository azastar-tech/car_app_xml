package com.teste.carrent.ActivityPages;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.teste.carrent.FragmentPages.AccountFragment;
import com.teste.carrent.FragmentPages.BookingFragment;
import com.teste.carrent.R;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

      private BookingFragment bookingFragment;
    private AccountFragment accountFragment;

    private String loggedInCustomerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);

        initComponents();
        setFragment(vehicleCategoryFragment);

        clickListener();

    }

    private void clickListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.nav_vehicle:
                        setFragment(vehicleCategoryFragment);
                        return true;

                    case R.id.nav_booking:
                        setFragment(bookingFragment, loggedInCustomerID);
                        return true;

                    case R.id.nav_account :
                        setFragment(accountFragment, loggedInCustomerID);
                        return true;
                }

                return false;
            }
        });
    }

    private void setFragment(Fragment fragment,String Data) {
        Bundle bundle = new Bundle();
        bundle.putString("CUSTOMERID",Data);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,fragment);
        fragmentTransaction.commit();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,fragment);
        fragmentTransaction.commit();
    }

    private void initComponents(){
        bottomNavigationView = findViewById(R.id.bottom_nav);
        frameLayout = findViewById(R.id.framelayout);

        vehicleCategoryFragment = new VehicleCategoryFragment();
        bookingFragment= new BookingFragment();
        accountFragment = new AccountFragment();

        loggedInCustomerID = getIntent().getStringExtra("CUSTOMERID");

    }
}
