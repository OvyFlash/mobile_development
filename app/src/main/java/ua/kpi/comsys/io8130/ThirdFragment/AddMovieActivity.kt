package ua.kpi.comsys.io8130.ThirdFragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import ua.kpi.comsys.io8130.R
import ua.kpi.comsys.io8130.ThirdFragment.Models.Movie

class AddMovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_add_movie)

        val btnAdd = findViewById<TextView>(R.id.btn_add_movie)

        btnAdd.setOnClickListener {
            var title: EditText = findViewById<EditText>(R.id.editTitle)
            var type: EditText = findViewById<EditText>(R.id.editType)
            var year: EditText = findViewById<EditText>(R.id.editYear)

            if (title.text.toString() == "" && type.text.toString() == "" && year.text.toString() == "") {
                Toast.makeText(applicationContext, "Заповніть хоча б одне поле", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Fine", Toast.LENGTH_SHORT).show()
                val movie: Movie = Movie(title.text.toString(), type.text.toString(), year.text.toString())

                val intent: Intent = Intent()
                intent.putExtra("newMovie", movie)
                setResult(RESULT_OK, intent)
                finish()
            }
        }

    }
}