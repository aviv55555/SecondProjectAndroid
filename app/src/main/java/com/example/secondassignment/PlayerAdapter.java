package com.example.secondassignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.MyViewHolder> {

    private ArrayList<DataModel> dataSet;

    public PlayerAdapter(ArrayList<DataModel> dataSet) {
        this.dataSet = dataSet;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewDescription;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.NameOfThePlayer);
            textViewDescription = itemView.findViewById(R.id.DesriptionPlayer);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardrow, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataModel dataModel = dataSet.get(position);

        holder.textViewName.setText(dataSet.get(position).getName());
        holder.textViewDescription.setText(dataSet.get(position).getDescription());
        holder.imageView.setImageResource(dataSet.get(position).getImage());
        // Set tags on the itemView to hold the player data
        holder.itemView.setTag(R.id.NameOfThePlayer, dataModel.getName());
        holder.itemView.setTag(R.id.DesriptionPlayer, dataModel.getDescription());
        holder.itemView.setTag(R.id.imageView, dataModel.getImage());
        // Set a click listener on the card
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call funcPopUp without passing arguments explicitly
                ((MainActivity) v.getContext()).funcPopUp(v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}