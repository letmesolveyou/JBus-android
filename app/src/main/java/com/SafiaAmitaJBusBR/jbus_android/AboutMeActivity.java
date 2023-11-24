package com.SafiaAmitaJBusBR.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.SafiaAmitaJBusBR.jbus_android.model.BaseResponse;
import com.SafiaAmitaJBusBR.jbus_android.request.BaseApiService;
import com.SafiaAmitaJBusBR.jbus_android.request.UtilsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeActivity extends AppCompatActivity {
    private Button topUpButton =null;
    private TextView usernameEdit, emailEdit, balanceEdit, profileInitial;
    private EditText topUpBalance;
    private BaseApiService mApiService;
    private Context mContext;
    private String userEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        TextView email = findViewById(R.id.email);
        TextView name = findViewById(R.id.username);
        TextView balance = findViewById(R.id.balance);
        topUpBalance = findViewById(R.id.topUp);
        profileInitial = findViewById(R.id.initial);

        String initialS = LoginActivity.loggedAccount.name.toString().charAt(0) + "";
        String nameS = LoginActivity.loggedAccount.name.toString();
        String emailS = LoginActivity.loggedAccount.email.toString();
        Double doub = LoginActivity.loggedAccount.balance;
        String balanceS = doub.toString();

        Button topUp = findViewById(R.id.topUp_button);

        profileInitial.setText(initialS);
        name.setText(nameS);
        email.setText(emailS);
        balance.setText(balanceS);
        mContext = this;
        mApiService = UtilsApi.getApiService();

        topUp.setOnClickListener(v-> {
            handleTopup();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    public void moveActivity(Context ctx, Class<?> cls){
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    private void viewToast(Context ctx, String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void handleTopup() {
        String getBalance = topUpBalance.getText().toString();
        double newBalance = Double.valueOf(getBalance);

        mApiService.topUp(LoginActivity.loggedAccount.id, newBalance).enqueue(new Callback<BaseResponse<Double>>(){
            @Override
            public void onResponse(Call<BaseResponse<Double>> call, Response<BaseResponse<Double>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext, "Application error " +
                            response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseResponse<Double> res = response.body();
                if (res.success) {
                    finish();
                    LoginActivity.loggedAccount.balance = LoginActivity.loggedAccount.balance + newBalance;
                    viewToast(mContext, "TopUp Berhasil");
                    Toast.makeText(mContext, res.message, Toast.LENGTH_SHORT).show();
                    startActivity(getIntent());
                }
            }
            @Override
            public void onFailure(Call<BaseResponse<Double>> call, Throwable t) {
                Toast.makeText(mContext, "Problem with the server",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}