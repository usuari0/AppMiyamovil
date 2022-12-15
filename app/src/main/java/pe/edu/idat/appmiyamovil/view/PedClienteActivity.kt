package pe.edu.idat.appmiyamovil.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import pe.edu.idat.appmiyamovil.R
import pe.edu.idat.appmiyamovil.databinding.ActivityHomeBinding
import pe.edu.idat.appmiyamovil.databinding.ActivityPedClienteBinding
import pe.edu.idat.appmiyamovil.retrofit.response.ResponseCliente
import pe.edu.idat.appmiyamovil.utilitarios.AppMensaje
import pe.edu.idat.appmiyamovil.utilitarios.MiApp
import pe.edu.idat.appmiyamovil.utilitarios.TipoMensaje
import pe.edu.idat.appmiyamovil.view.fragments.PedidosFragment
import retrofit2.Response

class PedClienteActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityPedClienteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPedClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnatras.setOnClickListener(this)
        binding.btnsiguiente.setOnClickListener(this)
    }

    override fun onClick(vista: View) {
        when(vista.id){
            R.id.btnatras-> finish()
            R.id.btnsiguiente -> {
                if(validarForumulario()){
                    var intent:Intent = Intent(applicationContext, ListaProductosActivity::class.java)
                    intent.putExtra("nombre", binding.etpednombre.text.toString())
                    println("hola"+binding.etpednombre.text.toString())
                    intent.putExtra("apellido", binding.etpedapellido.text.toString())
                    intent.putExtra("dni", binding.etpeddni.text.toString())
                    startActivity(intent)
                }
            }
        }
    }

    fun validarForumulario():Boolean{
        if(binding.etpednombre.text.length < 3){
            AppMensaje.enviarMensaje(binding.root,"Nombre: Min. 3 caracteres",TipoMensaje.ERROR)
            return false
        }
        if(binding.etpedapellido.text.length < 4){
            AppMensaje.enviarMensaje(binding.root,"Apellido: Min. 4 caracteres",TipoMensaje.ERROR)
            return false
        }
        if(binding.etpeddni.text.length != 8){
            AppMensaje.enviarMensaje(binding.root,"DNI: 8 caracteres",TipoMensaje.ERROR)
            return false
        }
        if(!binding.chbGPS.isChecked){
            AppMensaje.enviarMensaje(binding.root,"Activar envío de ubicación",TipoMensaje.ERROR)
            return false
        }
        return true
    }
}