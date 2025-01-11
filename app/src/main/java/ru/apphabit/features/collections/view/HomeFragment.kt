package ru.apphabit.features.collections.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.apphabit.R
import ru.apphabit.features.habits.view.HabitsVM

class HomeFragment : Fragment() {

    private val vmCollections: CollectionsVM by viewModel()
    private val vmHabits: HabitsVM by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewCollections = requireView().findViewById<RecyclerView>(R.id.habits_recycler)
        val recyclerViewHabits = requireView().findViewById<RecyclerView>(R.id.habits_recycler)
        val llm = LinearLayoutManager(requireView().context)
        recyclerView.layoutManager = llm
        recyclerView.setHasFixedSize(true)

        recyclerView.adapter = HomeAdapter(emptyList(), childFragmentManager)

        vm.collections.observe(viewLifecycleOwner) { collections ->
            Log.d("CollectionsFragment", "Observed collections: $collections")
            recyclerView.adapter = HomeAdapter(collections, childFragmentManager)
        }

        vm.getAllCollections()

    }


    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

}