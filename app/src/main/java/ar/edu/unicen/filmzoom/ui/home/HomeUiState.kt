package ar.edu.unicen.filmzoom.ui.home

import ar.edu.unicen.filmzoom.domain.model.Movie

data class HomeUiState(
    val isLoading : Boolean = false,
    val popularMovies: List<Movie> = emptyList(),
    val trendingMovies: List<Movie> = emptyList(),
    val errorMessage: String? = null
)