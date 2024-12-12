package ru.apphabit.features.habits.view


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

class HabitsFragment : Fragment() {
    private val vm: HabitsVM by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.all_habits, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = requireView().findViewById<RecyclerView>(R.id.habits_recycler)
        val llm = LinearLayoutManager(requireView().context)
        recyclerView.layoutManager = llm
        recyclerView.setHasFixedSize(true)

        recyclerView.adapter = HabitAdapter(emptyList(), childFragmentManager)

        vm.habits.observe(viewLifecycleOwner) { habits ->
            Log.d("HabitsFragment", "Observed habits: $habits")
            recyclerView.adapter = HabitAdapter(habits, childFragmentManager)
        }

        vm.getAllHabits()

    }



    companion object {
        @JvmStatic
        fun newInstance() = HabitsFragment()
    }
}