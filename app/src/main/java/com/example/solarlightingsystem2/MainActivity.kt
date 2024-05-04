package com.example.solarlightingsystem2

import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    private lateinit var bluetoothService: BluetoothService
    private val deviceName: String? = null
    private val deviceAddress: String? = null
    var handler: Handler? = null
    var mmSocket: BluetoothSocket? = null
    var connectedThread: ConnectedThread? = null
    var createConnectThread: com.example.solarlightingsystem2.MainActivity.CreateConnectThread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        bluetoothService = BluetoothService(Handler())

        val buttonConnect = findViewById<Button>(R.id.buttonConnect)
        val buttonLightingPref = findViewById<Button>(R.id.button_lighting_pref)



        buttonConnect.setOnClickListener {
            // Move to adapter list
            val intent = Intent(this@MainActivity, SelectDeviceActivity::class.java)
            startActivity(intent)
        }

        buttonLightingPref.setOnClickListener {
            // Move to LightingPrefActivity
            val intent = Intent(this@MainActivity, LightingPrefActivity::class.java)
            startActivity(intent)
        }
    }
}