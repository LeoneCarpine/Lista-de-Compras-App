package com.example.listacompraapp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.listacompraapp.databinding.ActivityAddListaBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

class AddListaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddListaBinding
    private var imageUri: Uri? = null //guarda o endereço da imagem

    private val CameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                val file = createImageFile()
                imageUri = FileProvider.getUriForFile(this, "${applicationContext.packageName}.provider", file)
                imageUri?.let { uri ->
                    getPhotoLauncher.launch(uri)
                }
            } else {
                Toast.makeText(this, "Permita para adicionar tirar fotos", Toast.LENGTH_SHORT).show()
            }
        }
    private val GaleriaPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                pickImageLauncher.launch(intent)
            } else {
                Toast.makeText(this, "Permita para adicionar fotos da galeria", Toast.LENGTH_SHORT).show()
            }
        }
    private val getPhotoLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                binding.ivAddLista.setImageURI(imageUri)
            }
        }

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                imageUri = result.data?.data
                binding.ivAddLista.setImageURI(imageUri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddListaBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnAddImg.setOnClickListener {
            mostrarDialogoEscolhaImagem()
        }

        binding.btnAddLista.setOnClickListener {
            val nomeLista = binding.tietNomeNovaLista.text.toString().trim()

            if (nomeLista.isNotEmpty()){
                val resultIntent = Intent()

                resultIntent.putExtra("NOME_DA_LISTA", nomeLista)
                // Enviando o endereço da imagem
                imageUri?.let {
                    resultIntent.putExtra("IMG_RES_ID", it.toString())
                }

                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }else{
                binding.tilNomeNovaLista.error = "O nome não pode ser vazio"
            }
        }
    }
    private fun mostrarDialogoEscolhaImagem() {
        val opcoes = arrayOf("Tirar Foto", "Escolher da Galeria")
        AlertDialog.Builder(this)
            .setTitle("Escolha uma imagem")
            .setItems(opcoes) { _, which ->
                when (which) {
                    0 -> checarPermissaoCamera() // Tirar Foto
                    1 -> checarPermissaoGaleria() // Escolher da Galeria
                }
            }
            .show()
    }

    private fun checarPermissaoGaleria() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED -> {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                pickImageLauncher.launch(intent)
            }
            else -> {
                GaleriaPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }
        }
    }

    private fun checarPermissaoCamera() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                abrirCamera()
            }
            else -> {
                CameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun abrirCamera() {
        val file = createImageFile()
        imageUri = FileProvider.getUriForFile(this, "${applicationContext.packageName}.provider", file)
        //nulabilidade
        imageUri?.let { uri ->
            getPhotoLauncher.launch(uri)
        }
    }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }
}