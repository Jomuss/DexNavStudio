package com.joemoss.dexnavstudio;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

public class SpriteViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprite_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        int pokeNum = intent.getIntExtra("pokemonNum", -1);
        toolbar.setTitle("Entry #"+pokeNum+" - Sprite View ");
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        Glide
                .with(SpriteViewActivity.this)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+pokeNum+".png")
                .placeholder(R.drawable.ic_launcher_background)
                .fitCenter()
                .into((ImageView)findViewById(R.id.frontDefMale));
        Glide
                .with(SpriteViewActivity.this)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/"+pokeNum+".png")
                .placeholder(R.drawable.ic_launcher_background)
                .fitCenter()
                .into((ImageView)findViewById(R.id.backDefMale));
        Glide
                .with(SpriteViewActivity.this)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/"+pokeNum+".png")
                .placeholder(R.drawable.ic_launcher_background)
                .fitCenter()
                .into((ImageView)findViewById(R.id.frontShinyMale));
        Glide
                .with(SpriteViewActivity.this)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/"+pokeNum+".png")
                .placeholder(R.drawable.ic_launcher_background)
                .fitCenter()
                .into((ImageView)findViewById(R.id.backShinyMale));

        Glide
                .with(SpriteViewActivity.this)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/female/"+pokeNum+".png")
                .fitCenter()
                .into((ImageView)findViewById(R.id.frontDefFemale));
        Glide
                .with(SpriteViewActivity.this)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/female/"+pokeNum+".png")
                .fitCenter()
                .into((ImageView)findViewById(R.id.backDefFemale));

        Glide
                .with(SpriteViewActivity.this)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/female/"+pokeNum+".png")
                .fitCenter()
                .into((ImageView)findViewById(R.id.frontShinyFemale));
        Glide
                .with(SpriteViewActivity.this)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/female/"+pokeNum+".png")
                .fitCenter()
                .into((ImageView)findViewById(R.id.backShinyFemale));


    }
    //Code found @ https://stackoverflow.com/questions/19893342/how-to-implement-up-navigation-in-android-for-2-parents-that-point-to-1-child-ac/20145765
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }
}
