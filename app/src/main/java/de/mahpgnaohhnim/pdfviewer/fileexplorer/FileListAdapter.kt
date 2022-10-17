package de.mahpgnaohhnim.pdfviewer.fileexplorer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
        val filepath = file.name
        holder.textView.text = filepath
        if (file.isDirectory) {
            holder.imageIcon.setImageResource(R.drawable.folder)
        } else {
            holder.imageIcon.setImageResource(R.drawable.viewpdf)
        }
    }

    override fun getItemCount(): Int {
        return files.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.fs_item_filename)
        val imageIcon: ImageView = itemView.findViewById(R.id.icon_file)
    }
}