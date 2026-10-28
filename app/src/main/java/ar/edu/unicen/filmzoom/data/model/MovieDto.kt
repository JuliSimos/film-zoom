package ar.edu.unicen.filmzoom.data.model

import ar.edu.unicen.filmzoom.domain.model.Movie
import com.google.gson.annotations.SerializedName

// DTO para la lista de películas (Home)
data class MovieResponseDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("poster_path")
    val posterPath: String, // rutaPortada

    @SerializedName("title")
    val title: String,       // titulo

    @SerializedName("release_date")
    val releaseDate: String, // fecha

    @SerializedName("vote_average")
    val voteAverage: Double,  // ranking
)

data class MoviesPageResponseDto(
    @SerializedName("results")
    val results: List<MovieResponseDto>
)

fun MovieResponseDto.toDomain(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        posterPath = "https://image.tmdb.org/t/p/w500${this.posterPath}", // Manejamos el nulo por si TMDB no manda imagen
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage,
    )
}

