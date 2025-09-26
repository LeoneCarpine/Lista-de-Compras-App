package com.example.listadecomprasapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listadecomprasapp.databinding.ItemCompraBinding

class ItemAdapter(private val items: List<ItemCompra>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ItemCompraBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemCompraBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // Conecta os dados do ItemCompra às views
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val itemAtual = items[position]
        holder.binding.textviewItemName.text = itemAtual.nome
        holder.binding.checkboxItem.isChecked = itemAtual.isChecked
    }
}