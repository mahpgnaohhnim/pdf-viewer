package de.mahpgnaohhnim.pdfviewer

import android.content.Intent
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import com.github.barteksc.pdfviewer.PDFView
import java.io.File

class PdfActivity : ComponentActivity() {
    private lateinit var pdfView: PDFView

    private lateinit var passwordContainer: LinearLayout
    private lateinit var passwordField: EditText
    private lateinit var submitBtn: Button
    private var pdfPath: String? = null

    companion object {
        const val PDF_PATH = "PDF_PATH"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pdf_activity)
        pdfView = findViewById(R.id.pdfView)

        passwordContainer = findViewById(R.id.pdfPasswordContainer)
        passwordField = findViewById(R.id.pdfPassword)
        submitBtn = findViewById(R.id.passwordSubmitBtn)
        submitBtn.setOnClickListener { v -> handleSubmitPsswd() }
        val intentAction = intent?.action
        when {
            intentAction == Intent.ACTION_VIEW -> {
                handleIntentFile(intent)
            }
            else -> {
                val intentPath: String? = this.intent.extras?.getString(PDF_PATH)
                this.pdfPath = intentPath
                val pdfFile = File(pdfPath ?: "")
                initPdf(pdfFile)
            }
        }
    }

    private fun handleIntentFile(intent: Intent) {
        val uri = intent.data
        val intentType = intent.type
        if (intentType == "application/pdf" && uri != null) {
            val tempFilePath = "${this.externalCacheDir}/tmp.pdf"
            val tmpFile = File(tempFilePath)
            tmpFile.outputStream().use {
                val inputStream = contentResolver.openInputStream(uri)
                inputStream?.copyTo(it)
                inputStream?.close()
            }
            this.pdfPath = tempFilePath
            this.initPdf(tmpFile)
        }

    }

    private fun initPdf(pdfFile: File?) {
        if (pdfFile?.exists() == true) {
            try {
                PdfRenderer(ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY))
                pdfView.fromFile(pdfFile)
                    .defaultPage(0)
                    .load()
            } catch (e: SecurityException) {
                pdfView.visibility = PDFView.GONE
                passwordContainer.visibility = LinearLayout.VISIBLE
            }
        }
    }

    private fun handleSubmitPsswd() {
        val passwd = passwordField.text.toString()
        if (passwd.isNotBlank()) {
            openPdf(passwd)
        }
    }

    private fun openPdf(passwd: String) {
        val pdfFile = File(this.pdfPath ?: "")
        if (pdfFile.exists()) {
            passwordContainer.visibility = LinearLayout.GONE
            pdfView.visibility = PDFView.VISIBLE
            pdfView.fromFile(pdfFile)
                .defaultPage(0)
                .password(passwd)
                .load()
        }
    }
}