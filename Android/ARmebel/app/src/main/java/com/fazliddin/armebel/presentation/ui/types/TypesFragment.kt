package com.fazliddin.armebel.presentation.ui.types

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fazliddin.armebel.presentation.common.FurnituresListAdapter
import com.fazliddin.armebel.databinding.FragmentTypesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TypesFragment : Fragment() {
    private lateinit var binding: FragmentTypesBinding
    private val viewModel: TypesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTypesBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val args = TypesFragmentArgs.fromBundle(requireArguments())

        viewModel.setup(args.categoryID)

        binding.viewModel = viewModel

        binding.typesRecyclerView.adapter =
            TypesListAdapter(TypesListAdapter.OnClickListener {
                findNavController().navigate(
                    TypesFragmentDirections.actionTypesFragmentToFurnitureByTypeFragment(
                        it.id
                    )
                )
            })

        val furnitureListAdapter = FurnituresListAdapter(FurnituresListAdapter.OnClickListener {
            findNavController().navigate(
                TypesFragmentDirections.actionTypesFragmentToFurnitureDetailsFragment(
                    it
                )
            )
        })

        binding.viewAllBtn.setOnClickListener {
            findNavController().navigate(TypesFragmentDirections.actionTypesFragmentToAllTypesFragment())
        }

        binding.furnitureRecyclerView.adapter = furnitureListAdapter

        return binding.root
    }
}