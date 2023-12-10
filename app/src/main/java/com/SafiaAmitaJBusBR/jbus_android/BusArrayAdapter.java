package com.SafiaAmitaJBusBR.jbus_android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.SafiaAmitaJBusBR.jbus_android.model.Bus;

import java.util.List;

public class BusArrayAdapter extends ArrayAdapter<Bus> {
        public static Bus mainSelectedBus;
        private Context mContext;

        public BusArrayAdapter(@NonNull Context context, List<Bus> list) {
            super(context, 0, list);
            mContext = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View currentItemView = convertView;
            if (currentItemView == null) {
                currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.bus_view_main, parent, false);
            }
            Bus bus = getItem(position);
            if (bus != null) {
                TextView textView1 = currentItemView.findViewById(R.id.text_view);
                textView1.setText(bus.name);
                Button busDetail = currentItemView.findViewById(R.id.bus_detail);
                busDetail.setOnClickListener(v -> {
                    mainSelectedBus = getItem(position);
                    Intent intent = new Intent(mContext, MakeBookingActivity.class);
                    mContext.startActivity(intent);
                });
            }
            return currentItemView;
        }
    }