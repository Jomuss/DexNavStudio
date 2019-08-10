package com.joemoss.dexnavstudio;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface GetDataService {

    @GET("pokemon?offset=0&limit=801")
    Call<RetroList> getAllPokemon();

    @GET
    Call<RetroPokemon> getPokemon(@Url String pokemonNumber);

}