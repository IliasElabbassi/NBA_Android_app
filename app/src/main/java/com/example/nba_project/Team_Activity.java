package com.example.nba_project;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nba_project.data.API.API_Client;
import com.example.nba_project.data.API.API_interface;
import com.example.nba_project.data.model.Meta;
import com.example.nba_project.data.model.NbaPlayer;
import com.example.nba_project.data.model.NbaPlayers;
import com.example.nba_project.data.model.Team;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Team_Activity extends AppCompatActivity {

    API_interface apiService = API_Client.getClient().create(API_interface.class);

    private int teamIdD;
    private String fullname;
    private String division;
    private String city;
    private String abreviation;

    private List<NbaPlayer> players;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapterPlayers recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        Toolbar toolbar=findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        this.players = new ArrayList<NbaPlayer>();

        setAdapter();
        getValueFromBundle();
        showContent();
    }

    private void getValueFromBundle(){
        Bundle bundle = getIntent().getExtras();

        this.fullname = bundle.getString("fullname");
        this.division = bundle.getString("division");
        this.city = bundle.getString("city");
        this.abreviation = bundle.getString("abreviation");
        this.teamIdD = bundle.getInt("id");
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
        ImageView logo=(ImageView) findViewById(R.id.team_logo);
        TVfullname.setText(fullname);
        TVcity.setText(city);
        TVabreviation.setText(abreviation);
        TVdivision.setText(division);
        logo.setImageResource(this.retrieveTeamsLogosExtra());
    }

    private void callPlayers(){
        final int[] page = {0,0};
        Call<NbaPlayers> call_players = apiService.getPlayersWithPage(page[0]);

        call_players.enqueue(new Callback<NbaPlayers>() {
                 @Override
                 public void onResponse(Call<NbaPlayers> call, Response<NbaPlayers> response) {
                     Meta metaInfo = response.body().getMeta();
                     page[0] = metaInfo.getTotalPages();
                     page[1] = metaInfo.getCurrentPage();

                     Log.d("ILIAS","on response [page "+Integer.toString(page[0])+"]");
                     displayListOfPlayers(response.body());
                 }

                 @Override
                 public void onFailure(Call<NbaPlayers> call, Throwable t) {
                     Log.d("ILIAS","on failure" + t.toString());
                 };
             }
        );


        for(int i = page[1]; i <= page[0]; i++){
            call_players = apiService.getPlayersWithPage(i);

            call_players.enqueue(new Callback<NbaPlayers>() {
                     @Override
                     public void onResponse(Call<NbaPlayers> call, Response<NbaPlayers> response) {
                         Meta metaInfo = response.body().getMeta();
                         int currentPage = metaInfo.getCurrentPage();

                         Log.d("ILIAS","on response [page "+Integer.toString(currentPage)+"]");
                         displayListOfPlayers(response.body());
                     }

                     @Override
                     public void onFailure(Call<NbaPlayers> call, Throwable t) {
                         Log.d("ILIAS","on failure" + t.toString());
                     }
                 }
            );
        }
    }


    private void displayListOfPlayers(NbaPlayers players){
        StringBuilder stringBuilder = new StringBuilder();
        for (NbaPlayer player : players.getData()){
            stringBuilder.append("-"+player.getFullName()+"\n");

            Team playerTeam = player.getTeam();
            int playerTeamID = playerTeam.getId();

            if(playerTeamID == this.teamIdD) {
                this.players.add(player);
                recyclerAdapter.notifyItemInserted(this.players.size());
            }
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

    private  int retrieveTeamsLogosExtra(){
        Bundle extras = getIntent().getExtras();
        return extras.getInt("team_logo");

    }
}