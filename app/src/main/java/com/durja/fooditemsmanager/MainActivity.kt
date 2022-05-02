package com.durja.fooditemsmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.durja.fooditemsmanager.databinding.ActivityMainBinding
import com.durja.fooditemsmanager.db.FoodItem
import com.durja.fooditemsmanager.db.FoodItemDatabase
import com.durja.fooditemsmanager.db.FoodItemRepository
import com.durja.fooditemsmanager.db.FoodViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var foodItemViewModel: FoodItemViewModel
    private lateinit var adapter: FoodItemsAdapter
    private val foodItemArrayList = ArrayList<FoodItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dao = FoodItemDatabase.getInstance(applicationContext).foodDao
        val repository = FoodItemRepository(dao)
        val factory = FoodViewModelFactory(repository)

        foodItemViewModel = ViewModelProvider(this, factory)[FoodItemViewModel::class.java]

        binding.lifecycleOwner = this

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.foodItemRecyclerView.layoutManager = GridLayoutManager(this, 3)
        adapter = FoodItemsAdapter(
            this::deleteItemClicked, this::editItemClicked
        )
        /*binding.foodItemRecyclerView.addItemDecoration(object : ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view!!) // item position
                val spanCount = 2
                val spacing = 10 //spacing between views in grid
                if (position >= 0) {
                    val column = position % spanCount // item column
                    outRect.left =
                        spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                    outRect.right =
                        (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
                    if (position < spanCount) { // top edge
                        outRect.top = spacing
                    }
                    outRect.bottom = spacing // item bottom
                } else {
                    outRect.left = 0
                    outRect.right = 0
                    outRect.top = 0
                    outRect.bottom = 0
                }
            }
        })*/
        binding.foodItemRecyclerView.adapter = adapter

        displayFoodItemsList()
    }

    private fun displayFoodItemsList() {
        foodItemViewModel.getAllFoodItems().observe(this) {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu?.findItem(R.id.actionSearch)

        val searchView = searchItem?.actionView as SearchView

        searchView.setQuery("", false)
        searchItem.collapseActionView()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String?): Boolean {
                filterSearch(query)
                return true
            }

            override fun onQueryTextSubmit(text: String?): Boolean {
                return true
            }
        })

        return true
    }

    private fun filterSearch(query: String?) {
        foodItemViewModel.getAllFoodItems().observe(this) {
            if (query.isNullOrEmpty()) {
                adapter.setList(it)
                adapter.notifyDataSetChanged()
            } else {
                val filteredList = ArrayList<FoodItem>()
                val foodItemsList = it
                for (foodItem in foodItemsList) {
                    if (foodItem.foodName.lowercase().contains(query.toString().lowercase())) {
                        filteredList.add(foodItem)
                    }
                }

                if (filteredList.isNotEmpty()) {
                    adapter.setList(filteredList)
                    adapter.notifyDataSetChanged()
                } else {
                    adapter.setList(ArrayList())
                    adapter.notifyDataSetChanged()
//                    Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun editItemClicked(foodItem: FoodItem) {
        editItem(foodItem)
    }

    private fun deleteItemClicked(id: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Delete")
        builder.setMessage("Are you sure you want to delete this item?")
        builder.setNegativeButton("Cancel") { dialogInterface, i ->
            dialogInterface.dismiss()
        }
        builder.setPositiveButton("Delete") { dialogInterface, i ->
            foodItemViewModel.deleteItem(id)
        }

        val dialog = builder.create()
        dialog.show()
    }

    fun addFoodItem(view: View?) {
        showDialog(null)
    }

    private fun editItem(foodItem: FoodItem) {
        showDialog(foodItem)
    }

    private fun showDialog(foodItem: FoodItem?) {
        val layoutInflater = LayoutInflater.from(this)
        val dialogView = layoutInflater.inflate(R.layout.add_item_layout, null)
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(dialogView)

        val nameEditText: TextInputEditText = dialogView.findViewById(R.id.food_name_edittext)
        val oneKgEditText: TextInputEditText = dialogView.findViewById(R.id.one_kg_edittext)
        val halfKgEditText: TextInputEditText = dialogView.findViewById(R.id.half_kg_edittext)
        val quarterKgEditText: TextInputEditText = dialogView.findViewById(R.id.quarter_kg_edittext)
        val halfQuarterKgEditText: TextInputEditText =
            dialogView.findViewById(R.id.half_quarter_kg_edittext)
        val hundredGmEditText: TextInputEditText =
            dialogView.findViewById(R.id.hundred_gms_edittext)
        val fiftyGmEditText: TextInputEditText = dialogView.findViewById(R.id.fifty_gms_edittext)

        val buttonText = if (foodItem == null) "Add" else "Update"

        if (foodItem != null) {
            nameEditText.setText(foodItem.foodName)
            oneKgEditText.setText(foodItem.oneKgAmount.toString())
            halfKgEditText.setText(foodItem.halfKgAmount.toString())
            quarterKgEditText.setText(foodItem.quarterKgAmount.toString())
            halfQuarterKgEditText.setText(foodItem.halfQuarterKgAmount.toString())
            hundredGmEditText.setText(foodItem.hundredGramsAmount.toString())
            fiftyGmEditText.setText(foodItem.fiftyGramsAmount.toString())
        }

        builder.setPositiveButton(buttonText) { dialogInterface, i ->
            when {
                nameEditText.text.isNullOrEmpty() -> {
                    Toast.makeText(this, "Please enter food name", Toast.LENGTH_SHORT).show()
                }
                oneKgEditText.text.isNullOrEmpty() -> {
                    Toast.makeText(this, "Please enter one kg amount", Toast.LENGTH_SHORT).show()
                }
                halfKgEditText.text.isNullOrEmpty() -> {
                    Toast.makeText(this, "Please enter half kg amount", Toast.LENGTH_SHORT).show()
                }
                quarterKgEditText.text.isNullOrEmpty() -> {
                    Toast.makeText(this, "Please enter quarter kg amount", Toast.LENGTH_SHORT)
                        .show()
                }
                halfQuarterKgEditText.text.isNullOrEmpty() -> {
                    Toast.makeText(this, "Please enter half quarter kg amount", Toast.LENGTH_SHORT)
                        .show()
                }
                /*hundredGmEditText.text.isNullOrEmpty() -> {
                    Toast.makeText(this, "Please enter 100 gms amount", Toast.LENGTH_SHORT).show()
                }
                fiftyGmEditText.text.isNullOrEmpty() -> {
                    Toast.makeText(this, "Please enter 50 gms amount", Toast.LENGTH_SHORT).show()
                }*/
                else -> {
                    if (foodItem == null) {
                        foodItemViewModel.insert(
                            FoodItem(
                                0,
                                nameEditText.text.toString(),
                                oneKgEditText.text.toString().toInt(),
                                halfKgEditText.text.toString().toInt(),
                                quarterKgEditText.text.toString().toInt(),
                                halfQuarterKgEditText.text.toString().toInt(),
                                if (hundredGmEditText.text.toString().isEmpty()) 0
                                    else hundredGmEditText.text.toString().toInt(),
                                if (fiftyGmEditText.text.toString().isEmpty()) 0
                                    else fiftyGmEditText.text.toString().toInt()
                            )
                        )
                    } else {
                        foodItemViewModel.updateItem(
                            FoodItem(
                                foodItem.id,
                                nameEditText.text.toString(),
                                oneKgEditText.text.toString().toInt(),
                                halfKgEditText.text.toString().toInt(),
                                quarterKgEditText.text.toString().toInt(),
                                halfQuarterKgEditText.text.toString().toInt(),
                                hundredGmEditText.text.toString().toInt(),
                                fiftyGmEditText.text.toString().toInt()
                            )
                        )
                    }
                    dialogInterface.dismiss()
                }
            }
        }
        builder.setNegativeButton("Cancel") { dialogInterface, i ->
            dialogInterface.dismiss()
        }
        val dialog = builder.create()
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        dialog.show()
    }
}