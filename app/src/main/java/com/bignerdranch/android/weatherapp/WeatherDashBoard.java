package com.bignerdranch.android.weatherapp;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import android.text.format.DateFormat;

import java.text.DecimalFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.support.v7.app.ActionBarDrawerToggle;

import com.bignerdranch.android.weatherapp.model.WeatherData;
import com.bignerdranch.android.weatherapp.model.WeatherInfo;
import com.bignerdranch.android.weatherapp.network.API_Client;
import com.bignerdranch.android.weatherapp.network.NetworkTest;
import com.bignerdranch.android.weatherapp.network.OpenWeatherMapAPI;


/**
 * Created by berekethaile on 4/23/18.
 */

public class WeatherDashBoard extends AppCompatActivity {

    public static final String BASE_URL = "http://api.openweathermap.org/";
    private static final String OWM_API_KEY = "04a4f6ddca99010416c338c1cf456d70";
    public static final String ZIP_CODE = "Searched_Zip";
    private static final String UNITS = "Imperial";

    private static final int FIRST_DATA = 0;

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;

    private TextView currCondition;
    private TextView currDetails;
    private TextView zipTemperature;
    private TextView wSpeed;
    private TextView pressure;
    private TextView humidity;
    private TextView sunrise;
    private TextView sunset;
    private TextView cityName;

    private OpenWeatherMapAPI mOpenWeatherMapAPI;

    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_dashboard);

        currCondition = findViewById(R.id.current_condition);
        currDetails = findViewById(R.id.current_details);
        zipTemperature = findViewById(R.id.temperature);
        wSpeed = findViewById(R.id.wind_speed);
        pressure = findViewById(R.id.pressure);
        humidity = findViewById(R.id.humidity);
        sunrise = findViewById(R.id.sunrise);
        sunset = findViewById(R.id.sunset);
        cityName = findViewById(R.id.city_name);

        mDrawerList = findViewById(R.id.navList);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle bundle = getIntent().getExtras();
        String currentZip = bundle.getString(ZIP_CODE);

        ActionBar actionBar = this.getSupportActionBar();

        mOpenWeatherMapAPI = API_Client.getClientAPI(BASE_URL).create(OpenWeatherMapAPI.class);
        obtainZipWeather(currentZip);

    }


    private void addDrawerItems() {
        String[] osArray = { "Android", "iOS", "Windows", "OS X", "Linux" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weather_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void obtainZipWeather(final String zipCode) {
        if (NetworkTest.isNetworkAvailable(getApplicationContext())) {
            mOpenWeatherMapAPI.getWeather(zipCode,UNITS, OWM_API_KEY)
                    .enqueue(new Callback<WeatherData>() {
                        @Override
                        public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                            if (response.isSuccessful()) {
                                WeatherInfo weatherInfo = new WeatherInfo(
                                        response.body().getWeather().get(FIRST_DATA).getMain(),
                                        response.body().getWeather().get(FIRST_DATA).getDescription(),
                                        response.body().getMain().getTemp(),
                                        response.body().getWind().getSpeed(),
                                        response.body().getMain().getPressure(),
                                        response.body().getMain().getHumidity(),
                                        response.body().getSys().getSunrise(),
                                        response.body().getSys().getSunset(),
                                        response.body().getName() + ", " +
                                                response.body().getSys().getCountry());
                                populateWeatherInfo(weatherInfo);

                            } else {
                                showAlertDialog("", "Please input correct zip code", "", "Try again");
                            }
                        }

                        @Override
                        public void onFailure(Call<WeatherData> call, Throwable t) {
                            showAlertDialog("", "Please input correct zip code", "", "Try again");
                        }
                    });
        }
    }

    public void showAlertDialog(String zipcode, String title, String positive, String negative) {
        DialogInterface.OnClickListener pListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        };

        DialogInterface.OnClickListener nListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //obtainZipWeather(currentZip);
            }
        };

        ErrorHandler.dialogWindow(this, zipcode, title, positive, pListener, negative, nListener);

    }

    private void populateWeatherInfo(WeatherInfo weatherInfo) {
        currCondition.setText(weatherInfo.getMainCondition());
        currDetails.setText(weatherInfo.getDescriptionCondition());

        String temperature = String.valueOf(new DecimalFormat("#").format(weatherInfo.getTemperature()));
        String temp = temperature + "\u00b0F";
        //SpannableStringBuilder stringBuilder = new SpannableStringBuilder(temp);
        //stringBuilder.setSpan(new TextAppearanceSpan(this, R.style.BoldStyle));
        zipTemperature.setText(temp);
        String wind = String.valueOf(weatherInfo.getWindSpeed()) + " " + "m/s";
        wSpeed.setText(wind);
        String press = String.valueOf(weatherInfo.getPressure()) + " " + "hpa";
        pressure.setText(press);
        String humid = String.valueOf(weatherInfo.getHumidity()) + "%";
        humidity.setText(humid);

        Calendar sunriseCal = Calendar.getInstance();
        sunriseCal.setTimeInMillis(weatherInfo.getSunrise() * 1000);
        String suntime = DateFormat.format("hh:mm", sunriseCal).toString() + "AM";
        sunrise.setText(suntime);

        Calendar sunsetCal = Calendar.getInstance();
        sunsetCal.setTimeInMillis(weatherInfo.getSunset() * 1000);
        String downtime = DateFormat.format("hh:mm", sunsetCal).toString() + "PM";
        sunset.setText(downtime);

        cityName.setText(String.valueOf(weatherInfo.getCityName()));
    }

}
