package ru.stepanmakeev.a1projectborisproit
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiInterface {

    @GET("3/movie/popular")
    fun getMovies(@Query ("api_key") sort : String) : Call<PopularMovies>

    @GET("3/movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId : Int, @Query ("api_key") sort: String ) : Call<MovieDetails>
   //                   @Path используется для передачи ДИНАМИЧЕСКОЙ части
    companion object {

        var BASE_URL = "https://api.themoviedb.org/"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}