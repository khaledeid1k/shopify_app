package com.kh.mo.shopyapp.local.dp.sharedPref


import android.content.Context
import android.content.SharedPreferences
import com.kh.mo.shopyapp.utils.Constants
import com.kh.mo.shopyapp.utils.Constants.PREFERENCE_NAME

class SharedPreferencesShopy(val context: Context) {
    fun customPreference(): SharedPreferences =
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
}

inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
    val editMe = edit()
    operation(editMe)
    editMe.apply()
}

var SharedPreferences.customerId
    get() = getLong(Constants.CUSTOMER_ID, 0L)
    set(value) {

        editMe {
            it.putLong(Constants.CUSTOMER_ID, value)
        }
    }

var SharedPreferences.favoriteDraftId
    get() = getLong(Constants.DRAFT_FAVORITE_ID, 0)
    set(value) {

        editMe {
            it.putLong(Constants.DRAFT_FAVORITE_ID, value)
        }
    }

var SharedPreferences.cartDraftId
    get() = getLong(Constants.DRAFT_CART_ID, -1)
    set(value) {
        editMe {
            it.putLong(Constants.DRAFT_CART_ID, value)
        }
    }

var SharedPreferences.currencyUnit: String
    get() = getString(Constants.CURRENCY_ID, Constants.EGP_ID) ?: Constants.EGP_ID
    set(value) = editMe {
        it.putString(Constants.CURRENCY_ID, value)
    }


var SharedPreferences.email: String?
    get() = getString(Constants.EMAIL, "")
    set(value) = editMe {
        it.putString(Constants.EMAIL, value)
    }


var SharedPreferences.userName: String?
    get() = getString(Constants.USER_NAME, "")
    set(value) = editMe {
        it.putString(Constants.USER_NAME, value)
    }

var SharedPreferences.clearValues: () -> Unit
    get() = { this.edit().clear().apply() }
    set(value) {}