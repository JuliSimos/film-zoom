package ar.edu.unicen.filmzoom.ui.home.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import ar.edu.unicen.filmzoom.R
import ar.edu.unicen.filmzoom.domain.model.Movie
import ar.edu.unicen.filmzoom.ui.components.MessaggeError
import ar.edu.unicen.filmzoom.ui.theme.AppSpacing

@Composable
fun MovieGridScreenPaging(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    lazyPagingItems: LazyPagingItems<Movie>,
    onMovieClick: (Int) -> Unit = {}
) {
    val refreshState = lazyPagingItems.loadState.refresh

    // Detectamos la orientación actual del teléfono igual que en tu grilla original
    val configuration = LocalConfiguration.current
    val screenColumns = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        2
    } else {
        1
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
            .consumeWindowInsets(innerPadding)
    ) {
        when {
            refreshState is LoadState.Loading -> {
                LoadingCardsWireframe()
            }

            refreshState is LoadState.Error -> {
                MessaggeError(
                    message = stringResource(id = R.string.error_unexpected_title),
                    solution = stringResource(id = R.string.error_unexpected_solution),
                    buttonText = stringResource(id = R.string.action_retry),
                    onActionClick = {
                        lazyPagingItems.retry()
                    },
                    onDismiss = {}
                )
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(screenColumns),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = AppSpacing.medium, vertical = AppSpacing.small),
                    horizontalArrangement = Arrangement.spacedBy(AppSpacing.mediumSmall),
                    verticalArrangement = Arrangement.spacedBy(AppSpacing.mediumSmall)
                ) {
                    items(
                        count = lazyPagingItems.itemCount,
                        key = { index ->
                            val movie = lazyPagingItems[index]
                            // Usamos el ID de la película combinado con su índice para garantizar unicidad absoluta
                            if (movie != null) "${movie.id}-$index" else index
                        }
                    ) { index ->
                        val movie = lazyPagingItems[index]
                        if (movie != null) {
                            MovieCard(
                                movie = movie,
                                onMovieClicked = { onMovieClick(movie.id) }
                            )
                        }
                    }

                }
            }
        }
    }
}