package com.codebyashish.foodui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.codebyashish.foodui.R
import com.codebyashish.foodui.models.FoodCart
import kotlin.math.log


class FoodCartAdapter(
    var mContext: Context,
    private var typesList: ArrayList<FoodCart>,
    private var clickListener: Interface
) : RecyclerView.Adapter<FoodCartAdapter.MyViewHolder>() {

    val TAG = "FoodCartAdapter"
    private val totalCount = 1

    interface Interface {
        fun OnFoodRemove(pos: Int, model: FoodCart)
        fun onCountAdd(pos: Int, model: FoodCart, list : ArrayList<FoodCart>)
        fun onCountRemove(pos: Int, model: FoodCart, list : ArrayList<FoodCart>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_food_in_cart, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = typesList[position]

        model.amt = model.price * model.qty

        Log.d(TAG, "onBindViewHolder: first   ${model.price}  * ${model.qty}  = ${model.amt}")

        holder.price.text = "₹ ${model.amt}"
        holder.label.text = model.label
        holder.label.isSelected = true
        holder.count.text = model.qty.toString()

        if (model.icon == 0) {
            holder.label.visibility = View.VISIBLE
        } else {
            holder.img.setImageResource(model.icon)
        }

        holder.close.setOnClickListener {
            clickListener.OnFoodRemove(holder.adapterPosition, model)
            notifyDataSetChanged()
        }


        holder.add.setOnClickListener {
            model.qty += totalCount
            holder.count.text = model.qty.toString()

            model.amt = model.qty * (model.amt / (model.qty - totalCount)) // Adjust price
            holder.price.text = "₹ ${model.amt}"


            notifyItemChanged(holder.adapterPosition)

            clickListener.onCountAdd(holder.adapterPosition, model, typesList)

        }

        holder.minus.setOnClickListener {
            if (model.qty > 1) {
                model.qty -= totalCount
                holder.count.text = model.qty.toString()

                model.amt = model.qty * (model.amt / (model.qty + totalCount))
                holder.price.text = "₹ ${model.amt}"

                clickListener.onCountRemove(holder.adapterPosition, model, typesList)
                notifyItemChanged(holder.adapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return typesList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: AppCompatImageView = itemView.findViewById(R.id.ivFoodType)
        var label: AppCompatTextView = itemView.findViewById(R.id.tvFoodName)
        var price: AppCompatTextView = itemView.findViewById(R.id.tvFoodPrice)
        var close: AppCompatImageView = itemView.findViewById(R.id.ivClose)
        var add: AppCompatImageView = itemView.findViewById(R.id.ivAdd)
        var minus: AppCompatImageView = itemView.findViewById(R.id.ivMinus)
        var count: AppCompatTextView = itemView.findViewById(R.id.tvCounts)
    }
}
