package com.example.popularmoviesapp_latest.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.example.popularmoviesapp_latest.data.Movies;
import com.example.popularmoviesapp_latest.DetailActivity;
import com.example.popularmoviesapp_latest.R;
import com.example.popularmoviesapp_latest.databinding.FragmentCommonLayoutBinding;
import com.example.popularmoviesapp_latest.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class Favourite extends Fragment{
    private static final String TAG = "Favourite";

    private ArrayList<Movies> mMoviesArrayList;
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

        mMoviesArrayList=new ArrayList<>();
        mMoviesAdapter=new MoviesAdapter(getContext().getApplicationContext(), mMoviesArrayList, new ItemClick() {
            @Override
            public void onItemClicked(Movies movies) {
                Intent intent = new Intent(getContext().getApplicationContext(), DetailActivity.class);
                //Passing in the data to the detailed activity.
                Bundle data=new Bundle();

                data.putString("ID",movies.getId());
                data.putString("NAME",movies.getTitle());
                data.putString("Image",movies.getPoster_path());
                data.putString("OVERVIEW",movies.getOverview());
                data.putString("VOTE_AVERAGE",movies.getVote_average());
                data.putString("RELEASE_DATE",movies.getRelease_date());

                intent.putExtras(data);

                //Starting the activity with all the data passed to the next one.
                startActivity(intent);
            }
        });

        mLayoutManager=new GridLayoutManager(this.getActivity(),numberOfColumns());
        mFragmentCommonLayoutBinding.moviesRecycler.setLayoutManager(mLayoutManager);
        mFragmentCommonLayoutBinding.moviesRecycler.setAdapter(mMoviesAdapter);
        mFragmentCommonLayoutBinding.moviesRecycler.setHasFixedSize(true);
        mFragmentCommonLayoutBinding.moviesRecycler.setItemAnimator(new DefaultItemAnimator());
        mFragmentCommonLayoutBinding.moviesRecycler.setNestedScrollingEnabled(false);

        // Observe the data and update the UI
        setUpViewModel();

        return view;
    }

    //Every time the data is updated, the onChanged callback will be invoked and update the UI
    private void setUpViewModel() {
        MainActivityViewModel mainActivityViewModel= new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getFavoriteMovies().observe(this.getActivity(), new Observer<List<Movies>>() {
            @Override
            public void onChanged(@NonNull List<Movies> movies) {
                if(mMoviesArrayList.size()>0)
                    mMoviesArrayList.clear();
                mMoviesArrayList.addAll(movies);
                mMoviesAdapter.notifyDataSetChanged();
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
}
