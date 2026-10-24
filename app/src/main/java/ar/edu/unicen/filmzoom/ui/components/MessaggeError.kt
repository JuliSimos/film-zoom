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
import ar.edu.unicen.filmzoom.ui.theme.FilmZoomTheme


@Composable
fun MessaggeError(
    message: String,
    solution: String,
    buttonText: String,
    onActionClick: () -> Unit,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = .1f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .background(
                    MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(14.dp, 14.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),

            ) {
                    Text(
                        text = "Error",
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.align(Alignment.BottomStart)
                    )

                // 2. Botón de cerrar pegado estrictamente a la esquina superior derecha
                IconButton(
                    onClick = onDismiss, // Conectado a la acción de cerrar
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(48.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar"
                    )
                }
            }

            Text(
                text = message,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
            )

            // Texto de solución
            Text(
                fontSize = 16.sp,
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
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 14.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = buttonText,
                    fontSize = 16.sp,
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
            onDismiss = { }
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
            onDismiss = { }
        )
    }
}