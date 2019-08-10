package com.joemoss.dexnavstudio;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.gson.JsonObject;
import java.util.ArrayList;

public class PokemonMovesAdapter extends RecyclerView.Adapter<PokemonMovesAdapter.MyViewHolder>{
    public static final String EXTRA_MESSSAGE ="com.joemoss.dexnavstudio.pokemon";
    private ArrayList<JsonObject> moveList;
    private Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    PokemonMovesAdapter(Context context, ArrayList<JsonObject> moveList){
        this.context = context;
        this.moveList = moveList;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class MyViewHolder extends RecyclerView.ViewHolder {

        final View mView;
        private TextView moveName;
        private TextView levelNum;

        MyViewHolder(View v) {
            super(v);
            mView = v;
            moveName = mView.findViewById(R.id.moveName);
            levelNum = mView.findViewById(R.id.levelLearned);

        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PokemonMovesAdapter.MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent,
                                                                                    int viewType) {
        // create a new view
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.custom_moves_row, parent, false);
        return new PokemonMovesAdapter.MyViewHolder(v);
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull PokemonMovesAdapter.MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.moveName.setText(RetroPokemon.capitalize(moveList.get(position).get("name").getAsString()));
        holder.levelNum.setText(RetroPokemon.capitalize(moveList.get(position).get("levelLearned").getAsString()));
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return moveList.size();
    }
}


