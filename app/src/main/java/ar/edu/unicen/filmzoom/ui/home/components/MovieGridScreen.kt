package ar.edu.unicen.filmzoom.ui.home.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ar.edu.unicen.filmzoom.domain.model.Movie
import ar.edu.unicen.filmzoom.ui.theme.AppSpacing

@Composable
fun MovieGridScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    movies: List<Movie>,
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
    val gridState = rememberLazyGridState()

    // Si está en horizontal (Landscape), ponemos 2 columnas; si está en vertical, 1
    val screenColumns = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        2
    } else {
        1
    }

    // Altura real del contenedor para la barra lateral
    var containerHeight by remember { mutableStateOf(0) }
    val density = LocalDensity.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
            .consumeWindowInsets(innerPadding)
            .onGloballyPositioned { coordinates ->
                containerHeight = coordinates.size.height
            }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Fixed(screenColumns),
                modifier = Modifier.fillMaxSize(),
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

            val totalItems = movies.size
            if (totalItems > 0 && containerHeight > 0) {
                val trackHeightDp = with(density) { containerHeight.toDp() }
                val thumbHeight = 40.dp
                val maxOffset: Dp = (trackHeightDp - thumbHeight).coerceAtLeast(0.dp)

                val firstVisibleIndex = gridState.firstVisibleItemIndex
                val columns = screenColumns

                val totalRows = (totalItems + columns - 1) / columns
                val currentRow = firstVisibleIndex / columns

                val progress = if (totalRows > 1) {
                    (currentRow.toFloat() / (totalRows - 1).toFloat()).coerceIn(0f, 1f)
                } else {
                    0f
                }

                val currentOffset = maxOffset * progress

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .fillMaxHeight()
                        .width(4.dp)
                        .padding(vertical = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 4.dp, height = thumbHeight)
                            .offset(y = currentOffset)
                            .clip(RoundedCornerShape(2.dp))
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.6f))
                    )
                }
            }
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
