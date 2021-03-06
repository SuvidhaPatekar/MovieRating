package demo.codeparva.movie

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.util.Log
import copyList
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class MovieViewModel : ViewModel() {

    data class ViewState(
            val movies: List<Movie>,
            val showDialog: Boolean = false,
            val showMessage: Boolean = false
    )

    val viewState: MutableLiveData<ViewState> = MutableLiveData()
    private fun currentViewState(): ViewState = viewState.value!!

    init {
        viewState.value = ViewState(movies = ArrayList())
    }

    fun getMovies(context: Context) {
        if (currentViewState().movies.isEmpty()) {
            val movieList = ArrayList<Movie>()
            val obj = JSONObject(loadJSONFromAsset(context))
            val movieArray = obj.getJSONArray("movies")
            Log.d("Details-->", movieArray.toString())
            try {
                for (i in 0 until movieArray.length()) {
                    val jsonObject = movieArray.getJSONObject(i)
                    val description = jsonObject.getString("description")
                    val name = jsonObject.getString("name")
                    val rating = jsonObject.getString("rating")
                    val url = jsonObject.getString("url")
                    val selection = jsonObject.getString("selection")
                    val director = jsonObject.getString("director")
                    val year = jsonObject.getString("year")
                    val length = jsonObject.getString("length")
                    val stars = jsonObject.getString("stars")
                    val image = jsonObject.getString("image")

                    movieList.add(
                            Movie(
                                    description = description, name = name, rating = rating.toDouble(), url = url,
                                    selection = selection.toBoolean(), director = director, year = year,
                                    length = length, stars = stars, image = image
                            )
                    )
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            viewState.value = currentViewState().copy(movies = movieList)
        }
    }

    private fun loadJSONFromAsset(context: Context): String? {
        var json: String? = null
        try {
            val inputStream = context.assets.open("Movies")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    fun onSelect(position: Int) {
        val localMovies = copyList(currentViewState().movies)
        localMovies[position].selection = !localMovies[position].selection
        viewState.value =
                currentViewState().copy(movies = localMovies, showMessage = false, showDialog = false)
    }

    fun onClearAll() {
        val localMovies = copyList(currentViewState().movies)
        for (movie in localMovies) {
            if (movie.selection)
                movie.selection = false
        }
        viewState.value =
                currentViewState().copy(movies = localMovies, showDialog = false, showMessage = false)
    }

    fun onSelectAll() {
        val localMovies = copyList(currentViewState().movies)

        for (movie in localMovies) {
            if (!movie.selection)
                movie.selection = true
        }
        viewState.value =
                currentViewState().copy(movies = localMovies, showMessage = false, showDialog = false)
    }

    fun setShowDialog() {
        var isShowDialog = false
        var isShowMessage = false
        for (movie in currentViewState().movies) {
            if (movie.selection) {
                isShowDialog = true
                isShowMessage = false
                break
            } else {
                isShowMessage = true
            }
        }
        viewState.value =
                currentViewState().copy(showMessage = isShowMessage, showDialog = isShowDialog)
    }

    fun onDelete() {
        val localMovies = copyList(currentViewState().movies)
        val deleteMovie = ArrayList<Movie>()
        for (movie in localMovies) {
            if (movie.selection) {
                deleteMovie.add(movie)
            }
        }
        localMovies.removeAll(deleteMovie)
        viewState.value =
                currentViewState().copy(movies = localMovies, showDialog = false, showMessage = false)
    }

    fun onLongClick(position: Int) {
        val localMovies = copyList(currentViewState().movies)

        val localMovie = localMovies[position]

        localMovies.add(position + 1, localMovie.copy(id = Random().nextInt(10000)))
        viewState.value =
                currentViewState().copy(movies = localMovies, showMessage = false, showDialog = false)
    }
}