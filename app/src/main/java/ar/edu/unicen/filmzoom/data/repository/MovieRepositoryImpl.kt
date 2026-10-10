package ar.edu.unicen.filmzoom.data.repository

import ar.edu.unicen.filmzoom.data.model.toDomain
import ar.edu.unicen.filmzoom.data.remote.TmdbApiService
import ar.edu.unicen.filmzoom.domain.model.Movie
import ar.edu.unicen.filmzoom.domain.model.MovieDetails
import ar.edu.unicen.filmzoom.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Locale
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api : TmdbApiService
): MovieRepository{

    // Obtenemos el lenguaje actual del dispositivo (ej: "es-ES", "es-AR", "en-US")
    private val currentLanguage: String
        get() = Locale.getDefault().toLanguageTag()

    override suspend fun getPopularMovies(): List<Movie> {
        val response = api.getPopularMovies((currentLanguage))
        return response.results.map {it.toDomain()}
    }

    override suspend fun getTrendingMovies(): List<Movie> {
        // Pedimos las tendencias de la semana (o del día, como prefieras)
        val response = api.getTrendingMovies(timeWindow = "week", language = currentLanguage)
        return response.results.map { it.toDomain() }
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetails {
        val detailDto = api.getMovieDetails(movieId,currentLanguage)
        val creditsDto = api.getMovieCredits(movieId, currentLanguage)
        return detailDto.toDomain(creditsDto.crew)
    }

    // Métodos de la Wishlist (Favoritos)
    // Después se conecta a una base de datos local (Room)
    override fun getWishlistMovies(): Flow<List<Movie>> {
        return flow { emit(emptyList()) }
    }

    override suspend fun addToWishlist(movie: Movie) {
        // TODO: Guardar en base de datos local
    }

    override suspend fun removeFromWishlist(movieId: Int) {
        // TODO: Eliminar de base de datos local
    }

}