package com.bjs.pdanative.presentation.ui.login

import com.bjs.pdanative.databinding.FragmentLoginSuccessBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bjs.pdanative.R
import com.bjs.pdanative.databinding.FragmentLoginBinding
import com.bjs.pdanative.presentation.ui.camera.CameraViewModel
import com.bumptech.glide.Glide

/**
 * Created by deepak on {11/05/21}
 */
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val cameraViewModel: CameraViewModel by activityViewModels()


    private val callback: OnBackPressedCallback =
        object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                // Handle the back button event
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        cameraViewModel.userProfile.observe(viewLifecycleOwner, {
           /* SignInActivity.userList[SignInActivity.userList.lastIndex].userImage =
                it[0].imageUri.toString()*/
            Glide.with(requireActivity()).load(it[0].imageUri).into(binding.cvProfilePic)
        })

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_loggedInFragment)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}