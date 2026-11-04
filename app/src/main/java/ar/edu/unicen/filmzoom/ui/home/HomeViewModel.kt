package ar.edu.unicen.filmzoom.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import ar.edu.unicen.filmzoom.R
import ar.edu.unicen.filmzoom.data.repository.AppError
import ar.edu.unicen.filmzoom.domain.model.Movie
import ar.edu.unicen.filmzoom.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel() {
    val popularMovies: Flow<PagingData<Movie>> = movieRepository
        .getPopularMovies()
        .cachedIn(viewModelScope)

    val trendingMovies: Flow<PagingData<Movie>> = movieRepository
        .getTrendingMovies()
        .cachedIn(viewModelScope)

    // Wishlist de Room reactiva
    val wishlistMovies: StateFlow<List<Movie>> = movieRepository.getWishlistMovies()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Estados del Buscador
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchResults: Flow<PagingData<Movie>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) {
                flowOf(PagingData.empty())
            } else {
                movieRepository.searchMovies(query)
                    .cachedIn(viewModelScope)
            }
        }

    // Estado de errores
    private val _homeErrorState = MutableStateFlow<HomeErrorUiState?>(null)
    val homeErrorState: StateFlow<HomeErrorUiState?> = _homeErrorState.asStateFlow()

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onSearchToggled(searching: Boolean) {
        _isSearching.value = searching
        if (!searching) {
            _searchQuery.value = ""
        }
    }
    fun handlePagingError(throwable: Throwable) {
        val appError = when (throwable) {
            is IOException -> AppError.NO_INTERNET
            is HttpException -> {
                when (throwable.code()) {
                    404 -> AppError.NOT_FOUND
                    500 -> AppError.SERVER
                    else -> AppError.UNKNOWN
                }
            }
            else -> AppError.UNKNOWN
        }

        _homeErrorState.value = when (appError) {
            AppError.NO_INTERNET -> HomeErrorUiState(
                message = R.string.error_no_internet_title,
                solution = R.string.error_no_internet_solution,
                buttonText = R.string.action_retry,
                isRetry = true
            )
            AppError.NOT_FOUND -> HomeErrorUiState(
                message = R.string.error_unexpected_title,
                solution = R.string.error_404,
                buttonText = R.string.action_go_back,
                isRetry = false
            )
            AppError.SERVER -> HomeErrorUiState(
                message = R.string.error_operation_failed,
                solution = R.string.error_500,
                buttonText = R.string.action_retry,
                isRetry = true
            )
            AppError.UNKNOWN -> HomeErrorUiState(
                message = R.string.error_unexpected_title,
                solution = R.string.error_unexpected_solution,
                buttonText = R.string.action_go_back,
                isRetry = false
            )
        }
    }

    fun dismissError() {
        _homeErrorState.value = null
    }

        // Eliminar de deseados
    fun removeFromWishlist(movieId: Int) {
        viewModelScope.launch {
            movieRepository.removeFromWishlist(movieId)
        }
    }
}