package com.codebyashish.foodui.utitlity

import android.content.Context
import android.content.SharedPreferences

class SharedPref {

    private lateinit var context : Context



  companion object {

      private val PREFERENCE_NAME = "FoodUi"


      fun setString(context: Context, key: String, msg: String) {
          val editor: SharedPreferences.Editor =
              sharedPref(context).edit()
          editor.putString(key, msg)
          editor.apply()
      }

      fun getString(context: Context, key: String, msg: String): String {
          return sharedPref(context).getString(key, msg).toString();
      }


      fun sharedPref(context: Context): SharedPreferences {
          return context.getSharedPreferences(
              PREFERENCE_NAME, Context.MODE_PRIVATE
          )
      }

      fun clearSharedPreferences(mContext: Context) {
          val pref = mContext.getSharedPreferences(
              PREFERENCE_NAME,
              Context.MODE_PRIVATE
          )
          val editor = pref.edit()
          editor.clear()
          editor.apply()
      }
  }
}