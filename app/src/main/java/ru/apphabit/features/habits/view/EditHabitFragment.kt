package ru.apphabit.features.habits.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
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
import ru.apphabit.features.habits.model.Habit
import kotlin.properties.Delegates

class EditHabitFragment : Fragment() {
    private val vmHabits: HabitsVM by viewModel()
    private val vmCategories: CategoriesVM by viewModel()

    private var selectedImageUri: String = ""
    private var selectedCategoryId by Delegates.notNull<Int>()
    private var habitId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            habitId = it.getInt(HABIT_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.habit_edit, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        habitId.takeIf { it != 0 }?.let { id ->
            vmHabits.getHabitById(id)
        } ?: run {
            Toast.makeText(requireContext(), "Ошибка: Не удалось загрузить данные привычки", Toast.LENGTH_SHORT).show()
        }


        val inputTitle = view.findViewById<EditText>(R.id.title_habit_edit)
        val inputDescription = view.findViewById<EditText>(R.id.description_habit_edit)
        val uploadPhotoButton = view.findViewById<TextView>(R.id.upload_photo_habit_edit_text)
        val imageEditHabit = view.findViewById<ImageView>(R.id.habit_edit_image)
        val categorySpinner = view.findViewById<Spinner>(R.id.category_habit_edit_spinner)
        val updateButton = view.findViewById<Button>(R.id.button_habit_update)
        val deleteButton = view.findViewById<Button>(R.id.button_habit_delete)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.habit_edit_toolbar)

        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
            (activity as? MainActivity)?.setBottomNavVisibility(true)
        }

        setupCategorySpinner(categorySpinner)
        loadHabitData(inputTitle, inputDescription, imageEditHabit)

        uploadPhotoButton.setOnClickListener { pickImage() }
        updateButton.setOnClickListener { updateHabit(inputTitle, inputDescription) }
        deleteButton.setOnClickListener { deleteHabit() }
    }





    private fun setupCategorySpinner(spinner: Spinner) {
        vmCategories.categories.observe(viewLifecycleOwner) { categories ->
            if (categories.isNullOrEmpty()) {
                return@observe
            }

            val titles = categories.map { it.title }

            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                titles
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

            vmHabits.habit.observe(viewLifecycleOwner) { habit ->
                if (habit != null) {
                    val position = categories.indexOfFirst { it.id == habit.categoryId }
                    if (position != -1) {
                        Log.d("EditHabitFragment", "Выбрана категория: ${categories[position].title}")
                        spinner.setSelection(position)
                    } else {
                        Log.e("EditHabitFragment", "Категория с ID ${habit.categoryId} не найдена")
                    }
                } else {
                    Log.e("EditHabitFragment", "Привычка не загружена")
                }
            }

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    selectedCategoryId = categories[position].id
                    Log.d("EditHabitFragment", "Выбрана категория ID: ${selectedCategoryId}")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.w("EditHabitFragment", "Никакая категория не выбрана")
                }
            }
        }
        vmCategories.getAllCategories()
    }


    private fun loadHabitData(titleField: EditText, descriptionField: EditText, imageView: ImageView) {
        vmHabits.getHabitById(habitId)
        vmHabits.habit.observe(viewLifecycleOwner) { habit ->
            titleField.setText(habit.title)
            descriptionField.setText(habit.description)
            selectedImageUri = habit.image ?: ""
            if (selectedImageUri.isNotEmpty()) {
                imageView.setImageURI(MediaStore.Images.Media.EXTERNAL_CONTENT_URI.buildUpon()
                    .appendEncodedPath(selectedImageUri).build())
            }
        }
    }

    private fun updateHabit(titleField: EditText, descriptionField: EditText) {
        val title = titleField.text.toString().trim()
        val description = descriptionField.text.toString().trim()

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(requireContext(), "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedHabit = Habit(
            id = habitId,
            title = title,
            description = description,
            image = selectedImageUri,
            categoryId = selectedCategoryId
        )

        vmHabits.updateHabit(habitId, updatedHabit)
        Toast.makeText(requireContext(), "Привычка обновлена", Toast.LENGTH_SHORT).show()
        backActivity()
    }

    private fun deleteHabit() {
        vmHabits.deleteHabit(habitId)
        Toast.makeText(requireContext(), "Привычка удалена", Toast.LENGTH_SHORT).show()
        requireActivity().onBackPressed()
        backActivity()
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
    }

    private fun backActivity() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AllHabitsFragment())
            .addToBackStack(null)
            .commit()

        (activity as? MainActivity)?.setBottomNavVisibility(false)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            if (uri != null) {
                selectedImageUri = uri.toString()
                view?.findViewById<ImageView>(R.id.habit_edit_image)?.setImageURI(uri)
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 1001
        private const val HABIT_ID = "id"

        @JvmStatic
        fun newInstance(id: Int?): EditHabitFragment {
            return EditHabitFragment().apply {
                arguments = Bundle().apply {
                    putInt(HABIT_ID, id ?: 0)
                }
            }
        }
    }
}
