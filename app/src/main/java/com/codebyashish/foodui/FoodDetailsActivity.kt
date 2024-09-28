package com.codebyashish.foodui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.codebyashish.foodui.adapters.FoodSuggestionAdapter
import com.codebyashish.foodui.databinding.ActivityFoodDetailsBinding
import com.codebyashish.foodui.models.FoodPopular
import com.codebyashish.foodui.utitlity.Constants
import com.codebyashish.foodui.utitlity.SharedPref
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class FoodDetailsActivity : AppCompatActivity(), FoodSuggestionAdapter.Interface {
    private val binding: ActivityFoodDetailsBinding by lazy {
        ActivityFoodDetailsBinding.inflate(
            layoutInflater
        )
    }
    private var popFoods = ArrayList<FoodPopular>()
    private lateinit var context: Context
    private val TAG = "FoodDetails"
    private var itemCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        context = this

        val model: FoodPopular = intent.getParcelableExtra<FoodPopular>("model") as FoodPopular
        popFoods = intent.getParcelableArrayListExtra("popFoods")!!


        binding.ivFoodType.setImageResource(model.icon)
        binding.tvFoodName.text = model.label
        binding.tvFoodPrice.text = "₹ ${model.price}"


        binding.tvCounts.text = itemCount.toString()

        binding.ivAdd.setOnClickListener {
            model.counts += itemCount
            binding.tvCounts.text = model.counts.toString()
            val price : Int = (model.price).toInt() * (model.counts)
            binding.tvFoodPrice.text = "₹ ${price}"
        }

        binding.ivMinus.setOnClickListener {
            if (model.counts > 1) {
                model.counts -= itemCount
                binding.tvCounts.text = model.counts.toString()
                val price : Int = (model.price).toInt() * (model.counts)
                binding.tvFoodPrice.text = "₹ ${price}"
            }
        }

        binding.btnAddCart.setOnClickListener {
            val oldData = SharedPref.getString(context, Constants.cartArray, "")
            val jsonArray = if (oldData.isNotEmpty()) {
                JSONArray(oldData)
            } else {
                JSONArray()
            }

//            for (i in 0 until jsonArray.length()) {
//                val innerArrayString = jsonArray.optString(i)
//
//                val innerArray = JSONArray(innerArrayString)
//                Log.e(TAG, "onCreate: oldData ->  ${innerArray}", )
//
//                for (j in 0 until innerArray.length()){
//                    val jsonObject = innerArray.optJSONObject(j)
//
//                    val key = jsonObject.optString("id")
//
//                    Log.i(TAG, "onCreate: keys -> $key")
//
//                    if (key == (model.id).toString()){
////                        Toast.makeText(context, "Same", Toast.LENGTH_SHORT).show()
////                        model.counts += itemCount
//                    } else {
//
//                    }
//
//                }
//            }

            val gson = Gson()
            val foodList = arrayListOf(model)
            val array = gson.toJson(foodList)
            jsonArray.put(array)

            SharedPref.setString(context, Constants.cartArray, jsonArray.toString())

            startActivity(Intent(context, MainActivity::class.java).putExtra("index", "2"))
            finish()
        }


        // for suggestions
        val adapter = FoodSuggestionAdapter(context, popFoods, this)
        binding.rvFoodSuggestion.adapter = adapter

    }

    override fun onFoodClick(pos: Int, model: FoodPopular) {
        val oldData = SharedPref.getString(context, Constants.cartArray, "")
        val jsonArray = if (oldData.isNotEmpty()) {
            JSONArray(oldData)
        } else {
            JSONArray()
        }

        val gson = Gson()
        val foodList = arrayListOf(model)
        val array = gson.toJson(foodList)
        jsonArray.put(array)

        SharedPref.setString(context, Constants.cartArray, jsonArray.toString())

        popFoods.remove(model)
    }
}