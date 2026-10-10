package ar.edu.unicen.filmzoom.data.model

import ar.edu.unicen.filmzoom.domain.model.MovieDetails
import com.google.gson.annotations.SerializedName


// DTO para el detalle completo de la película (/movie/{id})
data class MovieDetailResponseDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("overview")
    val overview: String?,

    @SerializedName("poster_path")
    val posterPath: String?, // portada vertical

    @SerializedName("backdrop_path")
    val backdropPath: String?, // portada de fondo (banner)

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("runtime")
    val runtime: Int?,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("homepage")
    val homepage: String?,

    @SerializedName("genres")
    val genres: List<GenreDto>,

    @SerializedName("production_companies")
val productionCompanies: List<ProductionCompanyDto>?
)

data class GenreDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String
)

data class ProductionCompanyDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("logo_path")
    val logoPath: String?
)

data class CreditsResponseDto(
    @SerializedName("crew")
    val crew: List<CrewMemberDto>
)

data class CrewMemberDto(
    @SerializedName("name")
    val name: String,

    @SerializedName("job")
    val job: String // Aquí vendrá "Director"
)

// --- Función de extensión para mapear el detalle a Dominio ---
fun MovieDetailResponseDto.toDomain(crewList: List<CrewMemberDto>): MovieDetails {
    // Buscamos al director dentro de la lista de crew
    val directorName = crewList.firstOrNull { it.job.equals("Director", ignoreCase = true) }?.name

    return MovieDetails(
        id = id,
        title = title,
        overview = overview.orEmpty(),
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate.orEmpty(),
        runtime = runtime ?: 0,
        voteAverage = voteAverage,
        homepage = homepage,
        genres = genres.map { it.name },
        production = productionCompanies?.firstOrNull()?.name.orEmpty(),
        director = directorName
    )
}