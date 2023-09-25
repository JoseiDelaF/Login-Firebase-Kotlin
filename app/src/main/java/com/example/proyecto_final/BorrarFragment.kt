package com.example.proyecto_final

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.proyecto_final.databinding.FragmentBorrarBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


/**
 * A simple [Fragment] subclass.
 * Use the [BorrarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BorrarFragment : Fragment() {


    private lateinit var binding: FragmentBorrarBinding
    private lateinit var auth: FirebaseAuth

    private  fun deleteAccount(password : String) {
        val user = auth.currentUser

        if (user != null){
            val email = user.email
            val credential = EmailAuthProvider
                .getCredential(email!!, password)

            user.reauthenticate(credential)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {

                        user.delete()
                            .addOnCompleteListener { taskDeleteAcount ->
                                if (taskDeleteAcount.isSuccessful) {
                                    Toast.makeText(requireContext(), "Se elminó tu cuenta.",
                                        Toast.LENGTH_SHORT).show()
                                    signOut()
                                }
                            }

                    } else {
                        Toast.makeText(requireContext(), "La contraseña ingresada es incorrecta.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private  fun signOut(){
        auth.signOut()
        val intent = Intent(requireActivity(), InicioActivity::class.java)
        this.startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBorrarBinding.inflate(inflater,container,false)
        //setContentView(binding.root)
        auth = Firebase.auth

        binding.btnBorrar.setOnClickListener {
            val password = binding.etEliminiarContraseA.text.toString()
            deleteAccount (password)
        }

        return binding.root

    }


}