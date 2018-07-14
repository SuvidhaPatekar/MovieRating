import demo.codeparva.movie.Movie

fun copyList(movies: List<Movie>): MutableList<Movie> {
    val copy = mutableListOf<Movie>()
    for (t in movies) {
        copy.add(t.copy())
    }
    return copy
}