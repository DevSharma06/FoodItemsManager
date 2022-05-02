package com.durja.fooditemsmanager

import androidx.appcompat.app.AppCompatActivity
import com.durja.fooditemsmanager.db.FoodItem
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.durja.fooditemsmanager.R
import android.content.Intent
import android.text.TextWatcher
import android.text.Editable
import androidx.lifecycle.ViewModelProvider
import com.durja.fooditemsmanager.databinding.ActivityFoodAmountLayoutBinding
import com.durja.fooditemsmanager.db.FoodItemDatabase
import com.durja.fooditemsmanager.db.FoodItemRepository
import com.durja.fooditemsmanager.db.FoodViewModelFactory
import java.lang.String

class FoodAmountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodAmountLayoutBinding
    private lateinit var foodItemViewModel: FoodItemViewModel

    private var foodItemID : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_food_amount_layout)

        val dao = FoodItemDatabase.getInstance(applicationContext).foodDao
        val repository = FoodItemRepository(dao)
        val factory = FoodViewModelFactory(repository)

        foodItemViewModel = ViewModelProvider(this, factory)[FoodItemViewModel::class.java]

        binding.lifecycleOwner = this

        val intent = intent
        foodItemID = intent.getIntExtra("FOOD_ID", 0)

        foodItemViewModel.getFoodItemByID(foodItemID).observe(this) {
            getDataAndSetFoodItem(it)
            calculateGrams(it)
        }
    }

    private fun getDataAndSetFoodItem(foodItem: FoodItem?) {
        if (foodItem != null) {
            binding.foodNameTextView.text = foodItem.foodName
            binding.oneKgTextView.text = String.valueOf(foodItem.oneKgAmount)
            binding.halfKgTextView.text = String.valueOf(foodItem.halfKgAmount)
            binding.quarterKgTextView.text = String.valueOf(foodItem.quarterKgAmount)
            binding.halfQuarterKgTextView.text = String.valueOf(foodItem.halfQuarterKgAmount)
            binding.hundredGmTextView.text = String.valueOf(foodItem.hundredGramsAmount)
            binding.fiftyGmTextView.text = String.valueOf(foodItem.fiftyGramsAmount)
        }
    }

    private fun calculateGrams(foodItem: FoodItem?) {
        if (foodItem != null) {
            binding.amountEdittext.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun afterTextChanged(editable: Editable) {
                    if (editable != null && editable.length > 0) {
                        val amount = editable.toString().toInt()
                        val oneKgAmount: Int = foodItem.oneKgAmount
                        val resultAmount = 1000 * amount / oneKgAmount
                        binding.gramsTextView.text = resultAmount.toString() + " gms"
                    } else {
                        binding.gramsTextView.text = ""
                    }
                }
            })
        }
    }
}