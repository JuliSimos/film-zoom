package ar.edu.unicen.filmzoom.ui.detail.detailcomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unicen.filmzoom.R
import ar.edu.unicen.filmzoom.ui.theme.AppSpacing
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme

@Composable
fun MovieDetailTopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = AppSpacing.small, vertical = AppSpacing.small),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Izquierda: Botón de volver y Título
            Row(
                horizontalArrangement = Arrangement.spacedBy(AppSpacing.extraSmall),
                verticalAlignment = Alignment.CenterVertically
            ) {
                //Para seguir con la idea de accesibilidad
                val backArrowDescription = stringResource(R.string.cd_back_arrow)

                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.semantics{
                        contentDescription = backArrowDescription
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                Text(
                    text = stringResource(R.string.detail_top_bar_title),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 18.sp,
                        letterSpacing = 0.sp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
@Preview(name = "Light Mode", showBackground = true)
@Composable
fun MovieDetailTopBarLightPreview() {
    FilmZoomTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MovieDetailTopBar()
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun MovieDetailTopBarDarkPreview() {
    FilmZoomTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MovieDetailTopBar()
        }
    }
}