package com.example.solarlightingsystem2

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Handler
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*

class BluetoothService(private val handler: Handler) {
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var bluetoothSocket: BluetoothSocket? = null
    private var inputStream: InputStream? = null
    private var outputStream: OutputStream? = null

    companion object {
        private const val TAG = "BluetoothService"
        const val CONNECTING_STATUS = 1
        const val MESSAGE_READ = 2
    }

    init {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    }

    fun connectToDevice(deviceAddress: String) {
        val device: BluetoothDevice? = bluetoothAdapter?.getRemoteDevice(deviceAddress)
        val uuid: UUID = device?.uuids?.get(0)?.uuid ?: return

        try {
            bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid)
            bluetoothSocket?.connect()
            handler.obtainMessage(CONNECTING_STATUS, 1, -1).sendToTarget()
            inputStream = bluetoothSocket?.inputStream
            outputStream = bluetoothSocket?.outputStream
            ConnectedThread(inputStream).start()
        } catch (e: IOException) {
            Log.e(TAG, "Could not connect to device: ${e.message}")
            handler.obtainMessage(CONNECTING_STATUS, -1, -1).sendToTarget()
        }
    }

    fun sendMessage(message: String) {
        val bytes = message.toByteArray()
        try {
            outputStream?.write(bytes)
        } catch (e: IOException) {
            Log.e(TAG, "Error occurred while sending message: ${e.message}")
        }
    }

    fun disconnect() {
        try {
            bluetoothSocket?.close()
            inputStream?.close()
            outputStream?.close()
        } catch (e: IOException) {
            Log.e(TAG, "Error occurred while disconnecting: ${e.message}")
        }
    }

    fun isConnected(): Boolean {
        return bluetoothSocket != null && bluetoothSocket!!.isConnected
    }

    inner class ConnectedThread(private val mmInStream: InputStream?) : Thread() {
        private val mmBuffer: ByteArray = ByteArray(1024)

        override fun run() {
            var numBytes: Int
            while (true) {
                try {
                    numBytes = mmInStream?.read(mmBuffer) ?: return
                    val readMessage = String(mmBuffer, 0, numBytes)
                    handler.obtainMessage(MESSAGE_READ, readMessage).sendToTarget()
                } catch (e: IOException) {
                    Log.e(TAG, "Input stream was disconnected: ${e.message}")
                    break
                }
            }
        }
    }
}
