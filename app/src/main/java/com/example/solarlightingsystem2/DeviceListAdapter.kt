package com.example.solarlightingsystem2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DeviceListAdapter(
    private val context: Context,
    private val deviceList: List<DeviceInfoModel>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<DeviceListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(deviceInfo: DeviceInfoModel)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName: TextView = itemView.findViewById(R.id.textViewDeviceName)
        val textAddress: TextView = itemView.findViewById(R.id.textViewDeviceAddress)
        val linearLayout: LinearLayout = itemView.findViewById(R.id.linearLayoutDeviceInfo)

        init {
            linearLayout.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val deviceInfo = deviceList[position]
                    itemClickListener.onItemClick(deviceInfo)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.device_info_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val deviceInfoModel = deviceList[position]
        holder.textName.text = deviceInfoModel.name
        holder.textAddress.text = deviceInfoModel.address
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }
}
