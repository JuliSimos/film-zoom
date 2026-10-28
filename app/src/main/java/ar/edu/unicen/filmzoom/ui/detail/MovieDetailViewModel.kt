package ar.edu.unicen.filmzoom.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.filmzoom.R
import ar.edu.unicen.filmzoom.data.repository.NetworkResult
import ar.edu.unicen.filmzoom.data.repository.AppError
import ar.edu.unicen.filmzoom.domain.model.toMovie
import ar.edu.unicen.filmzoom.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val movieId: Int = savedStateHandle.get<Int>("movieId") ?: 0

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    // Observamos en tiempo real si esta película está en la wishlist
    val isWishlisted: StateFlow<Boolean> = movieRepository.isMovieInWishlist(movieId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    init {
        if (movieId != 0)
            loadDetailData(movieId)
    }

    fun loadDetailData(id: Int) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading

            when (val result = movieRepository.getMovieDetail(id)) {

                is NetworkResult.Success -> {
                    _uiState.value = DetailUiState.Success(
                        details = result.data
                    )
                }

                is NetworkResult.Loading -> {
                    _uiState.value = DetailUiState.Loading
                }

                is NetworkResult.Error -> {
                    _uiState.value = when (result.errorType) {
                        AppError.NO_INTERNET -> DetailUiState.Error(
                            message = R.string.error_no_internet_title,
                            solution = R.string.error_no_internet_solution,
                            buttonText = R.string.action_retry,
                            isRetry = true
                        )
                        AppError.NOT_FOUND -> DetailUiState.Error(
                            message = R.string.error_unexpected_title,
                            solution = R.string.error_404,
                            buttonText = R.string.action_go_back,
                            isRetry = false
                        )
                        AppError.SERVER -> DetailUiState.Error(
                            message = R.string.error_operation_failed,
                            solution = R.string.error_500,
                            buttonText = R.string.action_retry,
                            isRetry = true
                        )
                        AppError.UNKNOWN -> DetailUiState.Error(
                            message = R.string.error_unexpected_title,
                            solution = R.string.error_unexpected_solution,
                            buttonText = R.string.action_go_back,
                            isRetry = false
                        )
                    }
                }
            }
        }
    }


    // Función para el botón de reintento en la UI de detalle
    fun retry() {
        if (movieId != 0) {
            loadDetailData(movieId)
        }
    }

    fun onWishlistClick() {
        val currentState = _uiState.value
        if (currentState is DetailUiState.Success) {
            val movie = currentState.details.toMovie()
            val currentlyWishlisted = isWishlisted.value

            viewModelScope.launch {
                if (currentlyWishlisted) {
                    movieRepository.removeFromWishlist(movie.id)
                } else {
                    movieRepository.addToWishlist(movie)
                }
            }
        }
    }
}
