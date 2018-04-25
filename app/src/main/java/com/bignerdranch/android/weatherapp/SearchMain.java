package com.bignerdranch.android.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bignerdranch.android.weatherapp.network.NetworkTest;

import static com.bignerdranch.android.weatherapp.WeatherDashBoard.ZIP_CODE;

public class SearchMain extends AppCompatActivity {

    private EditText zCode;
    private Button zipButton;
    private Context mContext= this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_search);

        zCode = findViewById(R.id.zip_code);
        zipButton = findViewById(R.id.zButton);

        zipCode();
    }

    private void zipCode() {
        zCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ValidateZip.isAValidZipCode(zCode.getText().toString())) {
                    zipButton.setEnabled(true);

                } /*else {
                    Toast.makeText(getApplicationContext(), "Please Enter Correct Zip Code", Toast.LENGTH_LONG).show();
                }*/
            }
        });

        zipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetworkTest.isNetworkAvailable(getApplicationContext())) {
                    //zipButton.setEnabled(true);
                    Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(mContext, WeatherDashBoard.class);
                intent.putExtra(ZIP_CODE, zCode.getText().toString());
                startActivity(intent);
                zCode.setText("");
            }
        });
    }
}
