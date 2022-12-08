package com.bjs.pdanative.presentation.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import com.bjs.pdanative.R
import com.bjs.pdanative.databinding.FragmentResetPasscodeBinding
import com.bjs.pdanative.presentation.ui.base.BaseFragment

/**
 * Created by deepak on {11/05/21}
 */
class ResetPinFragment : BaseFragment() {
    private var _binding: FragmentResetPasscodeBinding? = null
    private val binding get() = _binding!!

    private val callback: OnBackPressedCallback =
        object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                // Handle the back button event
                binding.btnBack.performClick()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResetPasscodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnBack.setOnClickListener {
                Navigation.findNavController(requireView())
                    .popBackStack(R.id.loginFragment, false)
            }
            btnResetPin.setOnClickListener {
                when {
                    otpViewNewPin.length() < 4 -> {
                        showOneButtonErrorOrSuccessDialog(
                            {},
                            getString(R.string.new_pin_empty),
                            getString(R.string.pls_try_again),
                            true,
                            getString(R.string.try_again)
                        )
                    }
                    otpViewNewPin.text.toString() != otpViewConfirmPin.text.toString() -> {
                        showOneButtonErrorOrSuccessDialog(
                            {},
                            getString(R.string.confirm_pin_error),
                            getString(R.string.pls_try_again),
                            true,
                            getString(R.string.try_again)
                        )
                    }
                    else -> {
                        showOneButtonErrorOrSuccessDialog(
                            {
                                if (it)
                                    Navigation.findNavController(requireView())
                                        .popBackStack(R.id.loginFragment, false)

                            },
                            getString(R.string.pin_updation_text),
                            getString(R.string.pin_description),
                            false,
                            getString(R.string.done)
                        )
                    }
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}