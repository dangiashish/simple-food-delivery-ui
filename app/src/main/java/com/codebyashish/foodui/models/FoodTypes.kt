package com.codebyashish.foodui.models

class FoodTypes(
    var icon : Int = 0,
    var label : String = ""
) {

    private var isSelected = false

    fun isSelected(): Boolean {
        return isSelected
    }

    fun setSelected(selected: Boolean) {
        isSelected = selected
    }


}