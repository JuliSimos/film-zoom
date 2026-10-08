package ar.edu.unicen.filmzoom.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme

@Composable
fun LoadingStateView(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
@Preview(name = "Light Mode", showBackground = true)
@Composable
fun LoadingStateViewLightPreview(){
    FilmZoomTheme(darkTheme = false) {
        LoadingStateView("Cargando Peliculas")
    }
}
@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun LoadingStateViewDarkPreview(){
    FilmZoomTheme(darkTheme = true) {
        LoadingStateView("Cargando Peliculas")

    }
}