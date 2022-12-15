package pe.edu.idat.appmiyamovil.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import pe.edu.idat.appmiyamovil.retrofit.MiyamovilCliente
import pe.edu.idat.appmiyamovil.retrofit.request.RequestClient
import pe.edu.idat.appmiyamovil.retrofit.response.ResponseClient
import pe.edu.idat.appmiyamovil.retrofit.response.ResponseDetailPedido
import pe.edu.idat.appmiyamovil.retrofit.response.ResponseMensaje
import pe.edu.idat.appmiyamovil.retrofit.response.ResponsePedido
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PedidoRepository {
    var pedidoResponse = MutableLiveData<List<ResponsePedido>>()
    var mensajeResponse = MutableLiveData<ResponseMensaje>()
    var pedidoDetailResponse = MutableLiveData<ResponseDetailPedido>()

    fun eliminarPedido(idPedido: Int)
            : MutableLiveData<ResponseMensaje> {
        val call: Call<ResponseMensaje> = MiyamovilCliente.retrofitService.eliminar(idPedido)

        call.enqueue(object : Callback<ResponseMensaje> {
            override fun onResponse(
                call: Call<ResponseMensaje>,
                response: Response<ResponseMensaje>
            ){
                mensajeResponse.value = response.body()
            }

            override fun onFailure(call: Call<ResponseMensaje>, t: Throwable) {
                Log.e("Error al eliminar",t.message.toString())
            }
        })
        return mensajeResponse
    }

    fun traerPedido(idPedido: Int)
            : MutableLiveData<ResponseDetailPedido> {
        val call: Call<ResponseDetailPedido> = MiyamovilCliente.retrofitService.traerPedido(idPedido)

        call.enqueue(object : Callback<ResponseDetailPedido> {
            override fun onResponse(
                call: Call<ResponseDetailPedido>,
                response: Response<ResponseDetailPedido>
            ){
                pedidoDetailResponse.value = response.body()
            }

            override fun onFailure(call: Call<ResponseDetailPedido>, t: Throwable) {
                Log.e("Error al traer",t.message.toString())
            }
        })
        return pedidoDetailResponse
    }

    fun listarPedido(codigo: String)
            : MutableLiveData<List<ResponsePedido>> {
        val call: Call<List<ResponsePedido>> = MiyamovilCliente.retrofitService.listarPedido(codigo)

        call.enqueue(object : Callback<List<ResponsePedido>> {
            override fun onResponse(
                call: Call<List<ResponsePedido>>,
                response: Response<List<ResponsePedido>>
            ){
                pedidoResponse.value = response.body()
            }

            override fun onFailure(call: Call<List<ResponsePedido>>, t: Throwable) {
                Log.e("Error al listar",t.message.toString())
            }
        })
        return pedidoResponse
    }

    fun crearPedido(requestClient: RequestClient)
            :MutableLiveData<ResponseMensaje>{
        val call: Call<ResponseMensaje> = MiyamovilCliente.retrofitService.guardarPedido(requestClient)
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
}