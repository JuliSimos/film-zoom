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

    override suspend fun getPopularMovies(): NetworkResult<List<Movie>> =
        withContext(Dispatchers.IO) {
            try {
                val responseDto = api.getPopularMovies(currentLanguage, 1)

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

    override fun getPopularMoviesStream(): Flow<PagingData<Movie>> {
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

    override suspend fun getTrendingMovies(): NetworkResult<List<Movie>> =
        withContext(Dispatchers.IO) {
            try {
                val response =
                    api.getTrendingMovies(timeWindow = "week", language = currentLanguage, 1)
                val movies = response.results.map { it.toDomain() }
                NetworkResult.Success(movies)

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

    override fun getTrendingMoviesStream(): Flow<PagingData<Movie>> {
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

    // Métodos de la Wishlist (Favoritos)

// 1. Obtener la lista de deseados de forma reactiva desde Room
    override fun getWishlistMovies(): Flow<List<Movie>>{
        return movieDao.getWishlistMovies().map { entityList ->
            entityList.map { it.toDomain() }
        }
    }

    // 2. Guardar película en la wishlist
    override suspend fun addToWishlist(movie: Movie) = withContext(Dispatchers.IO) {
        withContext(Dispatchers.IO) {
            try {
                movieDao.insertMovie(movie.toEntity())
            } catch (e: Exception) {
                // Acá podés registrar el error con Log.e o manejarlo si fuera necesario
                e.printStackTrace()
            }
        }    }

    // 3. Eliminar película de la wishlist por su ID
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