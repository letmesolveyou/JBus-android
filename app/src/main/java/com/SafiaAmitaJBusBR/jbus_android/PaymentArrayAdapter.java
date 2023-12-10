package com.SafiaAmitaJBusBR.jbus_android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.SafiaAmitaJBusBR.jbus_android.model.BaseResponse;
import com.SafiaAmitaJBusBR.jbus_android.model.Bus;
import com.SafiaAmitaJBusBR.jbus_android.model.Invoice;
import com.SafiaAmitaJBusBR.jbus_android.model.Payment;
import com.SafiaAmitaJBusBR.jbus_android.request.BaseApiService;
import com.SafiaAmitaJBusBR.jbus_android.request.UtilsApi;

import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentArrayAdapter extends ArrayAdapter<Payment> {
    public PaymentArrayAdapter(Context context, List<Payment> payments) {
        super(context, 0, payments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Payment p = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.payment_view, parent, false);
        }

        TextView bus = convertView.findViewById(R.id.bus);
        TextView seat = convertView.findViewById(R.id.seat);
        TextView status = convertView.findViewById(R.id.status);

        String name = "";
        for (Bus i : MainActivity.listBus){
            if (i.id == p.getBusId()) {
                name = i.name;
                break;
            }
        }
        bus.setText(name);

        String seats = "";
        for (String i : p.busSeat) {
            seats = seats + i;
            }
        seat.setText(seats);

        status.setText(p.status.name());

        return convertView;
    }
}