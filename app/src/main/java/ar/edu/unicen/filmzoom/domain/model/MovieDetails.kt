package ar.edu.unicen.filmzoom.domain.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val runtime: String,
    val voteAverage: Double,
    val homepage: String?,
    val genres: List<String>,
    val production: String,
    val director: String?,
    val cast: List<CastMember>
)

fun MovieDetails.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        posterPath = posterPath ?: "",
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )
}