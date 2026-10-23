package ar.edu.unicen.filmzoom.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
    onRetry: () -> Unit,
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
                .padding(14.dp,14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 🔹 CABECERA: Usamos un Box para superponer el título al centro y la cruz a la derecha
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                // 1. Ícono y título agrupados y centrados en el Box
                Column(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(48.dp),
                        painter = painterResource(R.drawable.ic_error),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                    )
                    Text(
                        text = "Error",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

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

            // Mensaje principal centrado estéticamente
            Text(
                text = message,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            // Texto de solución
            Text(
                fontSize = 14.sp,
                text = solution,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            // Botón de reintentar ocupando todo el ancho limpio
            Button(
                onClick = onRetry,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onSecondary
                ),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 14.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Reintentar",
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
            onRetry = {},
            onDismiss = {}
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
            onRetry = {},
            onDismiss = {}
        )
    }
}