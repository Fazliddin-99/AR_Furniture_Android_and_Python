package com.fazliddin.armebel.presentation.ui.likedfurniture

import androidx.lifecycle.ViewModel
import com.fazliddin.armebel.domain.repository.FurnitureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LikedFurnitureViewModel @Inject constructor(
    repository: FurnitureRepository,
) : ViewModel() {
    var furnitureList = repository.getLikedFurniture()
}