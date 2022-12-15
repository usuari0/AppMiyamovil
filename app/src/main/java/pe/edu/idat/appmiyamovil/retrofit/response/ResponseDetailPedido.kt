package pe.edu.idat.appmiyamovil.retrofit.response

import java.util.Date

data class ResponseDetailPedido(
    var id:Int,
    var fechaPedido: Date,
    var fechaAtencion: Date,
    var atendido: Boolean,
    var nombre: String,
    var apellido: String,
    var dni: String,
    var productos: MutableList<DetailProducto>
)
