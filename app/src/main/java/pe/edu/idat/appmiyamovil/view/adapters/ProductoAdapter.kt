package pe.edu.idat.appmiyamovil.view.adapters

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnKeyListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pe.edu.idat.appmiyamovil.R
import pe.edu.idat.appmiyamovil.databinding.ItemProductoBinding
import pe.edu.idat.appmiyamovil.retrofit.response.ResponseProducto

class ProductoAdapter(private var lstproductos: List<ResponseProducto>
        , private val onClickListener:(ResponseProducto, Boolean)->Unit
        , private val cambiarCantidad:(ResponseProducto, Int)->Unit)
    :RecyclerView.Adapter<ProductoAdapter.ViewHolder>(){
    private lateinit var producto: ResponseProducto
    inner class ViewHolder(val binding: ItemProductoBinding)
        : RecyclerView.ViewHolder(binding.root){
    private var flag:Boolean = true
        fun render(responseProducto: ResponseProducto
                   , onClickListener:(ResponseProducto, Boolean)->Unit
                   , cambiarCantidad:(ResponseProducto, Int)->Unit){
            producto = responseProducto
            binding.etcantidad.isEnabled = false
            itemView.setOnClickListener {
                if(binding.tvflag.text == "No añadido"){
                    binding.tvflag.text = "Añadido"
                    binding.tvflag.setTextColor(binding.tvflag.context.getResources().getColor(R.color.green))
                    binding.etcantidad.isEnabled = true
                    binding.etcantidad.setText("1")
                    onClickListener(responseProducto, true)
                }
                else{
                    binding.tvflag.text = "No añadido"
                    binding.tvflag.setTextColor(binding.tvflag.context.getResources().getColor(R.color.snackbarerror))
                    binding.etcantidad.isEnabled = false
                    onClickListener(responseProducto, false)
                    binding.etcantidad.setText("1")
                }
                binding.etcantidad.setOnKeyListener(View.OnKeyListener{v,keyCode, event ->
                    if(keyCode == KeyEvent.KEYCODE_0 || keyCode == KeyEvent.KEYCODE_1 || keyCode == KeyEvent.KEYCODE_2 || keyCode == KeyEvent.KEYCODE_3 || keyCode == KeyEvent.KEYCODE_4 || keyCode == KeyEvent.KEYCODE_5 || keyCode == KeyEvent.KEYCODE_6 || keyCode == KeyEvent.KEYCODE_7 || keyCode == KeyEvent.KEYCODE_8 || keyCode == KeyEvent.KEYCODE_9){
                        if(flag && !(keyCode == KeyEvent.KEYCODE_0 && binding.etcantidad.text.toString() == "")){
                            cambiarCantidad(responseProducto, (binding.etcantidad.text.toString()+(keyCode-7)).toInt())
                        }
                        flag = !flag
                        true
                    }
                    false
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductoBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(lstproductos[position]){
                binding.tvnomproducto.text = nombre
                binding.tvprodescripcion.text = descripcion
                binding.tvprecio.text = "S/."+precio.toString()
                binding.tvcategoria.text = categoria
                render(lstproductos[position],onClickListener,cambiarCantidad)
            }
        }
    }

    override fun getItemCount() = lstproductos.size
}