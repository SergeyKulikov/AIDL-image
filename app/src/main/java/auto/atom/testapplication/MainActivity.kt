package auto.atom.testapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import auto.atom.testapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme

                val viewModel = SpeedometerViewModel()
                LaunchedEffect(Unit) {// TODO: Remowe this stub
                    while (true){
                        delay(1000)
                        viewModel.currentSpeed.value = (Math.random()*180).toFloat()
                    }

                }

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

                    Speedometer(speedometerViewModel = viewModel)
                }

            }
        }
    }
}

