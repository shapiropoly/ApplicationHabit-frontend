package ru.apphabit.features.authorisation.view

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import ru.apphabit.R

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.login, container, false)

        val emailInput = view.findViewById<EditText>(R.id.login_input_email)
        val passwordInput = view.findViewById<EditText>(R.id.login_input_password)
        val loginButton = view.findViewById<Button>(R.id.login_button)
        val registerLink = view.findViewById<Button>(R.id.register_link)

        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Вход выполнен", Toast.LENGTH_SHORT).show()
            }
        }

        registerLink.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RegistrationFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}
