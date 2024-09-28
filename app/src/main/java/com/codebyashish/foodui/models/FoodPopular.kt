package com.codebyashish.foodui.models

import android.os.Parcel
import android.os.Parcelable

class FoodPopular(
    var id : Int=0,
    var icon: Int = 0,
    var label: String = "",
    var price: String = ""
) : Parcelable {

    private var isSelected: Boolean = false
    var rate = 0.0
    var desc = ""
    var counts = 1
    var amt = 0

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
        parcel.readString() ?: ""
    ) {
        isSelected = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(icon)
        parcel.writeString(label)
        parcel.writeString(price)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FoodPopular> {
        override fun createFromParcel(parcel: Parcel): FoodPopular {
            return FoodPopular(parcel)
        }

        override fun newArray(size: Int): Array<FoodPopular?> {
            return arrayOfNulls(size)
        }
    }
}
