package com.example.listacompraapp

import android.util.Log

object UserDatabase {
    //lista de usuários
    private val users = mutableListOf<User>()

    //adiciona usuário na lista
    fun addUser(user: User){
        users.add(user)
    }

    //confirma se o email existe
    fun confirmarEmail(email: String): User?{
        return users.find { it.email.equals(email, ignoreCase = true) }
    }

    //limpar dados no logout
    fun clearUsers(){
        users.clear()
    }
}