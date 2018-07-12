package demo.codeparva.movie

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_movie.view.tvDescription
import kotlinx.android.synthetic.main.item_movie.view.tvTitle

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

  private lateinit var movies: ArrayList<Movie>

  fun setMovies(movies: ArrayList<Movie>) {
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
    return movies.size
  }

  override fun onBindViewHolder(
    holder: MovieViewHolder,
    position: Int
  ) {
    holder.bindTo(movies[position])
  }

  inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindTo(movie: Movie) {
      itemView.tvDescription.text = movie.description
      itemView.tvTitle.text = movie.name
    }
  }
}
