package pe.edu.idat.appmiyamovil.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import pe.edu.idat.appmiyamovil.R

class MainActivity : AppCompatActivity() {
    private lateinit var settings: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        settings = getSharedPreferences("MIYAMOVIL_STT", Context.MODE_PRIVATE)
        if(settings.getString("COD_CLIENT","NO")=="NO"){
            var editor: SharedPreferences.Editor = settings.edit()
            editor.putString("COD_CLIENT", "42gadg23rfxx46k0001")
            editor.commit()
        }
        Handler().postDelayed({
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        },4000)
    }
}