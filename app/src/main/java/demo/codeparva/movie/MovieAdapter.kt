package demo.codeparva.movie

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movies: ArrayList<Movie>? = null
    private lateinit var onClickListener: OnClickListener

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    fun setMovies(movies: ArrayList<Movie>?) {
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): MovieViewHolder {
        return MovieViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return if (movies != null)
            movies!!.size
        else
            0
    }

    override fun onBindViewHolder(
            holder: MovieViewHolder,
            position: Int
    ) {
        holder.bindTo(movies?.get(position), position)
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindTo(movie: Movie?, position: Int) {
            if (null != movie) {
                itemView.tvDescription.text = movie.description
                itemView.tvTitle.text = movie.name
                Picasso.get().load(movie.url).into(itemView.ivBanner)
                itemView.checkbox.isChecked = movie.selection

                itemView.checkbox.setOnClickListener {
                    onClickListener.onSelect(position)
                }
            }
        }
    }

    interface OnClickListener {
        fun onSelect(position: Int)
    }
}
