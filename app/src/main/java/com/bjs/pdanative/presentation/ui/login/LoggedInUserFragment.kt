package com.bjs.pdanative.presentation.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bjs.pdanative.R
import com.bjs.pdanative.databinding.FragmentLoggedInEmployeesBinding
import com.bjs.pdanative.presentation.ui.login.SignInActivity.Companion.isFromAnotherUser
import com.bjs.pdanative.presentation.ui.login.SignInActivity.Companion.isFromChangeRole

/**
 * Created by deepak on {11/05/21}
 */
class LoggedInUserFragment : Fragment(), LoggedInUserListAdapter.ItemClickListener {
    private var _binding: FragmentLoggedInEmployeesBinding? = null
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
        _binding = FragmentLoggedInEmployeesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val logExpenseListAdapter = LoggedInUserListAdapter(
            requireContext(), this,
            SignInActivity.userList as ArrayList<User>
        )
        isFromAnotherUser = false
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        binding.rvEmpList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = logExpenseListAdapter
        }

        binding.apply {
            btnAddAnotherUser.setOnClickListener {
                isFromAnotherUser = true
                findNavController().navigate(R.id.action_loggedInFragment_to_phoneNumberVerificationFragment)
            }

            btnBack.setOnClickListener {
                findNavController().navigate(R.id.action_loggedInFragment_to_bottomSheetBackFragment)
            }

            btnPreCheck.setOnClickListener {
                findNavController().navigate(R.id.action_loggedInFragment_to_vehicleAndProductScanFragment)

            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(user: User) {
        isFromChangeRole = true
        val bundle = Bundle()
        bundle.putString("BUNDLE_PHONE", user.phoneNo)
        findNavController().navigate(R.id.action_loggedInFragment_to_loginFragment, bundle)
    }
}