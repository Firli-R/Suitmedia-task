package com.example.task_suitmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.task_suitmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private var TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val textPalindrome = activityMainBinding.editTextPalindrome.text
        val nama = activityMainBinding.editTextName.text

        activityMainBinding.buttonCheck.setOnClickListener {
            var isPalindrome : Boolean
            isPalindrome = true
            if(textPalindrome.isEmpty()){
                    activityMainBinding.editTextPalindrome.error ="masih kosong"
                    isPalindrome = false
            }

            val textLength = textPalindrome.length
            Log.d(TAG, "ukuran:$textLength")
            var i = 0
            while(i<textLength/2){
                i++
                if(textPalindrome[i] != textPalindrome[textLength-1-i]){
                    isPalindrome = false
                    break
                }
            }
            if(isPalindrome){
                Toast.makeText(this@MainActivity, "Palindrome", Toast.LENGTH_SHORT).show()
            }else if(isPalindrome == false && textPalindrome.isNotEmpty()) {
                Toast.makeText(this@MainActivity, "Not Palindrome", Toast.LENGTH_SHORT).show()
            }
        }

        activityMainBinding.buttonNext.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            val coba = intent.putExtra(SecondActivity.EXTRA_NAME,nama.toString())
            Log.d(TAG, "$coba")
            if(nama.isEmpty()){
                activityMainBinding.editTextName.error = "masih kosong"
            }else startActivity(intent)

        }
    }
}