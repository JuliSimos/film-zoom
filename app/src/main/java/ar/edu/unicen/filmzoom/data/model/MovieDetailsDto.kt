package ar.edu.unicen.filmzoom.data.model

import ar.edu.unicen.filmzoom.domain.model.CastMember
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
    val runtime: String,

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
    @SerializedName("cast")
    val cast: List<CastMemberDto>,

    @SerializedName("crew")
    val crew: List<CrewMemberDto>
)
data class CastMemberDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("character")
    val character: String, // Papel / Personaje del actor
    @SerializedName("profile_path")
    val profilePath: String?
)
data class CrewMemberDto(

    @SerializedName("name")
    val name: String,

    @SerializedName("job")
    val job: String // Aquí vendrá "Director"
)

// --- Función de extensión para mapear el detalle a Dominio ---
fun MovieDetailResponseDto.toDomain(
    crewList: List<CrewMemberDto>,
    castList: List<CastMemberDto>
    ): MovieDetails {

    // Buscamos al director dentro de la lista de crew
    val directorName = crewList.firstOrNull { it.job.equals("Director", ignoreCase = true) }?.name

// Tamaños específicos para cada tipo de imagen
    val posterBaseUrl = "https://image.tmdb.org/t/p/w500"   // Pósteres y banners grandes
    val profileBaseUrl = "https://image.tmdb.org/t/p/w185"  // Fotos de actores (más livianas)

    return MovieDetails(
        id = id,
        title = title,
        overview = overview.orEmpty(),
// Concatenamos de forma segura solo si no es nulo/vacío
        posterPath = posterPath?.let { "$posterBaseUrl$it" },
        backdropPath = backdropPath?.let { "$posterBaseUrl$it" },
        releaseDate = releaseDate.orEmpty(),
        runtime = runtime,
        voteAverage = voteAverage,
        homepage = homepage,
        genres = genres.map { it.name },
        production = productionCompanies?.firstOrNull()?.name.orEmpty(),
        director = directorName,
        cast = castList.map{ actor ->
            CastMember(
                id = actor.id,
                name = actor.name,
                character = actor.character,
                profilePath = actor.profilePath?.let { "$profileBaseUrl$it" }
            )
        }
    )
}