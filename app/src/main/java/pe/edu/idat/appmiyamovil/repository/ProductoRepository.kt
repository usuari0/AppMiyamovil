package pe.edu.idat.appmiyamovil.repository

import androidx.lifecycle.MutableLiveData
import pe.edu.idat.appmiyamovil.retrofit.MiyamovilCliente
import pe.edu.idat.appmiyamovil.retrofit.response.ResponseProducto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductoRepository {
    var responseProducto = MutableLiveData<List<ResponseProducto>>()

    fun listarProductos():MutableLiveData<List<ResponseProducto>>{
        val call: Call<List<ResponseProducto>> =
            MiyamovilCliente.retrofitService.listarProducto()
        call.enqueue(object : Callback<List<ResponseProducto>> {
            override fun onResponse(
                call: Call<List<ResponseProducto>>,
                response: Response<List<ResponseProducto>>
            ) {
                responseProducto.value = response.body()
            }
            override fun onFailure(call: Call<List<ResponseProducto>>, t: Throwable) {

            }
        })
        return responseProducto
    }
}