package ru.apphabit.features.authorisation.view

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.apphabit.R
import ru.apphabit.features.collections.view.HomeFragment
import ru.apphabit.features.profile.view.UsersVM

class LoginFragment : Fragment() {
    private val vmUsers: UsersVM by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val emailInput = view.findViewById<EditText>(R.id.login_input_email)
        val passwordInput = view.findViewById<EditText>(R.id.login_input_password)
        val loginButton = view.findViewById<Button>(R.id.login_button)
        val registerLink = view.findViewById<Button>(R.id.register_link)

        vmUsers.user.observe(viewLifecycleOwner) { user ->
            if (user != null && user.password == passwordInput.text.toString().trim()) {
                Toast.makeText(context, "Добро пожаловать, ${user.name}!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Неверный e-mail или пароль", Toast.LENGTH_SHORT).show()
            }
        }

        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
            } else {
                vmUsers.getUserByEmail(email)
                changeFragment()
            }
        }

        registerLink.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RegistrationFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun changeFragment() {
        requireFragmentManager().beginTransaction().apply {
            replace(R.id.main_fragment, HomeFragment.newInstance())
            commit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}
