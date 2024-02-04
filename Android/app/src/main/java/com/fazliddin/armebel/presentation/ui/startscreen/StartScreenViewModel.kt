package com.fazliddin.armebel.presentation.ui.startscreen

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fazliddin.armebel.data.remote.dto.Furniture
import com.fazliddin.armebel.data.remote.dto.Category
import com.fazliddin.armebel.domain.repository.FurnitureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel @Inject constructor(
    private val repository: FurnitureRepository,
    @ApplicationContext app: Context
) : AndroidViewModel(app as Application) {

    private val _furnitureList = MutableLiveData<List<Furniture>?>()
    val furnitureList: LiveData<List<Furniture>?>
        get() = _furnitureList

    private val _categoriesList = MutableLiveData<List<Category>>()
    val categoriesList: LiveData<List<Category>>
        get() = _categoriesList

    private val _dataIsLoaded = MutableLiveData<Boolean>()
    val dataIsLoaded: LiveData<Boolean>
        get() = _dataIsLoaded

    var dataWasLoadedBefore: Boolean = false

    init {
        getFurniture()
        getCategories()
    }

    private var categoriesLoaded = false
    private var furnitureLoaded = false

    private fun getCategories() {
        viewModelScope.launch {
            _categoriesList.value = withContext(Dispatchers.IO) {
                repository.getCategories()
            }
        }

        categoriesLoaded = true
        checkDataLoaded()
    }

    private fun getFurniture() {
        viewModelScope.launch {
            _furnitureList.value = withContext(Dispatchers.IO) {
                repository.getFurniture()
            }
        }

        furnitureLoaded = true
        checkDataLoaded()
    }

    private fun checkDataLoaded() {
        if (furnitureLoaded && categoriesLoaded) {
            _dataIsLoaded.value = true
        }
    }
}