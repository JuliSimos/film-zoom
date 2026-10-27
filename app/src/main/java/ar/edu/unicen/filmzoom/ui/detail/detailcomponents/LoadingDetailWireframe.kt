package ar.edu.unicen.filmzoom.ui.detail.detailcomponents

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unicen.filmzoom.ui.theme.AppSpacing
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme

@Composable
fun LoadingDetailWireframe(
    modifier: Modifier = Modifier
) {
    // Animación de parpadeo rápido sincronizada
    val infiniteTransition = rememberInfiniteTransition(label = "shimmerDetail")
    val alphaAnim = infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 550),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alphaDetail"
    )

    val baseColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = alphaAnim.value * 0.2f)
    val accentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = alphaAnim.value * 0.4f)

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(bottom = AppSpacing.extraLarge),
        verticalArrangement = Arrangement.spacedBy(AppSpacing.large),
        userScrollEnabled = false
    ) {
        // 1. Esqueleto de la Cabecera (Banner + Póster superpuesto + Títulos)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = AppSpacing.small)
            ) {
                // Fila con póster flotante y textos falsos
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppSpacing.medium)
                        .padding(top = 130.dp),
                    horizontalArrangement = Arrangement.spacedBy(AppSpacing.medium),
                    verticalAlignment = Alignment.Bottom
                ) {
                    // Póster flotante falso
                    Box(
                        modifier = Modifier
                            .width(110.dp)
                            .height(160.dp)
                            .clip(MaterialTheme.shapes.medium)
                        .background(accentColor)
                    )

                    // Textos descriptivos falsos
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = AppSpacing.extraSmall),
                        verticalArrangement = Arrangement.spacedBy(AppSpacing.small)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .height(12.dp)
                                .clip(MaterialTheme.shapes.extraSmall)
                                .background(accentColor)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(24.dp)
                                .clip(MaterialTheme.shapes.extraSmall)
                                .background(baseColor)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.4f)
                                .height(12.dp)
                                .clip(MaterialTheme.shapes.extraSmall)
                                .background(accentColor)
                        )
                    }
                }
            }
        }

        // 2. Esqueleto de Botones de Acción (Deseados, Web, Compartir)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppSpacing.medium),
                horizontalArrangement = Arrangement.spacedBy(AppSpacing.mediumSmall),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clip(MaterialTheme.shapes.extraLarge)
                        .background(accentColor)
                )
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(MaterialTheme.shapes.extraLarge)
                        .background(accentColor)
                )
            }
        }

        // 3. Esqueleto de Géneros (Chips horizontales falsos)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppSpacing.medium),
                horizontalArrangement = Arrangement.spacedBy(AppSpacing.small)
            ) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .width(70.dp)
                            .height(32.dp)
                            .clip(MaterialTheme.shapes.large)
                            .background(baseColor)
                    )
                }
            }
        }

        // 4. Esqueleto de Sinopsis
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppSpacing.medium),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppSpacing.medium),
                    verticalArrangement = Arrangement.spacedBy(AppSpacing.mediumSmall)
                ) {
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(14.dp)
                            .clip(MaterialTheme.shapes.extraSmall)
                            .background(baseColor)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(12.dp)
                            .clip(MaterialTheme.shapes.extraSmall)
                            .background(baseColor)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(12.dp)
                            .clip(MaterialTheme.shapes.extraSmall)
                            .background(baseColor)
                    )
                }
            }
        }

        // 5. Esqueleto de Reparto Principal (Avatares circulares falsos)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppSpacing.medium),
                verticalArrangement = Arrangement.spacedBy(AppSpacing.mediumSmall)
            ) {
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(14.dp)
                        .clip(MaterialTheme.shapes.extraSmall)
                        .background(baseColor)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(AppSpacing.medium),
                    userScrollEnabled = false
                ) {
                    items(5) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(AppSpacing.extraSmall)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                                    .background(baseColor)
                            )
                            Box(
                                modifier = Modifier
                                    .width(50.dp)
                                    .height(10.dp)
                                    .clip(MaterialTheme.shapes.extraSmall)
                                    .background(baseColor)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Light Detail Wireframe", showBackground = true)
@Composable
fun LoadingDetailWireframeLightPreview() {
    FilmZoomTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            LoadingDetailWireframe()
        }
    }
}

@Preview(name = "Dark Detail Wireframe", showBackground = true)
@Composable
fun LoadingDetailWireframeDarkPreview() {
    FilmZoomTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            LoadingDetailWireframe()
        }
    }
}