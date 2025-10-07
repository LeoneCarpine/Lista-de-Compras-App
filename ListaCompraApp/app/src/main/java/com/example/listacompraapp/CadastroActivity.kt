package com.example.listacompraapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.listacompraapp.databinding.ActivityCadastroBinding

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
        binding.btnCriarCCCadastro.setOnClickListener {
            val nome: String = binding.tietNomeCadastro.text.toString().trim()
            val email: String = binding.tietEmailCadastro.text.toString().trim()
            val senha: String = binding.tietSenhaCadastro.text.toString().trim()
            val confirmarSenha: String = binding.tietConfirmarSenhaCadastro.text.toString().trim()

            binding.tilSenhaCadastro.error = null
            binding.tilConfirmarSenhaCadastro.error = null

            if(nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()){
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(confirmarSenha != senha){
                binding.tilConfirmarSenhaCadastro.error = "As senhas devem ser iguais!"
                return@setOnClickListener
            }
            if(UserDatabase.confirmarEmail(email) != null){
                binding.tilEmailCadastro.error = "Este e-mail já está em uso!"
                return@setOnClickListener
            }

            val newUser = User(nome = nome, email = email, senha = senha)
            UserDatabase.addUser(newUser)

            Toast.makeText(this, "Usuário cadastrado", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}