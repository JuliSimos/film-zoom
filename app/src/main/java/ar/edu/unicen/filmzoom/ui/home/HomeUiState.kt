package ar.edu.unicen.filmzoom.ui.home

import ar.edu.unicen.filmzoom.domain.model.Movie

sealed interface HomeUiState {
    object Loading : HomeUiState
    data class Success(
        val popularMovies: List<Movie>,
        val trendingMovies: List<Movie>,

    ) : HomeUiState
    data class Error(
    val message: Int,
    val solution: Int,
    val buttonText: Int,
    val isRetry: Boolean
    ) : HomeUiState
}