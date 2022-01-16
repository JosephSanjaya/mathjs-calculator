package com.joseph.mathjscalculator

import androidx.multidex.MultiDexApplication
import com.joseph.mathjscalculator.di.featuresModules
import com.joseph.mathjscalculator.di.networkModules
import org.koin.core.context.startKoin

class MainApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin { modules(networkModules, featuresModules) }
    }
}
