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
import com.codebyashish.foodui.models.FoodPopular
import com.codebyashish.foodui.models.FoodTypes
import kotlin.math.log


class FoodSuggestionAdapter(
    var mContext: Context,
    private var typesList: ArrayList<FoodPopular>,
    private var clickListener : Interface
) : RecyclerView.Adapter<FoodSuggestionAdapter.MyViewHolder>() {

    interface Interface {
        fun onFoodClick(pos: Int,model: FoodPopular)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_food_suggestion, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = typesList[position]

        holder.price.text = "₹ ${model.price}"
        holder.label.text = model.label
        holder.label.isSelected = true

        if (model.icon == 0) {
            holder.label.visibility = View.VISIBLE
        } else {
            holder.img.setImageResource(model.icon)
        }


        holder.itemView.setOnClickListener {

            clickListener.onFoodClick(holder.adapterPosition, model)
            notifyDataSetChanged()

        }
    }

    override fun getItemCount(): Int {
        return typesList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: AppCompatImageView = itemView.findViewById(R.id.ivFoodType)
        var label: AppCompatTextView = itemView.findViewById(R.id.tvFoodName)
        var price: AppCompatTextView = itemView.findViewById(R.id.tvFoodPrice)
    }
}
