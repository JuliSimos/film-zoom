package ar.edu.unicen.filmzoom.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import ar.edu.unicen.filmzoom.domain.model.Movie
import ar.edu.unicen.filmzoom.ui.components.homecomponents.MovieCard

@Composable
fun MovieGridScreen(
    movies: List<Movie>,

) {
    // Detectamos la orientación actual del teléfono
    val configuration = LocalConfiguration.current

    // Si está en horizontal (Landscape), ponemos 2 columnas; si está en vertical, 1 (o la lógica que prefieras)
    val screenColumns = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        2
    } else {
        1
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(screenColumns),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movies) { movie ->
            // Acá adentro va la tarjeta (Card) de cada película
            MovieCard(
            title = "Dune: Parte Dos",
            year = "2024",
            genres = "Ciencia ficción, Aventura",
            rating = "8.5",
            duration = "166 min",
            onMovieClicked = {}
            )
        }
    }
}