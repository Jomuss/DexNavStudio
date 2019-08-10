package com.joemoss.dexnavstudio;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PokemonViewActivity extends AppCompatActivity {
    private RecyclerView movesRecyclerView;
    private PokemonMovesAdapter movesAdapter;
    private RecyclerView.LayoutManager movesLayoutManager;

    private RecyclerView abilitiesRecyclerView;
    private PokemonAbilitiesAdapter abilitiesAdapter;
    private RecyclerView.LayoutManager abilitiesLayoutManager;
    int pokemonNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_view);

        Intent intent = getIntent();
        pokemonNum = intent.getIntExtra(MainViewAdapter.EXTRA_MESSSAGE, -1);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<RetroPokemon> call = service.getPokemon("pokemon/" + Integer.toString(pokemonNum));
        Toolbar bar = findViewById(R.id.toolbar);
        bar.setTitle("Entry #" + pokemonNum);
        setSupportActionBar(bar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        call.enqueue(new Callback<RetroPokemon>() {

            @Override
            public void onResponse(Call<RetroPokemon> call, Response<RetroPokemon> response) {
                RetroPokemon pokemon = response.body();
                loadPage(pokemon);
            }


            @Override
            public void onFailure(Call<RetroPokemon> call, Throwable t) {
                Toast.makeText(PokemonViewActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void loadPage(RetroPokemon pokemon) {
        TextView name = findViewById(R.id.pokemonName);
        name.setText(RetroPokemon.capitalize(pokemon.getName()));
        ImageView frontSprite = findViewById(R.id.normalSprite);
        ImageView shinyFrontSprite = findViewById(R.id.shinySprite);

        if (pokemon.getTypes().get(0).get("slot").getAsInt() == 2) {
            TextView type1 = findViewById(R.id.typeOne);
            TextView type2 = findViewById(R.id.typeTwo);

            type1.setText(RetroPokemon.capitalize(pokemon.getTypes().get(1).get("type").getAsJsonObject().get("name").getAsString()));
            GradientDrawable gd = new GradientDrawable();
            gd.setColor(pokemon.getTypeColor(pokemon.getTypes().get(1).get("type").getAsJsonObject().get("name").getAsString()));
            gd.setCornerRadius(10);
            type1.setBackgroundDrawable(gd);

            type2.setText(RetroPokemon.capitalize(pokemon.getTypes().get(0).get("type").getAsJsonObject().get("name").getAsString()));
            GradientDrawable gd2 = new GradientDrawable();
            gd2.setColor(pokemon.getTypeColor(pokemon.getTypes().get(0).get("type").getAsJsonObject().get("name").getAsString()));
            gd2.setCornerRadius(10);
            type2.setBackgroundDrawable(gd2);


        } else {
            TextView type = findViewById(R.id.oneTypeText);
            type.setText(RetroPokemon.capitalize(pokemon.getTypes().get(0).get("type").getAsJsonObject().get("name").getAsString()));
            GradientDrawable gd = new GradientDrawable();
            gd.setColor(pokemon.getTypeColor(pokemon.getTypes().get(0).get("type").getAsJsonObject().get("name").getAsString()));
            gd.setCornerRadius(10);
            type.setBackgroundDrawable(gd);
        }

        abilitiesRecyclerView = findViewById(R.id.abilitiesView);
        abilitiesAdapter = new PokemonAbilitiesAdapter(this, pokemon.getAbilities());
        abilitiesLayoutManager = new LinearLayoutManager(PokemonViewActivity.this);
        abilitiesRecyclerView.setLayoutManager(abilitiesLayoutManager);
        abilitiesRecyclerView.setAdapter(abilitiesAdapter);

        movesRecyclerView = findViewById(R.id.movesView);
        movesAdapter = new PokemonMovesAdapter(this, pokemon.getOrderedLevelUpMoves());
        movesLayoutManager = new LinearLayoutManager(PokemonViewActivity.this);
        movesRecyclerView.setLayoutManager(movesLayoutManager);
        movesRecyclerView.setAdapter(movesAdapter);
        Glide
                .with(PokemonViewActivity.this)
                .load(Uri.parse("file:///android_asset/front_sprites/" + (pokemonNum) + ".png"))
                .fitCenter()
                .into(frontSprite);
        Log.d("Debug", Boolean.toString(shinyFrontSprite == null));
        Glide
                .with(PokemonViewActivity.this)
                .load(pokemon.getSprites().get("front_shiny").getAsString())
                .fitCenter()
                .into(shinyFrontSprite);


    }

    public void loadSpriteView(View view) {
        Intent intent = new Intent(this, SpriteViewActivity.class);
        intent.putExtra("pokemonNum", pokemonNum);
        startActivity(intent);
    }

    //Code for making the home button function like the system back button found here:
    //https://stackoverflow.com/questions/22182888/actionbar-up-button-destroys-parent-activity-back-does-not
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void nextPokemon(View v){
        Intent intent = getIntent();
        if(pokemonNum < 801)
            pokemonNum++;
        intent.putExtra(MainViewAdapter.EXTRA_MESSSAGE, pokemonNum);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
    public void prevPokemon(View v){
        if(pokemonNum > 1)
            pokemonNum--;
        Intent intent = getIntent();
        intent.putExtra(MainViewAdapter.EXTRA_MESSSAGE, pokemonNum);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

}


