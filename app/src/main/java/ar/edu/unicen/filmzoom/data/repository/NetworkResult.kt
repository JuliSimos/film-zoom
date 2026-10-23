package ar.edu.unicen.filmzoom.data.repository

sealed interface NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>
    data class Error(val exception: Throwable, val errorType: AppError) : NetworkResult<Nothing>
    object Loading : NetworkResult<Nothing>
}

