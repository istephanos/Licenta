package com.example.petoibittlecontrol.util

import io.reactivex.rxjava3.core.Scheduler

interface RxSchedulers {

    fun androidUI(): Scheduler

    fun io(): Scheduler

    fun computation(): Scheduler

    fun network(): Scheduler

    fun immediate(): Scheduler

    fun background():Scheduler
}