package ru.apphabit.features.habits.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
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
import ru.apphabit.features.habits.model.HabitDTO
import kotlin.properties.Delegates

class AddHabitFragment : Fragment() {
    private val vmHabits: HabitsVM by viewModel()
    private val vmCategories: CategoriesVM by viewModel()
    private var selectedImageUri: String = ""
    private var selectedCategoryId by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.habit_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputTextHabitTitle = view.findViewById<EditText>(R.id.title_habit_add)
        val inputTextHabitDescription = view.findViewById<EditText>(R.id.description_habit_add)
        val uploadPhotoButton = view.findViewById<TextView>(R.id.upload_photo_habit_add_text)
        val imageAddHabit = view.findViewById<ImageView>(R.id.habit_add_image)
        val selectCategory = view.findViewById<Spinner>(R.id.category_habit_spinner)
        val buttonSaveHabit = view.findViewById<Button>(R.id.button_save_habit)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.habit_add_toolbar)

        vmCategories.categories.observe(viewLifecycleOwner) { categories ->
            if (categories != null) {
                val titles = categories.map { it.title }

                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    titles
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                selectCategory.adapter = adapter

                selectCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        selectedCategoryId = categories[position].id
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
        }

        vmCategories.getAllCategories()


        uploadPhotoButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
        }

        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
            (activity as? MainActivity)?.setBottomNavVisibility(true)
        }


        buttonSaveHabit.setOnClickListener {
            val habitTitle = inputTextHabitTitle.text.toString().trim()
            val habitDescription = inputTextHabitDescription.text.toString().trim()
            val habitImage = selectedImageUri

            if (habitTitle.isBlank()) {
                Toast.makeText(requireContext(), "Введите название привычки", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newHabit = Habit(
                id = null,
                title = habitTitle,
                description = habitDescription,
                image = habitImage,
                categoryId = selectedCategoryId
            )

            vmHabits.addHabit(newHabit)
            requireActivity().onBackPressed()
            (activity as? MainActivity)?.setBottomNavVisibility(true)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            if (uri != null) {
                selectedImageUri = uri.toString()
                val imageHabit = view?.findViewById<ImageView>(R.id.habit_add_image)
                imageHabit?.setImageURI(uri)
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 1001

        @JvmStatic
        fun newInstance() = AddHabitFragment()
    }
}