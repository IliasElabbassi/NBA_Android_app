package com.example.nba_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nba_project.data.entity.FavoriteTeam;

import java.util.List;


public class FavoriteAdapter extends  RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private List<FavoriteTeam> favoriteEntities;
    private int teams_logos[];

    Context context;

    public FavoriteAdapter(List<FavoriteTeam> favoriteEntities, Context context, int teams_logos[]) {
        this.favoriteEntities = favoriteEntities;
        this.context = context;
        this.teams_logos = teams_logos;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favorite_teams_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        FavoriteTeam favoriteTeam =favoriteEntities.get(position);
        viewHolder.fullname_team.setText(favoriteTeam.getFullname());
        viewHolder.logo.setImageResource(teams_logos[position]);


    }
    @Override
    public int getItemCount() {
        return favoriteEntities.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView logo;
        TextView fullname_team;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            logo=(ImageView)itemView.findViewById(R.id.logo);
            fullname_team=(TextView)itemView.findViewById(R.id.team_fullname);
        }
    }

}
