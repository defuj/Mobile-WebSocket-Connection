package id.co.bigtek.button_antrian.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import id.co.bigtek.button_antrian.R
import id.co.bigtek.button_antrian.network.ApiServices
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.URI

class ActivityLoket : AppCompatActivity() {
    private var txtLokets : TextView? = null
    private var btnPanggil : LinearLayout? = null
    private var btnPanggilUlang : LinearLayout? = null
    private var sisaAntrian : TextView? = null
    private var back : ImageButton? = null
    private var loket : String? = null
    private var noLoket : String? = null
    private var mWebSocketClient : WebSocketClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loket)

        initial()
    }

    private fun initial() {
        back = findViewById(R.id.btnBack)
        txtLokets = findViewById(R.id.txtLoket)
        btnPanggil = findViewById(R.id.layoutBtnPanggil)
        btnPanggilUlang = findViewById(R.id.layoutBtnPanggilUlang)
        sisaAntrian = findViewById(R.id.txtSisaAntrian)

        runFunction()
    }

    @SuppressLint("SetTextI18n")
    private fun runFunction() {
        back!!.setOnClickListener {
            finish()
        }

        loket = intent.getStringExtra("loket")
        noLoket = intent.getStringExtra("no_loket")
        txtLokets!!.text = "Tombol Antrian - Loket $loket"

        btnPanggil!!.setOnClickListener {
            call()
            btnPanggil!!.isEnabled = false
            btnPanggil!!.isEnabled = false
            object : CountDownTimer(2000,1000){
                override fun onFinish() {
                    btnPanggil!!.isEnabled = true
                    btnPanggil!!.isEnabled = true
                }

                override fun onTick(millisUntilFinished: Long) {

                }

            }.start()
        }

        btnPanggilUlang!!.setOnClickListener {
            recall()
            btnPanggilUlang!!.isEnabled = false
            btnPanggilUlang!!.isEnabled = false
            object : CountDownTimer(2000,1000){
                override fun onFinish() {
                    btnPanggilUlang!!.isEnabled = true
                    btnPanggilUlang!!.isEnabled = true
                }

                override fun onTick(millisUntilFinished: Long) {

                }

            }.start()
        }
        sisaAntrian()

        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                mainHandler.postDelayed(this, 2000)
                sisaAntrian()
            }
        })
    }

    private fun call() {
        val uri = URI(ApiServices.WS_URL)
        mWebSocketClient = object : WebSocketClient(uri){
            override fun onOpen(handshakedata: ServerHandshake?) {
                mWebSocketClient!!.send("call$noLoket")
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
    }

    private fun recall() {
        val uri = URI(ApiServices.WS_URL)
        mWebSocketClient = object : WebSocketClient(uri){
            override fun onOpen(handshakedata: ServerHandshake?) {
                mWebSocketClient!!.send("recall$noLoket")
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
        sisaAntrian()
    }

    private fun sisaAntrian() {
        when (noLoket) {
            "1" -> antrian1()
            "2" -> antrian2()
            "3" -> antrian3()
            else -> antrian4()
        }
    }

    private fun antrian1() {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiServices.URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val api = retrofit.create(ApiServices::class.java)
        val call = api.sisaAntrian()
        call.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@ActivityLoket,"Jeringan Bermasalah, Gagal Mendapatkan Data.",
                    Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<String>, response: retrofit2.Response<String>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        val jsonObject = JSONObject(response.body()!!)
                        sisaAntrian!!.text = jsonObject.optString("sisa")
                    }else{
                        Toast.makeText(this@ActivityLoket,"Tidak ada data yang didapatkan.", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this@ActivityLoket,"Gagal Mendapatkan Data.", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun antrian2() {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiServices.URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val api = retrofit.create(ApiServices::class.java)
        val call = api.sisaAntrian2()
        call.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@ActivityLoket,"Jeringan Bermasalah, Gagal Mendapatkan Data.",
                    Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<String>, response: retrofit2.Response<String>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        val jsonObject = JSONObject(response.body()!!)
                        sisaAntrian!!.text = jsonObject.optString("sisa")
                    }else{
                        Toast.makeText(this@ActivityLoket,"Tidak ada data yang didapatkan.", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this@ActivityLoket,"Gagal Mendapatkan Data.", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun antrian3() {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiServices.URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val api = retrofit.create(ApiServices::class.java)
        val call = api.sisaAntrian3()
        call.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@ActivityLoket,"Jeringan Bermasalah, Gagal Mendapatkan Data.",
                    Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<String>, response: retrofit2.Response<String>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        val jsonObject = JSONObject(response.body()!!)
                        sisaAntrian!!.text = jsonObject.optString("sisa")
                    }else{
                        Toast.makeText(this@ActivityLoket,"Tidak ada data yang didapatkan.", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this@ActivityLoket,"Gagal Mendapatkan Data.", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun antrian4() {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiServices.URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val api = retrofit.create(ApiServices::class.java)
        val call = api.sisaAntrian4()
        call.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(this@ActivityLoket,"Jeringan Bermasalah, Gagal Mendapatkan Data.",
                    Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<String>, response: retrofit2.Response<String>) {
                if(response.isSuccessful){
                    if(response.body() != null){
                        val jsonObject = JSONObject(response.body()!!)
                        sisaAntrian!!.text = jsonObject.optString("sisa")
                    }else{
                        Toast.makeText(this@ActivityLoket,"Tidak ada data yang didapatkan.", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this@ActivityLoket,"Gagal Mendapatkan Data.", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
}
