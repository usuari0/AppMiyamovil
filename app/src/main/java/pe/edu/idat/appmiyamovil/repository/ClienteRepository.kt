package pe.edu.idat.appmiyamovil.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import pe.edu.idat.appmiyamovil.retrofit.MiyamovilCliente
import pe.edu.idat.appmiyamovil.retrofit.request.RequestClient
import pe.edu.idat.appmiyamovil.retrofit.request.RequestConsulta
import pe.edu.idat.appmiyamovil.retrofit.response.ResponseClient
import pe.edu.idat.appmiyamovil.retrofit.response.ResponseMensaje
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClienteRepository {
    var clientResponse = MutableLiveData<ResponseClient>()
    var mensajeResponse = MutableLiveData<ResponseMensaje>()

    fun crearConsulta(requestConsulta: RequestConsulta)
            :MutableLiveData<ResponseMensaje>{
        val call: Call<ResponseMensaje> = MiyamovilCliente.retrofitService.crearConsulta(requestConsulta)
        call.enqueue(object : Callback<ResponseMensaje> {
            override fun onResponse(
                call: Call<ResponseMensaje>,
                response: Response<ResponseMensaje>
            ){
                mensajeResponse.value = response.body()
            }
            override fun onFailure(call: Call<ResponseMensaje>, t: Throwable) {
                Log.e("Error al crear",t.message.toString())
            }
        })
        return mensajeResponse
    }

    fun crearCliente(requestClient: RequestClient)
    :MutableLiveData<ResponseClient>{
        val call: Call<ResponseClient> = MiyamovilCliente.retrofitService.nuevo(requestClient)
        call.enqueue(object : Callback<ResponseClient> {
            override fun onResponse(
                call: Call<ResponseClient>,
                response: Response<ResponseClient>
            ){
                clientResponse.value = response.body()
            }
            override fun onFailure(call: Call<ResponseClient>, t: Throwable) {
                Log.e("Error al crear",t.message.toString())
            }
        })
        return clientResponse
    }
}