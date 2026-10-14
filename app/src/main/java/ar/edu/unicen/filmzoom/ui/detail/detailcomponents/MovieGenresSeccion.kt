package ar.edu.unicen.filmzoom.ui.detail.detailcomponents

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unicen.filmzoom.R
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme

@Composable
fun MovieGenresSection(
    modifier: Modifier = Modifier,
    genres: List<String>?
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),

    ) {
        Text(
            text = stringResource(R.string.detail_section_genres),
            style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Fila horizontal deslizable por si hay muchos géneros
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (genres.isNullOrEmpty()) return
            else{
                genres.forEach { genre ->
                    Surface(
                        modifier = Modifier
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(24.dp),
                                spotColor = Color.Black.copy(alpha = 0.20f)),
                        shape = RoundedCornerShape(24.dp),
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        Text(
                            text = genre,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

            }
        }
    }
}

//@Preview(name = "Light Mode", showBackground = true)
//@Composable
//fun MovieGenresSectionLightPreview() {
//    FilmZoomTheme(darkTheme = false) {
//        Surface(color = MaterialTheme.colorScheme.background) {
//            MovieGenresSection()
//        }
//    }
//}
//
//@Preview(name = "Dark Mode", showBackground = true)
//@Composable
//fun MovieGenresSectionDarkPreview() {
//    FilmZoomTheme(darkTheme = true) {
//        Surface(color = MaterialTheme.colorScheme.background) {
//            MovieGenresSection()
//        }
//    }
//}