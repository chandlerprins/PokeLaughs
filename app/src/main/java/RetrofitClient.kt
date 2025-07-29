package vcmsa.projects.pokelaughs.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val retrofitPokemon = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitJoke = Retrofit.Builder()
        .baseUrl("https://v2.jokeapi.dev/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val pokeApiService: ApiService = retrofitPokemon.create(ApiService::class.java)
    val jokeApiService: ApiService = retrofitJoke.create(ApiService::class.java)
}
