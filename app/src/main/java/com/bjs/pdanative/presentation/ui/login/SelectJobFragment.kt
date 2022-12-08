package com.bjs.pdanative.presentation.ui.login

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.bjs.pdanative.R
import com.bjs.pdanative.databinding.FragmentLoginBinding
import com.bjs.pdanative.common.AllUtils
import com.bjs.pdanative.presentation.ui.base.BaseFragment
import com.bjs.pdanative.presentation.ui.otpview.OnOtpCompletionListener

/**
 *
 */
class SelectJobFragment : BaseFragment(), AdapterView.OnItemSelectedListener,
    OnOtpCompletionListener {
    private lateinit var userPhone: String
    private lateinit var user: User
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var jobRoles: ArrayList<String>
    private lateinit var pin: String
    private lateinit var jobTitle: String

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
        userPhone = arguments?.getString("BUNDLE_PHONE")!!
        if (!SignInActivity.isFromChangeRole)
            user = User("", userPhone, "", "")

        binding.apply {
            btnLogin.setOnClickListener {
                if (jobTitle == jobRoles[0]) {
                    showOneButtonErrorOrSuccessDialog(
                        {},
                        getString(R.string.select_ur_role),
                        getString(R.string.pls_try_again),
                        true,
                        getString(R.string.try_again)
                    )
                }/* else if (otpView.length() < 4) {
                    showOneButtonErrorOrSuccessDialog(
                        {},
                        getString(R.string.pin_empty),
                        getString(R.string.pls_try_again),
                        true,
                        getString(R.string.try_again)
                    )
                }*/ else if (!chkTerms.isChecked) {
                    showOneButtonErrorOrSuccessDialog(
                        {},
                        getString(R.string.terms_condition_error),
                        getString(R.string.terms_condition_error_desc),
                        true,
                        getString(R.string.try_again)
                    )
                } else {
                    userNavigate()
                }
            }
          /*  tvForgotPin.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_otpFragment)
            }*/
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
            btnTerms.setOnClickListener {
                showTermsCondition()
            }
            chkTerms.setOnClickListener {
                showTermsCondition()
            }
          //  otpView.setOtpCompletionListener(this@SelectJobFragment)
        }

        if (SignInActivity.isFromChangeRole) {
            binding.chkTerms.isChecked = true
            binding.btnLogin.text = getString(R.string.update)
           // binding.tvLogin.text = getString(R.string.update_role)
        } else {
            binding.chkTerms.isChecked = false
            binding.btnLogin.text = getString(R.string.log_in)
          //  binding.tvLogin.text = getString(R.string.log_in)
        }
        addJobRoles()
    }

    private fun addJobRoles() {
        jobRoles = ArrayList()
        jobRoles.add(0, getString(R.string.select_role))
        jobRoles.add(getString(R.string.driver))
        jobRoles.add(getString(R.string.sideman))
        val roleAdapter: ArrayAdapter<String> =
            ArrayAdapter(requireActivity(), R.layout.spinner_layout, jobRoles)
        binding.spnJobRole.apply {
            onItemSelectedListener = this@SelectJobFragment
            this.adapter = roleAdapter
        }
    }


    private fun userNavigate() {
        if (SignInActivity.isFromChangeRole) {
            for (item in SignInActivity.userList) {
                if (item.phoneNo == userPhone) {
                    item.jobTitle = jobTitle
                    item.passcode = pin
                    break
                }
            }
            findNavController().navigateUp()
        } else {
            user.passcode = pin
            user.jobTitle = jobTitle
            SignInActivity.userList.add(user)
          //  findNavController().navigate(R.id.action_loginFragment_to_frontCameraFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showTermsCondition() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(R.string.dialog_terms)
        builder.setMessage(AllUtils.camelCase(getString(R.string.terms)))
        val positiveText = getString(R.string.txt_agree)
        builder.setPositiveButton(positiveText) { _: DialogInterface?, _: Int ->
            binding.chkTerms.isChecked = true
        }
        val negativeText = getString(R.string.do_not_agree)
        builder.setNegativeButton(negativeText) { _: DialogInterface?, _: Int ->
            binding.chkTerms.isChecked = false
        }
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        jobTitle = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onOtpCompleted(otp: String?) {
        pin = otp!!
    }


}