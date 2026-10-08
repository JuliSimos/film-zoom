package ar.edu.unicen.filmzoom.data.model

import com.google.gson.annotations.SerializedName


// DTO para el detalle completo de la película (/movie/{id})
data class MovieDetailResponseDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("overview")
    val overview: String?,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("runtime")
    val runtime: Int?,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("homepage")
    val homepage: String?,

    @SerializedName("genres")
    val genres: List<GenreDto>
)

data class GenreDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String
)