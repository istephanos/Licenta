package com.example.petoibittlecontrol.scan.model

import android.util.SparseArray
import androidx.core.util.forEach
import java.math.BigInteger

internal fun <T> SparseArray<T>.values(): List<T> {
    val list = ArrayList<T>()
    forEach { _, value ->
        list.add(value)
    }
    return list.toList()
}

internal fun ByteArray.toHex() = this.joinToString("") {
    java.lang.String.format("%02x",it)
}

internal fun swapEndian(str: String): BigInteger {
    val length = str.length
    val regex = Regex("^(.(..)*)$")
    val sec = str.replace(regex, "0$1")
    val matches = sec.chunked(2).reversed().joinToString("")

    val joined = matches.padStart(length + 1, '0')
    return joined.toBigInteger(16)
    }