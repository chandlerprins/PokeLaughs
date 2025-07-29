package vcmsa.projects.pokelaughs.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import vcmsa.projects.pokelaughs.Joke
import vcmsa.projects.pokelaughs.Pokemon

interface ApiService {
    @GET("pokemon/{id}")
    fun getPokemon(@Path("id") id: Int): Call<Pokemon>

    @GET("joke/Any?format=json")
    fun getJoke(): Call<Joke>
}
