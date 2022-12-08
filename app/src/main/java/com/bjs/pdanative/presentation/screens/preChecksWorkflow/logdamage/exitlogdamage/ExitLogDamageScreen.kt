package com.bjs.pdanative.presentation.screens.preChecksWorkflow.logdamage.exitlogdamage

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bjs.pdanative.R
import com.bjs.pdanative.presentation.screens.common.errorscreen.ScanErrorScreen

@Composable
@Preview
fun ExitLogDamageScreen(
    damageComponent: String = "",
    onCancelClick: () -> Unit = {},
    onExitClick: () -> Unit = {},
    onCallDHClick: () -> Unit = {}
) {
    ScanErrorScreen(
        errorTitle = stringResource(id = R.string.damage_exit),
        errorInfo = stringResource(id = R.string.exit_confirmation),
        errorProblemMessage = stringResource(id = R.string.havingProblem),
        onTryAgainClick = {
            onExitClick()
        },
        onAddManuallyClick = {
            onCancelClick()
        },
        onCallDHClick = {
            onCallDHClick()
        },
        whiteButtonText = stringResource(id = R.string.damage_exit_confirmation),
        outLinedButtonText = stringResource(id = R.string.cancel),
        showFaultEntry = false,
    )
}