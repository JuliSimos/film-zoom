package ar.edu.unicen.filmzoom.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.filmzoom.R
import ar.edu.unicen.filmzoom.data.repository.AppError
import ar.edu.unicen.filmzoom.data.repository.NetworkResult
import ar.edu.unicen.filmzoom.domain.model.Movie
import ar.edu.unicen.filmzoom.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel(){

    // 1. Estado mutable interno (privado para que nadie de afuera lo rompa)
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    // 2. Estado reactivo de la Wishlist directamente desde Room
    val wishlistMovies: StateFlow<List<Movie>> = movieRepository.getWishlistMovies()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        fetchMovies()
    }

    fun fetchMovies() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            // Ejecutamos ambas peticiones en paralelo para optimizar tiempo
            val popularDeferred = async { movieRepository.getPopularMovies() }
            val trendingDeferred = async { movieRepository.getTrendingMovies() }

            val popularResult = popularDeferred.await()
            val trendingResult = trendingDeferred.await()

            // Verificamos si ambas fueron exitosas
            if (popularResult is NetworkResult.Success && trendingResult is NetworkResult.Success) {
                _uiState.value = HomeUiState.Success(
                    popularMovies = popularResult.data,
                    trendingMovies = trendingResult.data
                )
            } else {
                // Si alguna falló, capturamos el error para mostrar la pantalla de error correspondiente
                val errorResult = (popularResult as? NetworkResult.Error)
                    ?: (trendingResult as? NetworkResult.Error)

                val errorType = errorResult?.errorType ?: AppError.UNKNOWN

                _uiState.value = when (errorType) {
                    AppError.NO_INTERNET -> HomeUiState.Error(
                        message = R.string.error_no_internet_title,
                        solution = R.string.error_no_internet_solution,
                        buttonText = R.string.action_retry,
                        isRetry = true
                    )
                    AppError.NOT_FOUND -> HomeUiState.Error(
                        message = R.string.error_unexpected_title,
                        solution = R.string.error_404,
                        buttonText = R.string.action_retry,
                        isRetry = true
                    )
                    AppError.SERVER -> HomeUiState.Error(
                        message = R.string.error_operation_failed,
                        solution = R.string.error_500,
                        buttonText = R.string.action_retry,
                        isRetry = true
                    )
                    AppError.UNKNOWN -> HomeUiState.Error(
                        message = R.string.error_unexpected_title,
                        solution = R.string.error_unexpected_solution,
                        buttonText = R.string.action_retry,
                        isRetry = true
                    )
                }
            }
        }
    }

    fun retry() {
        fetchMovies()
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