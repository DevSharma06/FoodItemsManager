package com.durja.fooditemsmanager

import androidx.recyclerview.widget.RecyclerView
import com.durja.fooditemsmanager.FoodItemsAdapter.FoodItemViewHolder
import com.durja.fooditemsmanager.db.FoodItem
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.durja.fooditemsmanager.R
import android.content.Intent
import android.view.View
import com.durja.fooditemsmanager.FoodAmountActivity
import com.durja.fooditemsmanager.databinding.FoodItemLayoutBinding
import java.util.ArrayList

class FoodItemsAdapter(
    private val deleteClickListener: (Int) -> Unit,
    private val editClickListener: (FoodItem) -> Unit
) : RecyclerView.Adapter<FoodItemViewHolder>() {

    private val foodItemArrayList = ArrayList<FoodItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutBinding: FoodItemLayoutBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.food_item_layout,
            parent,
            false
        )
        return FoodItemViewHolder(layoutBinding)
    }

    override fun onBindViewHolder(holder: FoodItemViewHolder, position: Int) {
        holder.bind(foodItemArrayList[position], deleteClickListener, editClickListener)
    }

    override fun getItemCount(): Int {
        return foodItemArrayList.size
    }

    fun setList(foodItemArrayList: List<FoodItem>?) {
        this.foodItemArrayList.clear()
        this.foodItemArrayList.addAll(foodItemArrayList!!)
    }

    inner class FoodItemViewHolder(private val binding: FoodItemLayoutBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {
        fun bind(foodItem: FoodItem,deleteClickListener: (Int) -> Unit, editClickListener: (FoodItem) -> Unit) {
            binding.foodItemTextView.text = foodItem.foodName

            binding.foodCardView.setOnClickListener { view: View ->
                val intent = Intent(view.context, FoodAmountActivity::class.java)
                intent.putExtra("FOOD_ID", foodItem.id)
                view.context.startActivity(intent)
            }

            binding.editButton.setOnClickListener {
                editClickListener(foodItem)
            }

            binding.deleteButton.setOnClickListener {
                deleteClickListener(foodItem.id)
            }
        }
    }
}