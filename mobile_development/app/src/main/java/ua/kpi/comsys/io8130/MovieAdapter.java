package ua.kpi.comsys.io8130;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.movieViewHolder> {

    List<Movie> mdata;

    public MovieAdapter(List<Movie> mdata) {
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public movieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_movie, parent, false);
        return new movieViewHolder(view);

    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onBindViewHolder(@NonNull movieViewHolder holder, int position) {
        Movie item = this.mdata.get(position);

        holder.Title.setText(item.getTitle());
        holder.Year.setText(item.getYear());
        holder.Type.setText(item.getType());

        int img = holder.itemView.getContext().getResources().getIdentifier(
          mdata.get(position).getPoster().toLowerCase().replace(".jpg", ""),
                "drawable", holder.itemView.getContext().getPackageName()
        );

        Log.e("13", item.getPoster());
        Glide.with(holder.itemView.getContext()).
                load(img).
        transforms(new CenterCrop()).
        into(holder.imgMovie);

        if (holder.itemView.getResources().getConfiguration().isNightModeActive()) {
            holder.Title.setTextColor(Color.BLACK);
            holder.Year.setTextColor(Color.BLACK);
            holder.Type.setTextColor(Color.BLACK);
        }

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class movieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMovie;
        TextView Title, Year, Type;

        public movieViewHolder(@NonNull View itemView) {
            super(itemView);

            imgMovie = itemView.findViewById(R.id.item_movie_img);
            Title = itemView.findViewById(R.id.item_movie_title);
            Year = itemView.findViewById(R.id.item_movie_date);
            Type = itemView.findViewById(R.id.item_movie_type);

        }
    }
}
