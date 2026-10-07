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

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val rut =
            findViewById<EditText>(R.id.edtUsuario)

        val contrasena =
            findViewById<EditText>(R.id.edtContrasena)

        val iniciar =
            findViewById<Button>(R.id.btnIniciarSesion)

        val registrarse =
            findViewById<Button>(R.id.btnRegistrarse)

        registrarse.setOnClickListener {

            val intent =
                Intent(this, RegistroActivity::class.java)

            startActivity(intent)
        }

        iniciar.setOnClickListener {

            val rutIngresado =
                rut.text.toString().trim()

            val claveIngresada =
                contrasena.text.toString()

            if (
                rutIngresado.isEmpty() ||
                claveIngresada.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Complete todos los campos",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            iniciarSesion(
                rutIngresado,
                claveIngresada
            )
        }
    }

    private fun iniciarSesion(
        rut: String,
        contrasena: String
    ) {

        val url =
            Conexion.URL_WEB_SERVICES + "login.php"

        val solicitud = object : StringRequest(
            Request.Method.POST,
            url,

            { respuesta ->

                val resultado = respuesta.trim()

                if (resultado == "correcto") {

                    Toast.makeText(
                        this,
                        "Inicio de sesión correcto",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent =
                        Intent(this, InicioActivity::class.java)

                    startActivity(intent)

                } else {

                    Toast.makeText(
                        this,
                        "RUT o contraseña incorrectos",
                        Toast.LENGTH_SHORT
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