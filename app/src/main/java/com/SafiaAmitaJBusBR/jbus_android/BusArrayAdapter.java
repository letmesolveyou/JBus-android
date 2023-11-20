package com.SafiaAmitaJBusBR.jbus_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.SafiaAmitaJBusBR.jbus_android.model.Bus;

import java.util.List;

public class BusArrayAdapter extends ArrayAdapter<Bus> {

    private Context context;
    private List<Bus> busList;

    public BusArrayAdapter(@NonNull Context context, List<Bus> busList) {
        super(context, 0, busList);
        this.context = context;
        this.busList = busList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.bus_view, parent, false);
        }

        // Set data to the custom view
        TextView busNameTextView = view.findViewById(R.id.busNameTextView);
        Bus currentBus = busList.get(position);
        busNameTextView.setText(currentBus.name);

        return view;
    }
}