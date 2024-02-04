package com.fazliddin.armebel.presentation.ui.furniturebytype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fazliddin.armebel.presentation.common.FurnituresListAdapter
import com.fazliddin.armebel.databinding.FragmentFurnitureByTypeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FurnitureByTypeFragment : Fragment() {

    private lateinit var binding: FragmentFurnitureByTypeBinding
    private val viewModel: FurnitureByTypeViewModel by viewModels()
    private lateinit var furnitureListAdapter: FurnituresListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFurnitureByTypeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        furnitureListAdapter = FurnituresListAdapter(FurnituresListAdapter.OnClickListener {
            findNavController().navigate(
                FurnitureByTypeFragmentDirections.actionFurnitureByTypeFragmentToFurnitureDetailsFragment(
                    it
                )
            )
        })

        binding.furnitureRecyclerView.adapter = furnitureListAdapter

        return binding.root
    }
}