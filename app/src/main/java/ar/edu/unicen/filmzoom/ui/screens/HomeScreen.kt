package ar.edu.unicen.filmzoom.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unicen.filmzoom.ui.components.homecomponents.BottomNavigationBar
import ar.edu.unicen.filmzoom.ui.components.homecomponents.MovieCard
import ar.edu.unicen.filmzoom.ui.components.homecomponents.TopNavegationBar
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme

// Modelo de datos simple para representar cada película
data class MovieTemp(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val genres: String,
    val voteAverage: String,
    val duration: String
)

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onMovieClick: (Int) -> Unit = {}
) {
    // Lista de ejemplo con datos ficticios
    val movies = remember {
        listOf(
            MovieTemp(1, "Dune: Parte Dos", "2024", "Ciencia ficción, Aventura", "8.5", "166 min"),
            MovieTemp(2, "Oppenheimer", "2023", "Biografía, Drama, Historia", "8.9", "180 min"),
            MovieTemp(3, "Spider-Man: Across the Spider-Verse", "2023", "Animación, Acción", "8.7", "140 min"),
            MovieTemp(4, "The Batman", "2022", "Acción, Crimen, Drama", "7.8", "176 min"),
            MovieTemp(5, "Interstellar", "2014", "Ciencia ficción, Aventura", "8.7", "169 min")
        )
    }

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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
// Margen lateral de 16.dp para toda la lista, y un respiro arriba/abajo
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            // Espacio vertical automático entre cada item del LazyColumn
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = movies,
                key = { movie -> movie.id }
            ) { movie ->
                MovieCard(
                    title = movie.title,
                    year = movie.releaseDate,
                    genres = movie.genres,
                    rating = movie.voteAverage,
                    duration = movie.duration,
                    onMovieClicked = { onMovieClick(movie.id) }
                )
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