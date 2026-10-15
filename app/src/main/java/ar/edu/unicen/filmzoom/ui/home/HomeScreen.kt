package ar.edu.unicen.filmzoom.ui.home

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unicen.filmzoom.ui.home.components.BottomNavigationBar
import ar.edu.unicen.filmzoom.ui.home.components.LoadingCardsWireframe
import ar.edu.unicen.filmzoom.ui.home.components.TopNavegationBar
import ar.edu.unicen.filmzoom.ui.home.components.MovieGridScreen
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onSearchClick: () -> Unit = {},
    onMovieClick: (Int) -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()

    // 1. Creamos el estado del Pager indicando cuántas pestañas tenemos (en este caso 2: populares y tendencias)
    val pagerState = rememberPagerState(pageCount = { 2 })

    // Opcional: Sincronizamos el estado del Pager con una variable si la necesitas,
    // o puedes usar directamente pagerState.currentPage en tu BottomNavigationBar
    val selectedTab = pagerState.currentPage

    // Usamos una corrutina para animar el cambio de página cuando tocan la barra inferior
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
                    // Al tocar la barra inferior, deslizamos la pantalla suavemente hacia la página seleccionada
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
            if (state.isLoading) {
                LoadingCardsWireframe()
            } else {
                // 2. Envolvemos las listas en un HorizontalPager para permitir el gesto de deslizar
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    // El parámetro 'page' representa el índice actual (0 o 1)
                    val moviesToDisplay = when (page) {
                        0 -> state.popularMovies
                        1 -> state.trendingMovies
                        else -> emptyList()
                    }

                    MovieGridScreen(
                        movies = moviesToDisplay,
                        onMovieClick = onMovieClick
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