package id.co.bigtek.button_antrian.network

import retrofit2.Call
import retrofit2.http.GET

interface ApiServices {
    companion object {
        val URL = "http://192.168.2.102/antrian/button/"
        val WS_URL = "ws://192.168.2.102:8080"
    }

    @GET("api?val=antrian_1")
    fun sisaAntrian(): Call<String>

    @GET("api?val=antrian_2")
    fun sisaAntrian2(): Call<String>

    @GET("api?val=antrian_3")
    fun sisaAntrian3(): Call<String>

    @GET("api?val=antrian_4")
    fun sisaAntrian4(): Call<String>
}