package ar.edu.unicen.filmzoom.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import ar.edu.unicen.filmzoom.ui.components.MessaggeError
import ar.edu.unicen.filmzoom.ui.home.components.BottomNavigationBar
import ar.edu.unicen.filmzoom.ui.home.components.LoadingCardsWireframe
import ar.edu.unicen.filmzoom.ui.home.components.TopNavegationBar
import ar.edu.unicen.filmzoom.ui.home.components.MovieGridScreen
import ar.edu.unicen.filmzoom.ui.home.components.MovieGridScreenPaging
import ar.edu.unicen.filmzoom.ui.home.components.WishlistItemCard
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onSearchClick: () -> Unit = {},
    onMovieClick: (Int) -> Unit = {},
) {

// Recolectamos los flujos paginados con Paging 3
    val popularMoviesItems = viewModel.popularMovies.collectAsLazyPagingItems()
    val trendingMoviesItems = viewModel.trendingMovies.collectAsLazyPagingItems()

    // Observamos la lista de deseos en tiempo real desde Room
    val wishlistMovies by viewModel.wishlistMovies.collectAsState()

    // Creamos el estado del Pager indicando cuántas pestañas tenemos
    val pagerState = rememberPagerState(pageCount = { 3 })

    // Opcional: Sincronizamos el estado del Pager con una variable si la necesitas,
    // o puedes usar directamente pagerState.currentPage en tu BottomNavigationBar
    val selectedTab = pagerState.currentPage

    // corrutina para animar el cambio de página cuando tocan la barra inferior
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopNavegationBar(
                onSearchClick = onSearchClick,
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { index ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // HorizontalPager maneja tus 3 pestañas intactas
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> {
                        // Pestaña 0: Populares (Paginado)
                        MovieGridScreenPaging(
                            innerPadding = PaddingValues(0.dp),
                            lazyPagingItems = popularMoviesItems,
                            onMovieClick = onMovieClick
                        )
                    }
                    1 -> {
                        // Pestaña 1: Tendencias (Paginado)
                        MovieGridScreenPaging(
                            innerPadding = PaddingValues(0.dp),
                            lazyPagingItems = trendingMoviesItems,
                            onMovieClick = onMovieClick
                        )
                    }
                    2 -> {
                        // Pestaña 2: Deseados (Room)
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