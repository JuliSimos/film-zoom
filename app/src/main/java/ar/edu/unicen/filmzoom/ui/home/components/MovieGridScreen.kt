package ar.edu.unicen.filmzoom.ui.home.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unicen.filmzoom.domain.model.Movie
import ar.edu.unicen.filmzoom.ui.theme.AppSpacing

@Composable
fun MovieGridScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    movies : List<Movie>,
    onMovieClick: (Int) -> Unit = {}, //Recibe el id de la pelicula
    movieItemContent: @Composable (Movie) -> Unit = { movie ->
        MovieCard(
            movie = movie,
            onMovieClicked = { onMovieClick(movie.id) }
        )
}
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
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
            .consumeWindowInsets(innerPadding),
        contentPadding = PaddingValues(horizontal = AppSpacing.medium, vertical = AppSpacing.small),
        horizontalArrangement = Arrangement.spacedBy(AppSpacing.mediumSmall),
        verticalArrangement = Arrangement.spacedBy(AppSpacing.mediumSmall)
    ) {
        items(
            items = movies,
            key = { movie -> movie.id }
        ) { movie ->
            movieItemContent(movie)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieGridScreenPreview() {
    val sampleMovies = listOf(
        Movie(
            id = 1,
            title = "Inception",
            posterPath = "",
            releaseDate = "2010-07-16",
            voteAverage = 8.8,
        ),
        Movie(
            id = 2,
            title = "The Dark Knight",
            posterPath = "",
            releaseDate = "2008-07-18",
            voteAverage = 9.0,
        )
    )

    Surface {
        MovieGridScreen(movies = sampleMovies)
    }
}
