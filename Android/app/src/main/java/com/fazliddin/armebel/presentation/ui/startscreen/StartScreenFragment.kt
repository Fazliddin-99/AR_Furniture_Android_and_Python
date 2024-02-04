package com.fazliddin.armebel.presentation.ui.startscreen

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fazliddin.armebel.presentation.common.FurnituresListAdapter
import com.fazliddin.armebel.databinding.FragmentStartScreenBinding
import dagger.hilt.android.AndroidEntryPoint

private const val FADE_ANIM_DURATION = 1000L

@AndroidEntryPoint
class StartScreenFragment : Fragment() {

    private val viewModel: StartScreenViewModel by viewModels()
    private lateinit var furnitureListAdapter: FurnituresListAdapter
    private lateinit var categoriesListAdapter: CategoriesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentStartScreenBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        if (viewModel.dataWasLoadedBefore) {
            binding.scrollViewData.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }

        furnitureListAdapter = FurnituresListAdapter(FurnituresListAdapter.OnClickListener {
            findNavController().navigate(
                StartScreenFragmentDirections.actionStartScreenFragmentToFurnitureDetailsFragment(
                    it
                )
            )
        })

        binding.furnitureRecyclerView.adapter = furnitureListAdapter

        viewModel.dataIsLoaded.observe(viewLifecycleOwner) {
            if (it == true && !viewModel.dataWasLoadedBefore)
                crossfade(binding.scrollViewData, binding.progressBar)
            viewModel.dataWasLoadedBefore = true
        }

        categoriesListAdapter = CategoriesListAdapter(CategoriesListAdapter.OnClickListener {
            findNavController().navigate(
                StartScreenFragmentDirections.actionStartScreenFragmentToTypesFragment(
                    it.id
                )
            )
        })

        binding.categoriesRecyclerView.adapter = categoriesListAdapter

        binding.viewAllBtn.setOnClickListener {
            findNavController().navigate(
                StartScreenFragmentDirections.actionStartScreenFragmentToCategoriesFragment()
            )
        }

        return binding.root
    }


    private fun crossfade(viewComeUp: View, viewGoOut: View) {
        viewComeUp.apply {
            // Set the content view to 0% opacity but visible, so that it is visible
            // (but fully transparent) during the animation.
            alpha = 0f
            visibility = View.VISIBLE

            // Animate the content view to 100% opacity, and clear any animation
            // listener set on the view.
            animate()
                .alpha(1f)
                .setDuration(FADE_ANIM_DURATION)
                .setListener(null)
        }
        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        viewGoOut.animate()
            .alpha(0f)
            .setDuration(FADE_ANIM_DURATION)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    viewGoOut.visibility = View.GONE
                }
            })
    }
}
