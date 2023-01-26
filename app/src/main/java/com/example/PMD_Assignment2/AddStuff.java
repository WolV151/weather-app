package com.example.PMD_Assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

public class AddStuff extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText title, woeid;
    Button save;
    Spinner spinner;
    HashMap<String, String> locations = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stuff);

        locations.put("London", "44418"); // this is probably better off saved in the database
        locations.put("Paris", "615702");
        locations.put("Dublin", "560743");
        locations.put("Milan", "718345");
        locations.put("Rome", "721943");
        locations.put("Sofia", "839722");
        locations.put("Berlin", "638242");
        locations.put("Geneva", "782538");
        locations.put("Kiev", "924938");
        locations.put("Prague", "796597");
        locations.put("Edinburgh", "19344");
        locations.put("Moscow", "2122265");
        locations.put("Bucharest", "868274");

        title = findViewById(R.id.editTextTitle);
        woeid = findViewById(R.id.editTextWoeid);
        save = findViewById(R.id.save_button);

        spinner = findViewById(R.id.title_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.title_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        save.setOnClickListener(view -> {
            String spinnerVal = spinner.getSelectedItem().toString();
            String locationTitle = title.getText().toString();
            String locationWoeid = woeid.getText().toString();

            if (locationTitle.equals("") || locationWoeid.equals("")){
                if (spinnerVal.equals("Select")){
                    Toast.makeText(AddStuff.this, "Empty fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                locationTitle = spinnerVal;
                locationWoeid = locations.get(spinnerVal);
            }

            DBHandler db = new DBHandler(AddStuff.this);

            if (db.FetchLocation(locationWoeid) != null){
                Toast.makeText(AddStuff.this, "Record already exists!",Toast.LENGTH_SHORT).show();
                return;
            }

            db.insertRecord(locationTitle, locationWoeid);
            Toast.makeText(AddStuff.this, "Record inserted successfully",Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        ;
    }
}