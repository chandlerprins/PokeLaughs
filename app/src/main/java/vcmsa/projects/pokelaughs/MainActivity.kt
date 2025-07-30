package vcmsa.projects.pokelaughs

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vcmsa.projects.pokelaughs.api.RetrofitClient
import vcmsa.projects.pokelaughs.Joke
import vcmsa.projects.pokelaughs.Pokemon

class MainActivity : AppCompatActivity() {

    private lateinit var pokemonName: TextView
    private lateinit var pokemonImage: ImageView
    private lateinit var pokemonType: TextView
    private lateinit var jokeText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        pokemonName = findViewById(R.id.pokemonName)
        pokemonImage = findViewById(R.id.pokemonImage)
        pokemonType = findViewById(R.id.pokemonType)
        jokeText = findViewById(R.id.jokeText)

        findViewById<android.widget.Button>(R.id.btnRefresh).setOnClickListener {
            fetchRandomPokemonAndJoke()
        }

        fetchRandomPokemonAndJoke()
    }

    private fun fetchRandomPokemonAndJoke() {
        val randomId = (1..151).random() // Limit to Gen 1 for simplicity

        RetrofitClient.pokeApiService.getPokemon(randomId).enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                if (response.isSuccessful) {
                    response.body()?.let { pokemon ->
                        pokemonName.text = pokemon.name.replaceFirstChar { it.uppercase() }
                        pokemonType.text = "Type: ${pokemon.types[0].type.name.capitalize()}"
                        Glide.with(this@MainActivity)
                            .load(pokemon.sprites.front_default)
                            .into(pokemonImage)
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Failed to load Pokémon", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error loading Pokémon: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

        RetrofitClient.jokeApiService.getJoke().enqueue(object : Callback<Joke> {
            override fun onResponse(call: Call<Joke>, response: Response<Joke>) {
                if (response.isSuccessful) {
                    response.body()?.let { joke ->
                        val jokeTextToShow = when (joke.type) {
                            "single" -> joke.joke ?: "No joke found."
                            "twopart" -> "${joke.setup ?: ""}\n${joke.delivery ?: ""}"
                            else -> "No joke found."
                        }
                        this@MainActivity.jokeText.text = jokeTextToShow
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Failed to load joke", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Joke>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error loading joke: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })


        }
    }
