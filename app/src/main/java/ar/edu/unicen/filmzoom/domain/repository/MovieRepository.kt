package ar.edu.unicen.filmzoom.domain.repository

import androidx.paging.PagingData
import ar.edu.unicen.filmzoom.data.repository.NetworkResult
import ar.edu.unicen.filmzoom.domain.model.Movie
import ar.edu.unicen.filmzoom.domain.model.MovieDetails


import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    // Origen Remoto (API TMDB)

    // sección de Populares
    fun getPopularMovies(): Flow<PagingData<Movie>>

    // sección de Tendencias
    fun getTrendingMovies(): Flow<PagingData<Movie>>

    fun searchMovies(query: String): Flow<PagingData<Movie>>

    // pantalla de Detalle
    suspend fun getMovieDetail(movieId: Int): NetworkResult<MovieDetails>

    suspend fun getRecommendations(movieId: Int): NetworkResult<List<Movie>>

    // Origen Local (Deseados)
    fun getWishlistMovies(): Flow<List<Movie>>

    suspend fun addToWishlist(movie: Movie)

    suspend fun removeFromWishlist(movieId: Int)

    fun isMovieInWishlist(movieId: Int): Flow<Boolean>

}