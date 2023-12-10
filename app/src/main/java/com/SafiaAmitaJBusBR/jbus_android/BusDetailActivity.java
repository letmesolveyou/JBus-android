package com.SafiaAmitaJBusBR.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class BusDetailActivity extends AppCompatActivity {
    private TextView busName, capacity, price, departure, arrival, facilities, busType;
    private String busNameS, capacityS, priceS, departureS, arrivalS, facilitiesS, busTypeS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_bus_detail);
        busName = findViewById(R.id.name_bus);
        capacity = findViewById(R.id.capacity_bus);
        price = findViewById(R.id.price_bus);
        departure = findViewById(R.id.departure_dropdown);
        arrival = findViewById(R.id.arrival_dropdown);
        facilities = findViewById(R.id.facilities_bus);
        busType = findViewById(R.id.bus_type_dropdown);

        busNameS = AccountBusArrayAdapter.selectedBus.name;
        Integer cap = Integer.valueOf(AccountBusArrayAdapter.selectedBus.capacity);
        capacityS = cap.toString();
        Double pri = Double.valueOf(AccountBusArrayAdapter.selectedBus.price.price);
        priceS = pri.toString();
        departureS = AccountBusArrayAdapter.selectedBus.departure.city.toString();
        arrivalS = AccountBusArrayAdapter.selectedBus.arrival.city.toString();
        facilitiesS = AccountBusArrayAdapter.selectedBus.facilities.toString();
        busTypeS = AccountBusArrayAdapter.selectedBus.busType.toString();

        busName.setText(busNameS);
        capacity.setText(capacityS);
        price.setText(priceS);
        departure.setText(departureS);
        arrival.setText(arrivalS);
        facilities.setText(facilitiesS);
        busType.setText(busTypeS);

        Button back = findViewById(R.id.back_button);
        back.setOnClickListener(v->{
            finish();
        });
    }
}