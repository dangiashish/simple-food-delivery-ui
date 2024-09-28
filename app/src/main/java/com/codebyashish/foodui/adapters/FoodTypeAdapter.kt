package com.codebyashish.foodui.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.codebyashish.foodui.R
import com.codebyashish.foodui.models.FoodTypes
import kotlin.math.log


class FoodTypeAdapter(
    var mContext: Context,
    private var typesList: ArrayList<FoodTypes>,
    private var isTypeSelected: Boolean,
    var clickInterface: Interface
) : RecyclerView.Adapter<FoodTypeAdapter.MyViewHolder>() {

//    private var selectedItem = RecyclerView.NO_POSITION
    private var selectedItem = 0


    interface Interface {
        fun onBrandSelect(pos: Int, logo: Int, name: String?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_food_type, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = typesList[position]

        holder.label.text = model.label

        if (model.icon == 0) {
            holder.label.visibility = View.VISIBLE
        } else {
            holder.img.setImageResource(model.icon)
        }

        if (position == selectedItem) {
            holder.bgLayout.backgroundTintList = ColorStateList.valueOf(mContext.resources.getColor(R.color.themeColor))
            holder.label.setTextColor(mContext.resources.getColor(R.color.textColor))
        } else {
            holder.bgLayout.backgroundTintList = ColorStateList.valueOf(mContext.resources.getColor(R.color.gray_lite))
            holder.label.setTextColor(mContext.resources.getColor(R.color.black))
        }

        holder.itemView.setOnClickListener {
            Log.d("FoodUI", "onBindViewHolder: $isTypeSelected")
            if (isTypeSelected) {
                val previousSelectedItem = selectedItem
                selectedItem = holder.adapterPosition
                notifyItemChanged(previousSelectedItem)
                notifyItemChanged(selectedItem)
            } else {
                showToast()
            }
        }
    }

    private fun showToast() {
        Toast.makeText(mContext, "Please select vehicles types first", Toast.LENGTH_SHORT).show()
    }


    override fun getItemCount(): Int {
        return typesList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: AppCompatImageView = itemView.findViewById(R.id.ivFoodType)
        var label: AppCompatTextView = itemView.findViewById(R.id.tvFoodType)
        var bgLayout: LinearLayout = itemView.findViewById(R.id.bgLayout)
    }
}
