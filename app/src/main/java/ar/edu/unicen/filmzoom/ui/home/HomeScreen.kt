package ar.edu.unicen.filmzoom.ui.home

import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unicen.filmzoom.ui.home.components.BottomNavigationBar
import ar.edu.unicen.filmzoom.ui.home.components.TopNavegationBar
import ar.edu.unicen.filmzoom.ui.home.components.MovieGridScreen
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme

// Modelo de datos simple para representar cada película


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onSearchClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onMovieClick: (Int) -> Unit = {}
) {

    val state by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopNavegationBar(
                onSearchClick = onSearchClick,
                onProfileClick = onProfileClick
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
        
    ) { innerPadding ->

        MovieGridScreen(
            modifier = modifier,
            innerPadding = innerPadding,
            movies = state.popularMovies,
            onMovieClick = onMovieClick
        )
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