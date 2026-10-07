package com.example.pruebaiot

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.pruebaiot.datos.Conexion

class RegistroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_registro)

        val rut = findViewById<EditText>(R.id.edtRutRegistro)
        val clave = findViewById<EditText>(R.id.edtClaveRegistro)
        val repetirClave = findViewById<EditText>(R.id.edtRepetirClave)

        val registrar = findViewById<Button>(R.id.btnCrearCuenta)
        val volver = findViewById<Button>(R.id.btnVolver)

        registrar.setOnClickListener {

            val rutIngresado = rut.text.toString().trim()
            val claveIngresada = clave.text.toString()
            val claveRepetida = repetirClave.text.toString()

            if (
                rutIngresado.isEmpty() ||
                claveIngresada.isEmpty() ||
                claveRepetida.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Complete todos los campos",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (claveIngresada != claveRepetida) {

                Toast.makeText(
                    this,
                    "Las contraseñas no coinciden",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            registrarUsuario(
                rutIngresado,
                claveIngresada
            )
        }

        volver.setOnClickListener {
            finish()
        }
    }

    private fun registrarUsuario(
        rut: String,
        contrasena: String
    ) {

        val url =
            Conexion.URL_WEB_SERVICES + "ingreso.php"

        val solicitud = object : StringRequest(
            Request.Method.POST,
            url,

            { respuesta ->

                val resultado = respuesta.trim()

                if (resultado == "registrado") {

                    Toast.makeText(
                        this,
                        "Usuario registrado correctamente",
                        Toast.LENGTH_LONG
                    ).show()

                    val intent =
                        Intent(this, MainActivity::class.java)

                    startActivity(intent)
                    finish()

                } else if (resultado == "existe") {

                    Toast.makeText(
                        this,
                        "Ese RUT ya está registrado",
                        Toast.LENGTH_LONG
                    ).show()

                } else {

                    Toast.makeText(
                        this,
                        "Respuesta: $resultado",
                        Toast.LENGTH_LONG
                    ).show()
                }
            },

            {

                Toast.makeText(
                    this,
                    "Error al conectar con la base de datos",
                    Toast.LENGTH_LONG
                ).show()
            }

        ) {

            override fun getParams(): MutableMap<String, String> {

                val parametros =
                    HashMap<String, String>()

                parametros["rut"] = rut
                parametros["contrasena"] = contrasena

                return parametros
            }
        }

        Volley.newRequestQueue(this).add(solicitud)
    }
}