package ru.stepanmakeev.a1projectborisproit

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Button

class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        // getting the recyclerview by its id  получение recyclerview по его идентификатору
        val recyclerview: RecyclerView = findViewById(R.id.recyclerview)

        // this creates a vertical layout Manager это создает менеджер вертикальной компоновки
        recyclerview.layoutManager = GridLayoutManager(this, 2)

        // ArrayList of class ItemsViewModel ArrayList класса Itemviewmodel
        val data = ArrayList<Item>()

        // This loop will create 20 Views containing Этот цикл создаст 20 просмотров, содержащих
        // the image with the count of view изображение с количеством просмотров
       // for (i in 1..7) {
      //      data.add(Item(R.drawable.gfg_logo, "Item $i"))
      //  }

        val apiInterface = ApiInterface.create().getMovies("2f978d243389e30b980131cce8c72874")

        //apiInterface.enqueue( Callback<List<Movie>>())
        apiInterface.enqueue( object : Callback<PopularMovies>, Adapter.ItemClickListener {
            override fun onResponse(call: Call<PopularMovies>?, response: Response<PopularMovies>?) {

                Log.d("TestLogs", "OnResponse Succes ${response?.body()?.results}")

                // This will pass the ArrayList to our Adapter Это передаст ArrayList нашему адаптеру
                val adapter = Adapter(response?.body()?.results, this)

                // Setting the Adapter with the recyclerview Установив адаптер с помощью recyclerview
                recyclerview.adapter = adapter

            }

            override fun onFailure(call: Call<PopularMovies>?, t: Throwable?) {
                    Log.d("TestLogs", "onFailure : ${t?.message}")

            }

            override fun onItemClick(position: Int) {
                Toast.makeText(this@MoviesActivity, "click $position", Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finishAffinity()
    }
}