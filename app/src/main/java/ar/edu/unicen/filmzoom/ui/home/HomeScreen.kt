package ar.edu.unicen.filmzoom.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
import ar.edu.unicen.filmzoom.ui.theme.AppSpacing
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
    val searchMoviesItems = viewModel.searchResults.collectAsLazyPagingItems()
    val wishlistMovies by viewModel.wishlistMovies.collectAsState()
    val errorState by viewModel.homeErrorState.collectAsState()

    // Estados de búsqueda y controlador de teclado
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    var selectedTab by remember { mutableIntStateOf(0) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val movieRemovedMessage = stringResource(R.string.msg_movie_removed)

    // Estado de la grilla para unificar el control de scroll
    val gridState = rememberLazyGridState()

    // Cuando el usuario hace scroll en la grilla
    LaunchedEffect(gridState) {
        snapshotFlow { gridState.isScrollInProgress }
            .collect { isScrolling ->
                if (isScrolling) {
                    keyboardController?.hide()
                    if (isSearching && searchQuery.isBlank()) {
                        viewModel.onSearchToggled(false)
                    }
                }
            }
    }

    val currentLoadState = when {
        isSearching -> searchMoviesItems.loadState.refresh
        selectedTab == 0 -> popularMoviesItems.loadState.refresh
        selectedTab == 1 -> trendingMoviesItems.loadState.refresh
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
                searchQuery = searchQuery,
                onQueryChanged = { viewModel.onSearchQueryChanged(it) },
                isSearching = isSearching,
                onSearchToggled = { viewModel.onSearchToggled(it) }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { index ->
                    if (selectedTab != index) {
                        selectedTab = index
                        if (isSearching) {
                            viewModel.onSearchToggled(false)
                        }
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
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    keyboardController?.hide()
                }
        ) {
            // Fondo de carga o error
            if (currentLoadState is LoadState.Loading || errorState != null) {
                LoadingCardsWireframe()
            }

            // Se valida si la búsqueda está activa, tiene texto, ya terminó de cargar y no trajo resultados
            val isSearchEmpty = isSearching &&
                    searchQuery.isNotBlank() &&
                    searchMoviesItems.itemCount == 0 &&
                    searchMoviesItems.loadState.refresh is LoadState.NotLoading

            if (isSearchEmpty) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.search_no_results, searchQuery),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = AppSpacing.extraLarge)
                    )
                }
            } else if (isSearching && searchQuery.isNotBlank()) {
                MovieGridScreenPaging(
                    innerPadding = PaddingValues(0.dp),
                    lazyPagingItems = searchMoviesItems,
                    gridState = gridState,
                    onMovieClick = onMovieClick
                )
            } else {
                // Si no se está buscando, mostramos la pestaña seleccionada
                when (selectedTab) {
                    0 -> {
                        MovieGridScreenPaging(
                            innerPadding = PaddingValues(0.dp),
                            lazyPagingItems = popularMoviesItems,
                            gridState = gridState,
                            onMovieClick = onMovieClick
                        )
                    }
                    1 -> {
                        MovieGridScreenPaging(
                            innerPadding = PaddingValues(0.dp),
                            lazyPagingItems = trendingMoviesItems,
                            gridState = gridState,
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
            }

            // Diálogo/Vista de error
            errorState?.let { error ->
                if (selectedTab != 2) {
                    MessaggeError(
                        message = stringResource(error.message),
                        solution = stringResource(error.solution),
                        buttonText = stringResource(error.buttonText),
                        onActionClick = {
                            viewModel.dismissError()
                            when {
                                isSearching -> searchMoviesItems.retry()
                                selectedTab == 0 -> popularMoviesItems.retry()
                                else -> trendingMoviesItems.retry()
                            }
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