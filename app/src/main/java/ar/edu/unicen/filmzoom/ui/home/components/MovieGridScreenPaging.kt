package ar.edu.unicen.filmzoom.ui.home.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import ar.edu.unicen.filmzoom.R
import ar.edu.unicen.filmzoom.domain.model.Movie
import ar.edu.unicen.filmzoom.ui.theme.AppSpacing
import kotlinx.coroutines.launch

@Composable
fun MovieGridScreenPaging(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    lazyPagingItems: LazyPagingItems<Movie>,
    gridState: LazyGridState = rememberLazyGridState(),
    onMovieClick: (Int) -> Unit = {}
) {
    val refreshState = lazyPagingItems.loadState.refresh
    val scope = rememberCoroutineScope()

    val showScrollToTopButton by remember {
        derivedStateOf {
            gridState.firstVisibleItemIndex > 5
        }
    }

    val configuration = LocalConfiguration.current
    val screenColumns = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        2
    } else {
        1
    }

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
        // Si está cargando por primera vez, mostramos el wireframe de carga
        if (refreshState is LoadState.Loading) {
            LoadingCardsWireframe()
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyVerticalGrid(
                    state = gridState,
                    columns = GridCells.Fixed(screenColumns),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        horizontal = AppSpacing.medium,
                        vertical = AppSpacing.small
                    ),
                    horizontalArrangement = Arrangement.spacedBy(AppSpacing.mediumSmall),
                    verticalArrangement = Arrangement.spacedBy(AppSpacing.small)
                ) {
                    items(
                        count = lazyPagingItems.itemCount,
                        key = { index ->
                            val movie = lazyPagingItems[index]
                            if (movie != null) "paging_${movie.id}_$index" else "placeholder_$index"
                        }
                    ) { index ->
                        val movie = lazyPagingItems[index]
                        if (movie != null) {
                            MovieCard(
                                modifier = Modifier,
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
                            .width(AppSpacing.extraSmall)
                            .padding(vertical = AppSpacing.small)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(width = AppSpacing.extraSmall, height = thumbHeight)
                                .offset(y = currentOffset)
                                .clip(MaterialTheme.shapes.extraSmall)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.6f))
                        )
                    }
                }
            }
        }
        AnimatedVisibility(
            visible = showScrollToTopButton,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(AppSpacing.large),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Surface(
                onClick = {
                    scope.launch {
                        gridState.animateScrollToItem(0)
                    }
                },
                modifier = Modifier.size(56.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                shadowElevation = 0.dp,
                tonalElevation = 0.dp
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_up),
                        contentDescription = stringResource(R.string.cd_scroll_to_top),

                    )
                }
            }
        }
    }
}
