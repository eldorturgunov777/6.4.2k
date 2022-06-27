package com.example.a642k

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val cache = findViewById<Button>(R.id.cache)
        val files = findViewById<Button>(R.id.files)
        val delete = findViewById<Button>(R.id.delete)

        cache.setOnClickListener {
            saveExternalCache("CACHE")
        }
        files.setOnClickListener {
            saveExternalFile("FILES")
        }
        delete.setOnClickListener {
            deleteExternalFile()
        }
        checkPermission()
    }

    private val requestPermissionLauncher = registerForActivityResult(
        RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d("@@@", "isGranted")
        } else {
            Log.d("@@@", "Not isGranted")
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("@@@", "checkSelfPermission")
        } else {
            Log.d("@@@", "requestPermissionLauncher.launch")
            requestPermissionLauncher.launch(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    private fun deleteExternalFile() {
        val fileName = "external.txt"
        val file = File(getExternalFilesDir(null), fileName)
        val cache = File(externalCacheDir, fileName)
        file.delete()
        cache.delete()
        Utils.fireToast(this, String.format("File %s has been deleted", fileName))
    }

    private fun saveExternalFile(files: String) {
        val fileName = "external.txt"
        val file: File = File(getExternalFilesDir(null), fileName)

        try {
            val fileOutputStream = FileOutputStream(file)
            fileOutputStream.write(files.toByteArray(StandardCharsets.UTF_8))
            Utils.fireToast(this, String.format("Write to %s successful", fileName))
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Utils.fireToast(this, String.format("Write to file %s failed", fileName))
        }
    }

    private fun saveExternalCache(cache: String) {
        val fileName = "external.txt"
        val file = File(externalCacheDir, fileName)

        try {
            val fileOutputStream = FileOutputStream(file)
            fileOutputStream.write(cache.toByteArray(StandardCharsets.UTF_8))
            Utils.fireToast(this, String.format("Write to %s successful", fileName))
        } catch (e: Exception) {
            e.printStackTrace()
            Utils.fireToast(this, String.format("Write to file %s failed", fileName))
        }
    }
}