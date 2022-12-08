package com.bjs.pdanative.presentation.ui.lgvcheck;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bjs.pdanative.R;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Created by deepak on {13/07/21}
 */

@AndroidEntryPoint
public class FaultImagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fault_images);
    }
}
