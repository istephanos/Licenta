package com.example.petoibittlecontrol

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.petoibittlecontrol.databinding.ActivityMainControllerBinding

private const val EXTRA_MAC_ADDRESS = "extra_mac_address"

class DeviceActivity : AppCompatActivity() {

    /*private lateinit var binding: ActivityMainControllerBinding
    companion object {
        fun newInstance(context: Context, macAddress: String): Intent =
            Intent(context, DeviceActivity::class.java).apply { putExtra(EXTRA_MAC_ADDRESS, macAddress) }
    }

    private lateinit var macAddress: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device)

        macAddress = intent.getStringExtra(EXTRA_MAC_ADDRESS)!!
        supportActionBar!!.subtitle = getString(R.string.mac_address, macAddress)

        connect.setOnClickListener { startActivity(ConnectionExampleActivity.newInstance(this, macAddress)) }
        discovery.setOnClickListener { startActivity(ServiceDiscoveryExampleActivity.newInstance(this, macAddress)) }
    }*/
}