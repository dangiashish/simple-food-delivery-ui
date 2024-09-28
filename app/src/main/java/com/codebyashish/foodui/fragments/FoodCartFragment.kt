package com.codebyashish.foodui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.codebyashish.foodui.adapters.FoodCartAdapter
import com.codebyashish.foodui.databinding.FragmentFoodCartBinding
import com.codebyashish.foodui.models.BinsInventoryModel
import com.codebyashish.foodui.models.FoodCart
import com.codebyashish.foodui.models.SelectedBinsModel
import com.codebyashish.foodui.utitlity.Constants
import com.codebyashish.foodui.utitlity.SharedPref
import org.json.JSONArray

class FoodCartFragment : Fragment(), FoodCartAdapter.Interface {
    private val binding: FragmentFoodCartBinding by lazy {
        FragmentFoodCartBinding.inflate(
            layoutInflater
        )
    }
    private lateinit var context: Context
    private val TAG = "FoodCartLogs"
    private val cartList = ArrayList<FoodCart>()
    private val invList = ArrayList<BinsInventoryModel>()
    private var totalPrice: Int = 0
    private lateinit var array: String
    private lateinit var data: String
    private lateinit var jsonArray: JSONArray
    var hashMap: HashMap<Any, SelectedBinsModel> = HashMap()
    private lateinit var adapter: FoodCartAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        context = requireContext()

        array = SharedPref.getString(context, Constants.cartArray, "")
        if (array.isNotEmpty()) {
            jsonArray = JSONArray(array)
            updateUI(jsonArray)
            binding.tvTotalPrice.text = "₹ $totalPrice"
        }

        adapter = FoodCartAdapter(requireActivity(), cartList, this)
//        val adapter = BinsAdapter(requireActivity(), invList, this)
        val layoutManager = GridLayoutManager(context, 1)
        binding.rvCartItems.layoutManager = layoutManager
        binding.rvCartItems.adapter = adapter


        return binding.root
    }

    override fun OnFoodRemove(pos: Int, model: FoodCart) {
        totalPrice -= (model.price * model.qty)
        binding.tvTotalPrice.text = "₹ $totalPrice"
        jsonArray.remove(pos)
        updateUI(jsonArray)
        SharedPref.setString(context, Constants.cartArray, jsonArray.toString())
    }

    override fun onCountAdd(pos: Int, model: FoodCart, list: ArrayList<FoodCart>) {

        totalPrice += (model.price)
        binding.tvTotalPrice.text = "₹ $totalPrice"
    }

    override fun onCountRemove(pos: Int, model: FoodCart, list: ArrayList<FoodCart>) {
        totalPrice -= model.price
        binding.tvTotalPrice.text = "₹ $totalPrice"
    }


    private fun updateUI(jsonArray: JSONArray) {
        cartList.clear()
        totalPrice = 0
        for (i in 0 until jsonArray.length()) {
            val innerArrayString = jsonArray.optString(i)

            val innerArray = JSONArray(innerArrayString)

            for (j in 0 until innerArray.length()) {
                val jsonObject = innerArray.optJSONObject(j)
                if (jsonObject != null) {
                    val foodCart = FoodCart()

                    foodCart.label = jsonObject.optString("label")
                    foodCart.desc = jsonObject.optString("desc")
                    foodCart.price = jsonObject.optInt("price")
                    foodCart.icon = jsonObject.optInt("icon")
                    foodCart.id = jsonObject.optInt("id")
                    foodCart.qty = jsonObject.optInt("counts")
                    foodCart.amt = jsonObject.optInt("amt") * jsonObject.optInt("counts")

                    cartList.add(foodCart)

                    totalPrice += (jsonObject.optInt("price") * jsonObject.optInt("counts"))

                }
            }
        }

        binding.tvCartCounts.text = "${cartList.size} items in cart"

    }

}