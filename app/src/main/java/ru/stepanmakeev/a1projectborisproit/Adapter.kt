package ru.stepanmakeev.a1projectborisproit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import android.widget.Button


// ВАЖНО корректно передать список
class Adapter(private val list: List<ResultX>?, val mItemClickListener: ItemClickListener) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    interface ItemClickListener {
        fun onItemClick(position: Int)
    }

    // create new views создавайте новые виды
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)
                         //ВАЖНО верхний ЛАЙАУТ правильный передать
        return ViewHolder(view)
    }

    // binds the list items to a view привязывает элементы списка к представлению ((( 0 1 2 3 4 5 6 ... )))
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list?.get(position) // ((( 0 1 2 3 4 5 6 ...)))
                            // основная ссылка откуда подгрузка          позиция с которой подгружает + ЧТО именно       into - куда подгружаем ! В имейджВью из 46 строки
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + list?.get(position)?.poster_path).into(holder.imageView);

        // sets the image to the imageview from our itemHolder class устанавливает для изображения значение imageview из нашего класса itemHolder
        // ВАЖНО

        // sets the text to the textview from our itemHolder class устанавливает текст в textview из нашего класса itemHolder
        // ВАЖНО

    }

    // return the number of the items in the list возвращает количество элементов в списке
    override fun getItemCount(): Int {
        return list!!.size
    }

    // Holds the views for adding it to image and text Содержит виды для добавления к изображению и тексту
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) { // ВАЖНО снизу производить корректную инициализацию с верными АЙДИШНИКАМИ
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        init {
            itemView.setOnClickListener {
                mItemClickListener.onItemClick(adapterPosition)
            }
        }
    }
}