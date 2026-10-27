package ar.edu.unicen.filmzoom.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unicen.filmzoom.R
import ar.edu.unicen.filmzoom.ui.theme.AppSpacing
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme

@Composable
fun TopNavegationBar(
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit = {},
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppSpacing.medium),
            horizontalArrangement =  Arrangement.spacedBy(AppSpacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_film),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )

            Text(
                text = stringResource(id = R.string.home_title_popular),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )
//
//            Spacer(modifier = Modifier.weight(1f))
//
//            IconButton(
//                onClick = onSearchClick,
//                modifier = Modifier.size(40.dp)
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_search),
//                    contentDescription = stringResource(id = R.string.cd_search_icon),
//                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
//                    modifier = Modifier.size(22.dp)
//                )
//            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun TopNavegationBarLightPreview() {
    FilmZoomTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            TopNavegationBar()
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun TopNavegationBarDarkPreview() {
    FilmZoomTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            TopNavegationBar()
        }
    }
}