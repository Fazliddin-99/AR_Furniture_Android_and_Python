package com.fazliddin.armebel.presentation.ui.details

import androidx.lifecycle.*
import com.fazliddin.armebel.data.database.models.LikedFurniture
import com.fazliddin.armebel.data.remote.dto.BaseFurniture
import com.fazliddin.armebel.data.remote.dto.Furniture
import com.fazliddin.armebel.domain.repository.FurnitureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FurnitureDetailsViewModel @Inject constructor(
    private val repository: FurnitureRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var furniture: BaseFurniture = Furniture()

    init {
        savedStateHandle.get<BaseFurniture>("furniture")?.let {
            furniture = it
        }
    }

    private val _furnitureLD = MutableLiveData(furniture)
    val furnitureLD: LiveData<BaseFurniture>
        get() = _furnitureLD

    private val _likedFurniture = MutableLiveData<LikedFurniture?>()
    val likedFurniture: LiveData<LikedFurniture?>
        get() = _likedFurniture

    init {
        onStart()
    }

    fun addToCart() {
        furnitureLD.value?.let { furniture ->
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    repository.addToCart(furniture)
                }
            }
        }
    }

    private fun onStart() {
        viewModelScope.launch {
            _likedFurniture.value = withContext(Dispatchers.IO) {
                _furnitureLD.value?.let {
                    var furniture = repository.getLikedFurnitureById(it.id)
                    if (furniture == null)
                        furniture = (it as Furniture).toLikedFurniture()
                    furniture
                }
            }
        }
    }

    fun onStop() {
        likedFurniture.value?.let {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    if (it.isLiked) {
                        repository.addLikedFurniture(it)
                    } else {
                        repository.removeLikedFurniture(it)
                    }
                }
            }
        }
    }
}