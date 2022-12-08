package com.bjs.pdanative.presentation.ui.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.navigation.fragment.findNavController
import com.bjs.pdanative.R
import com.bjs.pdanative.databinding.FragmentPhoneVerificationBinding
import com.bjs.pdanative.databinding.FragmentVehicleRegVerificationBinding
import com.bjs.pdanative.presentation.ui.base.BaseFragment


/**
 *
 */
class VerifyVehicleRegFragment : BaseFragment() {
    private lateinit var userPhone: String
    private lateinit var user: User
    private var _binding: FragmentVehicleRegVerificationBinding? = null
    private val binding get() = _binding!!
    private lateinit var jobRoles: ArrayList<String>
    private lateinit var pin: String
    private lateinit var jobTitle: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVehicleRegVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.action_verifyVehicleRegFragment_to_verifyPasscodeFragment)

        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}