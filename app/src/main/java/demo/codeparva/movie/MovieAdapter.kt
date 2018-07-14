package demo.codeparva.movie

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso


class MovieAdapter : ListAdapter<Movie, MovieAdapter.MovieRatingViewHolder>(MovieDiffIUtil()) {

    private lateinit var onClickListener: OnClickListener
    private val VIEW_TYPE_MOVIE = 0
    private val VIEW_TYPE_MOVIE_RATING = 1

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).rating >= 8) {
            VIEW_TYPE_MOVIE_RATING
        } else {
            VIEW_TYPE_MOVIE
        }
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): MovieRatingViewHolder {

        val viewholder = when (viewType) {
            VIEW_TYPE_MOVIE_RATING -> MovieRatingViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_movie_rating, parent, false)
            )

            VIEW_TYPE_MOVIE -> MovieRatingViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
            )

            else -> MovieRatingViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
            )
        }

        viewholder.checkboxRating.setOnClickListener {
            if (viewholder.adapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
            onClickListener.onSelect(viewholder.adapterPosition)
        }

        viewholder.itemView.setOnLongClickListener {
            if (viewholder.adapterPosition == RecyclerView.NO_POSITION) true
            else {
                onClickListener.onLongClick(viewholder.adapterPosition)
                true
            }
        }
        return viewholder
    }


    override fun onBindViewHolder(holder: MovieRatingViewHolder, position: Int) {
        MovieRatingViewHolder(holder.itemView).bindTo(
                getItem(position))
    }

    inner class MovieRatingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvDescriptionRating: TextView = itemView.findViewById(R.id.tvDescription)
        private val tvTitleRating: TextView = itemView.findViewById(R.id.tvTitle)
        val checkboxRating: CheckBox = itemView.findViewById(R.id.checkbox)
        private val tvMovieRating: TextView = itemView.findViewById(R.id.tvRating)
        private val ivBannerRating: ImageView = itemView.findViewById(R.id.ivBanner)


        fun bindTo(movie: Movie) {
            tvDescriptionRating.text = movie.description
            tvTitleRating.text = movie.name
            checkboxRating.isChecked = movie.selection
            tvMovieRating.text = movie.rating.toString()
            Picasso.get().load(movie.url)
                    .fit()
                    .into(ivBannerRating)
        }
    }

    interface OnClickListener {
        fun onSelect(position: Int)
        fun onLongClick(position: Int)
    }
}
