package com.example.pruebaiot

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class InicioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_inicio)

        val botonCerrar =
            findViewById<Button>(
                R.id.btnCerrarSesion
            )

        botonCerrar.setOnClickListener {

            val intent =
                Intent(
                    this,
                    MainActivity::class.java
                )

            startActivity(intent)

            finish()
        }
    }
}