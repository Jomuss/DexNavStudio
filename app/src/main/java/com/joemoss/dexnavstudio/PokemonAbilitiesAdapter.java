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

public class PokemonAbilitiesAdapter extends RecyclerView.Adapter<PokemonAbilitiesAdapter.MyViewHolder>{
    public static final String EXTRA_MESSSAGE ="com.joemoss.dexnavstudio.pokemon";
    private ArrayList<JsonObject> abilitiesList;
    private Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    PokemonAbilitiesAdapter(Context context, ArrayList<JsonObject> abilitiesList){
        this.context = context;
        this.abilitiesList = abilitiesList;
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class MyViewHolder extends RecyclerView.ViewHolder {

        final View mView;
        private TextView abilityName;

        MyViewHolder(View v) {
            super(v);
            mView = v;

            abilityName = mView.findViewById(R.id.abilityLabel);

        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PokemonAbilitiesAdapter.MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.custom_abilities_row, parent, false);
        return new PokemonAbilitiesAdapter.MyViewHolder(v);
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(PokemonAbilitiesAdapter.MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.abilityName.setText(RetroPokemon.capitalize(abilitiesList.get(position).getAsJsonObject().get("ability").getAsJsonObject().get("name").getAsString()));
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return abilitiesList.size();
    }
}
