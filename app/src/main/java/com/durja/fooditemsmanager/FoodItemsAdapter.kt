package com.durja.fooditemsmanager

import androidx.recyclerview.widget.RecyclerView
import com.durja.fooditemsmanager.FoodItemsAdapter.FoodItemViewHolder
import com.durja.fooditemsmanager.db.FoodItem
import android.view.ViewGroup
import android.view.LayoutInflater
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.durja.fooditemsmanager.databinding.FoodItemLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.lang.String
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
               /* val intent = Intent(view.context, FoodAmountActivity::class.java)
                intent.putExtra("FOOD_ID", foodItem.id)
                view.context.startActivity(intent)*/
                showBottomSheetDialog(view, foodItem)
            }

            binding.editButton.setOnClickListener {
                editClickListener(foodItem)
            }

            binding.deleteButton.setOnClickListener {
                deleteClickListener(foodItem.id)
            }
        }
    }

    private fun showBottomSheetDialog(view: View, foodItem: FoodItem) {
        val bottomSheetDialog = BottomSheetDialog(view.context)
        bottomSheetDialog.setContentView(R.layout.food_bottom_sheet_dialog)

        val foodNameTextView: TextView? = bottomSheetDialog.findViewById(R.id.food_name_textView)
        val oneKgTextView: TextView? = bottomSheetDialog.findViewById(R.id.one_kg_textView)
        val halfKgTextView: TextView? = bottomSheetDialog.findViewById(R.id.half_kg_textView)
        val quarterKgTextView: TextView? = bottomSheetDialog.findViewById(R.id.quarter_kg_textView)
        val halfQuarterKgTextView: TextView? = bottomSheetDialog.findViewById(R.id.half_quarter_kg_textView)
        val hundredGmTextView: TextView? = bottomSheetDialog.findViewById(R.id.hundred_gm_textView)
        val fiftyGmTextView: TextView? = bottomSheetDialog.findViewById(R.id.fifty_gm_textView)
        val gramsTextView: TextView? = bottomSheetDialog.findViewById(R.id.grams_textView)

        val amountEdittext: EditText? = bottomSheetDialog.findViewById(R.id.amount_edittext)

        foodNameTextView!!.text  = "Item name: " + foodItem.foodName
        oneKgTextView!!.text = String.valueOf("Rs: ${foodItem.oneKgAmount}")
        halfKgTextView!!.text = String.valueOf("Rs: ${foodItem.halfKgAmount}")
        quarterKgTextView!!.text = String.valueOf("Rs: ${foodItem.quarterKgAmount}")
        halfQuarterKgTextView!!.text = String.valueOf("Rs: ${foodItem.halfQuarterKgAmount}")
        hundredGmTextView!!.text = String.valueOf("Rs: ${foodItem.hundredGramsAmount}")
        fiftyGmTextView!!.text = String.valueOf("Rs: ${foodItem.fiftyGramsAmount}")

        amountEdittext!!.addTextChangedListener(object : TextWatcher {
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
                    gramsTextView!!.text = resultAmount.toString() + " gms"
                } else {
                    gramsTextView!!.text = ""
                }
            }
        })

        bottomSheetDialog.show()
    }
}