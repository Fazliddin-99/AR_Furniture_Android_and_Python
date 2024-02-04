package com.fazliddin.armebel.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fazliddin.armebel.R
import com.fazliddin.armebel.databinding.FragmentDetailsFurnitureBinding
import com.fazliddin.armebel.domain.utils.setViewDrawableTint
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FurnitureDetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsFurnitureBinding
    private val viewModel: FurnitureDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsFurnitureBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        binding.arBtn.setOnClickListener {
            findNavController()
                .navigate(
                    FurnitureDetailsFragmentDirections
                        .actionFurnitureDetailsFragmentToFurnitureArFragment(
                            viewModel.furnitureLD.value?.model3d ?: ""
                        )
                )
        }

        binding.cartBtn.setOnClickListener {
            viewModel.addToCart()
        }

        binding.likeBtn.setOnClickListener { view ->
            val snackbarText: String
            val drawableColorId: Int

            if (viewModel.likedFurniture.value?.isLiked == true) {
                snackbarText = "Saqlanganlar ro'yxatidan o'chirildi!"
                drawableColorId = R.color.white
                viewModel.likedFurniture.value?.isLiked = false
            } else {
                snackbarText = "Saqlanganlar ro'yxatiga qo'shildi!"
                drawableColorId = R.color.heart_icon_color
                viewModel.likedFurniture.value?.isLiked = true
            }

            Snackbar.make(view, snackbarText, Snackbar.LENGTH_SHORT)
                .apply {
                    anchorView = view
                    show()
                }
            setViewDrawableTint(binding.likeBtn, drawableColorId)
        }

        return binding.root
    }

    override fun onStop() {
        viewModel.onStop()
        super.onStop()
    }
}