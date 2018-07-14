package demo.codeparva.movie

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), MovieAdapter.OnClickListener {

    lateinit var movieAdapter: MovieAdapter
    lateinit var movieViewModel: MovieViewModel
    lateinit var movieList: ArrayList<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        movieViewModel = ViewModelProviders.of(this)
                .get(MovieViewModel::class.java)

        setData(movieViewModel)
        movieViewModel.getMovies(this)
        movieAdapter = MovieAdapter()
        movieAdapter.setOnClickListener(this, context = this)

        rvMovies.adapter = movieAdapter
        rvMovies.isNestedScrollingEnabled = false

        btnClearAll.setOnClickListener {
            movieViewModel.onClearAll()
        }

        btnSelectAll.setOnClickListener {
            movieViewModel.onSelectAll()
        }

        btnDelete.setOnClickListener {
            movieViewModel.setShowDialog()
        }

        tvRefresh.setOnClickListener {
            movieViewModel.getMovies(this)
        }
    }

    private fun showDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.delete_movie))
                .setMessage(getString(R.string.delete_confirmation_msg))
                .setPositiveButton(
                        android.R.string.yes
                ) { dialog, which ->
                    movieViewModel.onDelete()
                    dialog.cancel()
                }
                .setNegativeButton(
                        android.R.string.no
                ) { dialog, which ->
                    dialog.cancel()
                }
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
    }

    private fun setData(movieViewModel: MovieViewModel) {
        movieViewModel.viewState.observe(this, Observer {
            if (null != it && it.movies.isNotEmpty()) {
                movieList = it.movies
                movieAdapter.setMovies(movies = it.movies)
                rvMovies.visibility = View.VISIBLE
                btnClearAll.visibility = View.VISIBLE
                btnSelectAll.visibility = View.VISIBLE
                btnDelete.visibility = View.VISIBLE
                tvRefresh.visibility = View.GONE
            } else {
                movieAdapter.setMovies(movies = null)
                rvMovies.visibility = View.GONE
                btnClearAll.visibility = View.GONE
                btnSelectAll.visibility = View.GONE
                btnDelete.visibility = View.GONE
                tvRefresh.visibility = View.VISIBLE
            }

            if (null != it && it.showDialog) {
                showDialog()
            }

            if (null != it && it.showMessage) {
                Snackbar.make(
                        btnDelete,
                        getString(R.string.select_movie),
                        Snackbar.LENGTH_LONG //
                )
                        .show()
            }
        })
    }

    override fun onLongClick(position: Int) {
        movieViewModel.onLongClick(position)
    }


    override fun onSelect(position: Int) {
        Snackbar.make(
                btnDelete,
                movieList[position].name,
                Snackbar.LENGTH_SHORT
        )
                .show()
        movieViewModel.onSelect(position)

    }
}
