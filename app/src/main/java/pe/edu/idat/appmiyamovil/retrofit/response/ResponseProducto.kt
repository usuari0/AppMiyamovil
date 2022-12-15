package pe.edu.idat.appmiyamovil.retrofit.response

data class ResponseProducto (
    var id:Int,
    var nombre:String,
    var descripcion:String,
    var precio:Double,
    var imagen:String,
    var categoria:String
)