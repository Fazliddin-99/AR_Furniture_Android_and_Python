package com.fazliddin.armebel.presentation.ui.ar

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.fazliddin.armebel.R
import com.fazliddin.armebel.databinding.FragmentArFurnitureBinding
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode

class FurnitureArFragment : Fragment() {

    private lateinit var arFragment: ArFragment
    private lateinit var binding: FragmentArFurnitureBinding
    private var modelUriAddress: String? = null
    private var modelRenderable: ModelRenderable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArFurnitureBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val args = FurnitureArFragmentArgs.fromBundle(requireArguments())
        modelUriAddress = args.modelUri

        loadModel(Uri.parse(modelUriAddress))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arFragment = childFragmentManager.findFragmentById(R.id.arFragment) as ArFragment

        arFragment.setOnTapArPlaneListener { hitResult, _, _ ->
            if (modelRenderable != null)
                addNodeToScene(hitResult.createAnchor())
        }
    }

    private fun loadModel(modelUri: Uri) {
        val renderableSource = RenderableSource.builder()
            .setSource(requireContext(), modelUri, RenderableSource.SourceType.GLB)
            .setRecenterMode(RenderableSource.RecenterMode.ROOT)
            .build()

        ModelRenderable.builder()
            .setSource(requireContext(), renderableSource)
            .setRegistryId(modelUri)
            .build()
            .thenAccept {
                modelRenderable = it
            }
            .exceptionally {
                Toast.makeText(requireContext(), "Error loading the 3d model", Toast.LENGTH_LONG)
                    .show()
                null
            }
    }

    private fun addNodeToScene(anchor: Anchor) {
        val anchorNode = AnchorNode(anchor)
        TransformableNode(arFragment.transformationSystem).apply {
            renderable = modelRenderable
            scaleController.isEnabled = false
            setParent(anchorNode)
        }

        arFragment.arSceneView.scene.addChild(anchorNode)
    }
}