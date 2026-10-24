package ar.edu.unicen.filmzoom.ui.detail

import ar.edu.unicen.filmzoom.domain.model.MovieDetails
sealed interface DetailUiState {
    object Loading : DetailUiState

    data class Success(
        val details: MovieDetails,
    ) : DetailUiState

    data class Error(
        val message: Int,
        val solution: Int,
        val buttonText: Int,
        val isRetry: Boolean
    ) : DetailUiState
}