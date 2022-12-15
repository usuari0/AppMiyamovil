package pe.edu.idat.appmiyamovil.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import pe.edu.idat.appmiyamovil.repository.ProductoRepository
import pe.edu.idat.appmiyamovil.retrofit.response.ResponsePedido
import pe.edu.idat.appmiyamovil.retrofit.response.ResponseProducto
import retrofit2.Response

class ProductoViewModel: ViewModel() {
    private var repository = ProductoRepository()

    fun listarProductos(): LiveData<List<ResponseProducto>>{
        return repository.listarProductos()
    }
}