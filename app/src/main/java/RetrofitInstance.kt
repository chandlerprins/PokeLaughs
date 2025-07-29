import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vcmsa.projects.pokelaughs.api.ApiService

object RetrofitInstance {
    private val retrofitPoke = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitJoke = Retrofit.Builder()
        .baseUrl("https://v2.jokeapi.dev/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val pokeService: ApiService = retrofitPoke.create(ApiService::class.java)
    val jokeService: ApiService = retrofitJoke.create(ApiService::class.java)
}