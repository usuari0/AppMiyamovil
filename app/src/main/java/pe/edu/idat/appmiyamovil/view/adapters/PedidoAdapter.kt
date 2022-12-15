package pe.edu.idat.appmiyamovil.view.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pe.edu.idat.appmiyamovil.R
import pe.edu.idat.appmiyamovil.databinding.ItemPedidoBinding
import pe.edu.idat.appmiyamovil.databinding.ItemProductoBinding
import pe.edu.idat.appmiyamovil.retrofit.response.ResponsePedido
import pe.edu.idat.appmiyamovil.retrofit.response.ResponseProducto
import pe.edu.idat.appmiyamovil.view.DetallePedidoActivity
import java.text.SimpleDateFormat
import java.util.*

class PedidoAdapter(private var lstpedidos: List<ResponsePedido>)
    : RecyclerView.Adapter<PedidoAdapter.ViewHolder>() {
    private lateinit var contexto : Context

    inner class ViewHolder(val binding: ItemPedidoBinding)
        : RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        private var indice : Int = 0

        fun render(int: Int){
            indice = int
            binding.btnVerDetalle.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            when(view.id){
                R.id.btnVerDetalle -> {
                    var intent : Intent = Intent(contexto,DetallePedidoActivity::class.java)
                    intent.putExtra("idPedido",lstpedidos[indice].id)
                    contexto.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoAdapter.ViewHolder {
        val binding = ItemPedidoBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        contexto = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(lstpedidos[position]){
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("es", "ES"))
                val currentdate = sdf.format(fechaPedido)
                binding.tvfechapedido.text = currentdate.toString()
                render(position)
            }
        }
    }

    override fun getItemCount() = lstpedidos.size


}