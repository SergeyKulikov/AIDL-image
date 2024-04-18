package auto.atom.testapplication

import android.graphics.Paint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun Speedometer(speedometerViewModel: SpeedometerViewModel) {
    val currentSpeed by speedometerViewModel.currentSpeed

    StrokesAroundCircle(
        20.dp, 150.dp, 180f, 360f, 18, 1.dp
    )

    StrokesAroundCircle(
        40.dp, 140.dp, 180f, 360f, 9, 2.dp
    )
    val paint = Paint().apply {
        color = Color.Black.toArgb()
        textSize = 30f
    }

    val textList = (0..18 step 2).map { it.toString() }

    TextAroundCircle(
        textList, 150.dp, 180f, 360f, paint
    )

    AnimatedArrow(currentSpeed, 150.dp)


}

/**
 * Composable function for drawing strokes around a circle.
 *
 * @param strokeLength The length of the stroke.
 * @param distanceFromCenter The distance from the center of the circle.
 * @param startAngle The starting angle in degrees.
 * @param endAngle The ending angle in degrees.
 * @param numberOfStrokes The number of strokes around the circle.
 * @param strokeWidth The width of the stroke.
 */
@Composable
fun StrokesAroundCircle(
    strokeLength: Dp,
    distanceFromCenter: Dp,
    startAngle: Float,
    endAngle: Float,
    numberOfStrokes: Int,
    strokeWidth: Dp
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val radius = distanceFromCenter.toPx()

        val angleRange = endAngle - startAngle
        val angleIncrement = angleRange / numberOfStrokes

        repeat(numberOfStrokes) { index ->
            val currentAngle = startAngle + index * angleIncrement
            val startX = centerX + radius * cos(Math.toRadians(currentAngle.toDouble())).toFloat()
            val startY = centerY - radius * sin(Math.toRadians(currentAngle.toDouble())).toFloat()
            val endX = centerX + (radius + strokeLength.toPx()) * cos(Math.toRadians(currentAngle.toDouble())).toFloat()
            val endY = centerY - (radius + strokeLength.toPx()) * sin(Math.toRadians(currentAngle.toDouble())).toFloat()

            drawLine(
                color = Color.Black,
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = strokeWidth.toPx()
            )
        }
    }
}

/**
 * Composable function for displaying text around a circle.
 *
 * @param textList The list of strings to be displayed.
 * @param distanceFromCenter The distance from the center of the circle.
 * @param startAngle The starting angle in degrees.
 * @param endAngle The ending angle in degrees.
 * @param paint The paint object containing font parameters.
 **/
@Composable
fun TextAroundCircle(
    textList: List<String>,
    distanceFromCenter: Dp,
    startAngle: Float,
    endAngle: Float,
    paint: Paint
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val radius = min(centerX, centerY) - distanceFromCenter.toPx()

        val angleRange = endAngle - startAngle
        val angleIncrement = angleRange / textList.size

        textList.map { "${it}0" }.forEachIndexed { index, text ->
            val currentAngle = startAngle + index * angleIncrement
            val x = centerX + radius * cos(Math.toRadians(-currentAngle.toDouble())).toFloat()
            val y = centerY - radius * sin(Math.toRadians(-currentAngle.toDouble())).toFloat()

            drawContext.canvas.nativeCanvas.drawText(text, x, y, paint)
        }
    }
}

/**
 * Composable function for drawing an arrow of speedometer.
 *
 * @param distanceFromCenter The distance from the center of the circle.
 * @param angle The angle in degrees at which the arrow should point.
 */
@Composable
fun drawArrow(
    distanceFromCenter: Dp,
    angle: Float,
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val radius = distanceFromCenter.toPx()

        val endX = centerX + radius * cos(Math.toRadians(angle.toDouble())).toFloat()
        val endY = centerY - radius * sin(Math.toRadians(angle.toDouble())).toFloat()

        drawLine(
            color = Color.Black,
            start = Offset(centerX, centerY),
            end = Offset(endX, endY),
            strokeWidth = 2.dp.toPx()
        )
    }
}

@Composable
fun AnimatedArrow(angle: Float, distanceFromCenter:Dp) {
    val animatedAngle = remember { Animatable(0f) }
    LaunchedEffect(angle) {
        animatedAngle.animateTo(angle, animationSpec = tween(durationMillis = 500))
    }
    drawArrow(distanceFromCenter, angle = animatedAngle.value)
}

@Preview
@Composable
fun SpeedometerPreview() {
    val viewModel = SpeedometerViewModel()
    Speedometer(speedometerViewModel = viewModel)
}