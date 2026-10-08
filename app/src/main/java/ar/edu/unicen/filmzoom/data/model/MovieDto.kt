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

    @SerializedName("genre_ids")
    val genreIds: List<Int>?, // genero

    @SerializedName("runtime")
    val runtime: Int?
)

data class MoviesPageResponseDto(
    @SerializedName("results")
    val results: List<MovieResponseDto>
)

