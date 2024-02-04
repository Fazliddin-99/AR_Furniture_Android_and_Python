package com.fazliddin.armebel.presentation.ui.furniturebytype

import androidx.lifecycle.*
import com.fazliddin.armebel.data.remote.dto.BaseFurniture
import com.fazliddin.armebel.data.repository.FurnitureRepositoryImpl
import com.fazliddin.armebel.data.remote.dto.Furniture
import com.fazliddin.armebel.domain.repository.FurnitureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FurnitureByTypeViewModel @Inject constructor(
    private val repository: FurnitureRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var typeId: Long? = null

    init {
        savedStateHandle.get<Long>("typeId")?.let {
            typeId = it
        }
    }

    private val _furnitureList = MutableLiveData<List<Furniture>?>()
    val furnitureList: LiveData<List<Furniture>?>
        get() = _furnitureList

    init {
        viewModelScope.launch {
            _furnitureList.value = withContext(Dispatchers.IO) {
                repository.getFurniture(typeId = typeId)
            }
        }
    }


}