package ru.apphabit.features.registration.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import ru.apphabit.R
import ru.apphabit.features.habits.model.Habit

class RegistrationAdapter {

    inner class RegistrationHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        val nameInput: EditText = itemView.findViewById(R.id.registration_input_name)
        val usernameInput: EditText = itemView.findViewById(R.id.registration_input_username)
        val emailInput: EditText = itemView.findViewById(R.id.registration_input_email)
        val passwordInput: EditText = itemView.findViewById(R.id.registration_input_password)
        val registerButton: Button = itemView.findViewById(R.id.register_button)



//        // Установка обработчика клика для кнопки регистрации
//        registerButton.setOnClickListener {
//            val name = nameInput.text.toString().trim()
//            val email = emailInput.text.toString().trim()
//            val password = passwordInput.text.toString().trim()
//
//            // Проверка ввода
//            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
//                Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            // Передача данных на следующий экран
//            val intent = Intent(this, NextActivity::class.java)
//            intent.putExtra("name", name)
//            intent.putExtra("email", email)
//            intent.putExtra("password", password)
//            startActivity(intent)
//        }
    }


//        fun bind(id: Int, title: String, description: String, image: String, categoryId: Int) {
//            titleText.text = title
//            descriptionText.text = description
//
//            when (categoryId) {
//                7 -> imageView.setImageResource(R.drawable.ic_intelligence)
//                2 -> imageView.setImageResource(R.drawable.ic_intelligence)
//                3 -> imageView.setImageResource(R.drawable.ic_intelligence)
//                else -> imageView.setImageResource(R.drawable.ic_intelligence)
//            }
//
////            itemView.setOnClickListener {
////                fragmentManager.beginTransaction().apply {
////                    replace(R.id.flFragment, TrainEditFragment.newInstance(id))
////                    commit()
////                }
////            }
//        }
}
class HabitAdapter(
    private val habits: List<Habit?>,
    val fragmentManager: FragmentManager
)
    : RecyclerView.Adapter<HabitAdapter.HabitHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_habit, parent,false)
        return HabitHolder(view)
    }

    override fun onBindViewHolder(holder: HabitHolder, position: Int) {
        val habit = habits[position]!!
        holder.bind(habit.id, habit.title, habit.description, habit.image, habit.categoryId)
    }

    override fun getItemCount(): Int {
        return habits.size
    }

    inner class HabitHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        val titleText: TextView = itemView.findViewById(R.id.habit_title)
        val descriptionText: TextView = itemView.findViewById(R.id.habit_description)
        val imageView: ImageView = itemView.findViewById(R.id.habit_image)
        val categoryIdText: TextView = itemView.findViewById(R.id.habit_category_text)

        fun bind(id: Int?, title: String, description: String, image: String, categoryId: Int?) {
            titleText.text = title
            descriptionText.text = description

            when (categoryId) {
                7 -> imageView.setImageResource(R.drawable.ic_intelligence)
                2 -> imageView.setImageResource(R.drawable.ic_intelligence)
                3 -> imageView.setImageResource(R.drawable.ic_intelligence)
                else -> imageView.setImageResource(R.drawable.ic_intelligence)
            }

//            itemView.setOnClickListener {
//                fragmentManager.beginTransaction().apply {
//                    replace(R.id.flFragment, TrainEditFragment.newInstance(id))
//                    commit()
//                }
//            }
        }
    }
}
