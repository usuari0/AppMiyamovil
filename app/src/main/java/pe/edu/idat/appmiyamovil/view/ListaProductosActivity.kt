package pe.edu.idat.appmiyamovil.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import pe.edu.idat.appmiyamovil.databinding.ActivityListaProductosBinding
import pe.edu.idat.appmiyamovil.retrofit.response.ResponseProducto
import pe.edu.idat.appmiyamovil.view.adapters.ProductoAdapter
import pe.edu.idat.appmiyamovil.viewmodel.ProductoViewModel
import com.google.android.gms.location.*
import pe.edu.idat.appmiyamovil.R
import pe.edu.idat.appmiyamovil.retrofit.request.RequestClient
import pe.edu.idat.appmiyamovil.retrofit.request.RequestProducto
import pe.edu.idat.appmiyamovil.viewmodel.PedidoViewModel

class ListaProductosActivity : AppCompatActivity(), /*OnMapReadyCallback,*/View.OnClickListener  {

    private lateinit var binding: ActivityListaProductosBinding
    private lateinit var productoViewModel: ProductoViewModel
    private lateinit var listProducto : MutableList<ResponseProducto>
    private lateinit var listProdCant : MutableList<Int>
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var settings: SharedPreferences
    private lateinit var  pedidoViewModel: PedidoViewModel

    //variables para el GPS
    private val SOLICITAR_ACCESS_FINE_LOCATION = 1000
    private var haConcedidoPermisos = false
    private var ubicacion:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        println(intent.getStringExtra("nombre").toString()!!)

        settings = getSharedPreferences("MIYAMOVIL_STT", Context.MODE_PRIVATE)
        binding.rvproductos.layoutManager = LinearLayoutManager(this)
        productoViewModel = ViewModelProvider(this)
            .get(ProductoViewModel::class.java)
        binding.btnPedEnviar.setOnClickListener(this)

        listProducto = mutableListOf()
        listProdCant = mutableListOf()
        listarProductos()
    }

    private fun tienePermisos(permisos: Array<String>): Boolean {
        return permisos.all {
            return ContextCompat.checkSelfPermission(
                this,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun solicitarPermisos(permisos: Array<String>) {

        println("1")
        requestPermissions(
            permisos,
            SOLICITAR_ACCESS_FINE_LOCATION
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun verificarPermisos() {
        val permisos = arrayListOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permisos.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
        val permisosComoArray = permisos.toTypedArray()
        if (tienePermisos(permisosComoArray)) {
            haConcedidoPermisos = true
            GPSfinal()
        } else {
            solicitarPermisos(permisosComoArray)
        }
    }

    fun GPSfinal(){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener {
            if (it != null) {
                ubicacion = it.toString()
                println(ubicacion)
                enviarPedido()
            } else {
                println("No se pudo obtener la ubicación")
            }
        }
    }

    fun listarProductos(){
        productoViewModel.listarProductos().observe(
            this
        ) { responseProducto ->
            binding.rvproductos.adapter =
                ProductoAdapter(responseProducto, { ResponseProducto, Boolean ->
                    onItemSelected(
                        ResponseProducto, Boolean
                    )
                },{ ResponseProducto, Int ->
                    cambiarCantidad(
                        ResponseProducto, Int
                    )
                })
        }
    }

    fun onItemSelected(responseProducto: ResponseProducto, añadir:Boolean){
        if(añadir){
            listProducto.add(responseProducto)
            listProdCant.add(0)
            println(responseProducto.nombre+" 1")
        }else{
            listProducto.remove(responseProducto)
            listProdCant.remove(listProducto.indexOf(responseProducto))
            println(responseProducto.nombre+" eliminado")
        }
    }

    private fun cambiarCantidad(responseProducto: ResponseProducto, int: Int) {
        listProdCant[listProducto.indexOf(responseProducto)] = int
        println(responseProducto.nombre+" "+int)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(view: View) {
        when(view.id){
            R.id.btnPedEnviar -> {
                verificarPermisos()
                /*if(haConcedidoPermisos){
                    enviarPedido()
                }*/
            }
        }
    }

    private fun enviarPedido() {
        println(ubicacion)
        var lista : MutableList<RequestProducto> = mutableListOf()
        for (prod : ResponseProducto in listProducto){
            var producto : RequestProducto = RequestProducto(prod.id, listProdCant[listProducto.indexOf(prod)])
            lista.add(producto)
        }
        println(ubicacion)
        var pedido : RequestClient = RequestClient(
            settings.getString("COD_CLIENT","NO")!!,
            intent.getStringExtra("nombre").toString()!!,
            intent.getStringExtra("apellido").toString()!!,
            intent.getStringExtra("dni").toString()!!,
            ubicacion,
            lista
        )
        println(ubicacion)
        pedidoViewModel = ViewModelProvider(this)
            .get(PedidoViewModel::class.java)
        pedidoViewModel.responseMensaje.observe(this, Observer{
            println(this)
        })

        pedidoViewModel.guardarPedido(pedido)

        //saltando a otro activity
        var intent : Intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}