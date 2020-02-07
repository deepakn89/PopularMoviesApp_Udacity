package com.example.popularmoviesapp_latest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmoviesapp_latest.adapters.ReviewsAdapter;
import com.example.popularmoviesapp_latest.adapters.TrailerAdapter;
import com.example.popularmoviesapp_latest.click_interface.FavouriteClick;
import com.example.popularmoviesapp_latest.data.Movies;
import com.example.popularmoviesapp_latest.data.ReviewResponse;
import com.example.popularmoviesapp_latest.data.Reviews;
import com.example.popularmoviesapp_latest.data.TrailerResponse;
import com.example.popularmoviesapp_latest.data.Trailers;
import com.example.popularmoviesapp_latest.database.MovieDatabase;
import com.example.popularmoviesapp_latest.utils.AppExecutors;
import com.example.popularmoviesapp_latest.databinding.ActivityDetailBinding;
import com.example.popularmoviesapp_latest.utils.NetworkUtils;
import com.example.popularmoviesapp_latest.viewmodel.DetailActivityViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements FavouriteClick{
    private static final String TAG = "DetailActivity";

    private String movie_id,image,name,release_date,rating,overview;

    private TrailerAdapter mTrailerAdapter;
    private ReviewsAdapter mReviewsAdapter;

    private List<Trailers> mTrailersList;
    private List<Reviews> mReviewsList;

    private MovieDatabase mMovieDatabase;

    private Boolean isFavourite=false;

    ActivityDetailBinding mDetailBinding;

    private DetailActivityViewModel mDetailActivityViewModel;
    private Movies mMovieEntry;

    private FavouriteClick mFavouriteClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Use data binding
        mDetailBinding= DataBindingUtil.setContentView(this,R.layout.activity_detail);
        //Setting up toolbar
        setSupportActionBar(mDetailBinding.toolbarDetail);

        Intent intent=getIntent();

        //Instance of movie database
        mMovieDatabase=MovieDatabase.getInstance(getApplicationContext());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (intent != null) {
                getSupportActionBar().setTitle(intent.getExtras().getString("NAME"));
            }
        }

        //Getting data passed through intent from popular/top-rated classes
        movie_id=intent.getStringExtra("ID");
        image=intent.getStringExtra("Image");
        name=intent.getStringExtra("NAME");
        release_date=intent.getStringExtra("RELEASE_DATE");
        rating=intent.getStringExtra("VOTE_AVERAGE")+"/10";
        overview=intent.getStringExtra("OVERVIEW");

        //Load image
        Picasso.get().load("https://image.tmdb.org/t/p/" + "w500"+image).into(mDetailBinding.moviePoster);

        //Data-binding
        mDetailBinding.setMovies(new Movies(movie_id,name,image,overview,rating,release_date));

        //Declare view model
        mDetailActivityViewModel= new ViewModelProvider(this).get(DetailActivityViewModel.class);

        //Request network data for trailer and reviews
        if(NetworkUtils.isConnected(getApplicationContext())){
            // Observe the data and update the UI
            setUpTrailerViewModel(movie_id);
            setUpReviewViewModel(movie_id);
        }else{
            callSnackbar();
        }

        //Check for the favourite movie
        isFavourite=isMovieFavourite();

        //Binding the fav click handler
        mFavouriteClick=this::onFavouriteClicked;
        mDetailBinding.setHandler(mFavouriteClick);
    }

    //Every time the data is updated, the onChanged callback will be invoked and update the UI
    private void setUpTrailerViewModel(String movie_id) {
        mDetailActivityViewModel.getTrailerResponse(movie_id).observe(this, new Observer<TrailerResponse>() {
            @Override
            public void onChanged(TrailerResponse trailerResponse) {
                if(trailerResponse!=null){
                    mTrailersList=trailerResponse.getVideoResults();
                    trailerResponse.setVideoResults(mTrailersList);

                    // Check if there is a trailer
                    if (!mTrailersList.isEmpty()) {
                        mTrailerAdapter=new TrailerAdapter(mTrailersList,getApplicationContext());

                        mDetailBinding.trailerRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
                        mDetailBinding.trailerRecycler.setHasFixedSize(true);
                        mDetailBinding.trailerRecycler.setNestedScrollingEnabled(true);
                        mDetailBinding.trailerRecycler.setAdapter(mTrailerAdapter);
                    } else {
                        mTrailersList.clear();
                    }
                }
            }
        });
    }

    //Every time the data is updated, the onChanged callback will be invoked and update the UI
    private void setUpReviewViewModel(String movie_id) {
        mDetailActivityViewModel.getReviewResponse(movie_id).observe(this, new Observer<ReviewResponse>() {
            @Override
            public void onChanged(ReviewResponse reviewResponse) {
                if(reviewResponse!=null){

                    mReviewsList=reviewResponse.getReviewResults();
                    reviewResponse.setReviewResults(mReviewsList);

                    // Check if there is a reviews
                    if (!mReviewsList.isEmpty()) {
                        mReviewsAdapter=new ReviewsAdapter(mReviewsList,getApplicationContext());

                        mDetailBinding.reviewsRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
                        mDetailBinding.reviewsRecycler.setHasFixedSize(true);
                        mDetailBinding.reviewsRecycler.setNestedScrollingEnabled(true);
                        mDetailBinding.reviewsRecycler.setAdapter(mReviewsAdapter);
                    } else {
                        mReviewsList.clear();
                    }
                }
            }
        });
    }

    private void callSnackbar() {
        Snackbar snackbar=Snackbar.make(mDetailBinding.activityDetail,getResources().getString(R.string.connection_timeout),Snackbar.LENGTH_INDEFINITE)
                .setAction("Reload", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(NetworkUtils.isConnected(getApplicationContext())){
                            setUpTrailerViewModel(movie_id);
                            setUpReviewViewModel(movie_id);
                        }else{
                            callSnackbar();
                        }
                    }
                });

        snackbar.setActionTextColor(Color.YELLOW);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
        TextView snackText = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        snackText.setTextColor(Color.BLACK);
        snackbar.show();
    }

    private Boolean isMovieFavourite() {
        AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMovieEntry=mDetailActivityViewModel.getFavoriteMovieById(movie_id);
                isFavourite=mMovieEntry!=null;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(isFavourite)
                            mDetailBinding.favMovie.setBackgroundColor(Color.YELLOW);
                    }
                });
            }
        });
        return isFavourite;
    }

    @Override
    public void onFavouriteClicked() {
        Toast.makeText(DetailActivity.this, "favourite clicked", Toast.LENGTH_SHORT).show();
        isFavourite=isMovieFavourite();
        if(!isFavourite){
            AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mMovieEntry=new Movies(movie_id,name,image,overview,rating,release_date);
                    mMovieDatabase.movieDao().insertMovie(mMovieEntry);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mDetailBinding.favMovie.setBackgroundColor(Color.YELLOW);
                            isFavourite=true;
                        }
                    });
                }
            });
        }else{
            AppExecutors.getInstance().getDiskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mMovieEntry=new Movies(movie_id,name,image,overview,rating,release_date);
                    mMovieDatabase.movieDao().deleteMovie(mMovieEntry);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mDetailBinding.favMovie.setBackgroundColor(Color.WHITE);
                            isFavourite=false;
                        }
                    });
                }
            });
        }
    }
}
