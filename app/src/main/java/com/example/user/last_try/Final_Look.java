package com.example.user.last_try;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Final_Look extends AppCompatActivity {
    EditText CountryName,AreaName,CityName;
    String countryname,cityname,areaname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final__look);
        CountryName=(EditText)findViewById(R.id.CountryName);
        CityName=(EditText)findViewById(R.id.CityName);
        AreaName=(EditText)findViewById(R.id.AreaName);
    }
    public void go(View view){
        if(CountryName.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"please enter a country name", Toast.LENGTH_SHORT).show();
            return;
        }else{
            countryname=CountryName.getText().toString().substring(0, 1).toUpperCase() + CountryName.getText().toString().substring(1).toLowerCase();

        }
        if(CityName.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"please enter a city name", Toast.LENGTH_SHORT).show();
            return;
        }else{
            cityname=CityName.getText().toString().substring(0, 1).toUpperCase() + CityName.getText().toString().substring(1).toLowerCase();

        }
        if(AreaName.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"please enter an area name", Toast.LENGTH_SHORT).show();
            return;
        }else{
            areaname=AreaName.getText().toString().substring(0, 1).toUpperCase() + AreaName.getText().toString().substring(1).toLowerCase();

        }
        Intent intent = new Intent(getApplicationContext(), list_tester4.class);//list tester 4 ekta jaygay ki ki ase seta
        intent.putExtra("address", countryname+","+cityname+","+areaname);
        startActivity(intent);

    }
}
