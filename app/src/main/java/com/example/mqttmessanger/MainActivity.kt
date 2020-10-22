package com.example.mqttmessanger

import android.content.Context
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.MqttMessage

class MainActivity : AppCompatActivity()
{
   // val MqttClient =  MqttClient(this)
    private lateinit var mqttClient2: MqttClient2
    private lateinit var mqttClientLast: MqttLast
      val mqttClient1= MqttClient(this)

    val topic = "leeorc/f/sharira46"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnSend = findViewById(R.id.btnSend) as Button
        GetTextBox(R.id.txtTopic).setText(topic)
        LoadmqttClientLast()

        btnSend.setOnClickListener() {
            SendRequest()


        }
    }

    fun SendRequest() {

        //mqttClient1.publishMessage(topics[0],"sssfdsdfsdsfd")

        mqttClientLast.publish(GetTextBox(R.id.txtTopic).text.toString(),GetTextBox(R.id.txtMessage).text.toString())

    }


    fun GetTextBox(ctrlId:Int): EditText {
        val textBox: EditText = findViewById(ctrlId)
        return textBox
    }


    fun LoadmqttClientLast()
    {

      mqttClientLast = MqttLast()
        mqttClientLast.connect(this)
        //Thread.sleep(10000)

    }

}




