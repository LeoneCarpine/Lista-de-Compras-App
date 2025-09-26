package com.example.listadecomprasapp

import ListaAdapter
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.listadecomprasapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Obriga os cards a serem mostrados em 2 colunas
        binding.recyclerViewLista.layoutManager = GridLayoutManager(this, 2)

        val listaDeCategorias = listOf(
            ListaCategoria(titulo = "Mercado", imgId = R.drawable.ic_mercado),
            ListaCategoria(titulo = "Varejão", imgId = R.drawable.ic_varejao),
            ListaCategoria(titulo = "Farmacia", imgId = R.drawable.ic_farmacia)
        )
        val adapter = ListaAdapter(listaDeCategorias)
        binding.recyclerViewLista.adapter = adapter
    }
}