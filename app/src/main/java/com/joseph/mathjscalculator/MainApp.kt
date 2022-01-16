package com.joseph.mathjscalculator

import androidx.multidex.MultiDexApplication
import com.joseph.mathjscalculator.di.featuresModules
import com.joseph.mathjscalculator.di.networkModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(applicationContext)
            fragmentFactory()
            modules(networkModules, featuresModules)
        }
    }
}
