package com.example.listadecomprasapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listadecomprasapp.databinding.ActivityListaItensBinding

class ListaItensActivity : AppCompatActivity() {
    val listaItens = mutableListOf(
        ItemCompra("Banana", isChecked = false),
        ItemCompra("Arroz", isChecked = false),
        ItemCompra("Feijão", isChecked = false)
    )
    private lateinit var adapter: ItemAdapter

    private val addItemLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) { // Verifica se a ItemActivity retornou um resultado OK
            val data: Intent? = result.data // Pega a Intent com os dados
            val newItemName = data?.getStringExtra("NEW_ITEM_NAME") // Pega o nome do novo item

            if (newItemName != null && newItemName.isNotBlank()) {
                val novoItem = ItemCompra(newItemName, isChecked = false)
                listaItens.add(novoItem)
                adapter.notifyItemInserted(listaItens.size - 1) // Notifica o adapter
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityListaItensBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val listTitle = intent.getStringExtra("LIST_TITLE")

        if(listTitle != null){
            binding.toolbarItemList.hint = "Pesquisar em $listTitle"
        }

        adapter = ItemAdapter(listaItens)

        binding.recyclerViewItemList.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewItemList.adapter = adapter
        binding.fabAddItemList.setOnClickListener {
            val intent = Intent(this, ItemActivity::class.java)
            startActivity(intent)
        }
    }
}