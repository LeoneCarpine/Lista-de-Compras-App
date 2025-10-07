package com.example.listacompraapp

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItensAdapter(
    private val itens: MutableList<ItemCompra>,
    private val onItemCheckedChange: (item: ItemCompra, isChecked: Boolean) -> Unit
) : RecyclerView.Adapter<ItensAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivIcone: ImageView = view.findViewById(R.id.iv_icone_item)
        val tvNome: TextView = view.findViewById(R.id.tv_nome_item)
        val tvQuantidade: TextView = view.findViewById(R.id.tv_quantidade_item)
        val cbCheck: CheckBox = view.findViewById(R.id.cb_check_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_compra, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itens[position]

        holder.tvNome.text = item.nome
        holder.tvQuantidade.text = "${item.quantidade} ${item.unidade}"
        holder.ivIcone.setImageResource(getIconePorCategoria(item.categoria))

        holder.cbCheck.setOnCheckedChangeListener(null)
        holder.cbCheck.isChecked = item.check

        if (item.check) {
            holder.tvNome.paintFlags = holder.tvNome.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.tvQuantidade.paintFlags = holder.tvQuantidade.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.tvNome.setTextColor(Color.GRAY)
            holder.tvQuantidade.setTextColor(Color.GRAY)
        } else {
            holder.tvNome.paintFlags = holder.tvNome.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.tvQuantidade.paintFlags = holder.tvQuantidade.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.tvNome.setTextColor(Color.BLACK)
            holder.tvQuantidade.setTextColor(Color.DKGRAY)
        }

        holder.cbCheck.setOnCheckedChangeListener { _, isChecked ->
            onItemCheckedChange(item, isChecked)
        }
    }

    override fun getItemCount(): Int {
        return itens.size
    }

    private fun getIconePorCategoria(categoria: String): Int {
        return when (categoria.lowercase()) {
            "frutas/verduras" -> R.drawable.ic_fruta
            "proteínas" -> R.drawable.ic_carne
            else -> R.drawable.ic_other
        }
    }

    fun atualizarLista(novosItens: List<ItemCompra>) {
        itens.clear()
        itens.addAll(novosItens)
        notifyDataSetChanged() // Comando para redesenhar a lista inteira
    }
}