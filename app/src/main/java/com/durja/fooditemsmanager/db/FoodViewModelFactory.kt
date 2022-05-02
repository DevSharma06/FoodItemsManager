package com.durja.fooditemsmanager.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.durja.fooditemsmanager.FoodItemViewModel
import java.lang.IllegalArgumentException

class FoodViewModelFactory(private val repository: FoodItemRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FoodItemViewModel::class.java)) {
            return FoodItemViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}