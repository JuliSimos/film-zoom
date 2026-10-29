package ar.edu.unicen.filmzoom.ui.home.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
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
    val gridState = rememberLazyGridState()

    val configuration = LocalConfiguration.current
    val screenColumns = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        2
    } else {
        1
    }

    // altura real del contenedor
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
                            count = lazyPagingItems.itemCount,
                            key = { index ->
                                lazyPagingItems[index]?.id ?: index
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

                        if (lazyPagingItems.loadState.append is LoadState.Loading) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(AppSpacing.medium),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }

                    val totalItems = lazyPagingItems.itemCount
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
    }
}