package com.example.petoibittlecontrol.mainController

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.petoibittlecontrol.R
import com.example.petoibittlecontrol.databinding.ActivityMainControllerBinding

class MainControllerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainControllerBinding
    private val viewModel : MainControllerViewModel = MainControllerViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
    }

    private fun initBinding(){
    binding =DataBindingUtil.setContentView(this,R.layout.activity_main_controller)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}