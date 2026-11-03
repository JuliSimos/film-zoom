package ar.edu.unicen.filmzoom.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unicen.filmzoom.R
import ar.edu.unicen.filmzoom.ui.theme.AppSpacing
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme


@Composable
fun MessaggeError(
    message: String,
    solution: String,
    buttonText: String,
    onActionClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = .4f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(AppSpacing.medium)
                .background(
                    MaterialTheme.colorScheme.background,
                    shape = MaterialTheme.shapes.large
                )
                .padding(AppSpacing.medium, AppSpacing.large),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(AppSpacing.normal)
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = solution,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Button(
                onClick = onActionClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onSecondary
                ),
                contentPadding = PaddingValues(horizontal = AppSpacing.large, vertical = AppSpacing.normal),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = buttonText,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessaggeErrorPreview() {
    FilmZoomTheme(darkTheme = false){
        MessaggeError(
            message = "Sin conexión a internet",
            solution = "Comprobá que el Wi-Fi o los datos móviles estén activados",
            buttonText = "Reintentar",
            onActionClick = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MessaggeErrorPreviewDark() {
    FilmZoomTheme(darkTheme = true) {
        MessaggeError(
            message = "Sin conexión a internet",
            solution = "Comprobá que el Wi-Fi o los datos móviles estén activados",
            buttonText = "Reintentar",
            onActionClick = { },
        )
    }
}