package ar.edu.unicen.filmzoom.domain.repository

import androidx.paging.PagingData
import ar.edu.unicen.filmzoom.data.repository.NetworkResult
import ar.edu.unicen.filmzoom.domain.model.Movie
import ar.edu.unicen.filmzoom.domain.model.MovieDetails


import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    // --- 1. Origen Remoto (API TMDB) ---

    // Para la sección de Populares
    suspend fun getPopularMovies(): NetworkResult<List<Movie>>
    fun getPopularMoviesStream(): Flow<PagingData<Movie>>

    // Para la sección de Tendencias
    suspend fun getTrendingMovies(): NetworkResult<List<Movie>>
    fun getTrendingMoviesStream(): Flow<PagingData<Movie>>

    // Para la pantalla de Detalle
    suspend fun getMovieDetail(movieId: Int): NetworkResult<MovieDetails>


    // --- 2. Origen Local (Deseados / Favoritos) ---
    fun getWishlistMovies(): Flow<List<Movie>>

    // Pendiente: Métodos para gestionar los deseos localmente
    suspend fun addToWishlist(movie: Movie)

    suspend fun removeFromWishlist(movieId: Int)

    fun isMovieInWishlist(movieId: Int): Flow<Boolean>
}