package com.example.nba_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.nba_project.data.API.API_Client;
import com.example.nba_project.data.API.API_interface;
import com.example.nba_project.data.model.NbaPlayer;
import com.example.nba_project.data.model.NbaPlayers;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Team_Activity extends AppCompatActivity {

    API_interface apiService = API_Client.getClient().create(API_interface.class);

    private int id;
    private String fullname;
    private String division;
    private String city;
    private String abreviation;
    private String image_url;

    private List<NbaPlayer> players;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapterPlayers recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        this.players = new ArrayList<NbaPlayer>();

        setAdapter();
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

    @Override
    protected void onResume() {
        super.onResume();

        callPlayers();
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

    private void callPlayers(){
        Call<NbaPlayers> call_players = apiService.getPlayers();

        call_players.enqueue(new Callback<NbaPlayers>() {
                 @Override
                 public void onResponse(Call<NbaPlayers> call, Response<NbaPlayers> response) {
                     Log.d("ILIAS","on response");
                     displayListOfPlayers(response.body());
                 }

                 @Override
                 public void onFailure(Call<NbaPlayers> call, Throwable t) {
                     Log.d("ILIAS","on failure" + t.toString());
                 }
             }
        );
    }

    private void displayListOfPlayers(NbaPlayers players){
        StringBuilder stringBuilder = new StringBuilder();
        for (NbaPlayer player : players.getData()){
            stringBuilder.append("-"+player.getFullName()+"\n");
            this.players.add(player);
            recyclerAdapter.notifyItemInserted(this.players.size());
        }
        Log.d("ILIAS", "réponse = \n" + stringBuilder.toString());
    }

    private void setAdapter() {
        this.recyclerView = (RecyclerView) findViewById(R.id.players_recyclerViews);
        this.recyclerAdapter = new RecyclerAdapterPlayers(this, this.players);
        this.layoutManager = new LinearLayoutManager(getApplicationContext());
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(recyclerAdapter);
    }
}