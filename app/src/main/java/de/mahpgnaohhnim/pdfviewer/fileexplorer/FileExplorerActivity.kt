package de.mahpgnaohhnim.pdfviewer.fileexplorer

import android.os.Bundle
import android.os.Environment
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        fileList.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                return true
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
              
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                TODO("Not yet implemented")
            }

        })

        selectDiretory("$rootPath")
    }

    fun onItemClick(list: List<File>, position: Int) {
        val file = list[position]
        if (file.isDirectory) {
            this.selectDiretory(file.absolutePath)
        }
    }

    private fun selectDiretory(directory: String) {
        val dir = File(directory)
        if (!dir.exists()) {
            return
        }
        this.currentPath.text = dir.absolutePath
        val childFiles = dir.listFiles().filter { file ->
            file.isDirectory or (file.path.takeLast(4) == ".pdf")
        }.sortedWith(FileComparator)

        val adapter = FileListAdapter(childFiles)
        fileList.adapter = adapter
    }
}

interface OnItemClickListener {
    fun onItemClicked(position: Int, view: View)
}
