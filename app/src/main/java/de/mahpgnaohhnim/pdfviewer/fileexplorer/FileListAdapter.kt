package de.mahpgnaohhnim.pdfviewer.fileexplorer

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.mahpgnaohhnim.pdfviewer.R
import java.io.File


class FileListAdapter(private val files: List<File>) :
    RecyclerView.Adapter<FileListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.file_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val file = files[position]
        val filepath = file.path
        holder.textView.text = filepath
        if (file.isDirectory) {
            holder.textView.setTypeface(holder.textView.typeface, Typeface.BOLD)
        } else {
            holder.textView.setTypeface(holder.textView.typeface, Typeface.NORMAL)
        }
    }

    override fun getItemCount(): Int {
        return files.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.fs_item_filename)
    }
}