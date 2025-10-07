package com.example.listacompraapp

import android.net.Uri
import coil.load
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListasAdapter(
    private val listas: MutableList<ListaDeCompras>,
    private val onItemClicked: (ListaDeCompras) -> Unit
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
        // Pega o item da lista de dados na posição atual e define o texto do TextView do ViewHolder com o título da lista
        val lista = listas[position]
        holder.tvTituloLista.text = lista.titulo

        if (lista.imgResId != null) {
            val uri = Uri.parse(lista.imgResId)
            holder.ivImagemLista.load(uri){
                error(R.drawable.ic_default_img_foreground)
            }
        } else {
            // Se não houver imagem, define uma imagem padrão
            holder.ivImagemLista.setImageResource(R.drawable.ic_default_img_foreground)
        }
        holder.itemView.setOnClickListener {
            onItemClicked(lista) //chama a fun na main que informa qual lista foi clicada
        }
    }

    override fun getItemCount(): Int {
        return listas.size
    }
}