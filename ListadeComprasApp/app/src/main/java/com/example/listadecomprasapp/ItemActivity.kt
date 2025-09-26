package com.example.listadecomprasapp

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.listadecomprasapp.databinding.ActivityItemBinding

class ItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Configura o menu de Unidades

        val unidades = resources.getStringArray(R.array.unidades_array)
        val unitAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, unidades)
        binding.autoCompleteUnit.setAdapter(unitAdapter)

        // Configura o menu de Categorias
        val categorias = resources.getStringArray(R.array.categorias_array)
        val categoryAdapter =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categorias)
        binding.autoCompleteCategory.setAdapter(categoryAdapter)
    }
}