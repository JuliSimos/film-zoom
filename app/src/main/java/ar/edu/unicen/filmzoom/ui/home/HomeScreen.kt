package ar.edu.unicen.filmzoom.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import ar.edu.unicen.filmzoom.ui.home.components.BottomNavigationBar
import ar.edu.unicen.filmzoom.ui.home.components.TopNavegationBar
import ar.edu.unicen.filmzoom.ui.home.components.MovieGridScreen
import ar.edu.unicen.filmzoom.ui.home.components.MovieGridScreenPaging
import ar.edu.unicen.filmzoom.ui.home.components.WishlistItemCard
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit = {},
) {
    val popularMoviesItems = viewModel.popularMovies.collectAsLazyPagingItems()
    val trendingMoviesItems = viewModel.trendingMovies.collectAsLazyPagingItems()
    val wishlistMovies by viewModel.wishlistMovies.collectAsState()

    // Manejamos la pestaña seleccionada directamente con un estado simple
    var selectedTab by remember { mutableIntStateOf(0) }

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
                    selectedTab = index // Cambia al toque y sin animaciones pesadas de Pager
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Mostramos la vista correspondiente de forma directa y súper fluida
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
                                }
                            )
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