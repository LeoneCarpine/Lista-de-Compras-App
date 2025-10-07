package com.example.listacompraapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListaDeCompras(
    val id: Int,
    val titulo: String,
    val imgResId: String? = null,
    val itens: MutableList<ItemCompra> = mutableListOf()
) : Parcelable