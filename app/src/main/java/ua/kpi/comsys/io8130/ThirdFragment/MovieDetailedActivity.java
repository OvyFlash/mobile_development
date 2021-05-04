package ua.kpi.comsys.io8130.ThirdFragment;

import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import ua.kpi.comsys.io8130.R;
import ua.kpi.comsys.io8130.ThirdFragment.Models.Movie;

public class MovieDetailedActivity extends AppCompatActivity {
    ImageView imgMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //removing titles
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //finish removing titles
        setContentView(R.layout.activity_movie_detailed);

        imgMovie = findViewById(R.id.item_movie_img);

        Movie item = (Movie) getIntent().getExtras().getSerializable("movieObject");

        loadMovieData(item);
    }

    private void loadMovieData(Movie item) {
        TextView title = findViewById(R.id.item_movie_title_f);
        title.setText(item.getTitle());
        title.setTextColor(Color.BLACK);
        TextView year = findViewById(R.id.item_movie_date_f);
        year.setText(item.getYear());
        year.setTextColor(Color.BLACK);
        TextView genre = findViewById(R.id.item_movie_genre_f);
        genre.setText(item.getGenre());
        genre.setTextColor(Color.BLACK);

        TextView director = findViewById(R.id.item_movie_director_f);
        director.setText(item.getDirector());
        director.setTextColor(Color.BLACK);

        TextView actors = findViewById(R.id.item_movie_actors_f);
        actors.setText(item.getActors());
        actors.setTextColor(Color.BLACK);

        TextView country = findViewById(R.id.item_movie_country_f);
        country.setText(item.getCountry());
        country.setTextColor(Color.BLACK);
        TextView language = findViewById(R.id.item_movie_language_f);
        language.setText(item.getLanguage());
        language.setTextColor(Color.BLACK);
        TextView production = findViewById(R.id.item_movie_production_f);
        production.setText(item.getProduction());
        production.setTextColor(Color.BLACK);
        TextView released = findViewById(R.id.item_movie_released_f);
        released.setText(item.getReleased());
        released.setTextColor(Color.BLACK);
        TextView runtime = findViewById(R.id.item_movie_runtime_f);
        runtime.setText(item.getRuntime());
        runtime.setTextColor(Color.BLACK);

        TextView awards = findViewById(R.id.item_movie_awards_f);
        awards.setText(item.getAwards());
        awards.setTextColor(Color.BLACK);
        TextView rating = findViewById(R.id.item_movie_rating_f);
        rating.setText(item.getImdbRating());
        rating.setTextColor(Color.BLACK);

        TextView plot = findViewById(R.id.item_movie_plot_f);
        plot.setText(item.getPlot());
        plot.setTextColor(Color.BLACK);


        int img = this.getResources().getIdentifier(
                item.getPoster().toLowerCase().replace(".jpg", ""),
                "drawable", this.getPackageName()
        );

        Glide.with(this).
                load(img).
                transforms(new CenterCrop()).
                into(imgMovie);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (this.getResources().getConfiguration().isNightModeActive()) {
                title = findViewById(R.id.item_movie_title);
                title.setTextColor(Color.GRAY);

                year = findViewById(R.id.item_movie_date);
                year.setTextColor(Color.GRAY);

                genre = findViewById(R.id.item_movie_genre);
                genre.setTextColor(Color.GRAY);

                director = findViewById(R.id.item_movie_director);
                director.setTextColor(Color.GRAY);

                actors = findViewById(R.id.item_movie_actors);
                actors.setTextColor(Color.GRAY);

                country = findViewById(R.id.item_movie_country);
                country.setTextColor(Color.GRAY);

                language = findViewById(R.id.item_movie_language);
                language.setTextColor(Color.GRAY);

                production = findViewById(R.id.item_movie_production);
                production.setTextColor(Color.GRAY);

                released = findViewById(R.id.item_movie_released);
                released.setTextColor(Color.GRAY);

                runtime = findViewById(R.id.item_movie_runtime);
                runtime.setTextColor(Color.GRAY);

                awards = findViewById(R.id.item_movie_awards);
                awards.setTextColor(Color.GRAY);

                rating = findViewById(R.id.item_movie_rating);
                rating.setTextColor(Color.GRAY);

                plot = findViewById(R.id.item_movie_plot);
                plot.setTextColor(Color.GRAY);
            }
        }

        //Glide.with(this).load(item.getPoster()).into(imgMovie);
    }
}