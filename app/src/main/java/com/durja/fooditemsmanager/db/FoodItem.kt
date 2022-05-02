package com.durja.fooditemsmanager.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_item_table")
data class FoodItem(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "food_name")
    var foodName: String,

    @ColumnInfo(name = "one_kg_amount")
    var oneKgAmount: Int,

    @ColumnInfo(name = "half_kg_amount")
    var halfKgAmount: Int,

    @ColumnInfo(name = "quarter_kg_amount")
    var quarterKgAmount: Int,

    @ColumnInfo(name = "half_quarter_kg_amount")
    var halfQuarterKgAmount: Int,

    @ColumnInfo(name = "hundred_gms_amount")
    var hundredGramsAmount: Int,

    @ColumnInfo(name = "fifty_gms_amount")
    var fiftyGramsAmount: Int
)
/*public FoodItem(String foodName, int oneKg, int halfKg, int quarterKg, int halfQuarterKg, int hundredGrams, int fiftyGrams) {
   this.foodName = foodName;
   this.oneKg = oneKg;
   this.halfKg = halfKg;
   this.quarterKg = quarterKg;
   this.halfQuarterKg = halfQuarterKg;
   this.hundredGrams = hundredGrams;
   this.fiftyGrams = fiftyGrams;
}

public String getFoodName() {
   return foodName;
}

public void setFoodName(String foodName) {
   this.foodName = foodName;
}

public int getOneKg() {
   return oneKg;
}

public void setOneKg(int oneKg) {
   this.oneKg = oneKg;
}

public int getHalfKg() {
   return halfKg;
}

public void setHalfKg(int halfKg) {
   this.halfKg = halfKg;
}

public int getQuarterKg() {
   return quarterKg;
}

public void setQuarterKg(int quarterKg) {
   this.quarterKg = quarterKg;
}

public int getHalfQuarterKg() {
   return halfQuarterKg;
}

public void setHalfQuarterKg(int halfQuarterKg) {
   this.halfQuarterKg = halfQuarterKg;
}

public int getHundredGrams() {
   return hundredGrams;
}

public void setHundredGrams(int hundredGrams) {
   this.hundredGrams = hundredGrams;
}

public int getFiftyGrams() {
   return fiftyGrams;
}

public void setFiftyGrams(int fiftyGrams) {
   this.fiftyGrams = fiftyGrams;
}*/
