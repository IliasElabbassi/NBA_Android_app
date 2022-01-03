package com.example.nba_project.data.API;

import com.example.nba_project.data.model.NbaPlayer;
import com.example.nba_project.data.model.NbaPlayers;
import com.example.nba_project.data.model.Team;
import com.example.nba_project.data.model.Teams;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface API_interface {
    @GET("players")
    Call<NbaPlayers> getPlayers();

    @GET("players/{id}")
    Call<NbaPlayer> getPlayersByTeamId(@Path("id") int id);

    @GET("teams/{id}")
    Call<Team> getTeam(@Path("id") int id);

    @GET("teams")
    Call<Teams> getTeams();
}
