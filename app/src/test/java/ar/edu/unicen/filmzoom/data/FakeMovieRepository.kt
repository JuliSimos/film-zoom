package ar.edu.unicen.filmzoom.data

import ar.edu.unicen.filmzoom.data.repository.NetworkResult
import ar.edu.unicen.filmzoom.domain.model.Movie
import ar.edu.unicen.filmzoom.domain.model.MovieDetails
import ar.edu.unicen.filmzoom.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class FakeMovieRepository : MovieRepository {

    // Variable para cambiar el resultado de forma dinámica en cada test
    var resultToReturn: NetworkResult<List<Movie>> = NetworkResult.Success(emptyList())

    override suspend fun getPopularMovies(): NetworkResult<List<Movie>> {
        return resultToReturn
    }

    override suspend fun getTrendingMovies(): NetworkResult<List<Movie>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieDetail(movieId: Int): NetworkResult<MovieDetails> {
        TODO("Not yet implemented")
    }

    override fun getWishlistMovies(): Flow<List<Movie>> {
        TODO("Not yet implemented")
    }

    override suspend fun addToWishlist(movie: Movie) {
        TODO("Not yet implemented")
    }

    override suspend fun removeFromWishlist(movieId: Int) {
        TODO("Not yet implemented")
    }
}