package ar.edu.unicen.filmzoom.domain.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String, // Sinopsis
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,        // Fecha de lanzamiento
    val runtime: String,               // Duración en minutos
    val voteAverage: Double,        // Calificación
    val homepage: String?,          // Sitio web oficial
    val genres: List<String>, // Géneros
    val production: String,
    val director: String?,     // Director (obtenido del reparto/créditos
    val cast: List<CastMember>
)