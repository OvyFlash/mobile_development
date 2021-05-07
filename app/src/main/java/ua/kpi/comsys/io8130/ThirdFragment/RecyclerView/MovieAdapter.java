package ua.kpi.comsys.io8130.ThirdFragment.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ua.kpi.comsys.io8130.R;
import ua.kpi.comsys.io8130.ThirdFragment.Models.Movie;
import ua.kpi.comsys.io8130.ThirdFragment.Models.MovieCallback;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.movieViewHolder> implements Filterable {

    List<Movie> mdata;
    MovieCallback callback;
    List<Movie> moviesListAll;
    TextView notFound;
    public MovieAdapter(List<Movie> mdata, MovieCallback callback, TextView notFound) {
        this.mdata = mdata; this.callback = callback;
        this.moviesListAll = new ArrayList<>(mdata);
        this.notFound = notFound;
    }

    public List<Movie> getCurrentMovies() {
        return this.moviesListAll;
    }
    public void setCurrentMovies(List<Movie> movies) {
        this.moviesListAll = new ArrayList<Movie>(movies);
        this.mdata = new ArrayList<Movie>(this.moviesListAll);
        notifyDataSetChanged();
        if (mdata.size() == 0) {
            notFound.setVisibility(View.VISIBLE);
        } else {
            notFound.setVisibility(View.INVISIBLE);
        }
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

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List <Movie> filteredList = new ArrayList<>();

            if (constraint.toString().isEmpty()) {
                filteredList.addAll(moviesListAll);
            } else {
                for (Movie m: moviesListAll) {
                    if (m.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(m);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mdata.clear();
            mdata.addAll((Collection<? extends Movie>) results.values);

            if (((Collection<? extends Movie>) results.values).size() == 0) {
                notFound.setVisibility(View.VISIBLE);
            } else {
                notFound.setVisibility(View.INVISIBLE);
            }
            notifyDataSetChanged();
        }
    };

    public class movieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMovie, imgContainer;
        TextView Title, Year, Type;

        public movieViewHolder(@NonNull View itemView) {
            super(itemView);

            imgMovie = itemView.findViewById(R.id.item_movie_img);
            imgContainer = itemView.findViewById(R.id.container);
            Title = itemView.findViewById(R.id.item_movie_title);
            Year = itemView.findViewById(R.id.item_movie_date);
            Type = itemView.findViewById(R.id.item_movie_type);

            //Log.println(Log.INFO, "12", imgMovie.toString() + imgContainer.toString()+ Title.toString() + Year.toString() + Type.toString());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onMovieItemClick(getAdapterPosition(),
                            imgContainer,
                            imgContainer,
                            Title,
                            Year,
                            Type);
                }
            });

        }
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//            if (moviesListAll.size() > 0) {
//                moviesListAll.remove(viewHolder.getAdapterPosition());
//            }
            moviesListAll.remove(viewHolder.getAdapterPosition());
            mdata.remove(viewHolder.getAdapterPosition());
            //mdata.clear();
            //mdata.addAll(moviesListAll);
            notifyItemRemoved(viewHolder.getAdapterPosition());
            //notifyDataSetChanged();
        }
    };

    public final void SetItemTouchHelper(RecyclerView recyclerView) {
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }
}
