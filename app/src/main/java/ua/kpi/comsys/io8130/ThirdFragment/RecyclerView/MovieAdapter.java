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
import com.bumptech.glide.request.RequestOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ua.kpi.comsys.io8130.LoadingIndicator;
import ua.kpi.comsys.io8130.R;
import ua.kpi.comsys.io8130.ThirdFragment.Models.Movie;
import ua.kpi.comsys.io8130.ThirdFragment.Models.MovieCallback;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.movieViewHolder> implements Filterable {

    ArrayList<Movie> mdata = new ArrayList<Movie>(0);
    MovieCallback callback;
    List<Movie> moviesListAll;
    TextView notFound;
    public List<Movie> forFiltering;
    public MovieAdapter(ArrayList<Movie> mdata, MovieCallback callback, TextView notFound) {
        if (mdata != null) {
            this.mdata = mdata;
        }
        this.callback = callback;
        this.moviesListAll = new ArrayList<>(this.mdata);
        this.notFound = notFound;
        if (this.mdata.size() == 0) {
            notFound.setVisibility(View.VISIBLE);
        } else {
            notFound.setVisibility(View.INVISIBLE);
        }
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
        holder.loading.setVisible();

        holder.Title.setText(item.getTitle());
        holder.Year.setText(item.getYear());
        holder.Type.setText(item.getType());



        if (!mdata.get(position).getPoster().contains("https")) {
            int img = holder.itemView.getContext().getResources().getIdentifier(
                    mdata.get(position).getPoster().toLowerCase().replace(".jpg", ""),
                    "drawable", holder.itemView.getContext().getPackageName()
            );
            Log.e("13", item.getPoster());
            Glide.with(holder.itemView.getContext()).
                    load(img).
                    transforms(new CenterCrop()).
                    into(holder.imgMovie);
        } else {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round);

            Glide.with(holder.itemView.getContext()).
                    load(item.getPoster()).
                    apply(options).
                    transforms(new CenterCrop()).
                    into(holder.imgMovie);
        }



        if (holder.itemView.getResources().getConfiguration().isNightModeActive()) {
            holder.Title.setTextColor(Color.BLACK);
            holder.Year.setTextColor(Color.BLACK);
            holder.Type.setTextColor(Color.BLACK);
        }
        holder.loading.hide();

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
                for (Movie m: forFiltering) {
                    if (m.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(m);
                    }
                }
                for (Movie m: moviesListAll) {
                    if (m.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(m);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            forFiltering.clear();
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
        LoadingIndicator loading;

        public movieViewHolder(@NonNull View itemView) {
            super(itemView);

            imgMovie = itemView.findViewById(R.id.item_movie_img);
            imgContainer = itemView.findViewById(R.id.container);
            Title = itemView.findViewById(R.id.item_movie_title);
            Year = itemView.findViewById(R.id.item_movie_date);
            Type = itemView.findViewById(R.id.item_movie_type);
            loading = new LoadingIndicator(itemView.findViewById(R.id.my_loading1));

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
            try {
                if (moviesListAll != null) {
                    moviesListAll.remove(viewHolder.getAdapterPosition());
                }
                if (mdata != null) {
                    mdata.remove(viewHolder.getAdapterPosition());
                }
            } catch (Exception e) {
                System.out.println(e);
            }

            notifyItemRemoved(viewHolder.getAdapterPosition());

        }
    };

    public final void SetItemTouchHelper(RecyclerView recyclerView) {
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }
}
