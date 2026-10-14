package ar.edu.unicen.filmzoom.ui.detail.detailcomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unicen.filmzoom.R
import ar.edu.unicen.filmzoom.domain.model.Movie
import ar.edu.unicen.filmzoom.domain.model.MovieDetails
import ar.edu.unicen.filmzoom.ui.detail.MovieDetailViewModel
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme

@Composable
fun MovieActionButtons(
    modifier: Modifier = Modifier,
    hasWebsite: Boolean,
    onWishlistClick: (Boolean) -> Unit = {},
    onWebsiteClick: () -> Unit = {},
    onShareClick: () -> Unit = {}
) {

    // Estado local para manejar si está en deseados o no
    var isWishlisted by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Botón principal de Agregar a deseados (cambia de estado al hacer clic)
        Button(
            onClick = {
                isWishlisted = !isWishlisted
                onWishlistClick(isWishlisted)
            },
            modifier = Modifier
                .weight(1f)
                .height(48.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor =
                    if (isWishlisted) MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.primary
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_heart),
                    contentDescription = stringResource(R.string.cd_btn_wishlist),
                    tint = if (isWishlisted) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = if (isWishlisted) stringResource(R.string.action_in_your_list)
                    else stringResource(R.string.action_add_to_wishlist),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isWishlisted) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.onPrimary
                )

            }


        }
        if(hasWebsite){
            // Botón redondo de sitio web -> aparece si existe homePage
            Surface(
                modifier = Modifier
                    .height(48.dp)
                    .width(48.dp)
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(24.dp),
                        spotColor = Color.Black.copy(alpha = 0.20f)
                    )
                    .clickable { onWebsiteClick() },
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_open_in_new),
                    contentDescription = stringResource(R.string.cd_btn_website),
                    modifier = Modifier.padding(12.dp),
                    tint = MaterialTheme.colorScheme.primary

                )

            }
        }

        // Botón redondo de compartir
        Surface(
            modifier = Modifier
                .height(48.dp)
                .width(48.dp)
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(24.dp), spotColor = Color.Black.copy(alpha = 0.25f)),
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            onClick = onShareClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_share),
                contentDescription = stringResource(R.string.cd_btn_share),
                modifier = Modifier.padding(12.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
//
//@Preview(name = "Light Mode", showBackground = true)
//@Composable
//fun MovieActionButtonsLightPreview() {
//    FilmZoomTheme(darkTheme = false) {
//        Surface(color = MaterialTheme.colorScheme.background) {
//            MovieActionButtons(modifier = Modifier.padding(16.dp))
//        }
//    }
//}
//
//@Preview(name = "Dark Mode", showBackground = true)
//@Composable
//fun MovieActionButtonsDarkPreview() {
//    FilmZoomTheme(darkTheme = true) {
//        Surface(color = MaterialTheme.colorScheme.background) {
//            MovieActionButtons(modifier = Modifier.padding(16.dp))
//        }
//    }
//}