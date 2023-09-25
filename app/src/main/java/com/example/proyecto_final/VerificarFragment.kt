package com.example.proyecto_final

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.proyecto_final.databinding.FragmentVerificarBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase


/**
 * A simple [Fragment] subclass.
 * Use the [VerificarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VerificarFragment : Fragment() {

    private lateinit var binding: FragmentVerificarBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVerificarBinding.inflate(inflater,container,false)
        //setContentView(binding.root)
        auth = Firebase.auth
        val user = auth.currentUser
        binding.btnVerificar.setOnClickListener {


            val profileUpdates = userProfileChangeRequest {
            }
            user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (user.isEmailVerified) {
                            /*val intent = Intent(requireActivity(), MainActivity::class.java)
                            this.startActivity(intent)*/
                            val fragment = LoginFragment()
                            parentFragmentManager.beginTransaction().replace(R.id.frame_layout,fragment).addToBackStack(null).commit()

                            /*parentFragmentManager.beginTransaction().replace(R.id.frame_layout,fragment)
                                .addToBackStack(null)
                                .commit()*/

                        } else {
                            Toast.makeText(requireContext(), "Por favor verifica tu correo.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }

        return binding.root
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            if(currentUser.isEmailVerified){
                reload()
            } else {
                sendEmailVerification()
            }
        }
    }

    private fun sendEmailVerification() {
        val user = auth.currentUser
        user!!.sendEmailVerification()
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Se ha enviado un correo de verifiación.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun reload() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        this.startActivity(intent)
    }




}