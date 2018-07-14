package demo.codeparva.movie

import android.support.v7.util.DiffUtil
import android.util.Log

class MovieDiffIUtil : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie?, newItem: Movie?): Boolean {
        return oldItem?.id == newItem?.id
    }

    override fun areContentsTheSame(oldItem: Movie?, newItem: Movie?): Boolean {
        Log.i("Old Item new item", oldItem.toString() + newItem.toString())
        return oldItem == newItem
    }
}