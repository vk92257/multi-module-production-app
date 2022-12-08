package com.bjs.pdanative.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.viewpager2.widget.ViewPager2
import com.bjs.pdanative.R
import com.bjs.pdanative.databinding.FragmentHomeBinding
import com.bjs.pdanative.presentation.ui.base.BaseFragment
import com.bjs.pdanative.presentation.ui.runsheet.RunSheetActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by deepak on {26/07/21}
 */

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var appList: ArrayList<HomeApp>
    private lateinit var homeAppTypeAdapter: HomeAppTypeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewPager.registerOnPageChangeCallback(pageChangeCallback)
            setupHomeApps()
            viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            viewPager.isUserInputEnabled = true
            homeAppTypeAdapter =
                HomeAppTypeAdapter(
                    requireContext(),
                    appList
                ) { viewPagerPosition, appListItemPosition ->
                    if (appList[viewPagerPosition].appTypeName == "BJS TOOLS") {
                        // navigate bjs apps
                        startActivity(Intent(requireContext(), RunSheetActivity::class.java))
                        requireActivity().overridePendingTransition(
                            R.anim.slide_in_right,
                            R.anim.slide_out_left
                        )

                    } else {
                        // launch system apps
                        val intent: Intent? =
                            requireActivity().packageManager.getLaunchIntentForPackage(
                                appList[viewPagerPosition].appListData?.get(appListItemPosition)?.packageName.toString()
                            )
                        if (intent != null) startActivity(intent)
                    }
                }
            viewPager.adapter = homeAppTypeAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupHomeApps() {
        appList = ArrayList()
        val homeApp = HomeApp(appTypeName = "BJS TOOLS")
        val appInfoList = ArrayList<AppInfo>()
        appInfoList.add(
            AppInfo(
                label = "Route",
                icon = ResourcesCompat.getDrawable(
                    requireContext().resources,
                    R.drawable.ic_route_time,
                    null
                )
            )
        )
        appInfoList.add(
            AppInfo(
                label = "Accident Report",
                icon = ResourcesCompat.getDrawable(
                    requireContext().resources,
                    R.drawable.ic_accident_report,
                    null
                )
            )
        )
        appInfoList.add(
            AppInfo(
                label = "Refuel Log",
                icon = ResourcesCompat.getDrawable(
                    requireContext().resources,
                    R.drawable.ic_fuel_filled,
                    null
                )
            )
        )
        appInfoList.add(
            AppInfo(
                label = "Expense Log",
                icon = ResourcesCompat.getDrawable(
                    requireContext().resources,
                    R.drawable.ic_tyre,
                    null
                )
            )
        )
        homeApp.appListData?.addAll(appInfoList)
        appList.add(homeApp)

        val homeOtherApps = HomeApp(appTypeName = "OTHER APPS")
        val packageManager = requireContext().packageManager
        val i = Intent(Intent.ACTION_MAIN, null)
        i.addCategory(Intent.CATEGORY_LAUNCHER)
        val availableApps = packageManager.queryIntentActivities(i, 0)
        for (resolveInfo in availableApps) {
            val appInfo = AppInfo(
                resolveInfo.loadLabel(packageManager),
                resolveInfo.activityInfo.packageName,
                resolveInfo.activityInfo.loadIcon(packageManager)
            )
            if (appInfo.packageName != requireContext().packageName)
                homeOtherApps.appListData?.add(appInfo)
        }
        appList.add(homeOtherApps)
    }


    // swipe gesture listener
    private val pageChangeCallback: ViewPager2.OnPageChangeCallback =
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.apply {
                    tvAppType.text = appList[position].appTypeName
                    if (position == 0) {
                        indicatorOne.setBackgroundResource(R.drawable.background_indicator_selected_corner)
                        indicatorTwo.setBackgroundResource(R.drawable.background_indicator_unselected_corner)
                    } else {
                        indicatorTwo.setBackgroundResource(R.drawable.background_indicator_selected_corner)
                        indicatorOne.setBackgroundResource(R.drawable.background_indicator_unselected_corner)
                    }
                }
            }
        }

}