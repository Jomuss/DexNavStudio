package com.joemoss.dexnavstudio;

// this is a variant of this tutorial:
// https://developer.android.com/guide/topics/ui/layout/recyclerview
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Map;

public class MainViewAdapter extends RecyclerView.Adapter<MainViewAdapter.MyViewHolder>{
    static final String EXTRA_MESSSAGE ="com.joemoss.dexnavstudio.pokemon";
    private RetroList dataList;
    private Context context;
    private ArrayList<Integer> favPokemon;
    private SharedPreferences sharedPref;
    private boolean favoritesView = false;

    private Gson gson;

    // Provide a suitable constructor (depends on the kind of dataset)
    MainViewAdapter(Context context, RetroList dataList){

        this.context = context;
        this.dataList = dataList;
        gson = new Gson();

        sharedPref = context.getSharedPreferences(context.getString(R.string.pokemon_favorites_key), Context.MODE_PRIVATE);
        if(sharedPref.contains("favPokemon")){
            String jsonFavPokemon = sharedPref.getString("favPokemon", " ");
            favPokemon = gson.fromJson(jsonFavPokemon, new TypeToken<ArrayList<Integer>>(){}.getType());
        }else{
            favPokemon = new ArrayList<>();
        }
    }

    void searchFilter(String searchString){
        for(Map<String, String> pokeMap : dataList.getResults()){
            if(pokeMap.get("name").contains(searchString)){
                    pokeMap.put("isVisible", "true");
            }else{
                pokeMap.put("isVisible", "false");
            }
        }
        notifyDataSetChanged();
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class MyViewHolder extends RecyclerView.ViewHolder {

        final View mView;
        private TextView txtTitle;
        private ImageView coverImage;
        private ImageButton favButton;
        private int pokemonEntryNum;


        MyViewHolder(View v) {
            super(v);
            mView = v;

            txtTitle = mView.findViewById(R.id.title);
            coverImage = mView.findViewById(R.id.coverImage);
            favButton = mView.findViewById(R.id.favoriteButton);
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public MainViewAdapter.MyViewHolder onCreateViewHolder(final ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.custom_pokelist_row, parent, false);
        final MyViewHolder vh = new MyViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(vh.pokemonEntryNum+1);
                Intent intent = new Intent(parent.getContext(), PokemonViewActivity.class);
                intent.putExtra(EXTRA_MESSSAGE, vh.pokemonEntryNum+1);
                parent.getContext().startActivity(intent);
            }
        });


        return vh;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if(favoritesView){
            if (favPokemon.contains(position)){
                holder.mView.getLayoutParams().height = 250;
            }else{
                holder.mView.getLayoutParams().height = 0;
            }
        }
        else{
            if(dataList.getResults().get(position).get("isVisible").equals("true")){
                holder.mView.getLayoutParams().height = 250;
            }else{
                holder.mView.getLayoutParams().height = 0;
                return;
            }
        }
        final MyViewHolder curHolder = holder;

        holder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favPokemon.contains(position)){
                    favPokemon.remove((Object)position);
                    curHolder.favButton.setImageResource(R.drawable.favorite_button);

                }else {
                    favPokemon.add(position);
                    curHolder.favButton.setImageResource(R.drawable.favorite_button_on);
                }
                SharedPreferences.Editor editor = sharedPref.edit();
                String jsonFavPokemon = gson.toJson(favPokemon, favPokemon.getClass());
                editor.putString("favPokemon", jsonFavPokemon);
                editor.apply();
                System.out.println(favPokemon);
            }
        });
        if(favPokemon.contains(position)){
            holder.favButton.setImageResource(R.drawable.favorite_button_on);
        }else{
            holder.favButton.setImageResource(R.drawable.favorite_button);
        }
        holder.txtTitle.setText(RetroPokemon.capitalize(dataList.getResults().get(position).get("name")));
        holder.pokemonEntryNum = position;

        Glide
                .with(context)
                .load(Uri.parse("file:///android_asset/front_sprites/"+ (position+1) +".png"))
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.coverImage);



    }

    void setFavoritesView(boolean view){
        favoritesView = view;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataList.getResults().size();
    }
}
