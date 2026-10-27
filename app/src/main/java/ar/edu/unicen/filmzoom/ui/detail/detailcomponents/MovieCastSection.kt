package ar.edu.unicen.filmzoom.ui.detail.detailcomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unicen.filmzoom.R
import ar.edu.unicen.filmzoom.domain.model.CastMember
import ar.edu.unicen.filmzoom.ui.theme.AppSpacing
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme
import coil.compose.AsyncImage

@Composable
fun MovieCastSection(
    modifier: Modifier = Modifier,
    cast : List<CastMember>?
) {
    if (cast.isNullOrEmpty()) return

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(AppSpacing.small)
    ) {
        Text(
            text = stringResource(id = R.string.detail_section_cast),
            style = MaterialTheme.typography.labelLarge.copy(
                letterSpacing = 0.sp
            ),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )


        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(AppSpacing.medium)
        ) {
            items(cast.take(10)) { actor ->
                CastMemberItem(actor = actor)
            }

        }
    }
}

@Composable
fun CastMemberItem(
    actor: CastMember
) {
    Column(
        modifier = Modifier.width(72.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppSpacing.extraSmall)
    ) {
        Surface(
            modifier = Modifier
                .size(60.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surfaceVariant,
        ) {
            AsyncImage(
                model = actor.profilePath,
                contentDescription = stringResource(R.string.cd_actor_photo , actor.name),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Text(
            text = actor.name,
            style = MaterialTheme.typography.labelMedium.copy(
                letterSpacing = 0.sp),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = actor.character,
            style = MaterialTheme.typography.labelSmall.copy(
                letterSpacing = 0.sp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

private val previewCast = listOf(
    CastMember(id = 1, name = "Tom Holland", character = "Peter Parker", profilePath = ""),
    CastMember(id = 2, name = "Zendaya", character = "MJ", profilePath = ""),
    CastMember(id = 3, name = "Benedict Cumberbatch", character = "Doctor Strange", profilePath = ""),
    CastMember(id = 4, name = "Jacob Batalon", character = "Ned Leeds", profilePath = ""),
    CastMember(id = 5, name = "Jon Favreau", character = "Happy Hogan", profilePath = "")
)

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun MovieCastSectionLightPreview() {
    FilmZoomTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MovieCastSection(cast = previewCast, modifier = Modifier.padding(16.dp))
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun MovieCastSectionDarkPreview() {
    FilmZoomTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            MovieCastSection(cast = previewCast, modifier = Modifier.padding(16.dp))
        }
    }
}