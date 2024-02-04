package com.fazliddin.armebel.presentation.ui.types

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fazliddin.armebel.data.remote.dto.Furniture
import com.fazliddin.armebel.data.remote.dto.TypeFurniture
import com.fazliddin.armebel.domain.repository.FurnitureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TypesViewModel @Inject constructor(
    private val repository: FurnitureRepository
) : ViewModel() {

    private var categoryId: Long = 0

    private val _furnitureList = MutableLiveData<List<Furniture>?>()
    val furnitureList: LiveData<List<Furniture>?>
        get() = _furnitureList

    private val _typesList = MutableLiveData<List<TypeFurniture>>()
    val typesList: LiveData<List<TypeFurniture>>
        get() = _typesList


    private fun getTypes() {
        viewModelScope.launch {
            _typesList.value = withContext(Dispatchers.IO) {
                repository.getTypes(categoryId = categoryId)
            }
        }
    }

    private fun getFurniture() {
        viewModelScope.launch {
            _furnitureList.value = withContext(Dispatchers.IO) {
                repository.getFurniture(categoryId = categoryId)
            }
        }
    }

    fun setup(categoryId: Long) {
        if (this.categoryId != categoryId) {
            this.categoryId = categoryId
            getTypes()
            getFurniture()

        }
    }
}