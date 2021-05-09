package ua.kpi.comsys.io8130

import android.app.Activity
import android.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment

class LoadingIndicator(progressBar: ProgressBar) {
    //val fragment: Fragment
    private var progressBar: ProgressBar = progressBar
    fun setVisible() {
        progressBar.visibility = View.VISIBLE

    }
    fun hide() {
        progressBar.visibility = View.GONE

    }

}