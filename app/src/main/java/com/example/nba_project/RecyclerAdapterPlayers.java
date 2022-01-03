package com.example.nba_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nba_project.data.model.NbaPlayer;
import com.example.nba_project.data.model.Team;

import java.util.List;


public class RecyclerAdapterPlayers extends  RecyclerView.Adapter<com.example.nba_project.RecyclerAdapterPlayers.MyviewHolder> {

    public static final String EXTRA_MESSAGE = "com.example.api_balldontlie.MESSAGE";
    private List<NbaPlayer> players;
    Context context;

    public RecyclerAdapterPlayers(Context context, List<NbaPlayer> players) {
        this.players = players;
        this.context = context;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_layout, parent, false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        holder.lastname.setText(players.get(position).getLastName());
        holder.firstname.setText(players.get(position).getFirstName());
        holder.position.setText(players.get(position).getPosition());
        /**
        int h = (int) players.get(position).getHeightFeet();
        holder.height.setText(Integer.toString(h));
        **/
    }

    @Override
    public int getItemCount() {
        if (players != null) {
            return players.size();
        }
        return 0;
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        private TextView lastname;
        private TextView firstname;
        private TextView position;
        private TextView height;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            lastname = (TextView) itemView.findViewById(R.id.player_lastname);
            firstname = (TextView) itemView.findViewById(R.id.player_firstname);
            position = (TextView) itemView.findViewById(R.id.position);
            height = (TextView) itemView.findViewById(R.id.height);

        }
    }
}
