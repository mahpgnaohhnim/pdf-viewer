package de.mahpgnaohhnim.pdfviewer
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.github.barteksc.pdfviewer.PDFView
import java.io.File

class PdfActivity: ComponentActivity()  {
    private lateinit var pdfView: PDFView

    companion object {
        const val PDF_PATH = "PDF_PATH"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pdf_activity)
        pdfView = findViewById(R.id.pdfView)

        val pdfPath: String? = this.intent.extras?.getString(PDF_PATH)

        Log.d("PDFVIEW","PDF VIEWWWWWE")

        if(pdfPath?.isEmpty() == false){
            val pdfFile = File(pdfPath)
            if(pdfFile.exists()){
                pdfView.fromFile(pdfFile)
                    .defaultPage(0)
                    .load()
            }
        }
    }
}