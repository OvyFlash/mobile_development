package ua.kpi.comsys.io8130.ThirdFragment.Models

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.Serializable

class Movie (
    val Title: String,
    val Type: String,
    val Year: String,
    var Rated: String = "", //new
    var Released: String = "", //new
    var Runtime: String = "", //new
    var Genre: String = "", //new
    var Director: String = "", //new
    var Writer: String = "", //new
    var Actors: String = "", //new
    var Plot: String = "", //new
    var Language: String = "", //new
    var Country: String = "", //new
    var Awards: String = "", //new
    var Poster: String = "",
    var imdbRating: String= "", //new
    var imdbVotes: String = "", //new
    val imdbID: String = "",
    var Production: String = "" //new
): Serializable{
    fun AddFieldByImdbID(context: Context) {
        var reader: BufferedReader? = null;
        val search  = Gson()

        try {
            reader = BufferedReader(
                    InputStreamReader(context.assets.open(this.imdbID+".txt"))
            );

            val newInfo =  search.fromJson<Movie>(reader, Movie::class.java)
            this.mergeMovie(newInfo)
        } catch (e: IOException) {
            Log.println(Log.INFO, "12", e.toString())

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (e: IOException) {
                    Log.println(Log.INFO, "13", e.toString())
                }
            }
        }
    }

    fun mergeMovie(movie: Movie) {
        this.Rated = movie.Rated
        this.Released = movie.Released
        this.Runtime = movie.Runtime
        this.Genre = movie.Genre //new
        this.Director = movie.Director //new
        this.Writer = movie.Writer //new
        this.Actors = movie.Actors //new
        this.Plot = movie.Plot //new
        this.Language = movie.Language //new
        this.Country = movie.Country //new
        this.Awards = movie.Awards //new
        this.imdbRating = movie.imdbRating //new
        this.imdbVotes = movie.imdbVotes //new
        this.Production = movie.Production//new
    }

}