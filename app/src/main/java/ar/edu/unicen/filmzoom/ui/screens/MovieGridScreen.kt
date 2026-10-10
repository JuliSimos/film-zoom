package ar.edu.unicen.filmzoom.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unicen.filmzoom.ui.components.homecomponents.MovieCard

data class MovieTemp(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val voteAverage: String,
    val duration: String
)

@Composable
fun MovieGridScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    onMovieClick: (Int) -> Unit = {} //Recibe el id de la pelicula

) {
    // Detectamos la orientación actual del teléfono
    val configuration = LocalConfiguration.current

    // Lista de ejemplo con datos ficticios
    val movies = listOf(
        MovieTemp(1, "Dune: Parte Dos", "2024",  "8.5", "166 min"),
        MovieTemp(2, "Oppenheimer", "2023", "8.9", "180 min"),
        MovieTemp(3, "Spider-Man: Across the Spider-Verse", "2023",  "8.7", "140 min"),
        MovieTemp(4, "The Batman", "2022",  "7.8", "176 min"),
        MovieTemp(5, "Interstellar", "2014",  "8.7", "169 min"),
        MovieTemp(6, "Dune: Parte Dos", "2024",  "8.5", "166 min"),
        MovieTemp(7, "Oppenheimer", "2023",  "8.9", "180 min"),
        MovieTemp(8, "Spider-Man: Across the Spider-Verse", "2023",  "8.7", "140 min"),
        MovieTemp(9, "The Batman", "2022",  "7.8", "176 min"),
        MovieTemp(10, "Interstellar", "2014",  "8.7", "169 min")
    )


    // Si está en horizontal (Landscape), ponemos 2 columnas; si está en vertical, 1 (o la lógica que prefieras)
    val screenColumns = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        2
    } else {
        1
    }


    LazyVerticalGrid(
        columns = GridCells.Fixed(screenColumns),
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
            .consumeWindowInsets(innerPadding),
// Margen lateral de 16.dp para toda la lista, y un respiro arriba/abajo
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        // Espacio horizontal entre las 2 columnas
        horizontalArrangement = Arrangement.spacedBy(12.dp),
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
                rating = movie.voteAverage,
                duration = movie.duration,
                onMovieClicked = { onMovieClick(movie.id) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieGridScreenPreview() {
    // Se puede envolver en el Theme del proyecto si existe, e.g., FilmZoomTheme { ... }
    Surface {
        MovieGridScreen()
    }
}
