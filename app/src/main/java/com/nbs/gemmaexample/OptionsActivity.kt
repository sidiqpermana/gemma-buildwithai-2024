package com.nbs.gemmaexample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nbs.gemmaexample.databinding.ActivityOptionsBinding

class OptionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOptionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            btnQAGemma.setOnClickListener {
                startActivity(Intent(this@OptionsActivity, MainActivity::class.java))
            }

            btnFileSummarization.setOnClickListener {
                startActivity(Intent(this@OptionsActivity, FileSummarizationActivity::class.java))
            }
        }
    }
}