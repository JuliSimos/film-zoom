package ar.edu.unicen.filmzoom.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.filmzoom.R
import ar.edu.unicen.filmzoom.data.repository.AppError
import ar.edu.unicen.filmzoom.data.repository.NetworkResult
import ar.edu.unicen.filmzoom.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel(){



    // 2. Estado mutable interno (privado para que nadie de afuera lo rompa)
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)

    // 3. Estado público de sólo lectura al que se suscribe la UI
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        fetchMovies()
    }

    fun fetchMovies(){
        viewModelScope.launch {
            //estado de carga activado
            _uiState.value = HomeUiState.Loading
            //kotlinx.coroutines.delay(3500L)
            when(val result = movieRepository.getPopularMovies()){
                is NetworkResult.Success -> {
                    _uiState.value = HomeUiState.Success(
                        result.data,
                        trendingMovies = result.data)
                }
                is NetworkResult.Loading -> {
                    _uiState.value = HomeUiState.Loading
                }
                is NetworkResult.Error -> {
                    _uiState.value = when (result.errorType) {
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
    }

    fun retry() {
        fetchMovies()
    }

    fun dismissError() {
        TODO("Not yet implemented")
    }

}