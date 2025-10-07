package com.example.listacompraapp

import android.app.Activity
import androidx.activity.addCallback
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listacompraapp.databinding.ActivityListaDetalhesBinding

class ListaDetalhesActivity : AppCompatActivity() {
    private lateinit var listaDeCompras: ListaDeCompras // Guardar a lista recebida
    private lateinit var adapter: ItensAdapter

    private val addItemResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data

                val novoItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    data?.getParcelableExtra("NOVO_ITEM", ItemCompra::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    data?.getParcelableExtra("NOVO_ITEM")
                }

                if (novoItem != null) {
                    listaDeCompras.itens.add(novoItem)
                    reordenarEAtualizarLista()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityListaDetalhesBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listaDeCompras = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("LISTA_DE_COMPRAS", ListaDeCompras::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("LISTA_DE_COMPRAS")
        } ?: return // Se for nulo, encerra a activity para evitar crash

        binding.tvTituloDetalhes.text = listaDeCompras.titulo

        binding.fabAddItem.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            addItemResultLauncher.launch(intent)
        }

        adapter = ItensAdapter(mutableListOf()) { item, isChecked ->
            item.check = isChecked
            reordenarEAtualizarLista()
        }

        binding.rvItensDaLista.adapter = adapter
        binding.rvItensDaLista.layoutManager = LinearLayoutManager(this)
        onBackPressedDispatcher.addCallback(this) {
            val resultIntent = Intent()

            resultIntent.putExtra("LISTA_ATUALIZADA", listaDeCompras)
            setResult(Activity.RESULT_OK, resultIntent)

            finish()
        }
        reordenarEAtualizarLista()

    }
    private fun reordenarEAtualizarLista() {
        val listaOrdenada = listaDeCompras.itens.sortedWith(
            compareBy({ it.check }, { it.categoria }, { it.nome })
        )
        adapter.atualizarLista(listaOrdenada)
    }
}