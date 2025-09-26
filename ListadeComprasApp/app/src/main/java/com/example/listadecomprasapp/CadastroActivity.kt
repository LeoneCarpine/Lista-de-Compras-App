package com.example.listadecomprasapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.listadecomprasapp.databinding.ActivityCadastroBinding

class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnEntrarCadastro.setOnClickListener {
            val name = binding.nomeCadastro.editText?.text.toString()
            val email = binding.emailCadastro.editText?.text.toString()
            val senha = binding.senhaCadastro.editText?.text.toString()
            val confSenha = binding.confirmarSenha.editText?.text.toString()

            if (name.isBlank() || email.isBlank() || senha.isBlank() || confSenha.isBlank()){
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
            else if(senha != confSenha){
                Toast.makeText(this, "As senhas devem ser iguais!", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}