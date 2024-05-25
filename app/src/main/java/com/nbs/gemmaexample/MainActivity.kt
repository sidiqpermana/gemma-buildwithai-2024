package com.nbs.gemmaexample

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.mediapipe.tasks.genai.llminference.LlmInference
import com.google.mediapipe.tasks.genai.llminference.LlmInference.LlmInferenceOptions
import com.nbs.gemmaexample.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val llmOptions: LlmInferenceOptions by lazy {
        LlmInferenceOptions.builder()
            .setModelPath("data/local/tmp/llm/gemma-2b-it-cpu-int8.bin")
            .setMaxTokens(1000)
            .setTopK(40)
            .setTemperature(0.8f)
            .setRandomSeed(101)
            .build()
    }

    private lateinit var binding: ActivityMainBinding

    private var llmInference: LlmInference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        llmInference = LlmInference.createFromOptions(this, llmOptions)

        with(binding) {
            btnSubmit.setOnClickListener {
                if (edtPrompt.text.toString().isNotEmpty()) {
                    tvResponse.gone()
                    progressBar.visible()

                    val startMillis = System.currentTimeMillis()
                    val input = edtPrompt.text.toString().trim()

                    lifecycleScope.launch {
                        tvLabelResponse.text = "Response"
                        tvResponse.visible()
                        tvResponse.text = "Generating Response..."

                        val response = askResponse(input)
                        Log.d("ResponseAi", response)
                        val endMillis = System.currentTimeMillis()

                        tvLabelResponse.text = getReadableResponseTime(startMillis, endMillis)
                        tvResponse.text = response
                        progressBar.gone()
                    }
                }
            }
        }
    }

    suspend fun askResponse(prompt: String) : String {
        return withContext(Dispatchers.IO) {
            llmInference?.generateResponse(prompt).orEmpty()
        }
    }
}