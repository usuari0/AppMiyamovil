package pe.edu.idat.appmiyamovil.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import pe.edu.idat.appmiyamovil.repository.ClienteRepository
import pe.edu.idat.appmiyamovil.retrofit.request.RequestClient
import pe.edu.idat.appmiyamovil.retrofit.request.RequestConsulta
import pe.edu.idat.appmiyamovil.retrofit.response.ResponseClient
import pe.edu.idat.appmiyamovil.retrofit.response.ResponseMensaje

class ClienteViewModel:ViewModel() {
    var responseClient : LiveData<ResponseClient>
    private var repository = ClienteRepository()

    init{
        responseClient = repository.clientResponse
    }

    fun crearConsulta(requestConsulta: RequestConsulta): LiveData<ResponseMensaje>{
        return repository.crearConsulta(requestConsulta)
    }

    /*fun crearCliente(nombres:String, apellidos:String, dni:String, cantidad:Int, ubicacion:String){
        responseClient = repository.crearCliente(
            RequestClient(nombres, apellidos, dni, cantidad, ubicacion)
        )
    }*/
}