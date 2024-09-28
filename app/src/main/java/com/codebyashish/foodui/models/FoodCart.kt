package com.codebyashish.foodui.models

import android.os.Parcel
import android.os.Parcelable

class FoodCart(
    var id : Int =0,
    var icon: Int = 0,
    var label: String = "",
    var price: Int = 0
) : Parcelable {

    private var isSelected: Boolean = false
    var rate = 0.0
    var desc = ""
    var qty = 1
    var amt = price * qty

    private var selectedQuantity = 0

    fun getSelectedQuantity(): Int {
        return selectedQuantity
    }

    fun setSelectedQuantity(selectedQuantity: Int) {
        this.selectedQuantity = selectedQuantity
    }

    fun isSelected(): Boolean {
        return isSelected
    }

    fun setSelected(selected: Boolean) {
        isSelected = selected
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt()
    ) {
        isSelected = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(icon)
        parcel.writeString(label)
        parcel.writeInt(price)
        parcel.writeInt(qty)
        parcel.writeDouble(rate)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FoodCart> {
        override fun createFromParcel(parcel: Parcel): FoodCart {
            return FoodCart(parcel)
        }

        override fun newArray(size: Int): Array<FoodCart?> {
            return arrayOfNulls(size)
        }
    }
}
