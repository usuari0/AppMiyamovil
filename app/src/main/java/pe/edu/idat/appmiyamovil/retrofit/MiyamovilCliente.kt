package pe.edu.idat.appmiyamovil.retrofit

import okhttp3.OkHttpClient
import pe.edu.idat.appmiyamovil.utilitarios.Constantes
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object MiyamovilCliente {

    private var okHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

    private fun buildRetrofit() = Retrofit.Builder()
        .baseUrl(Constantes().API_MIYAMOVIL_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitService: MiyamovilServicio by lazy {
        buildRetrofit().create(MiyamovilServicio::class.java)
    }
}