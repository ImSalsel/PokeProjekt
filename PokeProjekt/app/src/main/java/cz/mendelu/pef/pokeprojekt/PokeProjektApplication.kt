package cz.mendelu.pef.pokeprojekt


import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

const val useMockedValues: Boolean = false


@HiltAndroidApp
class PokeProjektApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        lateinit var appContext: Context
            private set
    }

}