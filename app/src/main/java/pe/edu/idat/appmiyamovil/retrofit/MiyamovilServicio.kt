package pe.edu.idat.appmiyamovil.retrofit

import pe.edu.idat.appmiyamovil.retrofit.request.RequestClient
import pe.edu.idat.appmiyamovil.retrofit.request.RequestConsulta
import pe.edu.idat.appmiyamovil.retrofit.response.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MiyamovilServicio {

    @POST("clientes")
    fun nuevo(@Body requestcliente: RequestClient): Call<ResponseClient>

    @POST("consultas")
    fun crearConsulta(@Body requestConsulta: RequestConsulta): Call<ResponseMensaje>

    @GET("pedidos/forclient/{cod}")
    fun listarPedido(@Path("cod") codigo:String):Call<List<ResponsePedido>>

    @GET("productos")
    fun listarProducto():Call<List<ResponseProducto>>

    @POST("pedidos")
    fun guardarPedido(@Body requestcliente: RequestClient):Call<ResponseMensaje>

    @GET("pedidos/{id}")
    fun traerPedido(@Path("id") idPedido:Int):Call<ResponseDetailPedido>

    @DELETE("pedidos/{id}")
    fun eliminar(@Path("id") idPedido: Int):Call<ResponseMensaje>
}