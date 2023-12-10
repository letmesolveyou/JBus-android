package com.SafiaAmitaJBusBR.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.SafiaAmitaJBusBR.jbus_android.model.Account;
import com.SafiaAmitaJBusBR.jbus_android.model.BaseResponse;
import com.SafiaAmitaJBusBR.jbus_android.model.Bus;
import com.SafiaAmitaJBusBR.jbus_android.model.BusType;
import com.SafiaAmitaJBusBR.jbus_android.model.Facility;
import com.SafiaAmitaJBusBR.jbus_android.model.Renter;
import com.SafiaAmitaJBusBR.jbus_android.model.Station;
import com.SafiaAmitaJBusBR.jbus_android.request.BaseApiService;
import com.SafiaAmitaJBusBR.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRenterActivity extends AppCompatActivity {
    private BaseApiService mApiService;
    private Context mContext;
    private EditText commpanyName, address, phoneNumber;
    private Button registerCompany;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_renter);

        mContext = this;
        mApiService = UtilsApi.getApiService();

        commpanyName = this.findViewById(R.id.company_name);
        address = this.findViewById(R.id.address);
        phoneNumber = this.findViewById(R.id.phonenumber);
        registerCompany = this.findViewById(R.id.button_register);

        registerCompany.setOnClickListener(v->{
            handleRegisterCompany();
        });
    }

    protected void handleRegisterCompany() {
        // handling empty field
        String companyNameS = commpanyName.getText().toString();
        String addressS = address.getText().toString();
        String phoneNumberS = phoneNumber.getText().toString();

        if (companyNameS.isEmpty() || addressS.isEmpty() || phoneNumberS.isEmpty()) {
            Toast.makeText(mContext, "Field cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        mApiService.registerRenter(LoginActivity.loggedAcccount.id, companyNameS, addressS, phoneNumberS).enqueue(new Callback<BaseResponse<Renter>>() {
            @Override
            public void onResponse(Call<BaseResponse<Renter>> call, Response<BaseResponse<Renter>> response) {
                // handle the potential 4xx & 5xx error
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                BaseResponse<Renter> res = response.body();
                // if success finish this activity (back to AboutMe activity)
                if (res.success) mContext.startActivity(new Intent(mContext, AboutMeActivity.class));
                Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BaseResponse<Renter>> call, Throwable t) {

            }
        });
    }


}