package pe.edu.idat.appmiyamovil.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import pe.edu.idat.appmiyamovil.R
import pe.edu.idat.appmiyamovil.databinding.ActivityDetallePedidoBinding
import pe.edu.idat.appmiyamovil.databinding.ItemPedidoBinding
import pe.edu.idat.appmiyamovil.view.adapters.PedidoAdapter
import pe.edu.idat.appmiyamovil.view.adapters.ProductoAdapter
import pe.edu.idat.appmiyamovil.view.adapters.ShortProdAdapter
import pe.edu.idat.appmiyamovil.viewmodel.PedidoViewModel
import pe.edu.idat.appmiyamovil.viewmodel.ProductoViewModel
import java.text.SimpleDateFormat
import java.util.*

class DetallePedidoActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetallePedidoBinding
    private lateinit var pedidoViewModel: PedidoViewModel
    private var idPedido: Int = 0

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityDetallePedidoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        idPedido = intent.getIntExtra("idPedido",0)
        pedidoViewModel = ViewModelProvider(this)
            .get(PedidoViewModel::class.java)
        binding.rvdetproductos.layoutManager = LinearLayoutManager(this)

        binding.btndetatras.setOnClickListener(this)
        binding.btndetcancelar.setOnClickListener(this)

        llenarInfoDetalle()
    }

    private fun llenarInfoDetalle() {
        pedidoViewModel.traerPedido(idPedido).observe(
            this
        ) { responseDetailPedido ->
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("es", "ES"))
            val currentdate = sdf.format(responseDetailPedido.fechaPedido)
            binding.tvfechaped.text = "Fecha Pedido: "+currentdate.toString()
            if(responseDetailPedido.fechaAtencion == null || responseDetailPedido.fechaAtencion.toString() == ""){
                binding.tvfechaatencion.text = "Fecha atención: Pendiente"
            }else{
                val sdf2 = SimpleDateFormat("dd/MM/yyyy", Locale("es", "ES"))
                val currentdate2 = sdf2.format(responseDetailPedido.fechaAtencion)
                binding.tvfechaped.text = "Fecha atención: "+currentdate2.toString()
            }
            if(responseDetailPedido.atendido){
                binding.tvatendido.text = "Atendido: SI"
                binding.btndetcancelar.isEnabled = false
            }else{
                binding.tvatendido.text = "Atendido: NO"
            }
            binding.tvdetnombre.text = "Nombre: "+responseDetailPedido.nombre
            binding.tvdetapellido.text = "Apellidos: "+responseDetailPedido.apellido
            binding.tvdetdni.text = "DNI: "+responseDetailPedido.dni


            binding.rvdetproductos.adapter =
                ShortProdAdapter(responseDetailPedido.productos)
        }
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.btndetatras -> {
                finish()
            }
            R.id.btndetcancelar -> {
                pedidoViewModel.eliminarPedido(idPedido)
                startActivity(Intent(this,HomeActivity::class.java))
                finish()
            }
        }
    }
}