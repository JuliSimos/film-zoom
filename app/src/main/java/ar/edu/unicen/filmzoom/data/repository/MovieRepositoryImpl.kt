package ar.edu.unicen.filmzoom.data.repository

import ar.edu.unicen.filmzoom.data.model.toDomain
import ar.edu.unicen.filmzoom.data.remote.TmdbApiService
import ar.edu.unicen.filmzoom.domain.model.Movie
import ar.edu.unicen.filmzoom.domain.model.MovieDetails
import ar.edu.unicen.filmzoom.domain.repository.MovieRepository
import okio.IOException
import retrofit2.HttpException
import java.util.Locale
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api : TmdbApiService
): MovieRepository{

    // Obtenemos el lenguaje actual del dispositivo (ej: "es-ES", "es-AR", "en-US")
    private val currentLanguage: String
        get() = Locale.getDefault().toLanguageTag()

    override suspend fun getPopularMovies(): NetworkResult<List<Movie>> {
        return try {
            val responseDto = api.getPopularMovies(currentLanguage)

            val movies = responseDto.results.map { it.toDomain() }

            NetworkResult.Success(movies)

        } catch (e: IOException) {
            // Error de red
            NetworkResult.Error(exception = e, errorType = AppError.NO_INTERNET)

        } catch (e: HttpException) {
            // Error del servidor: clasificamos según el código HTTP
            val errorType = when (e.code()) {
                404 -> AppError.NOT_FOUND
                500 -> AppError.SERVER
                else -> AppError.UNKNOWN // Para un 401, 503, etc.
            }
            NetworkResult.Error(exception = e, errorType = errorType)

        } catch (e: Exception) {
            // Cualquier otro error imprevisto
            NetworkResult.Error(exception = e, errorType = AppError.UNKNOWN)
        }
    }

    override suspend fun getTrendingMovies(): List<Movie> {
        // Pedimos las tendencias de la semana (o del día, como prefieras)
        val response = api.getTrendingMovies(timeWindow = "week", language = currentLanguage)
        return response.results.map { it.toDomain() }
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetails {
        val detailDto = api.getMovieDetails(movieId, currentLanguage)
        val creditsDto = api.getMovieCredits(movieId, currentLanguage)
        return detailDto.toDomain(creditsDto.crew, creditsDto.cast)
    }

    // Métodos de la Wishlist (Favoritos)
    // Después se conecta a una base de datos local (Room)
    override fun getWishlistMovies(): List<Movie>{
        return emptyList()
    }

    override suspend fun addToWishlist(movie: Movie) {
    }

    override suspend fun removeFromWishlist(movieId: Int) {
    }

}