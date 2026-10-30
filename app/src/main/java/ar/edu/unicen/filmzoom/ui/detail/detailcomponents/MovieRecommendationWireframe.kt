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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import android.content.res.Configuration
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unicen.filmzoom.ui.theme.AppSpacing
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme

@Composable
fun MovieRecommendationsWireframe(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer_rec")
    val alphaAnim = infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 550),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha_rec"
    )

    val skeletonColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = alphaAnim.value * 0.2f)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AppSpacing.small)
    ) {
        // Título fantasma de la sección
        Box(
            modifier = Modifier
                .width(160.dp)
                .height(22.dp)
                .clip(MaterialTheme.shapes.extraSmall)
                .background(skeletonColor)
        )

        // Simulamos un par de tarjetas fantasma en formato columna/grilla para las recomendaciones
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(AppSpacing.mediumSmall)
        ) {
            repeat(3) { // 3 filas fantasma para simular las recomendaciones
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.small,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .width(90.dp)
                                .height(130.dp)
                                .background(skeletonColor)
                        )
                        Spacer(modifier = Modifier.width(AppSpacing.small))
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(AppSpacing.small),
                            verticalArrangement = Arrangement.spacedBy(AppSpacing.extraSmall)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.85f)
                                    .height(20.dp)
                                    .clip(MaterialTheme.shapes.extraSmall)
                                    .background(skeletonColor)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(AppSpacing.small)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(36.dp)
                                        .height(16.dp)
                                        .clip(MaterialTheme.shapes.extraSmall)
                                        .background(skeletonColor)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewMovieRecommendationsWireframe() {
    FilmZoomTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppSpacing.medium),
            color = MaterialTheme.colorScheme.background
        ) {
            MovieRecommendationsWireframe()
        }
    }
}
