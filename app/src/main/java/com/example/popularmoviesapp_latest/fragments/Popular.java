package com.example.popularmoviesapp_latest.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmoviesapp_latest.click_interface.ItemClick;
import com.example.popularmoviesapp_latest.adapters.MoviesAdapter;
import com.example.popularmoviesapp_latest.data.MovieResponse;
import com.example.popularmoviesapp_latest.data.Movies;
import com.example.popularmoviesapp_latest.DetailActivity;
import com.example.popularmoviesapp_latest.R;
import com.example.popularmoviesapp_latest.databinding.FragmentCommonLayoutBinding;
import com.example.popularmoviesapp_latest.utils.NetworkUtils;
import com.example.popularmoviesapp_latest.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class Popular extends Fragment implements ItemClick {

    private static final String TAG = "Popular";
    public List<Movies> mMoviesArrayList;
    private MoviesAdapter mMoviesAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @NonNull
    FragmentCommonLayoutBinding mFragmentCommonLayoutBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Setting common layout for fragment using databinding
        mFragmentCommonLayoutBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_common_layout,container,false);
        View view=mFragmentCommonLayoutBinding.getRoot();

        mMoviesArrayList=new ArrayList<Movies>();

        mLayoutManager=new GridLayoutManager(this.getActivity(),numberOfColumns());

        //Check for network connection and request for data
        if(NetworkUtils.isConnected(getContext())){
            // Observe the data and update the UI
            setUpPopularMoviesViewModel();
        } else {
            Toast.makeText(getContext(), R.string.connection_timeout, Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    //Every time the data is updated, the onChanged callback will be invoked and update the UI
    private void setUpPopularMoviesViewModel() {
        MainActivityViewModel mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getPopularMoviesResponse().observe(getViewLifecycleOwner(), new Observer<MovieResponse>() {
            @Override
            public void onChanged(MovieResponse movieResponse) {
                if(movieResponse!=null){
                    mMoviesArrayList=movieResponse.getResults();
                    movieResponse.setResults(mMoviesArrayList);
                    mMoviesAdapter=new MoviesAdapter(getContext(),mMoviesArrayList,Popular.this::onItemClicked);

                    if (!mMoviesArrayList.isEmpty()) {
                        mFragmentCommonLayoutBinding.moviesRecycler.setLayoutManager(mLayoutManager);
                        mFragmentCommonLayoutBinding.moviesRecycler.setHasFixedSize(true);
                        mFragmentCommonLayoutBinding.moviesRecycler.setItemAnimator(new DefaultItemAnimator());
                        mFragmentCommonLayoutBinding.moviesRecycler.setNestedScrollingEnabled(false);
                        mFragmentCommonLayoutBinding.moviesRecycler.setAdapter(mMoviesAdapter);
                    } else {
                        mMoviesArrayList.clear();
                        mMoviesAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the poster
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }

    @Override
    public void onItemClicked(Movies movies) {
        Intent intent=new Intent(getContext().getApplicationContext(), DetailActivity.class);

        Bundle data=new Bundle();

        data.putString("ID",movies.getId());
        Log.d(TAG, "onItemClicked: movies.getId"+movies.getId());
        data.putString("NAME",movies.getTitle());
        Log.d(TAG, "onItemClicked: movies.getName()"+movies.getTitle());
        data.putString("Image",movies.getPoster_path());
        Log.d(TAG, "onItemClicked: image"+movies.getPoster_path());
        data.putString("OVERVIEW",movies.getOverview());
        Log.d(TAG, "onItemClicked: movies.getOverview()"+movies.getOverview());
        data.putString("VOTE_AVERAGE",movies.getVote_average());
        data.putString("RELEASE_DATE",movies.getRelease_date());

        intent.putExtras(data);

        //Start detail activity
        startActivity(intent);
    }
}
