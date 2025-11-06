package ucne.edu.victor_ferreiras_p2_ap2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import ucne.edu.victor_ferreiras_p2_ap2.presentation.GastoScreen
import ucne.edu.victor_ferreiras_p2_ap2.ui.theme.Victor_Ferreiras_P2_AP2Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Victor_Ferreiras_P2_AP2Theme {
                GastoScreen()
            }
        }
    }
}