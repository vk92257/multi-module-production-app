package com.bjs.pdanative.presentation.screens.preChecksWorkflow.logdamage.deletelogdamage

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bjs.pdanative.R
import com.bjs.pdanative.presentation.screens.common.errorscreen.ScanErrorScreen


@Composable
@Preview
fun DeleteLogDamageEntryScreen(
    damageComponent: String = "",
    index: Int = 0,
    onCancelClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onCallDHClick: () -> Unit = {}
) {
    ScanErrorScreen(
        errorTitle = stringResource(id = R.string.delete_damage_entry_title),
        errorInfo = stringResource(id = R.string.exit_confirmation),
        errorProblemMessage = stringResource(id = R.string.havingProblem),
        onTryAgainClick = {
            onDeleteClick()
        },
        onAddManuallyClick = {
            onCancelClick()
        },
        onCallDHClick = {
            onCallDHClick()
        },
        whiteButtonText = stringResource(id = R.string.delete_confirmation),
        outLinedButtonText = stringResource(id = R.string.cancel),
        showFaultEntry = true,
        damagedFault = damageComponent,
        index = index
    )
}