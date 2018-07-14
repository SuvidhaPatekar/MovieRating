package demo.codeparva.movie

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*
import kotlinx.android.synthetic.main.item_movie_rating.view.*


class MovieAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var movies: ArrayList<Movie>? = null
    private lateinit var onClickListener: OnClickListener
    private val VIEW_TYPE_MOVIE = 0
    private val VIEW_TYPE_MOVIE_RATING = 1
    private lateinit var context: Context

    fun setOnClickListener(
            onClickListener: OnClickListener,
            context: Context
    ) {
        this.onClickListener = onClickListener
        this.context = context
    }

    fun setMovies(movies: ArrayList<Movie>?) {
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (movies!![position].rating >= 8) {
            VIEW_TYPE_MOVIE_RATING
        } else {
            VIEW_TYPE_MOVIE
        }
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ViewHolder {

        when (viewType) {
            VIEW_TYPE_MOVIE_RATING -> return MovieViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_movie_rating, parent, false)
            )

            VIEW_TYPE_MOVIE -> return MovieViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
            )

            else -> return MovieViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
            )
        }
    }

    override fun getItemCount(): Int {
        return if (movies != null)
            movies!!.size
        else
            0
    }

    override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int
    ) {
        when (holder.itemViewType) {
            VIEW_TYPE_MOVIE_RATING -> (MovieRatingViewHolder(itemView = holder.itemView)).bindTo(
                    movies?.get(position), position
            )

            VIEW_TYPE_MOVIE -> (MovieViewHolder(itemView = holder.itemView)).bindTo(
                    movies?.get(position), position
            )
        }
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindTo(
                movie: Movie?,
                position: Int
        ) {
            if (null != movie) {
                itemView.tvDescription.text = movie.description
                itemView.tvTitle.text = movie.name
                itemView.tvRating.text = movie.rating.toString()
                Picasso.with(context).isLoggingEnabled = true

                Picasso.with(context)
                        .load(movie.url)
                        .into(itemView.ivBanner)
                itemView.checkbox.isChecked = movie.selection

                itemView.checkbox.setOnClickListener {
                    onClickListener.onSelect(position)
                }

                itemView.setOnLongClickListener { view ->
                    onClickListener.onLongClick(position)
                    true
                }
            }
        }
    }

    inner class MovieRatingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindTo(
                movie: Movie?,
                position: Int
        ) {
            if (null != movie) {
                itemView.tvDescriptionRating.text = movie.description
                itemView.tvTitleRating.text = movie.name
                itemView.checkboxRating.isChecked = movie.selection
                itemView.tvMovieRating.text = movie.rating.toString()
                val picasso = Picasso.Builder(context).listener { picasso, uri, exception -> exception.printStackTrace() }.build()
                picasso.isLoggingEnabled = true
                picasso.load(movie.url)
                        .into(itemView.ivBannerRating)
                itemView.checkboxRating.setOnClickListener {
                    onClickListener.onSelect(position)
                }

                itemView.setOnLongClickListener { view ->
                    onClickListener.onLongClick(position)
                    true
                }
            }
        }
    }

    interface OnClickListener {
        fun onSelect(position: Int)
        fun onLongClick(position: Int)
    }
}
