package ar.edu.unicen.filmzoom.ui.home

data class HomeErrorUiState(
    val message: Int,
    val solution: Int,
    val buttonText: Int,
    val isRetry: Boolean
)