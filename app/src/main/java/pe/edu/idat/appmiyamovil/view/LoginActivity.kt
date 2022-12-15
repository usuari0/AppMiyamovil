package pe.edu.idat.appmiyamovil.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import pe.edu.idat.appmiyamovil.databinding.ActivityLoginBinding
import pe.edu.idat.appmiyamovil.retrofit.response.ResponseClient
import pe.edu.idat.appmiyamovil.viewmodel.ClienteViewModel
import java.io.*
import java.nio.charset.Charset

class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    private lateinit var clienteViewModel: ClienteViewModel
    private lateinit var settings: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settings = getSharedPreferences("MIYAMOVIL_STT", Context.MODE_PRIVATE)

        binding.tvinfo.text = settings.getString("COD_CLIENT","NO")


    }

    private fun obtenerResultadoRegistro(response: ResponseClient?) {
        binding.tvinfo.text = response?.nombres
    }

    private fun corroboraCodigo(){

    }
}

/*var editor: SharedPreferences.Editor = settings.edit()
        editor.putString("COD_CLIENT", "42gadg23rfxx46k0001")
        editor.commit()*/

/*clienteViewModel =ViewModelProvider(this)
            .get(ClienteViewModel::class.java)
        clienteViewModel.responseClient.observe(this, Observer{
                response -> obtenerResultadoRegistro(response)
        })

        clienteViewModel.crearCliente("alberth","levi","3121241","albondiga")*/