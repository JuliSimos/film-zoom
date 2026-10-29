package ar.edu.unicen.filmzoom.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import ar.edu.unicen.filmzoom.domain.model.Movie
import ar.edu.unicen.filmzoom.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel() {

    // 1. Streams paginados de red (vienen del Pager que armamos en el repositorio)
    val popularMovies: Flow<PagingData<Movie>> = movieRepository
        .getPopularMoviesStream()
        .cachedIn(viewModelScope)

    val trendingMovies: Flow<PagingData<Movie>> = movieRepository
        .getTrendingMoviesStream()
        .cachedIn(viewModelScope)

    // Wishlist de Room
    val wishlistMovies: StateFlow<List<Movie>> = movieRepository.getWishlistMovies()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    fun retry() {
    }

    fun dismissError() {
        TODO("Not yet implemented")
    }

    fun removeFromWishlist(movieId: Int) {
        viewModelScope.launch {
            movieRepository.removeFromWishlist(movieId)
        }
    }

}