package ru.apphabit.features.collections.view

import android.app.AlertDialog
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
import ru.apphabit.features.collections.model.Collection
import ru.apphabit.features.habits.model.Habit
import ru.apphabit.features.habits.view.AllHabitsFragment
import ru.apphabit.features.habits.view.HabitsVM
import kotlin.properties.Delegates

class CollectionEditFragment : Fragment() {

    private val vmHabits: HabitsVM by viewModel()
    private val vmCollections: CollectionsVM by viewModel()

    private var collectionHabitsList: List<Habit> = listOf()
    private var selectedImageUri: String = ""
    private var selectedHabitId by Delegates.notNull<Int>()
    private var collectionId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            collectionId = it.getInt(COLLECTION_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.collection_edit, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectionId.takeIf { it != 0 }?.let { id ->
            vmCollections.getCollectionById(id)
        } ?: run {
            Toast.makeText(requireContext(), "Ошибка: Не удалось загрузить данные коллекции", Toast.LENGTH_SHORT).show()
        }

        vmCollections.getCollectionById(collectionId)
        vmHabits.getAllHabits()


        val inputTitle = view.findViewById<EditText>(R.id.title_collection_edit)
        val inputDescription = view.findViewById<EditText>(R.id.description_collection_edit)
        val uploadPhotoButton = view.findViewById<TextView>(R.id.upload_photo_collection_edit_text)
        val imageEditCollection = view.findViewById<ImageView>(R.id.collection_edit_image)
        val habitListSpinner = view.findViewById<Spinner>(R.id.select_list_habits_to_collection)
        val updateButton = view.findViewById<Button>(R.id.button_collection_update)
        val deleteButton = view.findViewById<Button>(R.id.button_collection_delete)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.collection_edit_toolbar)
        val selectHabitsButton = view.findViewById<Button>(R.id.select_habits_to_collection_button)

        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
            (activity as? MainActivity)?.setBottomNavVisibility(true)
        }

        vmCollections.collection.observe(viewLifecycleOwner) { collection ->
            collectionHabitsList = collection.habits
        }

        vmHabits.habits.observe(viewLifecycleOwner) { allHabits ->
            if (!allHabits.isNullOrEmpty()) {
                setupHabitSelectionDialog(selectHabitsButton, allHabits)
            }
        }

        setupCollectionsSpinner(habitListSpinner)
        loadCollectionData(inputTitle, inputDescription, imageEditCollection)

        updateButton.setOnClickListener { updateCollection(inputTitle, inputDescription, collectionHabitsList) }
        deleteButton.setOnClickListener { deleteCollection() }
    }

    private fun setupHabitSelectionDialog(selectHabitsButton: Button, allHabits: List<Habit>) {
        val habitTitles = allHabits.map { it.title }.toTypedArray()
        val selectedHabits = collectionHabitsList.toMutableList()
        val selectedIndices = BooleanArray(allHabits.size) { index ->
            selectedHabits.any { it.id == allHabits[index].id }
        }

        selectHabitsButton.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Выберите привычки")
                .setMultiChoiceItems(habitTitles, selectedIndices) { _, index, isChecked ->
                    val habit = allHabits[index]
                    if (isChecked) {
                        selectedHabits.add(habit)
                    } else {
                        selectedHabits.remove(habit)
                    }
                }
                .setPositiveButton("ОК") { _, _ ->
                    val selectedTitles = selectedHabits.map { it.title }
                    selectHabitsButton.text = selectedTitles.joinToString(", ")
                    collectionHabitsList = selectedHabits
                }
                .setNegativeButton("Отмена", null)
                .show()
        }
    }

    private fun setupCollectionsSpinner(spinner: Spinner) {
        vmHabits.habits.observe(viewLifecycleOwner) { habits ->
            if (habits.isNullOrEmpty()) {
                return@observe
            }

            val habitTitles = habits.map { it.title }
            val selectedHabits = mutableSetOf<Habit>()
            val selectedIndices = BooleanArray(habits.size)

            spinner.setOnClickListener {
                AlertDialog.Builder(requireContext())
                    .setTitle("Выберите привычки")
                    .setMultiChoiceItems(habitTitles.toTypedArray(), selectedIndices) { _, index, isChecked ->
                        if (isChecked) {
                            selectedHabits.add(habits[index])
                        } else {
                            selectedHabits.remove(habits[index])
                        }
                    }
                    .setPositiveButton("ОК") { _, _ ->
                        val selectedTitles = selectedHabits.map { it.title }
                        Toast.makeText(requireContext(), "Выбрано: ${selectedTitles.joinToString(", ")}", Toast.LENGTH_SHORT).show()

                        (spinner as? TextView)?.text = selectedTitles.joinToString(", ")
                        collectionHabitsList = selectedHabits.toList()
                    }
                    .setNegativeButton("Отмена", null)
                    .show()
            }
        }
    }


    private fun loadCollectionData(titleField: EditText, descriptionField: EditText, imageView: ImageView) {
        vmCollections.getCollectionById(collectionId)
        vmCollections.collection.observe(viewLifecycleOwner) { collection ->
            titleField.setText(collection.title)
            descriptionField.setText(collection.description)
            selectedImageUri = collection.image ?: ""
            if (selectedImageUri.isNotEmpty()) {
                imageView.setImageURI(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI.buildUpon()
                    .appendEncodedPath(selectedImageUri).build())
            }
        }
    }

    private fun updateCollection(titleField: EditText, descriptionField: EditText, collectionHabitsList: List<Habit>) {
        val title = titleField.text.toString().trim()
        val description = descriptionField.text.toString().trim()

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(requireContext(), "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedCollection = Collection(
            id = collectionId,
            title = title,
            description = description,
            image = selectedImageUri,
            habits = collectionHabitsList
        )

        vmCollections.updateCollection(collectionId, updatedCollection, collectionHabitsList)

        Toast.makeText(requireContext(), "Коллекция обновлена", Toast.LENGTH_SHORT).show()
        backActivity()
    }


    private fun deleteCollection() {
        vmCollections.deleteCollection(collectionId)
        Toast.makeText(requireContext(), "Коллекция удалена", Toast.LENGTH_SHORT).show()
        requireActivity().onBackPressed()
        backActivity()
    }

    private fun backActivity() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_main, AllHabitsFragment())
            .addToBackStack(null)
            .commit()

        (activity as? MainActivity)?.setBottomNavVisibility(false)
    }

    companion object {
        private const val COLLECTION_ID = "id"

        @JvmStatic
        fun newInstance(id: Int?): CollectionEditFragment {
            return CollectionEditFragment().apply {
                arguments = Bundle().apply {
                    putInt(COLLECTION_ID, id ?: 0)
                }
            }
        }
    }
}