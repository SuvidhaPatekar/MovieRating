package demo.codeparva.movie

data class Movie(
        val description: String,
        val stars: String,
        val name: String,
        val length: String?,
        val image: String,
        val year: String?,
        val rating: Double,
        val director: String?,
        val url: String,
        val selection: Boolean
)