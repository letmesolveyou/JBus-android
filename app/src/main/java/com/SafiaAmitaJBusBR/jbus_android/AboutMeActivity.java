package com.SafiaAmitaJBusBR.jbus_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AboutMeActivity extends AppCompatActivity {
    private Button topUpButton =null;
    private TextView usernameEdit;
    private TextView emailEdit;
    private TextView balanceEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        TextView usernameTextView = findViewById(R.id.username);
        TextView emailTextView = findViewById(R.id.email);
        TextView balanceTextView = findViewById(R.id.balance);

        setInitialValues(usernameTextView, emailTextView, balanceTextView);
    }

    private void setInitialValues(TextView usernameTextView, TextView emailTextView, TextView balanceTextView) {
        // Set the initial values for the TextViews
        usernameTextView.setText("Safia Amita");
        emailTextView.setText("safia.amita@gmail.com");
        balanceTextView.setText("$1000000000.00");
    }
}


}