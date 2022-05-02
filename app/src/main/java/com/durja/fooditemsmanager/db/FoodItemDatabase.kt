package com.durja.fooditemsmanager.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FoodItem::class], version = 1)
abstract class FoodItemDatabase : RoomDatabase() {

    abstract val foodDao: FoodDao

    companion object {
        @Volatile
        private var INSTANCE : FoodItemDatabase? = null
        fun getInstance(context: Context) : FoodItemDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FoodItemDatabase::class.java,
                        "food_item_database"
                    ).build()
                }
                return instance
            }
        }
    }
}