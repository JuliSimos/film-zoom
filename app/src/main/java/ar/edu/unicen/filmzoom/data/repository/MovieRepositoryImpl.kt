package ar.edu.unicen.filmzoom.data.repository

import ar.edu.unicen.filmzoom.data.model.toDomain
import ar.edu.unicen.filmzoom.data.remote.TmdbApiService
import ar.edu.unicen.filmzoom.domain.model.Movie
import ar.edu.unicen.filmzoom.domain.model.MovieDetails
import ar.edu.unicen.filmzoom.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

    override suspend fun getPopularMovies(): NetworkResult<List<Movie>> = withContext(Dispatchers.IO){
        try {
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
        val response = api.getTrendingMovies(timeWindow = "week", language = currentLanguage)
        return response.results.map { it.toDomain() }
    }

    override suspend fun getMovieDetail(movieId: Int): NetworkResult<MovieDetails> = withContext(Dispatchers.IO) {
        try {
            // Hacemos las dos peticiones a la API
            val detailDto = api.getMovieDetails(movieId, currentLanguage)
            val creditsDto = api.getMovieCredits(movieId, currentLanguage)

            // Mapeamos a dominio
            val movieDetails = detailDto.toDomain(creditsDto.crew, creditsDto.cast)

            NetworkResult.Success(movieDetails)

        } catch (e: HttpException) {
            // Clasificamos según el código HTTP (404, 500, etc.)
            val errorType = when (e.code()) {
                404 -> AppError.NOT_FOUND
                500 -> AppError.SERVER
                else -> AppError.UNKNOWN
            }
            NetworkResult.Error(exception = e, errorType = errorType)
        } catch (e: java.io.IOException) {
            // Error de red / sin internet
            NetworkResult.Error(exception = e, errorType = AppError.NO_INTERNET)
        } catch (e: Exception) {
            // Cualquier otro error inesperado
            NetworkResult.Error(exception = e, errorType = AppError.UNKNOWN)
        }
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