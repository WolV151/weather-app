package com.example.PMD_Assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity{
    Intent intent;
    RecycleViewAdapter recycleViewAdapter;
    RecyclerView recyclerView;
    ArrayList<HashMap<String, String>> userList;
    ArrayList<Weather> locTodayWeahter = new ArrayList<>();

    private WeatherData newWeatherData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHandler db = new DBHandler(this);
        userList = db.GetLocations();

//        locTodayWeahter.clear();
//        for (HashMap<String, String> entry : userList) {
//            String url = "https://www.metaweather.com/api/location/" + entry.get("woeid") + "/";
//            standardJsonObject(url, entry.get("title"), entry.get("woeid"));
//        }

        recyclerView = findViewById(R.id.rvLocation);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        recycleViewAdapter = new RecycleViewAdapter(MainActivity.this, locTodayWeahter, false);
        recyclerView.setAdapter(recycleViewAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, AddStuff.class);
                startActivity(intent);
            }
        });


    }
//
//    @Override
//    protected void onStart(){
//        super.onStart();
//        recyclerView = findViewById(R.id.rvLocation);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        recycleViewAdapter = new RecycleViewAdapter(this, locTodayWeahter, userList);
//
//        recyclerView.setAdapter(recycleViewAdapter);
//
//
//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                intent = new Intent(MainActivity.this, AddStuff.class);
//                startActivity(intent);
//            }
//        });
//    }


    public void standardJsonObject(String url, String title, String woeid) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new
                Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson1 = new Gson();
                        newWeatherData = (WeatherData) gson1.fromJson(String.valueOf((response)), WeatherData.class);

                        Weather entry_today = newWeatherData.getWeathers().get(0);
                        entry_today.setTitle(title);
                        entry_today.setWoeid(woeid);
                        locTodayWeahter.add(entry_today);
                        System.out.println(locTodayWeahter.toString());
                        recycleViewAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Volley Error ", Toast.LENGTH_SHORT).show();
                System.out.println("VOLLEY ERROR");
            }
        });
        RequestQueue rQueue1 = Volley.newRequestQueue(MainActivity.this);
        rQueue1.add(request);
        System.out.println("mylistweather: " + newWeatherData);

    }
















//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        getMenuInflater().inflate(R.menu.row_menu, menu);
//    }

//    @Override
//    public boolean onContextItemSelected(@NonNull MenuItem item) {
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
//        switch (item.getItemId()) {
//            case R.id.load:
//                intent = new Intent(MainActivity.this, AddStuff.class);
//                String woeid = ((TextView)info.targetView.findViewById(R.id.woeid)).getText().toString();
//                intent.putExtra("woeid", woeid);
//                startActivity(intent);
//                return true;
//            case R.id.delete:
//                DBHandler db = new DBHandler(MainActivity.this);
//                String title = ((TextView)info.targetView.findViewById(R.id.title)).getText().toString();
//                db.DeleteLocation(title);
//                finish();
//                overridePendingTransition(0, 0);
//                startActivity(getIntent());
//                overridePendingTransition(0, 0);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    @Override
    public void onResume() {  // gets called after onCreate(), so can be used to populate the list even after oCreate()
        super.onResume();
        DBHandler db = new DBHandler(this);
        userList.clear();
        locTodayWeahter.clear();
        userList = db.GetLocations();

        for (HashMap<String, String> entry : userList) {
            String url = "https://www.metaweather.com/api/location/" + entry.get("woeid") + "/";
            standardJsonObject(url, entry.get("title"), entry.get("woeid"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sql_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.change_theme:
                int flag = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                if (flag == Configuration.UI_MODE_NIGHT_YES){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }

                return true;
            case R.id.clear_db:
                DBHandler db = new DBHandler(MainActivity.this);
                db.ClearDatabase();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
