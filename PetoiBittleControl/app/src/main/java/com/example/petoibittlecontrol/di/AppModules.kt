package com.example.petoibittlecontrol.di
import com.example.petoibittlecontrol.scan.BleScanManager
import com.example.petoibittlecontrol.scan.RxBus
import com.example.petoibittlecontrol.util.AppRxSchedulers
import com.example.petoibittlecontrol.util.RxSchedulers
import com.polidea.rxandroidble3.RxBleClient

import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

var bleModule: Module = module{
    single { BleScanManager(get(), get(), get()) }
}

val rxModule: Module = module {
    single { AppRxSchedulers() as RxSchedulers }
    single { RxBleClient.create(androidApplication()) }
    single { RxBus() }
    factory { CompositeDisposable() }
}