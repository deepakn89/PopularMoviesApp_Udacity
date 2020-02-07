package com.example.popularmoviesapp_latest.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmoviesapp_latest.data.Trailers;
import com.example.popularmoviesapp_latest.R;
import com.example.popularmoviesapp_latest.databinding.ItemMoviesTrailersBinding;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>{
    private static final String TAG = "TrailerAdapter";

    List<Trailers> mTrailersList;
    Context mContext;

    public TrailerAdapter(List<Trailers> trailersList, Context context) {
        this.mTrailersList = trailersList;
        this.mContext = context;
    }
    public class TrailerViewHolder extends RecyclerView.ViewHolder{
        ItemMoviesTrailersBinding moviesTrailersBinding;
        public TrailerViewHolder(@NonNull ItemMoviesTrailersBinding moviesTrailersBinding) {
            super(moviesTrailersBinding.getRoot());
            this.moviesTrailersBinding=moviesTrailersBinding;
        }
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMoviesTrailersBinding itemMoviesTrailersBinding= DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.item_movies_trailers,parent,false);
        return new TrailerViewHolder(itemMoviesTrailersBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        Trailers trailers=mTrailersList.get(position);
            //Using DataBinding
            holder.moviesTrailersBinding.setTrailers(new Trailers(trailers.getName(), trailers.getKey()));
            final String videoURL = "vnd.youtube:" + trailers.getKey();

            holder.moviesTrailersBinding.trailerName.setText(trailers.getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoURL));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return mTrailersList.size();
    }
}
