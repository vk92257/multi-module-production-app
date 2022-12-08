package com.bjs.pdanative.presentation.screens.loginWorkflow.termsAndCondition

import com.bjs.pdanative.presentation.screens.loginWorkflow.termsAndCondition.components.TermsAndConditionData

/**
 * @Author: Vivek
 * @Date: 14/03/22
 */
data class TermsAndConditionState(
    val date: String = "",
    val termsAndConditionList: ArrayList<TermsAndConditionData>? = null
)