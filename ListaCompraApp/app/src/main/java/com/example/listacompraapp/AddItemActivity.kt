package com.example.listacompraapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.listacompraapp.databinding.ActivityAddItemBinding

class AddItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddItemBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val unidades = resources.getStringArray(R.array.unidades)
        val unidadesAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, unidades)
        binding.actvUnidadeItem.setAdapter(unidadesAdapter)

        val categorias = resources.getStringArray(R.array.categorias)
        val categoriasAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categorias)
        binding.actvCategoriaItem.setAdapter(categoriasAdapter)

        binding.btnAdicionarItem.setOnClickListener {
            val nome = binding.tietNomeItem.text.toString().trim()
            val quantidadeStr = binding.tietQuantidadeItem.text.toString().trim()
            val unidade = binding.actvUnidadeItem.text.toString()
            val categoria = binding.actvCategoriaItem.text.toString()

            if (nome.isEmpty() || quantidadeStr.isEmpty() || unidade.isEmpty() || categoria.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Converte a quantidade para Double de forma segura
            val quantidade = quantidadeStr.toDoubleOrNull()
            if (quantidade == null) {
                binding.tilQuantidadeItem.error = "Quantidade inválida"
                return@setOnClickListener
            }

            val novoItem = ItemCompra(
                id = 0,
                nome = nome,
                quantidade = quantidade,
                unidade = unidade,
                categoria = categoria
            )

            val resultIntent = Intent()
            resultIntent.putExtra("NOVO_ITEM", novoItem) // objeto inteiro
            setResult(Activity.RESULT_OK, resultIntent)
            
            finish()
        }

    }
}