package com.example.nba_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Team_Activity extends AppCompatActivity {

    String fullname;
    String division;
    String city;
    String abreviation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        getValueFromBundle();
        showContent();
    }

    private void getValueFromBundle(){
        Bundle bundle = getIntent().getExtras();

        fullname = bundle.getString("fullname");
        division = bundle.getString("division");
        city = bundle.getString("city");
        abreviation = bundle.getString("abreviation");
    }

    private void showContent(){
        TextView TVfullname = (TextView) findViewById(R.id.fullname_team);
        TextView TVcity = (TextView) findViewById(R.id.city_team);
        TextView TVdivision = (TextView) findViewById(R.id.divison_team);
        TextView TVabreviation = (TextView) findViewById(R.id.abreviation_team);

        TVfullname.setText(fullname);
        TVcity.setText(city);
        TVabreviation.setText(abreviation);
        TVdivision.setText(division);

    }
}