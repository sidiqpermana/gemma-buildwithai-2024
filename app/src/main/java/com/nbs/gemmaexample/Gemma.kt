package com.nbs.gemmaexample

import android.content.Context
import com.google.mediapipe.tasks.genai.llminference.LlmInference

object Gemma {
    const val GEMMA_2B_CPU_INT8 = "data/local/tmp/llm/gemma-2b-it-cpu-int8.bin"
    const val GEMMA_11_2B_CPU_INT8 = "data/local/tmp/llm/gemma-1.1-2b-it-cpu-int8.bin"

    private val llmOptions: LlmInference.LlmInferenceOptions by lazy {
        LlmInference.LlmInferenceOptions.builder()
            .setModelPath(GEMMA_11_2B_CPU_INT8)
            .setMaxTokens(1000)
            .setTopK(40)
            .setTemperature(0.8f)
            .setRandomSeed(101)
            .build()
    }

    fun getGemma(context: Context) : LlmInference {
        return LlmInference.createFromOptions(context, llmOptions)
    }
}