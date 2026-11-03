package ar.edu.unicen.filmzoom.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import ar.edu.unicen.filmzoom.R
import ar.edu.unicen.filmzoom.ui.components.MessaggeError
import ar.edu.unicen.filmzoom.ui.home.components.BottomNavigationBar
import ar.edu.unicen.filmzoom.ui.home.components.LoadingCardsWireframe
import ar.edu.unicen.filmzoom.ui.home.components.MovieGridScreen
import ar.edu.unicen.filmzoom.ui.home.components.MovieGridScreenPaging
import ar.edu.unicen.filmzoom.ui.home.components.TopNavegationBar
import ar.edu.unicen.filmzoom.ui.home.components.WishlistItemCard
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit = {},
) {
    val popularMoviesItems = viewModel.popularMovies.collectAsLazyPagingItems()
    val trendingMoviesItems = viewModel.trendingMovies.collectAsLazyPagingItems()
    val wishlistMovies by viewModel.wishlistMovies.collectAsState()
    val errorState by viewModel.homeErrorState.collectAsState()

    var selectedTab by remember { mutableIntStateOf(0) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val movieRemovedMessage = stringResource(R.string.msg_movie_removed)

    val currentLoadState = when (selectedTab) {
        0 -> popularMoviesItems.loadState.refresh
        1 -> trendingMoviesItems.loadState.refresh
        else -> null
    }

    if (currentLoadState is LoadState.Error) {
        viewModel.handlePagingError(currentLoadState.error)
    } else if (currentLoadState is LoadState.NotLoading) {
        viewModel.dismissError()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopNavegationBar(
                selectedTab = selectedTab,
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { index ->
                    if (selectedTab != index) {
                        selectedTab = index
                        viewModel.dismissError()
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Capa de fondo: Si está cargando O si hay un error activo, mostramos el wireframe de tarjetas de fondo
            if (currentLoadState is LoadState.Loading || errorState != null) {
                LoadingCardsWireframe()
            }

            when (selectedTab) {
                0 -> {
                    MovieGridScreenPaging(
                        innerPadding = PaddingValues(0.dp),
                        lazyPagingItems = popularMoviesItems,
                        onMovieClick = onMovieClick
                    )
                }
                1 -> {
                    MovieGridScreenPaging(
                        innerPadding = PaddingValues(0.dp),
                        lazyPagingItems = trendingMoviesItems,
                        onMovieClick = onMovieClick
                    )
                }
                2 -> {
                    MovieGridScreen(
                        innerPadding = PaddingValues(0.dp),
                        movies = wishlistMovies,
                        onMovieClick = onMovieClick,
                        movieItemContent = { movie ->
                            WishlistItemCard(
                                movie = movie,
                                onMovieClick = { onMovieClick(movie.id) },
                                onDeleteClick = {
                                    viewModel.removeFromWishlist(movie.id)
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = movieRemovedMessage
                                        )
                                    }
                                }
                            )
                        }
                    )
                }
            }
            errorState?.let { error ->
                if (selectedTab != 2) {
                    MessaggeError(
                        message = stringResource(error.message),
                        solution = stringResource(error.solution),
                        buttonText = stringResource(error.buttonText),
                        onActionClick = {
                            viewModel.dismissError()
                            if (selectedTab == 0) popularMoviesItems.retry()
                            else trendingMoviesItems.retry()
                        }
                    )
                }
            }
        }
    }
}
@Preview(name = "Light Mode", showBackground = true)
@Composable
fun HomeScreenLightPreview() {

    FilmZoomTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            HomeScreen()
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun HomeScreenDarkPreview() {

    FilmZoomTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            HomeScreen()
        }
    }
}