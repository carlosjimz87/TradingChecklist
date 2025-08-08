import androidx.compose.ui.graphics.Color
import com.carlosjimz87.tradingchecklist.domain.models.Strategy

fun softenColor(color: Color, targetAlpha: Float = 1f): Color {
    val mixRatio = 0.85f
    val r = (1 - mixRatio) * color.red + mixRatio * 1f
    val g = (1 - mixRatio) * color.green + mixRatio * 1f
    val b = (1 - mixRatio) * color.blue + mixRatio * 1f

    return Color(r, g, b, alpha = targetAlpha)
}

fun colorForStrategy(name: String): Color {
    val hash = name.hashCode()
    val hue = (hash % 360).toFloat().let { if (it < 0) it + 360 else it }

    val saturation = 0.4f + (hash shr 8 and 0xFF) / 255f * 0.2f  // Range 0.4–0.6 (más vivos)
    val lightness = 0.88f

    return Color.hsl(
        hue,
        saturation.coerceIn(0.4f, 0.6f),
        lightness.coerceIn(0.85f, 0.9f)
    )
}

fun colorFromHex(hex: String): Color {
    val cleaned = hex.trim().removePrefix("#")

    return try {
        when (cleaned.length) {
            6 -> {
                val r = cleaned.substring(0, 2).toInt(16)
                val g = cleaned.substring(2, 4).toInt(16)
                val b = cleaned.substring(4, 6).toInt(16)
                Color(r / 255f, g / 255f, b / 255f)
            }
            8 -> {
                val a = cleaned.substring(0, 2).toInt(16)
                val r = cleaned.substring(2, 4).toInt(16)
                val g = cleaned.substring(4, 6).toInt(16)
                val b = cleaned.substring(6, 8).toInt(16)
                Color(r / 255f, g / 255f, b / 255f, alpha = a / 255f)
            }
            else -> Color.Gray
        }
    } catch (_: Exception) {
        Color.Gray
    }
}

fun Strategy.getColor(): Color {
    val baseColor = if (color.isNotBlank()) colorFromHex(color) else colorForStrategy(name)
    return softenColor(baseColor)
}