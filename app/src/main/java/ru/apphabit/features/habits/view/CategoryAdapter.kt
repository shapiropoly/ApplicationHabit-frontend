package ru.apphabit.features.habits.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import ru.apphabit.R
import ru.apphabit.features.habits.model.Category


class CategoryAdapter(
    private var categories: MutableList<Category?> = mutableListOf(),
    val fragmentManager: FragmentManager,
    private val onCategoryClick: (Int?) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    private var selectedCategoryId: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_button_category, parent, false)
        return CategoryHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val category = categories[position]!!
        holder.bind(category.id, category.title, holder.itemView.context)
    }

    override fun getItemCount(): Int = categories.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateCategories(newCategories: List<Category?>) {
        (categories as? MutableList<Category?>)?.apply {
            clear()
            addAll(newCategories)
        }
        notifyDataSetChanged()
    }

    inner class CategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val buttonTitle: Button = itemView.findViewById(R.id.button_category)

        @SuppressLint("NotifyDataSetChanged")
        fun bind(id: Int, title: String, context: Context) {
            buttonTitle.text = title

            val color = if (selectedCategoryId == id) R.color.light_green else R.color.light_gray
            buttonTitle.backgroundTintList = ContextCompat.getColorStateList(context, color)

            buttonTitle.setOnClickListener {
                selectedCategoryId = if (selectedCategoryId == id) null else id
                onCategoryClick(selectedCategoryId)
                notifyDataSetChanged()
            }
        }
    }
}