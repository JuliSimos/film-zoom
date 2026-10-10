package ar.edu.unicen.filmzoom.data.model

import ar.edu.unicen.filmzoom.domain.model.Movie
import com.google.gson.annotations.SerializedName

// DTO para la lista de películas (Home)
data class MovieResponseDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("poster_path")
    val posterPath: String?, // rutaPortada

    @SerializedName("title")
    val title: String,       // titulo

    @SerializedName("release_date")
    val releaseDate: String?, // fecha

    @SerializedName("vote_average")
    val voteAverage: Double,  // ranking

    @SerializedName("runtime")
    val runtime: Int?
)

data class MoviesPageResponseDto(
    @SerializedName("results")
    val results: List<MovieResponseDto>
)

// Función de extensión para transformar el DTO de red al modelo de Dominio
fun MovieResponseDto.toDomain(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        posterPath = this.posterPath ?: "", // Manejamos el nulo por si TMDB no manda imagen
        releaseDate = this.releaseDate ?: "",
        voteAverage = this.voteAverage,
        runtime = this.runtime ?: 0
    )
}

