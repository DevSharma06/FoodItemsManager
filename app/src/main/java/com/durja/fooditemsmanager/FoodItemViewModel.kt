package com.durja.fooditemsmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.durja.fooditemsmanager.db.FoodItem
import com.durja.fooditemsmanager.db.FoodItemRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FoodItemViewModel(private val repository: FoodItemRepository) : ViewModel() {


    fun insert(foodItem: FoodItem) {
        viewModelScope.launch {
            repository.insert(foodItem)
        }
    }

    fun getFoodItemByID(id: Int) = liveData {
        repository.getFoodItemByID(id).collect() {
            emit(it)
        }
    }

    fun getAllFoodItems() = liveData {
        repository.foodItems.collect {
            emit(it)
        }
    }

    fun updateItem(foodItem: FoodItem) {
        viewModelScope.launch(IO) {
            repository.update(foodItem)
        }
    }

    fun deleteItem(id: Int) {
        viewModelScope.launch(IO) {
            repository.deleteItemByID(id)
        }
    }
}