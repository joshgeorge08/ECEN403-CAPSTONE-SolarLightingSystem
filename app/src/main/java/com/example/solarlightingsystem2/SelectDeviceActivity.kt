package com.example.solarlightingsystem2

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.example.solarlightingsystem2.DeviceInfoModel

class SelectDeviceActivity : AppCompatActivity(), DeviceListAdapter.OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_device)

        // Bluetooth Setup
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        // Get List of Paired Bluetooth Device
        val pairedDevices = bluetoothAdapter.bondedDevices
        val deviceList: MutableList<DeviceInfoModel> = ArrayList()
        if (pairedDevices.isNotEmpty()) {
            // There are paired devices. Get the name and address of each paired device.
            for (device in pairedDevices) {
                val deviceName = device.name
                val deviceHardwareAddress = device.address // MAC address
                val deviceInfoModel = DeviceInfoModel(deviceName, deviceHardwareAddress)
                deviceList.add(deviceInfoModel)
            }
            // Display paired devices using RecyclerView
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewDevice)
            recyclerView.layoutManager = LinearLayoutManager(this)
            val deviceListAdapter = DeviceListAdapter(this, deviceList, this)
            recyclerView.adapter = deviceListAdapter
            recyclerView.itemAnimator = DefaultItemAnimator()
        } else {
            // If no paired devices found, show a Snackbar
            val view = findViewById<View>(R.id.recyclerViewDevice)
            val snackbar = Snackbar.make(
                view,
                "Activate Bluetooth or pair a Bluetooth device",
                Snackbar.LENGTH_INDEFINITE
            )
            snackbar.setAction("OK") { }
            snackbar.show()
        }
    }

    override fun onItemClick(deviceInfo: DeviceInfoModel) {
        // Handle item click - Start MainActivity and pass selected device info
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("deviceName", deviceInfo.name)
        intent.putExtra("deviceAddress", deviceInfo.address)
        startActivity(intent)
    }
}
