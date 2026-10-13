package ar.edu.unicen.filmzoom.ui.detail

import ar.edu.unicen.filmzoom.domain.model.MovieDetails

data class DetailUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val detailMovie: MovieDetails?
) {
}