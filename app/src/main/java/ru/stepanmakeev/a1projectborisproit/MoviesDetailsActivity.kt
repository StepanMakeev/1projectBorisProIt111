package ru.stepanmakeev.a1projectborisproit

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.core.Constants
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesDetailsActivity : AppCompatActivity() {

    private lateinit var title: TextView
    private lateinit var releaseDate: TextView
    private lateinit var score: TextView
    private lateinit var overview: TextView
    private lateinit var banner: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_details)

        val id = intent.getIntExtra("id", 0)

        title = findViewById(R.id.movies_details_title)
        releaseDate = findViewById(R.id.movies_details_date)
        score = findViewById(R.id.movies_details_score)
        overview = findViewById(R.id.movies_details_body_overview)
        banner = findViewById(R.id.movies_details_image_banner)

        val apiInterface = id?.let { ApiInterface.create().getMovieDetails(it, "2f978d243389e30b980131cce8c72874") }
        apiInterface?.enqueue( object : Callback<MovieDetails> {
            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                title.text = response.body()?.title
                releaseDate.text = response.body()?.release_date.toString()
                score.text = response.body()?.vote_average.toString()
                overview.text = response.body()?.overview

                Picasso.get().load("https://image.tmdb.org/t/p/w500" + response.body()?.backdrop_path).into(banner)
            }

            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                Log.d("testing", "Ошибка ${t?.message}")
            }
        })
    }
}