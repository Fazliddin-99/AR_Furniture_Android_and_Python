package com.fazliddin.armebel.presentation.ui.likedfurniture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fazliddin.armebel.presentation.common.FurnituresListAdapter
import com.fazliddin.armebel.databinding.FragmentLikedFurnitureBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LikedFurnitureFragment : Fragment() {

    private val viewModel: LikedFurnitureViewModel by viewModels()
    private lateinit var binding: FragmentLikedFurnitureBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLikedFurnitureBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel
        val furnitureListAdapter = FurnituresListAdapter(FurnituresListAdapter.OnClickListener {
            findNavController().navigate(
                LikedFurnitureFragmentDirections.actionLikedFurnitureFragmentToFurnitureDetailsFragment(
                    it
                )
            )
        })

        binding.furnitureRecyclerView.adapter = furnitureListAdapter

        return binding.root
    }
}