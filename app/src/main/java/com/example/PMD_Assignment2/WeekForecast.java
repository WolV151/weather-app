package com.example.PMD_Assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class WeekForecast extends AppCompatActivity {
    Bundle bundle;
    RecycleViewAdapter recycleViewAdapter;
    RecyclerView recyclerView;
    ArrayList<Weather> locTodayWeahter = new ArrayList<>();
    private WeatherData newWeatherData;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_forecast);

        bundle = getIntent().getExtras();
        setTitle("Weekly Forecast for " + bundle.getString("title"));

        locTodayWeahter.clear();

        String url = "https://www.metaweather.com/api/location/" + bundle.getString("woeid") + "/";
        standardJsonObject(url, bundle.getString("woeid"));


        recyclerView = findViewById(R.id.rvForecast);

        recyclerView.setLayoutManager(new LinearLayoutManager(WeekForecast.this));

        recycleViewAdapter = new RecycleViewAdapter(WeekForecast.this, locTodayWeahter, true);

        recyclerView.setAdapter(recycleViewAdapter);

    }

    public void standardJsonObject(String url, String woeid) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new
                Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson1 = new Gson();
                        newWeatherData = (WeatherData) gson1.fromJson(String.valueOf((response)), WeatherData.class);

                        ArrayList<Weather> entry_today = newWeatherData.getWeathers();

                        for (Weather entry : entry_today){
                            entry.setTitle(entry.getApplicable_date());
                            entry.setWoeid(woeid);
                            locTodayWeahter.add(entry);
                        }

                        System.out.println(locTodayWeahter.toString());
                        recycleViewAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WeekForecast.this, "Volley Error ", Toast.LENGTH_SHORT).show();
                System.out.println("VOLLEY ERROR");
            }
        });
        RequestQueue rQueue1 = Volley.newRequestQueue(WeekForecast.this);
        rQueue1.add(request);
        System.out.println("mylistweather: " + newWeatherData);

    }

}