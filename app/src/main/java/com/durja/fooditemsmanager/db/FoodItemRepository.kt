package com.durja.fooditemsmanager.db

import kotlinx.coroutines.flow.Flow

class FoodItemRepository(private val foodDao: FoodDao) {

    val foodItems = foodDao.getAllFoodItems()

    suspend fun insert(foodItem: FoodItem): Long {
        return foodDao.insertFoodItem(foodItem)
    }

    suspend fun update(foodItem: FoodItem): Int {
        return foodDao.updateFoodItem(foodItem)
    }

    suspend fun delete(foodItem: FoodItem): Int {
        return foodDao.deleteFoodItem(foodItem)
    }

    fun getFoodItemByID(id: Int) : Flow<FoodItem> {
        return foodDao.getFoodItemByID(id)
    }

    suspend fun deleteItemByID(id: Int) : Int {
        return foodDao.deleteItemByID(id)
    }
}