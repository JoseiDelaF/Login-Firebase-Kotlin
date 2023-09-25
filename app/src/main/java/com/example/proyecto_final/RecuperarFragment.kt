package com.example.proyecto_final

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.proyecto_final.databinding.FragmentRecuperarBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest

/**
 * A simple [Fragment] subclass.
 * Use the [RecuperarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecuperarFragment : Fragment() {

    private lateinit var binding: FragmentRecuperarBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRecuperarBinding.inflate(inflater,container,false)
        //setContentView(binding.root)
        auth = Firebase.auth
        val user = auth.currentUser


        binding.btnRecuperarCuenta.setOnClickListener {
            val emailAddress = binding.etRecuperarCCuenta.text.toString()
            Firebase.auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(requireActivity(), InicioActivity::class.java)
                        this.startActivity(intent)
                        Toast.makeText(requireContext(), "Se Envio un Correo para Cambiar tu Contraseña, Revisa tu Correo!",
                            Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Ingrese un email de una cuenta valida.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }


        return binding.root
    }

}