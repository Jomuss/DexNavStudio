package com.joemoss.dexnavstudio;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesViewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MainViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Apply activity transition
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setExitTransition(new Fade());
            getWindow().setEnterTransition(new Fade());
        } else {
            // Swap without transition
        }
        setContentView(R.layout.activity_favorites_view);

        Toolbar bar = findViewById(R.id.mainToolbar);
        bar.setTitle("DexNav|Favorites");
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, bar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));
        mDrawerToggle.syncState();

        navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                int id = item.getItemId();
                switch(id){
                    case R.id.mainView:
                        mDrawerLayout.closeDrawers();
                        Intent intent = new Intent(FavoritesViewActivity.this, MainActivity.class);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(FavoritesViewActivity.this).toBundle());
                        }else{
                            startActivity(intent);
                        }
                        break;
                    case R.id.favoritesView:
                        mDrawerLayout.closeDrawers();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<RetroList> call = service.getAllPokemon();
        call.enqueue(new Callback<RetroList>() {

            @Override
            public void onResponse(Call<RetroList> call, Response<RetroList> response) {
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<RetroList> call, Throwable t) {
                Toast.makeText(FavoritesViewActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_LONG).show();
            }
        });
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener(){
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset){ }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDrawerOpened(@NonNull View view) {
                Random r = new Random();
                final int randPokemon = r.ints(1, 802).findFirst().getAsInt();
                ImageView drawerImage = findViewById(R.id.drawerImage);
                Glide
                        .with(getApplicationContext())
                        .load(Uri.parse("file:///android_asset/front_sprites/"+ (randPokemon) +".png"))
                        .circleCrop()
                        .into(drawerImage);
                drawerImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadPokemonView(v, randPokemon);
                    }
                });

            }

            @Override
            public void onDrawerClosed(@NonNull View view) { }

            @Override
            public void onDrawerStateChanged(int i) { }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    public void loadPokemonView(View view, int randPokemon) {
        Intent intent = new Intent(this, PokemonViewActivity.class);
        intent.putExtra(MainViewAdapter.EXTRA_MESSSAGE, randPokemon);
        startActivity(intent);

    }


    private void generateDataList(RetroList pokemonList) {
        recyclerView = findViewById(R.id.my_recycler_view);
        mAdapter = new MainViewAdapter(this, pokemonList);
        mAdapter.setFavoritesView(true);
        layoutManager = new LinearLayoutManager(FavoritesViewActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.searchFilter("");
    }
}
