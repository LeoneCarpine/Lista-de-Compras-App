package com.example.listacompraapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemCompra(
    val id: Int,
    val nome: String,
    val quantidade: Double,
    val unidade: String,
    val categoria: String,
    var check: Boolean = false
) : Parcelable