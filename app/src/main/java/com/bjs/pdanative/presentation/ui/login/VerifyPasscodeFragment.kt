package com.bjs.pdanative.presentation.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bjs.pdanative.R
import com.bjs.pdanative.databinding.FragmentPhoneVerificationBinding
import com.bjs.pdanative.databinding.FragmentVerifyPasscodeBinding
import com.bjs.pdanative.presentation.ui.base.BaseFragment
import com.bjs.pdanative.presentation.ui.login.SignInActivity.Companion.isFromChangeRole

/**
 *
 */
class VerifyPasscodeFragment : BaseFragment() {
    private var _binding: FragmentVerifyPasscodeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerifyPasscodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFromChangeRole = false
        binding.btnValidate.setOnClickListener {
            when {
                binding.etPasscode.text.toString().isEmpty() -> {
                    showOneButtonErrorOrSuccessDialog(
                        {},
                        getString(R.string.number_empty),
                        getString(R.string.pls_try_again),
                        true,
                        getString(R.string.try_again)
                    )
                }
                /* binding.etPasscode.text.toString().trim().length < 10 -> {
                     showOneButtonErrorOrSuccessDialog(
                         {},
                         getString(R.string.number_not_valid),
                         getString(R.string.pls_try_again),
                         true,
                         getString(R.string.try_again)
                     )
                 }*/
                else -> {
                    val bundle = Bundle()
                    bundle.putString("BUNDLE_PHONE", binding.etPasscode.text.toString())
                    findNavController().navigate(
                        R.id.action_verifyPasscodeFragment_to_frontCameraFragment,
                        bundle
                    )
                    binding.etPasscode.setText("")
                }
            }
        }

        if (SignInActivity.isFromAnotherUser) {
            binding.btnBack.visibility = View.VISIBLE
        } else {
            binding.btnBack.visibility = View.GONE
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}