package com.example.petoibittlecontrol.di

import android.app.Application
import com.polidea.rxandroidble3.BuildConfig
import com.polidea.rxandroidble3.LogConstants
import com.polidea.rxandroidble3.LogOptions
import com.polidea.rxandroidble3.RxBleClient
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        setRxJavaErrorHandler()
        initKoin()
        if (BuildConfig.DEBUG) {
            //initTimber()
            initRxBleClient()
        }
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    viewModelsModule,
                    rxModule,
                    bleModule
                )
            )
        }
    }
    }

   /* private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }*/

    private fun initRxBleClient() {
        RxBleClient.updateLogOptions(
            LogOptions.Builder()
                .setLogLevel(LogConstants.INFO)
                .setMacAddressLogSetting(LogConstants.MAC_ADDRESS_FULL)
                .setUuidsLogSetting(LogConstants.UUIDS_FULL)
                .setShouldLogAttributeValues(true)
                .build()
        )
    }

    private fun setRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler(Throwable::printStackTrace)
    }