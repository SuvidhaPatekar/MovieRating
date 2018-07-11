package demo.codeparva.movie

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class MovieViewModel(val context: Context) : ViewModel() {

    data class ViewState(val movies: ArrayList<Movie>)

    private val viewState: MutableLiveData<ViewState> = MutableLiveData()
    private fun currentViewState(): ViewState = viewState.value!!

    init {
        viewState.value = ViewState(movies = ArrayList())
    }

    fun getMovies() {
        val obj = JSONObject(loadJSONFromAsset())
        val movieArray = obj.getJSONArray("movies")
        val movieList = ArrayList<Movie>()
        try {
            for (i in 0 until movieArray.length()) {
                val jsonObject = movieArray.getJSONObject(i)
                Log.d("Details-->", jsonObject.getString("movies"))
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

                movieList.add(Movie(description = description, name = name, rating = rating.toDouble(), url = url, selection = selection.toBoolean(), director = director, year = year, length = length, stars = stars, image = image))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        viewState.value = currentViewState().copy(movies = movieList)
    }

    private fun loadJSONFromAsset(): String? {
        var json: String? = null
        try {
            val `is` = context.getAssets().open("Movies.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}