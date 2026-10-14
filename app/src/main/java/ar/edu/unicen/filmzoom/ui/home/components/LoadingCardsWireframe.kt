package ar.edu.unicen.filmzoom.ui.home.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme
@Composable
fun LoadingCardsWireframe(
    modifier: Modifier = Modifier
) {
    // Transición infinita para el parpadeo (versión más marcada)
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val alphaAnim = infiniteTransition.animateFloat(
        initialValue = 0.15f,  // Opacidad más baja para dar mayor contraste
        targetValue = 1.0f,   // Opacidad total en el punto más alto
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 550), // Animación más rápida
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    // Aplicamos el alfa directamente al color del esqueleto
    val baseColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = alphaAnim.value)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        userScrollEnabled = false
    ) {
        items(6) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Póster de esqueleto
                    Box(
                        modifier = Modifier
                            .width(90.dp)
                            .height(130.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(baseColor)
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    // Textos de esqueleto
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.75f)
                                .height(20.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(baseColor)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.35f)
                                .height(14.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(baseColor)
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "Light Mode Wireframe", showBackground = true)
@Composable
fun LoadingCardsWireframeLightPreview() {
    FilmZoomTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            LoadingCardsWireframe()
        }
    }
}

@Preview(name = "Dark Mode Wireframe", showBackground = true)
@Composable
fun LoadingCardsWireframeDarkPreview() {
    FilmZoomTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            LoadingCardsWireframe()
        }
    }
}