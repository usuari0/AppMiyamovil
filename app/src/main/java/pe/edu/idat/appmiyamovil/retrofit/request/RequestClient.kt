package pe.edu.idat.appmiyamovil.retrofit.request

data class RequestClient(
    var codigo: String,
    var nombres: String,
    var apellidos: String,
    var dni: String,
    var ubicacion: String,
    var listaProductos : MutableList<RequestProducto>
)
