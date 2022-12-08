package com.bjs.pdanative.presentation.ui.lgvcheck

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import com.bjs.pdanative.common.BUNDLE_IMAGES_LIST
import com.bjs.pdanative.common.BUNDLE_IS_FROM_ACTIVITY
import com.bjs.pdanative.R
import com.bjs.pdanative.databinding.FragmentLgvCheckBinding
import com.bjs.pdanative.common.SafeClickListener.Companion.setSafeOnClickListener
import com.bjs.pdanative.presentation.ui.base.BaseFragment
import com.bjs.pdanative.presentation.ui.camera.ImagesModel
import com.bjs.pdanative.presentation.ui.home.HomeActivity


/**
 * Created by deepak on {28/06/21}
 */
class LGVCheckFragment : BaseFragment(),
    LGVCheckItemAdapter.ItemClickListener {
    private var _binding: FragmentLgvCheckBinding? = null
    private val binding get() = _binding!!
    private lateinit var lgvCheckSelectionAdapter: LGVCheckAdapter
    private lateinit var lgvCheckItemAdapter: LGVCheckItemAdapter
    private lateinit var lgvCheckSummaryAdapter: LGVCheckSummaryAdapter
    private lateinit var lgvCheck: ArrayList<LgvCheck>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLgvCheckBinding.inflate(inflater, container, false)
        return binding.root
    }


    private val callback: OnBackPressedCallback =
        object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                // Handle the back button event

            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        setUpLGVModel()
        binding.apply {
            viewPager.visibility = View.VISIBLE
            llSummary.visibility = View.GONE
            viewPager.orientation = ORIENTATION_HORIZONTAL
            viewPager.isUserInputEnabled = false
            lgvCheckItemAdapter =
                LGVCheckItemAdapter(requireContext(), this@LGVCheckFragment, lgvCheck)
            viewPager.adapter = lgvCheckItemAdapter
            btnNo.setSafeOnClickListener {
                viewPager.visibility = View.VISIBLE
                llSummary.visibility = View.GONE
                fabBack.visibility = View.VISIBLE
                viewPager.currentItem = 0
                selectAdapterNotify(0)
            }

            btnYes.setOnClickListener {
                startActivity(Intent(requireContext(), HomeActivity::class.java))
                requireActivity().finish()
                requireActivity().overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
                )
            }

            fabBack.setOnClickListener {
                if (viewPager.currentItem == 0) {
                    findNavController().navigateUp()
                } else {
                    selectAdapterNotify(viewPager.currentItem - 1)
                    viewPager.currentItem = viewPager.currentItem - 1
                }
            }
        }
    }


    private fun setUpLGVModel() {
        lgvCheck = ArrayList()
        lgvCheck.add(
            LgvCheck(
                icon = R.drawable.ic_truck_solid,
                title = getString(R.string.exterior_check_title),
                selected = true,
                description = getString(R.string.exterior_check_desc),
                faultDescription = getString(R.string.fault_text),
                faultTitle = getString(R.string.exterior_fault_title)
            )
        )
        lgvCheck.add(
            LgvCheck(
                icon = R.drawable.ic_fuel,
                title = getString(R.string.fuel_title),
                selected = false,
                description = getString(R.string.fuel_check_desc),
                faultDescription = getString(R.string.fault_text),
                faultTitle = getString(R.string.fuel_fault_title)
            )
        )
        lgvCheck.add(
            LgvCheck(
                icon = R.drawable.ic_tyre,
                title = getString(R.string.tyre_pressure_title),
                selected = false,
                description = getString(R.string.tyre_pressure_desc),
                faultDescription = getString(R.string.fault_text),
                faultTitle = getString(R.string.tyre_pressure_fault_title)
            )
        )
        lgvCheck.add(
            LgvCheck(
                icon = R.drawable.ic_vehicle_fault,
                title = getString(R.string.vehicle_fault),
                selected = false,
                description = getString(R.string.vehicle_fault_desc),
                faultDescription = getString(R.string.fault_text),
                faultTitle = getString(R.string.vehicle_fault_title)
            )
        )
        lgvCheck.add(
            LgvCheck(
                icon = R.drawable.ic_interior,
                title = getString(R.string.interior_title),
                selected = false,
                description = getString(R.string.interior_check_desc),
                faultDescription = getString(R.string.fault_text),
                faultTitle = getString(R.string.interior_fault_title)
            )
        )

        binding.rvLgvCheck.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            // selection bar adapter
            lgvCheckSelectionAdapter = LGVCheckAdapter(requireContext(), lgvCheck)
            adapter = lgvCheckSelectionAdapter
        }
    }

    private fun showSummaryView() {
        binding.apply {
            viewPager.visibility = View.GONE
            fabBack.visibility = View.GONE
            llSummary.visibility = View.VISIBLE
            rvFaultStatus.layoutManager = LinearLayoutManager(requireContext())
            //setSummaryAdapter
            lgvCheckSummaryAdapter = LGVCheckSummaryAdapter(requireContext(), lgvCheck)
            rvFaultStatus.adapter = lgvCheckSummaryAdapter
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * function calls from LGVCheckItemAdapter
     * @param position lgvCheck item position
     */
    override fun onItemPassClick(position: Int) {
        lgvCheck[position].pass = true
        selectAdapterNotify(position + 1)
        lgvCheckItemAdapter.notifyItemChanged(position)
        binding.viewPager.currentItem = position + 1
        if (position == lgvCheck.size - 1) showSummaryView()
    }


    /**
     * notify select/unselect LGVCheckAdapter
     * @param position lgvCheck item position
     */
    private fun selectAdapterNotify(position: Int) {
        for (i in 0 until lgvCheck.size) {
            lgvCheck[i].selected = i == position
        }
        lgvCheckSelectionAdapter.notifyDataSetChanged()
    }

    /**
     * function calls from LGVCheckItemAdapter
     * @param position lgvCheck item position
     */
    override fun onItemFailClick(position: Int) {
        lgvCheck[position].pass = false
        lgvCheck[position].fail = true
        lgvCheckSelectionAdapter.notifyItemChanged(position)
        lgvCheckItemAdapter.notifyItemChanged(position)
    }


    /**
     * function calls from LGVCheckItemAdapter
     * @param position lgvCheck item position
     */
    override fun onItemCloseClick(position: Int) {
        lgvCheck[position].fail = false
        lgvCheck[position].pass = false
        lgvCheck[position].selected = true
        lgvCheckSelectionAdapter.notifyItemChanged(position)
        lgvCheckItemAdapter.notifyItemChanged(position)
    }


    /**
     *  function calls from LGVCheckItemAdapter
     *  @param position lgvCheck item position
     */
    override fun onItemContinueClick(position: Int) {
        selectAdapterNotify(position + 1)
        binding.viewPager.currentItem = position + 1
        if (position == lgvCheck.size - 1) showSummaryView()
    }


    /**
     * function calls from LGVCheckItemAdapter
     * @param position  fault logged position
     * @param imageList  captured fault image list
     */
    override fun onItemFaultImagesClick(position: Int, imageList: ArrayList<ImagesModel>) {
        // navigate to FaultImagesActivity which loads camera fragment
        startActivity(
            Intent(requireContext(), com.bjs.pdanative.presentation.ui.lgvcheck.FaultImagesActivity::class.java)
                .putExtra(
                    BUNDLE_IS_FROM_ACTIVITY,
                    true
                ).putParcelableArrayListExtra(BUNDLE_IMAGES_LIST, imageList)

        )
        requireActivity().overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        )

    }


}