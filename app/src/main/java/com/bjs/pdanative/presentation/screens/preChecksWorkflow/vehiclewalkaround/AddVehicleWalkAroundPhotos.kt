import android.util.Log
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.bjs.pdanative.presentation.screens.common.camera.cameraScreen.CameraScreen
import com.bjs.pdanative.presentation.screens.preChecksWorkflow.vehiclewalkaround.VehicleWalkAroundViewModel


@Composable
@Preview
fun AddVehicleWalkAroundPhotos(
    viewModel: VehicleWalkAroundViewModel = hiltViewModel(),
    navigateUP : ()-> Unit = {}
) {


}