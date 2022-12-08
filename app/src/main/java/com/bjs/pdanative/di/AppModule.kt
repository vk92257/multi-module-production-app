package com.bjs.pdanative.di

import android.content.Context
import com.bjs.pdanative.domain.models.lgvCheck.LgvCheckInfo
import com.bjs.pdanative.presentation.screens.lgv_check.LgvCheckData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//
// Created by gaurav on 03/11/21.
// Copyright (c) 2021 gc. All rights reserved.
//

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
     fun getCheckList(@ApplicationContext application: Context): MutableList<LgvCheckInfo> {
        return LgvCheckData.getLgvCheckData(application)
    }



}