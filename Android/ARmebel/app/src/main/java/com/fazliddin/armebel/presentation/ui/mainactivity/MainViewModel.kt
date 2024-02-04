package com.fazliddin.armebel.presentation.ui.mainactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fazliddin.armebel.data.repository.FurnitureRepositoryImpl
import com.fazliddin.armebel.data.database.FurnitureDatabaseDao
import com.fazliddin.armebel.domain.repository.FurnitureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    repository: FurnitureRepository,
) : ViewModel() {
    val cartItemCount: LiveData<Int> = repository.getFurnitureCountOnCart() ?: MutableLiveData(0)
}