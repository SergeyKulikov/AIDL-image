package auto.atom.testapplication

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

/**
 * ViewModel class for the Speedometer component.
 * It manages the current speed value and provides a method to update the speed.
 */
class SpeedometerViewModel : ViewModel() {

    /**
     * Mutable state holding the current speed value.
     */
    val currentSpeed:State<Float> = mutableStateOf(0f)


    /**
     * Updates the current speed value with the specified speed.
     *
     * @param speed The speed value to update.
     */
    fun updateSpeed(speed: Float) {
        (currentSpeed as MutableState).value = speed
    }
}