package com.example.listacompraapp

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listacompraapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val listasDeCompras = mutableListOf<ListaDeCompras>()
    private lateinit var adapter: ListasAdapter
    private val addListActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                val data: Intent? = result.data
                val nomeDaLista = data?.getStringExtra("NOME_DA_LISTA")
                val imagemUriString = data?.getStringExtra("IMG_RES_ID")

                if (!nomeDaLista.isNullOrEmpty()) {
                    val novaLista = ListaDeCompras(
                        id = (listasDeCompras.size + 1),
                        titulo = nomeDaLista,
                        imgResId = imagemUriString
                    )
                    listasDeCompras.add(novaLista)

                    adapter.notifyItemInserted(listasDeCompras.size - 1)
                }
            }
        }

    private val updateListActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Recebe a lista atualizada de volta
                val listaAtualizada = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra("LISTA_ATUALIZADA", ListaDeCompras::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    result.data?.getParcelableExtra("LISTA_ATUALIZADA")
                }

                if (listaAtualizada != null) {
                    val index = listasDeCompras.indexOfFirst { it.id == listaAtualizada.id }
                    if (index != -1) {
                        // Substituí a lista antiga pela nova
                        listasDeCompras[index] = listaAtualizada

                        // Avisa o adapter que o item naquela posição mudou
                        adapter.notifyItemChanged(index)
                    }
                }
            }
        }

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

        binding.btnLogout.setOnClickListener {
            UserDatabase.clearUsers()
            val intent = Intent(this, LoginActivity::class.java)
            //impede que o user volte para a main ao press "back"
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.btnAddlistMain.setOnClickListener {
            val intent = Intent(this, AddListaActivity::class.java)
            addListActivityResultLauncher.launch(intent)
        }

        val itensExemplo1 = mutableListOf(
            ItemCompra(1, "Maçã", 4.0, "un", "frutas/verduras"),
            ItemCompra(2, "Frango", 1.0, "kg", "proteína")
        )
        val itensExemplo2 = mutableListOf(
            ItemCompra(1, "Picanha", 1.0, "kg", "proteína")
        )

        val dadosIniciais: List<ListaDeCompras> = listOf(
            ListaDeCompras(id = 1, titulo = "Compras da Semana", imgResId = null, itens = itensExemplo1),
            ListaDeCompras(id = 2, titulo = "Churrasco", imgResId = null, itens = itensExemplo2),
            ListaDeCompras(id = 3, titulo = "Produtos de Limpeza") // Lista vazia
        )

        listasDeCompras.addAll(dadosIniciais)

        adapter = ListasAdapter(listasDeCompras) { listaClicada ->
            val intent = Intent(this, ListaDetalhesActivity::class.java)
            intent.putExtra("LISTA_DE_COMPRAS", listaClicada)
            updateListActivityResultLauncher.launch(intent)
        }
        binding.recyclerViewMain.adapter = adapter
        binding.recyclerViewMain.layoutManager = LinearLayoutManager(this)

    }
}