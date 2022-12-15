package pe.edu.idat.appmiyamovil.view.fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.Tasks.call
import pe.edu.idat.appmiyamovil.R
import pe.edu.idat.appmiyamovil.databinding.FragmentConsultasBinding
import pe.edu.idat.appmiyamovil.databinding.FragmentPedidosBinding
import pe.edu.idat.appmiyamovil.retrofit.request.RequestConsulta
import pe.edu.idat.appmiyamovil.utilitarios.AppMensaje
import pe.edu.idat.appmiyamovil.utilitarios.TipoMensaje
import pe.edu.idat.appmiyamovil.view.HomeActivity
import pe.edu.idat.appmiyamovil.view.adapters.PedidoAdapter
import pe.edu.idat.appmiyamovil.viewmodel.ClienteViewModel
import pe.edu.idat.appmiyamovil.viewmodel.PedidoViewModel

class ConsultasFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentConsultasBinding? = null
    private val binding get() = _binding!!
    private lateinit var clienteViewModel: ClienteViewModel
    private lateinit var settings: SharedPreferences

    //llamada
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){
        isGranted ->
        if (isGranted){
            val phone = "(01) 6199500"
            call(phone)
        }else{
            print("falta habilitar permisos de llamada")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConsultasBinding.inflate(inflater, container, false)
        clienteViewModel = ViewModelProvider(requireActivity())
            .get(ClienteViewModel::class.java)
        settings = container!!.context.getSharedPreferences("MIYAMOVIL_STT", Context.MODE_PRIVATE)
        binding.btnenviar.setOnClickListener(this)
        binding.fabllamar.setOnClickListener(this)

        return binding.root
    }

    fun validarForumulario():Boolean{
        if(binding.etconnombre.text.length < 3){
            AppMensaje.enviarMensaje(binding.root,"Nombre: Min. 3 caracteres", TipoMensaje.ERROR)
            return false
        }
        if(binding.etconapellido.text.length < 4){
            AppMensaje.enviarMensaje(binding.root,"Apellido: Min. 4 caracteres", TipoMensaje.ERROR)
            return false
        }
        if(binding.etcondni.text.length != 8){
            AppMensaje.enviarMensaje(binding.root,"DNI: 8 caracteres", TipoMensaje.ERROR)
            return false
        }
        if(binding.etconconsulta.text.length < 10){
            AppMensaje.enviarMensaje(binding.root,"Consulta: Min. 10 caracteres", TipoMensaje.ERROR)
        }
        return true
    }

    fun pedirPermisos(){
        val phone = "(01) 6199500"

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            when{
                requireActivity().checkSelfPermission(
                    Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED ->{
                    call(phone)
                }
                else -> requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
            }
        }
        else{
            call(phone)
        }
    }

    private fun call(phone : String) {
        startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:${phone}")))
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.btnenviar -> {
                if(validarForumulario()){
                    var consultaReq : RequestConsulta = RequestConsulta(
                        settings.getString("COD_CLIENT","NO")!!,
                        binding.etconnombre.text.toString(),
                        binding.etconapellido.text.toString(),
                        binding.etcondni.text.toString(),
                        binding.etconconsulta.text.toString()
                    )
                    clienteViewModel.crearConsulta(consultaReq).observe(
                        viewLifecycleOwner, Observer {
                                responseMensaje ->
                            println(responseMensaje)
                        }
                    )
                    startActivity(Intent(requireActivity(),HomeActivity::class.java))
                    requireActivity().finish()
                }
            }
            R.id.fabllamar -> {
                pedirPermisos()
            }
        }
    }

}