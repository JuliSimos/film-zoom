package ar.edu.unicen.filmzoom.ui.detail.detailcomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unicen.filmzoom.R
import ar.edu.unicen.filmzoom.ui.theme.AppSpacing
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme

@Composable
fun MovieActionButtons(
    modifier: Modifier = Modifier,
    hasWebsite: Boolean,
    isWishlisted: Boolean = false,
    onWishlistClick: () -> Unit = {},
    onWebsiteClick: () -> Unit = {},
    onShareClick: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(AppSpacing.mediumSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Botón principal de Agregar a deseados
        Button(
            onClick = onWishlistClick,
            modifier = Modifier
                .fillMaxWidth(0.7f) // <-- Ocupa exactamente el 70% del ancho disponible de forma porcentual
                .height(48.dp),
            shape = MaterialTheme.shapes.extraLarge,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isWishlisted) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.primary
            ),
            // 💡 Agregamos elevación para que se eleve y tenga contraste
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 3.dp,
                pressedElevation = 6.dp,
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AppSpacing.small)
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isWishlisted) R.drawable.ic_bookmark
                        else R.drawable.ic_bookmark_border
                    ),
                    contentDescription = null,
                    tint = if (isWishlisted) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = if (isWishlisted) stringResource(R.string.action_in_your_list)
                    else stringResource(R.string.action_add_to_wishlist),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Medium,
                    color = if (isWishlisted) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        if (hasWebsite) {
            // Botón redondo de sitio web
            Surface(
                modifier = Modifier
                    .size(48.dp),
                shape = MaterialTheme.shapes.extraLarge,
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 3.dp,
                onClick = onWebsiteClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_open_in_new),
                    contentDescription = stringResource(R.string.cd_btn_website),
                    modifier = Modifier.padding(AppSpacing.mediumSmall),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Botón redondo de compartir
        Surface(
            modifier = Modifier
                .size(48.dp),
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 3.dp,
            onClick = onShareClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_share),
                contentDescription = stringResource(R.string.cd_btn_share),
                modifier = Modifier.padding(AppSpacing.mediumSmall),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(name = "Light Mode - Default", showBackground = true)
@Composable
fun MovieActionButtonsLightPreview() {
    FilmZoomTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MovieActionButtons(
                hasWebsite = true,
                isWishlisted = false
            )
        }
    }
}

@Preview(name = "Dark Mode - Wishlisted", showBackground = true)
@Composable
fun MovieActionButtonsDarkPreview() {
    FilmZoomTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MovieActionButtons(
                hasWebsite = true,
                isWishlisted = true
            )
        }
    }
}