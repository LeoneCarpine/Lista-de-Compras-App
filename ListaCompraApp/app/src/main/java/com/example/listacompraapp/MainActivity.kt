package com.example.listacompraapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listacompraapp.databinding.ActivityMainBinding

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

        binding.btnLogout.setOnClickListener {
            UserDatabase.clearUsers()
            val intent = Intent(this, LoginActivity::class.java)
            //impede que o user volte para a main ao press "back"
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        val dadosDaLista: List<ListadeCompras> = listOf(
            ListadeCompras(id = 1, titulo = "Compras da Semana"),
            ListadeCompras(id = 2, titulo = "Churrasco do Fim de Semana"),
            ListadeCompras(id = 3, titulo = "Produtos de Limpeza"),
            ListadeCompras(id = 4, titulo = "Material Escolar")
        )

        val adapter = ListasAdapter(dadosDaLista)
        binding.recyclerViewMain.adapter = adapter
        binding.recyclerViewMain.layoutManager = LinearLayoutManager(this)
    }
}