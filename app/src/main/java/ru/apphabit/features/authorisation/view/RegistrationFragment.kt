package ru.apphabit.features.authorisation.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.apphabit.R
import ru.apphabit.features.habits.view.HabitsVM
import ru.apphabit.features.profile.model.User
import ru.apphabit.features.profile.view.UsersVM
import java.time.LocalDate

class RegistrationFragment : Fragment() {
    private val vmUsers: UsersVM by viewModel()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.registration, container, false)

        val nameInput = view.findViewById<EditText>(R.id.registration_input_name)
        val usernameInput = view.findViewById<EditText>(R.id.registration_input_username)
        val emailInput = view.findViewById<EditText>(R.id.registration_input_email)
        val passwordInput = view.findViewById<EditText>(R.id.registration_input_password)
        val registerButton = view.findViewById<Button>(R.id.register_button)
        val loginLink = view.findViewById<Button>(R.id.login_link)

        registerButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val username = usernameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Регистрация успешна", Toast.LENGTH_SHORT).show()
            }

            val user = User(
                id = null,
                name = name,
                username = username,
                email = email,
                password = password,
                dateRegistration = LocalDate.now(),
                dateLastActivity = LocalDate.now()
                )

            vmUsers.addUser(user)
        }

        loginLink.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}
