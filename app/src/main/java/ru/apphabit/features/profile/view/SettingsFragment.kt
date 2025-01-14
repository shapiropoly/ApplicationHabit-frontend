package ru.apphabit.features.profile.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.apphabit.R
import ru.apphabit.app.MainActivity
import ru.apphabit.features.profile.model.User
import ru.apphabit.features.authorisation.view.RegistrationFragment
import ru.apphabit.features.habits.view.AllHabitsFragment
import java.time.LocalDate

class SettingsFragment : Fragment() {
    private val vmUsers: UsersVM by viewModel()
    private var userId: Int = 1
    private var password = ""
    private lateinit var dateRegistration: String
    private lateinit var dateLastActivity: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userId = it.getInt(USER_ID, 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.settings, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (userId == 0) {
            Toast.makeText(
                requireContext(),
                "Ошибка: Не удалось загрузить данные о пользователе",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        vmUsers.getUserById(userId)

        val profileNameEdit: EditText = view.findViewById(R.id.profile_name_edit)
        val profileUsernameEdit: EditText = view.findViewById(R.id.profile_username_edit)
        val profileEmailEdit: EditText = view.findViewById(R.id.profile_email_edit)

        val profileButtonUpdate: Button = view.findViewById(R.id.profile_button_update)
        val profileButtonDelete: Button = view.findViewById(R.id.profile_button_delete)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_settings)

        toolbar.setNavigationOnClickListener {
            changeFragment()
        }

        loadUserData(profileNameEdit, profileUsernameEdit, profileEmailEdit)

        profileButtonUpdate.setOnClickListener {
            updateUser(profileNameEdit, profileUsernameEdit, profileEmailEdit)
        }

        profileButtonDelete.setOnClickListener {
            deleteUser()
        }
    }

    private fun loadUserData(
        profileNameEdit: EditText,
        profileUsernameEdit: EditText,
        profileEmailEdit: EditText
    ) {
        vmUsers.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                profileNameEdit.setText(user.name)
                profileUsernameEdit.setText(user.username)
                profileEmailEdit.setText(user.email)

                password = user.password
                dateRegistration = user.dateRegistration
                dateLastActivity = user.dateLastActivity

                Log.d("SettingsFragment", "Загруженные данные пользователя: $user")
            } else {
                Log.e("SettingsFragment", "Пользователь не найден")
                Toast.makeText(
                    requireContext(),
                    "Ошибка: Данные пользователя не найдены",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun updateUser(
        profileNameEdit: EditText,
        profileUsernameEdit: EditText,
        profileEmailEdit: EditText
    ) {
        val name = profileNameEdit.text.toString().trim()
        val username = profileUsernameEdit.text.toString().trim()
        val email = profileEmailEdit.text.toString().trim()

        if (name.isEmpty() || username.isEmpty() || email.isEmpty()) {
            Toast.makeText(requireContext(), "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedUser = User(
            id = userId,
            name = name,
            username = username,
            email = email,
            password = "password",
            dateRegistration = dateRegistration,
            dateLastActivity = dateLastActivity
        )

        Log.d("UpdateUser", "$updatedUser");

        vmUsers.updateUser(userId, updatedUser)
        Log.d("UpdateUser", "UpdateUser: $vmUsers");

        Toast.makeText(requireContext(), "Аккаунт обновлен", Toast.LENGTH_SHORT).show()
        changeFragment()
    }

    private fun deleteUser() {
        vmUsers.deleteUser(userId)
        Log.d("DeleteUser", "DeleteUser: $vmUsers");

        Toast.makeText(requireContext(), "Аккаунт удален", Toast.LENGTH_SHORT).show()
        backActivityDelete()
    }

    private fun backActivityDelete() {
        requireFragmentManager().beginTransaction().apply {
            replace(R.id.main_fragment, RegistrationFragment())
            commit()
        }
    }

    private fun changeFragment() {
        requireFragmentManager().beginTransaction().apply {
            replace(R.id.main_fragment, ProfileFragment.newInstance(userId))
            commit()
        }
    }

    companion object {
        private const val USER_ID = "id"

        @JvmStatic
        fun newInstance(id: Int?): SettingsFragment {
            return SettingsFragment().apply {
                arguments = Bundle().apply {
                    putInt(USER_ID, id ?: 0)
                }
            }
        }
    }
}

