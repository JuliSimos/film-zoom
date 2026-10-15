package ar.edu.unicen.filmzoom.domain.repository

import ar.edu.unicen.filmzoom.domain.model.Movie
import ar.edu.unicen.filmzoom.domain.model.MovieDetails


import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    // --- 1. Origen Remoto (API TMDB) ---

    // Para la sección de Populares
    suspend fun getPopularMovies(): List<Movie>

    // Para la sección de Tendencias
    suspend fun getTrendingMovies(): List<Movie>

    // Para la pantalla de Detalle
    suspend fun getMovieDetail(movieId: Int): MovieDetails


    // --- 2. Origen Local (Deseados / Favoritos) ---

    // Al usar flow, cuando se marca o desmarca una película
    // en el detalle, la lista de deseos de la Home se actualiza sola en tiempo real
    fun getWishlistMovies(): List<Movie>

    // Métodos para gestionar los deseos localmente
    suspend fun addToWishlist(movie: Movie)

    suspend fun removeFromWishlist(movieId: Int)
}