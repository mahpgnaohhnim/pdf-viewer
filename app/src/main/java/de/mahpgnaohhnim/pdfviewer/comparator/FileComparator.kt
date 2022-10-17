package de.mahpgnaohhnim.pdfviewer.comparator

import java.io.File

class FileComparator {
    companion object : Comparator<File> {
        override fun compare(f1: File, f2: File): Int = when {
            f1.isDirectory && !f2.isDirectory -> 1
            f1.isHidden && f2.isHidden -> 1
            else -> f1.name.compareTo(f2.name)
        }
    }
}