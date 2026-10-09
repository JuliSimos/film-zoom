package ar.edu.unicen.filmzoom.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.unicen.filmzoom.ui.components.homecomponents.BottomNavigationBar
import ar.edu.unicen.filmzoom.ui.components.homecomponents.TopNavegationBar
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme

// Modelo de datos simple para representar cada película


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    movies: List<MovieTemp> = emptyList(),
    onSearchClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onMovieClick: (Int) -> Unit = {}
) {


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

        MovieGridScreen(modifier, innerPadding,onMovieClick)
    }
}

val movies = listOf(
MovieTemp(1, "Dune: Parte Dos", "2024", "Ciencia ficción, Aventura", "8.5", "166 min"),
MovieTemp(2, "Oppenheimer", "2023", "Biografía, Drama, Historia", "8.9", "180 min"),
MovieTemp(3, "Spider-Man: Across the Spider-Verse", "2023", "Animación, Acción", "8.7", "140 min"),
MovieTemp(4, "The Batman", "2022", "Acción, Crimen, Drama", "7.8", "176 min"),
MovieTemp(5, "Interstellar", "2014", "Ciencia ficción, Aventura", "8.7", "169 min"), MovieTemp(6, "Dune: Parte Dos", "2024", "Ciencia ficción, Aventura", "8.5", "166 min"),
MovieTemp(7, "Oppenheimer", "2023", "Biografía, Drama, Historia", "8.9", "180 min"),
MovieTemp(8, "Spider-Man: Across the Spider-Verse", "2023", "Animación, Acción", "8.7", "140 min"),
MovieTemp(9, "The Batman", "2022", "Acción, Crimen, Drama", "7.8", "176 min"),
MovieTemp(10, "Interstellar", "2014", "Ciencia ficción, Aventura", "8.7", "169 min")
)
@Preview(name = "Light Mode", showBackground = true)
@Composable
fun HomeScreenLightPreview() {

    FilmZoomTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            HomeScreen(movies = movies)
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun HomeScreenDarkPreview() {

    FilmZoomTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            HomeScreen(movies = movies)
        }
    }
}