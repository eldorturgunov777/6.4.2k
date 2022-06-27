package com.example.a642k

import android.content.Context
import android.widget.Toast


/**
 * Created by Eldor Turgunov on 27.06.2022.
 * 6.4.2k
 * eldorturgunov777@gmail.com
 */
object Utils {
    fun fireToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}