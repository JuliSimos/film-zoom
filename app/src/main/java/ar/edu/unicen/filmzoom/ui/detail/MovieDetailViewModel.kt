package ar.edu.unicen.filmzoom.ui.detail

import androidx.compose.material.ripple.rememberRipple
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.filmzoom.domain.repository.MovieRepository
import ar.edu.unicen.filmzoom.ui.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    private val movieId: Int = savedStateHandle.get<Int>("movieId") ?: 0


    private val _uiState = MutableStateFlow(DetailUiState(false, null, null))

    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        if (movieId != 0)
            loadDetailData(movieId)
    }

    fun loadDetailData(id: Int){
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        try{
            val movieDetail = movieRepository.getMovieDetail(id)
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                detailMovie = movieDetail,
                errorMessage = null
            )
        }catch (e: Exception){
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                errorMessage = e.localizedMessage ?: "Error desconocido"
            )
        }
        }
    }





}