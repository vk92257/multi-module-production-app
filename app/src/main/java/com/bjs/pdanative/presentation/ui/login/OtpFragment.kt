package com.bjs.pdanative.presentation.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bjs.pdanative.R
import com.bjs.pdanative.databinding.FragmentOtpBinding
import com.bjs.pdanative.presentation.ui.otpview.OnOtpCompletionListener

/**
 * Created by deepak on {11/05/21}
 */
class OtpFragment : Fragment(), OnOtpCompletionListener {
    private var _binding: FragmentOtpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvResendPin.setOnClickListener {

            }

            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            otpView.setOtpCompletionListener(this@OtpFragment)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOtpCompleted(otp: String?) {
        findNavController().navigate(R.id.action_otpFragment_to_resetPinFragment)
        binding.otpView.setText("")
    }
}