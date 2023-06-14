package com.example.assignmentproject

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * @Author: Muhammad Zohaib
 * @Designation: Sr.SoftwareEngineer(Android)
 * @Gmail: muhammad.zohaib@axabiztech.com
 * @Company: Aksa SDS
 * @Created 6/13/2023 at 2:09 PM
 */
@HiltAndroidApp
class AppController : Application()  {

    override fun onCreate() {
        super.onCreate()
    }
}