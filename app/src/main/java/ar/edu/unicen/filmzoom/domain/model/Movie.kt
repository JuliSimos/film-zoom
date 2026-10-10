package ar.edu.unicen.filmzoom.domain.model

data class Movie(
    val id: Int,
    val posterPath: String?,
    val title: String,
    val releaseDate: String,
    val voteAverage: Double,
    val runtime: Int
)
