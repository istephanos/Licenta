package com.example.petoibittlecontrol.di

import com.example.petoibittlecontrol.mainController.MainControllerViewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val viewModelsModule: Module = module {
    viewModel { MainControllerViewModel( get()) }
}