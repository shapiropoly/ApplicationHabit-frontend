//package ru.apphabit.features.profile.view
//
//import android.annotation.SuppressLint
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.fragment.app.FragmentManager
//import androidx.recyclerview.widget.RecyclerView
//import ru.apphabit.R
//import ru.apphabit.features.profile.model.User
//
//class UserAdapter (
//    private var users: List<User?>,
//    val fragmentManager: FragmentManager,
//    private val onUserClick: (User) -> Unit)
//    : RecyclerView.Adapter<UserAdapter.UserHolder>() {
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
//            val inflater = LayoutInflater.from(parent.context)
//            val view = inflater.inflate(R.layout.item_habit, parent,false)
//            return UserHolder(view)
//        }
//
//        override fun onBindViewHolder(holder: UserHolder, position: Int) {
//            val habit = users[position]!!
//            holder.bind(habit)
//        }
//
//    override fun getItemCount(): Int {
//        return users.size
//    }
//
//    inner class UserHolder(itemView: View)
//        : RecyclerView.ViewHolder(itemView) {
//
//    }
//
//
////            itemView.setOnClickListener {
////                fragmentManager.beginTransaction().apply {
////                    replace(R.id.flFragment, TrainEditFragment.newInstance(id))
////                    commit()
////                }
////            }
//}