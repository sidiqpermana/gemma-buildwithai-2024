package com.nbs.gemmaexample

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import com.nbs.gemmaexample.databinding.ActivityFileSummarizationBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FileSummarizationActivity : AppCompatActivity() {
    private val pdfReader: PdfReader by lazy {
        PdfReader(assets.open("SurabayaHistory.pdf"))
    }

    private lateinit var binding: ActivityFileSummarizationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileSummarizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            tvFileSource.text = "File Source file:///android_asset/SurabayaHistory.pdf"
            btnSummarize.setOnClickListener {
                progressBar.gone()
                lifecycleScope.launch {
                    progressBar.visible()
                    val text = getTextFromPdf()
                    val summarizedText = getTextSummarization(text)
                    binding.tvResponse.text = summarizedText
                    progressBar.gone()
                }
            }
        }
    }

    suspend fun getTextSummarization(content: String) : String {
        return withContext(Dispatchers.IO) {
            val prompt = "Summarize the text\n$content"
            Gemma.getGemma(this@FileSummarizationActivity).generateResponse(prompt)
        }
    }

    suspend fun getTextFromPdf() : String {
        var extractedText = ""
        return try {
            withContext(Dispatchers.IO) {
                for (i in 0 until pdfReader.numberOfPages) {
                    extractedText = """
                        $extractedText${
                            PdfTextExtractor.getTextFromPage(pdfReader, i + 1).trim { it <= ' ' }
                        } 
                    """.trimIndent()
                }
                extractedText
            }
        } catch (e: Exception) {
            extractedText
        }
    }
}