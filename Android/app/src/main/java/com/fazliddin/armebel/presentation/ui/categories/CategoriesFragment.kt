package com.fazliddin.armebel.presentation.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fazliddin.armebel.databinding.FragmentCategoriesBinding
import com.fazliddin.armebel.presentation.ui.startscreen.StartScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private val viewModel: StartScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.categoriesRecyclerView.adapter =
            AllCategoriesListAdapter(AllCategoriesListAdapter.OnClickListener {
                findNavController().navigate(
                    CategoriesFragmentDirections.actionCategoriesFragmentToTypesFragment(
                        it.id
                    )
                )
            })

        return binding.root
    }
}