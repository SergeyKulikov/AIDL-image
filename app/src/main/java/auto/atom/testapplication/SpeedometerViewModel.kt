package auto.atom.testapplication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SpeedometerViewModel : ViewModel() {
    val currentSpeed = mutableStateOf(0f)

    fun updateSpeed(speed: Float) {
        currentSpeed.value = speed
    }
}