package com.example.solarlightingsystem2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Switch
import android.widget.TextView
import android.os.Handler
import android.content.Intent

class LightingPrefActivity : AppCompatActivity() {
    private lateinit var bluetoothService: BluetoothService

    private lateinit var textViewInfo: TextView
    private lateinit var voltageInfo: TextView
    private lateinit var buttonToggle: Button
    private lateinit var buttonToggle2: Button
    private lateinit var switchControl: Switch
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lighting_pref)

        val backToMainButton = findViewById<Button>(R.id.backToMainButton)
        backToMainButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Initialize BluetoothService
        bluetoothService = BluetoothService(Handler())

        // Initialize UI elements
        textViewInfo = findViewById(R.id.textViewInfo)
        voltageInfo = findViewById(R.id.voltageInfo)
        buttonToggle = findViewById(R.id.buttonToggle)
        buttonToggle2 = findViewById(R.id.buttonToggle2)
        switchControl = findViewById(R.id.switchControl)
        imageView = findViewById(R.id.imageView)

        if (!bluetoothService.isConnected()) {
            // Disable buttons and switch if no Bluetooth device is connected
            buttonToggle.isEnabled = false
            buttonToggle2.isEnabled = false
            switchControl.isEnabled = false
        }
        // Set up onClick listeners
        buttonToggle.setOnClickListener {
            toggleLED()
        }

        buttonToggle2.setOnClickListener {
            toggleLED2()
        }

        switchControl.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                switchToMotionMode()
            } else {
                switchToManualMode()
            }
        }
    }

    private fun toggleLED() {
        val currentState = buttonToggle.text.toString().lowercase()
        val message = if (currentState == "turn on") "<turn on>" else "<turn off>"
        bluetoothService.sendMessage(message)
    }

    private fun toggleLED2() {
        val currentState = buttonToggle2.text.toString().lowercase()
        val message = if (currentState == "turn on") "<turn on2>" else "<turn off2>"
        bluetoothService.sendMessage(message)
    }

    private fun switchToMotionMode() {
        buttonToggle.isEnabled = false
        buttonToggle2.isEnabled = false
        val message = "<motion>"
        bluetoothService.sendMessage(message)
    }

    private fun switchToManualMode() {
        buttonToggle.isEnabled = true
        buttonToggle2.isEnabled = true
        val message = "<manual>"
        bluetoothService.sendMessage(message)
    }
}
