package com.bjs.pdanative.presentation.ui.runsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bjs.pdanative.databinding.FragmentReturnBaseBinding
import com.bjs.pdanative.presentation.ui.base.BaseFragment

class ReturnToBaseFragment : BaseFragment() {
    private var _binding: FragmentReturnBaseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReturnBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}