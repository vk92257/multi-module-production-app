package com.bjs.pdanative.presentation.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bjs.pdanative.R
import com.bjs.pdanative.databinding.FragmentBottomSheetBackBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by deepak on {11/05/21}
 */
class BottomSheetBackFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetBackBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetBackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.apply {
            tvSignIn.setOnClickListener {
                SignInActivity.isFromAnotherUser = false
                findNavController()
                    .popBackStack(R.id.phoneNumberVerificationFragment, false)
            }
            tvClose.setOnClickListener {
                SignInActivity.isFromAnotherUser = false
                findNavController().navigateUp()
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}