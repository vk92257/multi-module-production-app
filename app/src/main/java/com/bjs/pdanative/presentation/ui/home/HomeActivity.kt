package com.bjs.pdanative.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bjs.pdanative.common.BUNDLE_IMAGES_LIST
import com.bjs.pdanative.common.BUNDLE_IS_FROM_ACTIVITY
import com.bjs.pdanative.R
import com.bjs.pdanative.databinding.ActivityHomeBinding
import com.bjs.pdanative.presentation.ui.base.BaseActivity
import com.bjs.pdanative.presentation.ui.camera.CameraFragment
import com.bjs.pdanative.presentation.ui.camera.ImagesModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by deepak on {21/07/21}
 */

@AndroidEntryPoint
class HomeActivity : BaseActivity(), CameraFragment.Companion.ItemListenerFromActivity {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {

            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController
            NavigationUI.setupWithNavController(
                homeContentLayout.bottomNavigation,
                navController
            )

            homeContentLayout.fabPhoto.setOnClickListener {
                CameraFragment.setListener(this@HomeActivity)
                startActivity(
                    Intent(this@HomeActivity, com.bjs.pdanative.presentation.ui.lgvcheck.FaultImagesActivity::class.java)
                        .putExtra(
                            BUNDLE_IS_FROM_ACTIVITY,
                            true
                        ).putParcelableArrayListExtra(BUNDLE_IMAGES_LIST, ArrayList())
                )
                overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
                )
            }

            homeContentLayout.rlSliderBar.setOnClickListener {
                findNavController(R.id.nav_host_fragment).navigate(R.id.homeBottomSheetMenuFragment)
            }

            homeContentLayout.bottomNavigation.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.action_route -> {
                        homeContentLayout.rlSliderBar.performClick()
                    }
                }
                true
            }
        }

    }

    //Setting Up the back button
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    override fun getImageList(imageList: ArrayList<ImagesModel>) {
        Toast.makeText(this, imageList.size.toString(), Toast.LENGTH_SHORT).show()

    }

}