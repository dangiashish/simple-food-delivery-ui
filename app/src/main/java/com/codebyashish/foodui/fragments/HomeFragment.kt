package com.codebyashish.foodui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.codebyashish.foodui.FoodDetailsActivity
import com.codebyashish.foodui.R
import com.codebyashish.foodui.adapters.FoodPopularAdapter
import com.codebyashish.foodui.adapters.FoodTypeAdapter
import com.codebyashish.foodui.databinding.FragmentHomeBinding
import com.codebyashish.foodui.models.FoodPopular
import com.codebyashish.foodui.models.FoodTypes
import com.codebyashish.foodui.utitlity.SharedPref

class HomeFragment : Fragment(), FoodTypeAdapter.Interface, FoodPopularAdapter.Interface {

    private val binding: FragmentHomeBinding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private var foodTypeList = ArrayList<FoodTypes>()
    private var popFoods = ArrayList<FoodPopular>()
    private lateinit var context: Context
    private val isFoodTypeSelected = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        context = requireContext()
        setFoodTypes()
        setPopularFoods()
//        SharedPref.clearSharedPreferences(context)

        return binding.root
    }

    private fun setPopularFoods() {
        popFoods.add(FoodPopular(1,R.drawable.img_food_1, "Beef Burger", "70"))
        popFoods.add(FoodPopular(2,R.drawable.img_food_2, "Paneer Pizza", "180"))
        popFoods.add(FoodPopular(3,R.drawable.img_food_3, "Choco IceCream", "50"))
        popFoods.add(FoodPopular(4,R.drawable.img_food_4, "Fries", "150"))
        popFoods.add(FoodPopular(5,R.drawable.img_food_5, "Tandoori Pizza", "200"))
        popFoods.add(FoodPopular(6,R.drawable.img_food_6, "Masala Fries", "100"))
        popFoods.add(FoodPopular(7,R.drawable.img_food_7, "Chilly Donuts", "99"))

        val adapter = FoodPopularAdapter(context, popFoods, this)
        val layoutManager = GridLayoutManager(context, 2)
        binding.rvPopular.layoutManager = layoutManager
        binding.rvPopular.adapter = adapter
    }

    private fun setFoodTypes() {
        foodTypeList.add(0, FoodTypes(R.drawable.img_splash, "All"))
        foodTypeList.add(1, FoodTypes(R.drawable.img_food_1, "Burger"))
        foodTypeList.add(2, FoodTypes(R.drawable.img_food_2, "Pizza"))
        foodTypeList.add(3, FoodTypes(R.drawable.img_food_3, "Dessert"))

        val adapter = FoodTypeAdapter(context, foodTypeList, isFoodTypeSelected, this)
        binding.rvFoodTypes.adapter = adapter

    }

    override fun onBrandSelect(pos: Int, logo: Int, name: String?) {

    }

    override fun OnFoodClick(pos: Int, model: FoodPopular) {
        startActivity(Intent(context, FoodDetailsActivity::class.java).putExtra("model", model).putParcelableArrayListExtra("popFoods", popFoods))
    }

}