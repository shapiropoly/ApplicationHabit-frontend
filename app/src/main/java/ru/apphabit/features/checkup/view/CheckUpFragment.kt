package ru.apphabit.features.checkup.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.apphabit.R


class CheckUpFragment : Fragment() {

    private lateinit var viewModel: CheckUpVM
    private lateinit var adapter: CheckUpAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.check_up, container, false)

        viewModel = ViewModelProvider(this)[CheckUpVM::class.java]

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_habits)
//        adapter = CheckUpAdapter { checkUpId, isCompleted ->
//            viewModel.updateCheckUp(checkUpId, isCompleted)
//        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

//        viewModel.checkup.observe(viewLifecycleOwner) { checkUpList ->
//            adapter.submitList(checkUpList)
//        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = CheckUpFragment()
    }
}