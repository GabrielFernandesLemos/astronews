package br.com.chicorialabs.astranovos

import android.app.Application
import br.com.chicorialabs.astranovos.data.di.DataModule
import br.com.chicorialabs.astranovos.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
        }

        PresentationModule.load()
        DataModule.load()
    }
}