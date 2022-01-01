package com.example.task_suitmedia

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.task_suitmedia.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var activitySecondBinding : ActivitySecondBinding
    private var TAG ="Second Screen"
    companion object{
        const val EXTRA_NAME = "extra_name"
        const val SELECTED_USER = "Selected User Name"
    }
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySecondBinding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(activitySecondBinding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = TAG

        val text = intent.getStringExtra(EXTRA_NAME)
        activitySecondBinding.namaUser.text = text
        Log.d(TAG, "second activity:$text ")

        var selectUser = activitySecondBinding.selectedUser.isVisible

        activitySecondBinding.namaUser.setOnClickListener {
            activitySecondBinding.selectedUser.visibility = View.VISIBLE
            activitySecondBinding.selectedUser.text = SELECTED_USER
            activitySecondBinding.namaUser.setTextColor(R.color.dark_blue)
            selectUser = false
        }

        Log.d(TAG, "$selectUser ")
        activitySecondBinding.buttonChoose.setOnClickListener {
            intent = Intent(this@SecondActivity,ThirdActivity::class.java)
            if (selectUser == false){
                startActivity(intent)
            }else {
                Toast.makeText(this@SecondActivity, "must click name!", Toast.LENGTH_SHORT).show()
            }
            }

    }

    override fun onSupportNavigateUp(): Boolean {
        finishAfterTransition()
        return super.onSupportNavigateUp()
    }

}