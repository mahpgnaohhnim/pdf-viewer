package de.mahpgnaohhnim.pdfviewer

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
        submitBtn.setOnClickListener{v -> handleSubmitPsswd()}
        initPdf()
    }

    private fun initPdf() {
        val pdfPath: String? = this.intent.extras?.getString(PDF_PATH)
        val pdfFile = File(pdfPath?: "")
        if (pdfFile.exists()) {
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
        val pdfPath = this.intent.extras?.getString(PDF_PATH)
        val pdfFile = File(pdfPath ?: "")
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