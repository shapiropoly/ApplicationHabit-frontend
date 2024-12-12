package ru.apphabit.features.collections.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import ru.apphabit.R
import ru.apphabit.features.collections.model.Collection


class HomeAdapter (
    private val collections: List<Collection?>,
    val fragmentManager: FragmentManager)
    : RecyclerView.Adapter<HomeAdapter.CollectionHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_habit, parent,false)
        return CollectionHolder(view)
    }

    override fun onBindViewHolder(holder: CollectionHolder, position: Int) {
        val collection = collections[position]!!
        holder.bind(collection.id, collection.title, collection.description, collection.image)
    }

    override fun getItemCount(): Int {
        return collections.size
    }

    inner class CollectionHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        //        val titleText: TextView = itemView.findViewById(R.id.collection_title)
//        val descriptionText: TextView = itemView.findViewById(R.id.collection_description)
//        val imageView: ImageView = itemView.findViewById(R.id.collection_image)
//
        fun bind(id: Int, title: String, description: String, image: String) {
//            titleText.text = title
//            descriptionText.text = description


//            itemView.setOnClickListener {
//                fragmentManager.beginTransaction().apply {
//                    replace(R.id.flFragment, TrainEditFragment.newInstance(id))
//                    commit()
//                }
        }
    }
}

