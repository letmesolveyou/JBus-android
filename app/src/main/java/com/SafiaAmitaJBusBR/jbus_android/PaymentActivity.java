package com.SafiaAmitaJBusBR.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.SafiaAmitaJBusBR.jbus_android.model.BaseResponse;
import com.SafiaAmitaJBusBR.jbus_android.model.Bus;
import com.SafiaAmitaJBusBR.jbus_android.model.Payment;
import com.SafiaAmitaJBusBR.jbus_android.model.Schedule;
import com.SafiaAmitaJBusBR.jbus_android.request.BaseApiService;
import com.SafiaAmitaJBusBR.jbus_android.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    private List<Payment> listPayment = new ArrayList<>();
    private ListView paymentListView = null;
    private BaseApiService mApiService;
    private PaymentArrayAdapter adapter;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getSupportActionBar().hide();

        mContext = this;
        mApiService = UtilsApi.getApiService();
        paymentListView = findViewById(R.id.payment_list);

        Button backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(v->{
            moveActivity(this, MainActivity.class);
        });

        //handlePayment();
        listPayment=Payment.samplePaymentList(5);

        adapter = new PaymentArrayAdapter(mContext, listPayment);
        paymentListView.setAdapter(adapter);
    }
    public void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    protected void handlePayment() {
        mApiService.getBuyerPayment(LoginActivity.loggedAcccount.id).enqueue(new Callback<BaseResponse<List<Payment>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Payment>>> call, Response<BaseResponse<List<Payment>>> response) {
                // handle the potential 4xx & 5xx error
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " +
                            response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<List<Payment>> res = response.body();

                // if success finish this activity (back to login activity)
                if(res.success){
                    List <Payment> tmp = res.payload;
                    listPayment.clear();
                    for (Payment i : tmp) {
                        listPayment.add(i);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Payment>>> call, Throwable t) {
                Log.e("NetworkError", "Error: " + t.getMessage(), t);
                Toast.makeText(mContext, "Problem with the server",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}