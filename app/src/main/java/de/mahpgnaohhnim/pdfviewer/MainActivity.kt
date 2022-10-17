package de.mahpgnaohhnim.pdfviewer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import androidx.activity.ComponentActivity
import de.mahpgnaohhnim.pdfviewer.fileexplorer.FileExplorerActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        startApp()
    }

    override fun onResume() {
        super.onResume()
        startApp()
    }

    fun startApp(){
        if (Environment.isExternalStorageManager()) {
            goToFileExplorer()
        } else {
            goToPermissionSettings()
        }
    }

    private fun goToFileExplorer() {
            val intent = Intent(this, FileExplorerActivity::class.java)
            overridePendingTransition(0, 0)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
            startActivity(intent)
    }

    private fun goToPermissionSettings() {
        val uri: Uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID)
        val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri)
        startActivity(intent)
    }

    inline fun runOnTimeout(crossinline block: () -> Unit, timeoutMillis: Long) {
        Handler(Looper.getMainLooper()).postDelayed({
            block()
        }, timeoutMillis)
    }

}