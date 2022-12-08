package com.bjs.pdanative.presentation.ui

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.content.ContextCompat
import com.bjs.pdanative.R
import com.bjs.pdanative.databinding.ActivitySplashBinding
import com.bjs.pdanative.presentation.ui.home.HomeActivity
import com.bjs.pdanative.presentation.ui.login.SignInActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by deepak on {06/05/21}
 */
@ExperimentalComposeUiApi
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding


    private var permsAll = arrayOf(
        "android.permission.CAMERA",
        "android.permission.RECORD_AUDIO",
        "android.permission.WRITE_EXTERNAL_STORAGE",
        "android.permission.ACCESS_FINE_LOCATION",
        "android.permission.CALL_PHONE",
        "android.permission.SEND_SMS",
        "android.permission.WRITE_CONTACTS",
        "android.permission.READ_PHONE_STATE"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }



    private fun checkPermission(): Boolean {
        var allSuccess = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in permsAll) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        permission
                    ) == PackageManager.PERMISSION_DENIED
                ) {
                    allSuccess = false
                }
            }
        }
        return allSuccess
    }

    override fun onResume() {
        super.onResume()
        if (checkPermission()) {
            startActivity()
        } else {
        /*    openPermissionDialog()*/
            startActivity()
        }
    }

    private fun startActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity, SignInActivity::class.java))
            finish()
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }, 2000)
    }

}