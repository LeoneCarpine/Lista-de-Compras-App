package com.example.listadecomprasapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.listadecomprasapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnEntrar.setOnClickListener {
            val email = binding.emailLogin.editText?.text.toString()
            val senha = binding.senhaLogin.editText?.text.toString()
            if(email.isBlank() || senha.isBlank()){
                Toast.makeText(this, "Email ou senha inválido!", Toast.LENGTH_SHORT).show()
            }else{
                if(email == "teste@gmail.com" && senha == "123"){
                    Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, _root_ide_package_.com.example.listadecomprasapp.MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this, "Email ou senha inválidos!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.btnCriarConta.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }
}