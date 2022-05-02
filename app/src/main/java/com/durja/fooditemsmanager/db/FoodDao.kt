package com.durja.fooditemsmanager.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {

    @Insert
    suspend fun insertFoodItem(foodItem: FoodItem): Long

    @Update
    suspend fun updateFoodItem(foodItem: FoodItem): Int

    @Delete
    suspend fun deleteFoodItem(foodItem: FoodItem): Int

    @Query("SELECT * FROM food_item_table WHERE id =:id")
    fun getFoodItemByID(id: Int): Flow<FoodItem>

    @Query("SELECT * FROM food_item_table")
    fun getAllFoodItems(): Flow<List<FoodItem>>

    @Query("DELETE FROM food_item_table WHERE id =:id")
    fun deleteItemByID(id: Int): Int
}