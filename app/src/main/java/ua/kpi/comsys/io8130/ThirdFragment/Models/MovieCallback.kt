package ua.kpi.comsys.io8130.ThirdFragment.Models

import android.widget.ImageView
import android.widget.TextView

interface MovieCallback {
    fun onMovieItemClick(pos: Int, imgContainer: ImageView, imgMovie: ImageView, title: TextView, date: TextView, type: TextView);
}