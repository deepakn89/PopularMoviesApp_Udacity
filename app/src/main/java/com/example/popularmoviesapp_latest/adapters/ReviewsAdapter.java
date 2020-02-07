package com.example.popularmoviesapp_latest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmoviesapp_latest.data.Reviews;
import com.example.popularmoviesapp_latest.R;
import com.example.popularmoviesapp_latest.databinding.ItemMoviesReviewsBinding;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>{
    private static final String TAG = "ReviewsAdapter";

    private List<Reviews> mReviewsList;
    Context mContext;

    public ReviewsAdapter(List<Reviews> reviewsList, Context context) {
        this.mReviewsList = reviewsList;
        this.mContext = context;
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder{
        ItemMoviesReviewsBinding moviesReviewsBinding;

        public ReviewsViewHolder(@NonNull ItemMoviesReviewsBinding moviesReviewsBinding) {
            super(moviesReviewsBinding.getRoot());
            this.moviesReviewsBinding=moviesReviewsBinding;
        }
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMoviesReviewsBinding itemMoviesReviewsBinding;
        itemMoviesReviewsBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_movies_reviews,parent,false);
        return new ReviewsViewHolder(itemMoviesReviewsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder holder, int position) {
        Reviews reviews=mReviewsList.get(position);
        holder.moviesReviewsBinding.author.setText(reviews.getAuthor());
        holder.moviesReviewsBinding.content.setText(reviews.getContent());
        //Using DataBinding
        holder.moviesReviewsBinding.setReview(new Reviews(reviews.getAuthor(), reviews.getContent()));
    }

    @Override
    public int getItemCount() {
        return mReviewsList.size();
    }

}
