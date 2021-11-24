package com.example.nba_project;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    API_interface apiService = API_Client.getClient().create(API_interface.class);
    private List<Team> teams;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter recyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.teams = new ArrayList<Team>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        setAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //callPlayers();
        callTeams();
    }

    private void callTeams(){
        Call<Teams> call_teams = apiService.getTeams();

        call_teams.enqueue(new Callback<Teams>() {
                               @Override
                               public void onResponse(Call<Teams> call, Response<Teams> response) {
                                   Log.d("ILIAS","on response");
                                   //Log.d("ILIAS",response.toString());
                                   displayListOfTeams(response.body());
                               }

                               @Override
                               public void onFailure(Call<Teams> call, Throwable t) {
                                   Log.d("ILIAS","on failure :" + t.toString());
                               }
                           }
        );
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
        }
        Log.d("ILIAS", "réponse = \n" + stringBuilder.toString());
    }

    private void displayListOfTeams(Teams Teams){
        StringBuilder stringBuilder = new StringBuilder();
        for (Team team : Teams.getData()){
            stringBuilder.append("-"+team.getFullName()+"\n");
            teams.add(team);
            recyclerAdapter.notifyItemInserted(teams.size());
        }
        Log.d("ILIAS", "réponse = \n" + stringBuilder.toString());
    }

    private void setAdapter() {
        this.recyclerAdapter = new RecyclerAdapter(this, teams);
        this.layoutManager = new LinearLayoutManager(getApplicationContext());
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(recyclerAdapter);
    }
}