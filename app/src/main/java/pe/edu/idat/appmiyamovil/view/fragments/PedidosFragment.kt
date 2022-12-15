package pe.edu.idat.appmiyamovil.view.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import pe.edu.idat.appmiyamovil.R
import pe.edu.idat.appmiyamovil.databinding.FragmentPedidosBinding
import pe.edu.idat.appmiyamovil.retrofit.response.ResponsePedido
import pe.edu.idat.appmiyamovil.utilitarios.AppMensaje
import pe.edu.idat.appmiyamovil.utilitarios.MiApp.Companion.applicationContext
import pe.edu.idat.appmiyamovil.utilitarios.TipoMensaje
import pe.edu.idat.appmiyamovil.view.PedClienteActivity
import pe.edu.idat.appmiyamovil.view.adapters.PedidoAdapter
import pe.edu.idat.appmiyamovil.viewmodel.PedidoViewModel

class PedidosFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentPedidosBinding? = null
    private val binding get() = _binding!!
    private lateinit var pedidoViewModel: PedidoViewModel
    private lateinit var settings: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPedidosBinding.inflate(inflater, container, false)
        pedidoViewModel = ViewModelProvider(requireActivity())
            .get(PedidoViewModel::class.java)
        settings = container!!.context.getSharedPreferences("MIYAMOVIL_STT", Context.MODE_PRIVATE)
        binding.rvpedidos.layoutManager = LinearLayoutManager(requireActivity())
        listarPedido()

        binding.fabnuevoped.setOnClickListener(this);
        return binding.root
    }

    override fun onClick(vista: View) {
        when(vista.id){
            R.id.fabnuevoped-> startActivity(
                Intent(applicationContext, PedClienteActivity::class.java)
            )
        }
    }

    fun listarPedido(){
        pedidoViewModel.listarPedido(settings.getString("COD_CLIENT","NO")!!).observe(
            viewLifecycleOwner, Observer {
                    responsePedido ->
                    binding.rvpedidos.adapter =
                        PedidoAdapter(responsePedido)
            }
        )
    }
}