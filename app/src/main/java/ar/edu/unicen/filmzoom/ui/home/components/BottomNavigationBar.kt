package ar.edu.unicen.filmzoom.ui.home.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unicen.filmzoom.R
import ar.edu.unicen.filmzoom.ui.theme.AppSpacing
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    selectedTab: Int = 0,
    onTabSelected: (Int) -> Unit = {}
) {
    Surface(
        modifier = modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 8.dp
    ) {
        Column {
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
                thickness = 1.dp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = AppSpacing.small),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomNavItem(
                    iconRes = R.drawable.ic_home,
                    label = stringResource(R.string.nav_popular),
                    isSelected = selectedTab == 0,
                    onClick = { onTabSelected(0) },
                    modifier = Modifier.weight(1f)
                )

                BottomNavItem(
                    iconRes = R.drawable.ic_tendences,
                    label = stringResource(id = R.string.nav_tending),
                    isSelected = selectedTab == 1,
                    onClick = { onTabSelected(1) },
                    modifier = Modifier.weight(1f)
                )

                BottomNavItem(
                    iconRes = R.drawable.ic_heart,
                    label = stringResource(id = R.string.nav_wishlist),
                    isSelected = selectedTab == 2,
                    onClick = { onTabSelected(2) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun BottomNavItem(
    iconRes: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contentColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    val fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium

    Column(
        modifier = modifier
            // Usa selectable en lugar de clickable para avisarle a TalkBack que es una pestaña y si está seleccionada
            .selectable(
                selected = isSelected,
                onClick = onClick,
                role = Role.Tab, // Anuncia "Pestaña, seleccionada/no seleccionada"
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = false, radius = 32.dp),
            )
            .padding(vertical = AppSpacing.extraSmall),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(26.dp)
        )
        Spacer(modifier = Modifier.height(AppSpacing.extraSmall))

        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = fontWeight,
            color = contentColor
        )
    }
}

@Preview(name = "Light Mode", showBackground = true, locale = "es")
@Composable
fun BottomNavigationBarLightPreview() {
    FilmZoomTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            BottomNavigationBar()
        }
    }
}

@Preview(name = "Dark Mode", showBackground = true, locale = "es")
@Composable
fun BottomNavigationBarDarkPreview() {
    FilmZoomTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            BottomNavigationBar()
        }
    }
}
