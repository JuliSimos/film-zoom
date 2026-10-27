package ar.edu.unicen.filmzoom.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp), // Para elementos pequeños
    small = RoundedCornerShape(8.dp),     // Para chips o botones pequeños
    medium = RoundedCornerShape(12.dp),   // Para Cards
    large = RoundedCornerShape(16.dp),    // Para contenedores más grandes o diálogos
    extraLarge = RoundedCornerShape(28.dp)// Para barras de búsqueda o elementos flotantes
)