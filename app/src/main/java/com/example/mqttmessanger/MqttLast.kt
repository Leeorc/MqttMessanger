package com.example.mqttmessanger


import android.content.Context
import android.util.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken

import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage

import org.eclipse.paho.client.mqttv3.*

class MqttLast {

    private lateinit var mqttClient: MqttAndroidClient
    // TAG
    companion object {
        const val TAG = "AndroidMqttClient"
    }

    fun connect(context: Context) {
        val serverURI = "tcp://broker.emqx.io:1883"
      //  val serverURI = "tcp://io.adafruit.com:1883"
        val userName = "leeorc"
        val pwd      = "aio_MPaK82bhzPeAMVmlso7tQDxr9SCI"

        mqttClient = MqttAndroidClient(context, serverURI, "kotlin_client")
        mqttClient.setCallback(object : MqttCallback {
                override fun messageArrived(topic: String?, message: MqttMessage?) {
                Log.d(TAG, "Receive message: ${message.toString()} from topic: $topic")
            }

            override fun connectionLost(cause: Throwable?) {
                Log.d(TAG, "Connection lost ${cause.toString()}")
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                Log.d(TAG, "deliveryComplete ")
            }
        })
        val options = MqttConnectOptions()
        try {
            Log.d(TAG, "Connecting: " + mqttClient.serverURI)
            options.connectionTimeout = 120
           // options.userName = userName
          //  options.password = pwd.toCharArray()
            mqttClient.connect(options, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.i(TAG, "Connection success")
                    //mqttClient.subscribe("/LeeorTest/sharira/test",1);
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.e(TAG, "Connection failure")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }

    }

    fun subscribe(topic: String, qos: Int = 1) {
        try {
            mqttClient.subscribe(topic, qos, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(TAG, "Subscribed to $topic")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.e(TAG, "Failed to subscribe $topic")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun publish(topic: String, msg: String, qos: Int = 1, retained: Boolean = false) {
        try {
            val message = MqttMessage()
            message.payload = msg.toByteArray()
            message.qos = qos
            message.isRetained = retained
            mqttClient.publish(topic, message, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(TAG, "$msg published to $topic")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(TAG, "Failed to publish $msg to $topic")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }
    fun disconnect() {
        try {
            mqttClient.disconnect(null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(TAG, "Disconnected")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(TAG, "Failed to disconnect")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }
}