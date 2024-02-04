package com.fazliddin.armebel.presentation.ui.alltypes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fazliddin.armebel.databinding.FragmentAllTypesBinding
import com.fazliddin.armebel.presentation.ui.types.TypesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllTypesFragment : Fragment() {

    private lateinit var binding: FragmentAllTypesBinding
    private val viewModel: TypesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllTypesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel
        binding.typesRecyclerView.adapter =
            AllTypesListAdapter(AllTypesListAdapter.OnClickListener {
                findNavController().navigate(
                    AllTypesFragmentDirections.actionAllTypesFragmentToFurnitureByTypeFragment(
                        it.id
                    )
                )
            })

        return binding.root
    }

}