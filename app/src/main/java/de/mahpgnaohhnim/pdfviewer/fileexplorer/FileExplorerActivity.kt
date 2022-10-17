package de.mahpgnaohhnim.pdfviewer.fileexplorer

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.mahpgnaohhnim.pdfviewer.PdfActivity
import de.mahpgnaohhnim.pdfviewer.R
import de.mahpgnaohhnim.pdfviewer.comparator.FileComparator
import java.io.File


class FileExplorerActivity : ComponentActivity() {
    private var rootPath = Environment.getExternalStorageDirectory().toString()
    private lateinit var currentPath: TextView
    private lateinit var fileList: RecyclerView
    private lateinit var fileListLayoutManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.file_explorer)
        currentPath = findViewById(R.id.currentPath)
        fileList = findViewById(R.id.directory_filelist)
        fileListLayoutManager = LinearLayoutManager(this)
        fileList.layoutManager = fileListLayoutManager
        selectDiretory(rootPath)
    }

    override fun onBackPressed() {
        onItemClick(File(".."))
    }

    fun onItemClick(file: File) {
        if (file.isDirectory) {
            var pathToGo = file.absolutePath
            if (file.path == "..") {
                val currentDirectory = File(this.currentPath.text.toString())

                if (currentDirectory.absolutePath == this.rootPath) {
                    pathToGo = this.rootPath
                } else {
                    pathToGo = currentDirectory.parent ?: this.rootPath
                }
            }
            this.selectDiretory(pathToGo)
        } else if (file.path.takeLast(4) == ".pdf") {
            val pdfIntent = Intent(this, PdfActivity::class.java)
            pdfIntent.putExtra(PdfActivity.PDF_PATH, file.absolutePath)
            startActivity(pdfIntent)
        }
    }

    private fun selectDiretory(directory: String) {
        val dir = File(directory)
        if (!dir.exists()) {
            return
        }
        this.currentPath.text = dir.absolutePath

        val childFiles: List<File> = dir.listFiles()?.filter { file ->
            file.isDirectory or (file.path.takeLast(4) == ".pdf")
        }?.sortedWith(FileComparator) ?: listOf()


        val adapter = FileListAdapter(listOf(File("..")) + childFiles, this::onItemClick)
        fileList.adapter = adapter
    }
}