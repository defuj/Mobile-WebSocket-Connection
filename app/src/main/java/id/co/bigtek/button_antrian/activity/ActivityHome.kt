package id.co.bigtek.button_antrian.activity

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import id.co.bigtek.button_antrian.R
import id.co.bigtek.button_antrian.network.ApiServices
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

class ActivityHome : AppCompatActivity() {
    private var btnReset : LinearLayout? = null
    private var btnLoket1 : LinearLayout? = null
    private var btnLoket2 : LinearLayout? = null
    private var btnLoket3 : LinearLayout? = null
    private var btnLoket4 : LinearLayout? = null
    private var mWebSocketClient : WebSocketClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initial()
    }

    private fun initial() {
        btnReset = findViewById(R.id.layoutBtnReset)
        btnLoket1 = findViewById(R.id.layoutBtnLoket1)
        btnLoket2 = findViewById(R.id.layoutBtnLoket2)
        btnLoket3 = findViewById(R.id.layoutBtnLoket3)
        btnLoket4 = findViewById(R.id.layoutBtnLoket4)

        runFunction()
    }

    private fun runFunction() {
        btnLoket1!!.setOnClickListener {
            val intent = Intent(this,ActivityLoket::class.java)
            intent.putExtra("loket","A")
            intent.putExtra("no_loket","1")
            startActivity(intent)
        }

        btnLoket2!!.setOnClickListener {
            val intent = Intent(this,ActivityLoket::class.java)
            intent.putExtra("loket","B")
            intent.putExtra("no_loket","2")
            startActivity(intent)
        }

        btnLoket3!!.setOnClickListener {
            val intent = Intent(this,ActivityLoket::class.java)
            intent.putExtra("loket","C")
            intent.putExtra("no_loket","3")
            startActivity(intent)
        }

        btnLoket4!!.setOnClickListener {
            val intent = Intent(this,ActivityLoket::class.java)
            intent.putExtra("loket","D")
            intent.putExtra("no_loket","4")
            startActivity(intent)
        }

        btnReset!!.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Reset Antrian?")
            builder.setMessage("Apakah Anda yakin akan mereset semua antrian untuk setiap loket?")
            builder.setPositiveButton("Ya"){ _, _ ->
                reset()
            }

            builder.setNeutralButton("Batalkan"){ _, _ ->
                Toast.makeText(applicationContext,"Reset Dibatalkan.",Toast.LENGTH_SHORT).show()
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    private fun reset() {
        val uri = URI(ApiServices.WS_URL)
        mWebSocketClient = object : WebSocketClient(uri){
            override fun onOpen(handshakedata: ServerHandshake?) {
                mWebSocketClient!!.send("reset")
                mWebSocketClient!!.close()
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {

            }

            override fun onMessage(message: String?) {

            }

            override fun onError(ex: Exception?) {

            }
        }
        mWebSocketClient!!.connect()
        Toast.makeText(this,"Antrian telah diatur ulang.",Toast.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Keluar dari Aplikasi?")
        builder.setMessage("Apakah Anda ingin meninggalkan Aplikasi ini?")
        builder.setPositiveButton("Ya"){ _, _ ->
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        builder.setNeutralButton("Batalkan"){ _, _ ->

        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}
