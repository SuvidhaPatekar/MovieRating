package demo.codeparva.movie

import java.util.*

data class Movie(
        val id: Int = Random().nextInt(10000),
        val description: String,
        val stars: String,
        var name: String,
        val length: String?,
        val image: String,
        val year: String?,
        val rating: Double,
        val director: String?,
        val url: String,
        var selection: Boolean
)