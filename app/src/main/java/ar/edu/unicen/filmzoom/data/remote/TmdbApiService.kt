package ar.edu.unicen.filmzoom.data.remote

import ar.edu.unicen.filmzoom.data.model.CreditsResponseDto
import ar.edu.unicen.filmzoom.data.model.MovieDetailResponseDto
import ar.edu.unicen.filmzoom.data.model.MoviesPageResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String
    ): MoviesPageResponseDto

    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") timeWindow: String = "week", // Puede ser "day" o "week", podria agregar una funcionalidad que permita modificarlo
        @Query("language") language: String
    ): MoviesPageResponseDto

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String
    ): MovieDetailResponseDto

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String
    ): CreditsResponseDto
}