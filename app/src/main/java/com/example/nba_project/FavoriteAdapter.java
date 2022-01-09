package com.example.nba_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
        FavoriteTeam favoriteTeam =favoriteEntities.get(position);
        viewHolder.fullname_team.setText(favoriteTeam.getFullname());
        viewHolder.logo.setImageResource(teams_logos[position]);


        viewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Team_Activity.class);
                intent.putExtra("team_logo",teams_logos[position]);
                Bundle bundle = new Bundle();
                bundle.putString("division", favoriteEntities.get(position).getDivision());
                bundle.putString("city",favoriteEntities.get(position).getCity());
                bundle.putString("fullname", favoriteEntities.get(position).getFullname());
                bundle.putString("abreviation", favoriteEntities.get(position).getAbbrevation());
                bundle.putInt("id", favoriteEntities.get(position).getId());

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    };


    @Override
    public int getItemCount() {
        return favoriteEntities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView logo;
        private TextView fullname_team;
        private  ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            logo= (ImageView) itemView.findViewById(R.id.logo);
            fullname_team= (TextView) itemView.findViewById(R.id.team_fullname);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.constraint_layout_favorite);
        }
    }

}
