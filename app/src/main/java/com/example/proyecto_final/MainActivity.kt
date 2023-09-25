package com.example.proyecto_final

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.proyecto_final.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =  ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //replaceFragment(MainActivity())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.login -> replaceFragment(LoginFragment())
                R.id.borrar -> replaceFragment(BorrarFragment())
                R.id.verificar -> replaceFragment(VerificarFragment())
                R.id.recuperar -> replaceFragment(RecuperarFragment())
                else -> {}


            }
            val textView = binding.tvBienvenida

            textView.visibility = when(it.itemId) {
                R.id.login, R.id.borrar, R.id.verificar, R.id.recuperar -> View.GONE // Hacer el TextView visible
                else -> View.VISIBLE // Ocultar el TextView
            }

            true
        }


    }

    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()

    }
}