package ru.apphabit.features.profile.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.apphabit.R
import ru.apphabit.app.MainActivity
import ru.apphabit.features.habits.view.*
import ru.apphabit.features.habits.view.HabitsVM;
import ru.apphabit.features.profile.model.User
import ru.apphabit.features.registration.view.RegistrationFragment
import java.time.LocalDate

class SettingsFragment : Fragment() {
    private val vmUsers: UsersVM by viewModel()
    private lateinit var userAdapter: UserAdapter
    private var userId: Int = 0
    private var password = ""
    private lateinit var dateRegistration: LocalDate
    private lateinit var dateLastActivity: LocalDate

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
            requireActivity().onBackPressed()
            (activity as? MainActivity)?.setBottomNavVisibility(true)
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
            password = password,
            dateRegistration = dateRegistration,
            dateLastActivity = dateLastActivity
        )

        vmUsers.updateUser(userId, updatedUser)
        Toast.makeText(requireContext(), "Аккаунт обновлен", Toast.LENGTH_SHORT).show()
        backActivityUpdate()
    }

    private fun deleteUser() {
        vmUsers.deleteUser(userId)
        Toast.makeText(requireContext(), "Аккаунт удален", Toast.LENGTH_SHORT).show()
        backActivityDelete()
    }

    private fun backActivityUpdate() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ProfileFragment())
            .addToBackStack(null)
            .commit()

        (activity as? MainActivity)?.setBottomNavVisibility(false)
    }

    private fun backActivityDelete() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, RegistrationFragment())
            .addToBackStack(null)
            .commit()

        (activity as? MainActivity)?.setBottomNavVisibility(false)
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

