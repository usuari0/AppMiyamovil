package pe.edu.idat.appmiyamovil.view.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pe.edu.idat.appmiyamovil.R
import pe.edu.idat.appmiyamovil.databinding.ItemPedidoBinding
import pe.edu.idat.appmiyamovil.databinding.ItemProdetailBinding
import pe.edu.idat.appmiyamovil.retrofit.response.DetailProducto
import pe.edu.idat.appmiyamovil.retrofit.response.ResponseDetailPedido
import pe.edu.idat.appmiyamovil.retrofit.response.ResponseProducto
import pe.edu.idat.appmiyamovil.view.DetallePedidoActivity

class ShortProdAdapter(private var lstproductos: List<DetailProducto>)
    : RecyclerView.Adapter<ShortProdAdapter.ViewHolder>() {

    private lateinit var contexto : Context
    private var indice : Int = 0

    inner class ViewHolder(val binding: ItemProdetailBinding)
        : RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        fun render(){

        }

        override fun onClick(view: View) {
            when(view.id){
                R.id.btnVerDetalle -> {

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortProdAdapter.ViewHolder {
        val binding = ItemProdetailBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        contexto = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShortProdAdapter.ViewHolder, position: Int) {
        with(holder) {
            with(lstproductos[position]) {
                binding.tvdetailnombre.text = nombre
                binding.tvdetailprecio.text = "Precio: S/."+precio.toString()
                binding.tvdetailcantidad.text = "Cantidod: "+cantidad.toString()
                binding.tvdetailtotal.text = "Total: S/."+(precio*cantidad).toString()
                indice = position.toString().toInt()
                render()
            }
        }
    }

    override fun getItemCount() = lstproductos.size
}