package ar.edu.unicen.filmzoom.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import ar.edu.unicen.filmzoom.data.local.dao.MovieDao
import ar.edu.unicen.filmzoom.data.local.entity.toDomain
import ar.edu.unicen.filmzoom.data.local.entity.toEntity
import ar.edu.unicen.filmzoom.data.model.toDomain
import ar.edu.unicen.filmzoom.data.remote.TmdbApiService
import ar.edu.unicen.filmzoom.domain.model.Movie
import ar.edu.unicen.filmzoom.domain.model.MovieDetails
import ar.edu.unicen.filmzoom.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import java.util.Locale
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api : TmdbApiService,
    private val movieDao: MovieDao
): MovieRepository {

    // Obtenemos el lenguaje actual del dispositivo (ej: "es-ES", "es-AR", "en-US")
    private val currentLanguage: String
        get() = Locale.getDefault().toLanguageTag()

    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 2
            ),
            pagingSourceFactory = {
                MoviePagingSource { page ->
                    val responseDto = api.getPopularMovies(language = currentLanguage, page = page)
                    responseDto.results.map { it.toDomain() }
                }
            }
        ).flow
    }

    override fun getTrendingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 2
            ),
            pagingSourceFactory = {
                MoviePagingSource { page ->
                    val responseDto = api.getTrendingMovies(
                        timeWindow = "week",
                        language = currentLanguage,
                        page = page
                    )
                    responseDto.results.map { it.toDomain() }
                }
            }
        ).flow
    }

    override suspend fun getMovieDetail(movieId: Int): NetworkResult<MovieDetails> =
        withContext(Dispatchers.IO) {
            try {
                val detailDto = api.getMovieDetails(movieId, currentLanguage)
                val creditsDto = api.getMovieCredits(movieId, currentLanguage)

                val movieDetails = detailDto.toDomain(creditsDto.crew, creditsDto.cast)

                NetworkResult.Success(movieDetails)

            } catch (e: HttpException) {
                val errorType = when (e.code()) {
                    404 -> AppError.NOT_FOUND
                    500 -> AppError.SERVER
                    else -> AppError.UNKNOWN
                }
                NetworkResult.Error(exception = e, errorType = errorType)
            } catch (e: java.io.IOException) {
                NetworkResult.Error(exception = e, errorType = AppError.NO_INTERNET)
            } catch (e: Exception) {
                NetworkResult.Error(exception = e, errorType = AppError.UNKNOWN)
            }
        }

    override suspend fun getRecommendations(movieId: Int): NetworkResult<List<Movie>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getRecommendations(movieId = movieId, language = currentLanguage)
                val movies = response.results.map { it.toDomain() }
                NetworkResult.Success<List<Movie>>(movies)

            } catch (e: IOException) {
                NetworkResult.Error(exception = e, errorType = AppError.NO_INTERNET)
            } catch (e: HttpException) {
                val errorType = when (e.code()) {
                    404 -> AppError.NOT_FOUND
                    500 -> AppError.SERVER
                    else -> AppError.UNKNOWN
                }
                NetworkResult.Error(exception = e, errorType = errorType)
            } catch (e: Exception) {
                NetworkResult.Error(exception = e, errorType = AppError.UNKNOWN)
            }
        }
    }

    override fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 2
            ),
            pagingSourceFactory = {
                MoviePagingSource { page ->
                    val responseDto = api.searchMovies(
                        query = query,
                        language = currentLanguage,
                        page = page
                    )
                    responseDto.results.map { it.toDomain() }
                }
            }
        ).flow
    }

    // Métodos de la Wishlist (Deseados)

// Obtener la lista de deseados desde Room
    override fun getWishlistMovies(): Flow<List<Movie>>{
        return movieDao.getWishlistMovies().map { entityList ->
            entityList.map { it.toDomain() }
        }
    }

    // Guardar película en la wishlist
    override suspend fun addToWishlist(movie: Movie) = withContext(Dispatchers.IO) {
        withContext(Dispatchers.IO) {
            try {
                movieDao.insertMovie(movie.toEntity())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }    }

    // Eliminar película de la wishlist por su ID
    override suspend fun removeFromWishlist(movieId: Int) = withContext(Dispatchers.IO) {
        withContext(Dispatchers.IO) {
            try {
                movieDao.deleteMovieById(movieId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    override fun isMovieInWishlist(movieId: Int): Flow<Boolean>{
        return movieDao.isMovieInWishlist(movieId)
    }

}