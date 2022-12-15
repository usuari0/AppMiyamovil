package pe.edu.idat.appmiyamovil.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import pe.edu.idat.appmiyamovil.repository.PedidoRepository
import pe.edu.idat.appmiyamovil.retrofit.request.RequestClient
import pe.edu.idat.appmiyamovil.retrofit.response.ResponseClient
import pe.edu.idat.appmiyamovil.retrofit.response.ResponseDetailPedido
import pe.edu.idat.appmiyamovil.retrofit.response.ResponseMensaje
import pe.edu.idat.appmiyamovil.retrofit.response.ResponsePedido

class PedidoViewModel: ViewModel() {
    private var repository = PedidoRepository()
    var responseMensaje : LiveData<ResponseMensaje>

    init{
        responseMensaje = repository.mensajeResponse
    }

    fun traerPedido(idPedido : Int): LiveData<ResponseDetailPedido>{
        return repository.traerPedido(idPedido)
    }

    fun listarPedido(codigo:String): LiveData<List<ResponsePedido>>{
        return repository.listarPedido(codigo)
    }

    fun guardarPedido(requestClient: RequestClient): LiveData<ResponseMensaje>{
        return repository.crearPedido(requestClient)
    }

    fun eliminarPedido(idPedido: Int): LiveData<ResponseMensaje>{
        return repository.eliminarPedido(idPedido)
    }
}