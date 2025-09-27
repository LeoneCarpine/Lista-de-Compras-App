package com.example.listacompraapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListasAdapter(
    private val listas: List<ListadeCompras>
) : RecyclerView.Adapter<ListasAdapter.ListaViewHolder>() {

    class ListaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImagemLista: ImageView = view.findViewById(R.id.iv_lista_main)
        val tvTituloLista: TextView = view.findViewById(R.id.tv_titulo_lista)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lista, parent, false)
        return ListaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListaViewHolder, position: Int) {
        // Pega o item da lista de dados na posição atual
        val lista = listas[position]

        // Define o texto do TextView do ViewHolder com o título da nossa lista
        holder.tvTituloLista.text = lista.titulo

        // Verifica se há uma imagem para ser exibida
        if (lista.imgResId != null) {
            holder.ivImagemLista.setImageResource(lista.imgResId)
        } else {
            // Se não houver imagem, podemos colocar uma imagem padrão
            holder.ivImagemLista.setImageResource(R.drawable.ic_carrinho) // Usando seu ícone padrão
        }
    }

    override fun getItemCount(): Int {
        return listas.size
    }
}