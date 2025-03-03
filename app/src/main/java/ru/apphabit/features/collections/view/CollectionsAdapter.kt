package ru.apphabit.features.collections.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import ru.apphabit.R
import ru.apphabit.features.habits.view.HabitAdapter
import ru.apphabit.features.collections.model.Collection

class CollectionsAdapter (
    private var collections: MutableList<Collection?> = mutableListOf(),
    val fragmentManager: FragmentManager,
    private val onCollectionInfoClick: (Int?) -> Unit,
    private val onCollectionFilterClick: (Int?) -> Unit
) : RecyclerView.Adapter<CollectionsAdapter.CollectionsHolder>() {

    private var selectedCollectionId: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionsHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_card_collection, parent, false)
        return CollectionsHolder(view)
    }

    override fun onBindViewHolder(holder: CollectionsHolder, position: Int) {
        val collection = collections[position]!!
        holder.bind(collection.id, collection.title, holder.itemView.context)
    }

    override fun getItemCount(): Int = collections.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateCategories(newCategories: List<Collection?>) {
        (collections as? MutableList<Collection?>)?.apply {
            clear()
            addAll(newCategories)
        }
        notifyDataSetChanged()
    }

    inner class CollectionsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val collectionTitle: TextView = itemView.findViewById(R.id.card_collection_title)
        private val cardCollection: ImageView = itemView.findViewById(R.id.card_collection_background)
        private val imageCollectionInfo: ImageView = itemView.findViewById(R.id.icon_card_info_collection)

        @SuppressLint("NotifyDataSetChanged")
        fun bind(id: Int, title: String, context: Context) {
            collectionTitle.text = title

            Log.d("Check color", "Check selectedCollectionId: $selectedCollectionId")
            Log.d("Check color", "Check id: $id")
            val color = if (selectedCollectionId == id) R.color.light_green else R.color.light_green
            cardCollection.backgroundTintList = ContextCompat.getColorStateList(context, color)

            imageCollectionInfo.setOnClickListener {
                onCollectionInfoClick(id)
                notifyDataSetChanged()
            }

            cardCollection.setOnClickListener {
                selectedCollectionId = if (selectedCollectionId == id) null else id
                onCollectionFilterClick(selectedCollectionId)
                notifyDataSetChanged()
            }
        }
    }
}