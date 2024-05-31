package com.example.petoibittlecontrol.mainController

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.petoibittlecontrol.R
import com.example.petoibittlecontrol.databinding.ItemDeviceBinding
import com.example.petoibittlecontrol.scan.model.DeviceModel

class DeviceAdapter(
    private var devices: List<DeviceModel>,
    private val clickListener: (DeviceModel) -> Unit
) : RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    class DeviceViewHolder(val binding: ItemDeviceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemDeviceBinding>(
            inflater, R.layout.item_device, parent, false
        )
        return DeviceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val device = devices[position]
        holder.binding.device = device
        holder.binding.root.setOnClickListener {
            clickListener(device)
        }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = devices.size

    fun updateDevices(newDevices: List<DeviceModel>) {
        devices = newDevices
        notifyDataSetChanged()
    }
}
