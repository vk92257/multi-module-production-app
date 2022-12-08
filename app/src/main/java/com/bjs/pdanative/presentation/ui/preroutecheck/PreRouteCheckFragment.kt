package com.bjs.pdanative.presentation.ui.preroutecheck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bjs.pdanative.R
import com.bjs.pdanative.databinding.FragmentPreRouteCheckBinding
import com.bjs.pdanative.presentation.ui.base.BaseFragment

/**
 * Created by deepak on {15/07/21}
 */
class PreRouteCheckFragment : BaseFragment(), PreRouteCheckItemAdapter.ItemClickListener {

    private var _binding: FragmentPreRouteCheckBinding? = null
    private val binding get() = _binding!!
    private lateinit var preRouteCheckList: ArrayList<PreRouteCheck>
    private lateinit var preRouteCheckItemAdapter: PreRouteCheckItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreRouteCheckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPreRouteCheckModel()
        binding.apply {
            preRouteCheckItemAdapter =
                PreRouteCheckItemAdapter(
                    requireContext(),
                    this@PreRouteCheckFragment,
                    preRouteCheckList
                )
            viewPager.adapter = preRouteCheckItemAdapter
        }
    }

    private fun setUpPreRouteCheckModel() {
        preRouteCheckList = ArrayList()
        preRouteCheckList.add(
            PreRouteCheck(
                icon = R.drawable.ic_satnav,
                title = getString(R.string.satnav_check_title),
                selected = true,
                description = getString(R.string.satnav_check_desc),
                faultDescription = getString(R.string.satnav_fault_desc),
                faultTitle = getString(R.string.satnav_fault_title)
            )
        )
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemYesClick(position: Int) {
        if (position == preRouteCheckList.size - 1) {
            findNavController().navigate(R.id.action_preRouteCheckFragment_to_LGVCheckFragment)
        } else {
            preRouteCheckItemAdapter.notifyItemChanged(position)
            binding.viewPager.currentItem = position + 1
        }
    }

    override fun onItemNoClick(position: Int) {
        preRouteCheckList[position].no = true
        preRouteCheckList[position].yes = false
        preRouteCheckItemAdapter.notifyItemChanged(position)
    }

    override fun onItemCloseClick(position: Int) {

        if (preRouteCheckList[position].preCheckRouteFault == null)
            preRouteCheckList[position].no = false
        preRouteCheckList[position].yes = false
        preRouteCheckItemAdapter.notifyItemChanged(position)
    }

    override fun onItemContinueClick(position: Int) {
        if (position == preRouteCheckList.size - 1) {
            findNavController().navigate(R.id.action_preRouteCheckFragment_to_LGVCheckFragment)
        } else {
            preRouteCheckItemAdapter.notifyItemChanged(position)
            binding.viewPager.currentItem = position + 1
        }
    }
}