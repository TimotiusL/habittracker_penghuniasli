package com.example.habittracker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.habittracker.R
import com.example.habittracker.data.SessionManager
import com.example.habittracker.data.local.AppDatabase
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())

        // Bonus: jika sudah pernah login sebelumnya, auto masuk ke Dashboard
        if (sessionManager.isLoggedIn()) {
            findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
            return
        }

        val username = view.findViewById<EditText>(R.id.edtUsername)
        val password = view.findViewById<EditText>(R.id.edtPassword)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val user = username.text.toString().trim()
            val pass = password.text.toString().trim()

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(requireContext(), "Username dan password wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Query informasi login ke tabel user secara lokal (Room)
            lifecycleScope.launch {
                val userDao = AppDatabase.getInstance(requireContext()).userDao()
                val result = userDao.login(user, pass)

                if (result != null) {
                    sessionManager.saveSession(result.username)
                    findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                } else {
                    Toast.makeText(requireContext(), "Login gagal", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}