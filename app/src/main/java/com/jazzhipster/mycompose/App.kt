package com.jazzhipster.mycompose

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application()
{
    init {
        instance = this
    }
    companion object{
        lateinit var instance:App
        fun applicationContext(): Context = instance.applicationContext
    }
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)


    }



    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            //Timber.plant(Timber.DebugTree())
        }

    }

}