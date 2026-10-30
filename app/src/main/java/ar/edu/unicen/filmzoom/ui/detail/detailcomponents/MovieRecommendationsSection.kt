package ar.edu.unicen.filmzoom.ui.detail.detailcomponents

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import ar.edu.unicen.filmzoom.R
import ar.edu.unicen.filmzoom.domain.model.Movie
import ar.edu.unicen.filmzoom.ui.home.components.MovieCard
import ar.edu.unicen.filmzoom.ui.theme.AppSpacing

@Composable
fun MovieRecommendationsSection(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit
) {
    if (movies.isNotEmpty()) {
        val configuration = LocalConfiguration.current
        // Si está en horizontal (Landscape), 2 columnas. si está en vertical, 1
        val columns = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 2 else 1

        // Agrupamos la lista de películas en bloques (filas) según la cantidad de columnas
        val rows = movies.chunked(columns)

        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(AppSpacing.small)
        ) {
            Text(
                text = stringResource(R.string.movie_recommendations_title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(AppSpacing.mediumSmall)
            ) {
                rows.forEach { rowMovies ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(AppSpacing.mediumSmall)
                    ) {
                        rowMovies.forEach { movie ->
                            Box(
                                modifier = Modifier.weight(1f)
                            ) {
                                MovieCard(
                                    modifier = Modifier.fillMaxWidth(),
                                    movie = movie,
                                    onMovieClicked = { onMovieClick(movie.id) }
                                )
                            }
                        }

                        if (rowMovies.size < columns) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}