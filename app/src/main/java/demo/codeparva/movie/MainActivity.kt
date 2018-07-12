package demo.codeparva.movie

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.content_main.rvMovies

class MainActivity : AppCompatActivity() {

  lateinit var movieAdapter: MovieAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)
    val movieViewModel: MovieViewModel = ViewModelProviders.of(this)
        .get(MovieViewModel::class.java)

    setData(movieViewModel)
    movieViewModel.getMovies(this)
    movieAdapter = MovieAdapter()
    rvMovies.adapter = movieAdapter
    rvMovies.isNestedScrollingEnabled = false
  }

  private fun setData(movieViewModel: MovieViewModel) {
    movieViewModel.viewState.observe(this, Observer {
      if (null != it && it.movies.isNotEmpty()) {
        movieAdapter.setMovies(movies = it.movies)
      } else {

      }
    })
  }
}
