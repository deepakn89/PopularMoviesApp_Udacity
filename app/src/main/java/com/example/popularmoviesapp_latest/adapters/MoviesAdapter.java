package com.example.popularmoviesapp_latest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmoviesapp_latest.click_interface.ItemClick;
import com.example.popularmoviesapp_latest.data.Movies;
import com.example.popularmoviesapp_latest.R;
import com.example.popularmoviesapp_latest.databinding.ItemMoviesGridBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> implements ItemClick{

    private static final String TAG = "MoviesAdapter";
    private List<Movies> mMoviesList;
    private Context mContext;

    private ItemClick mItemClick;

    public MoviesAdapter(Context context,List<Movies> moviesList, ItemClick itemClick) {
        this.mContext=context;
        this.mMoviesList = moviesList;
        this.mItemClick=itemClick;
    }

    @Override
    public void onItemClicked(Movies movies) {
        mItemClick.onItemClicked(movies);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ItemMoviesGridBinding mMoviesGridBinding;

        public MyViewHolder(@NonNull ItemMoviesGridBinding moviesGridBinding) {
            super(moviesGridBinding.getRoot());
            this.mMoviesGridBinding=moviesGridBinding;
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        ItemMoviesGridBinding itemMoviesGridBinding= DataBindingUtil.inflate(inflater,R.layout.item_movies_grid,parent,false);
        return new MyViewHolder(itemMoviesGridBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Movies movies=mMoviesList.get(position);
        holder.mMoviesGridBinding.setMovies(movies);

        String imagePath=movies.getPoster_path();
        Picasso.get().load("https://image.tmdb.org/t/p/" + "w500" +imagePath).into(holder.mMoviesGridBinding.imagePoster);
       holder.mMoviesGridBinding.setItemClickListener(this::onItemClicked);
    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }
}
