package com.example.popularmoviesapp_latest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.example.popularmoviesapp_latest.adapters.MoviesPagerAdapter;
import com.example.popularmoviesapp_latest.databinding.ActivityMainBinding;
import com.example.popularmoviesapp_latest.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private MoviesPagerAdapter mMoviesPagerAdapter;
    private MenuItem mPreviousMenuItem;

    ActivityMainBinding mMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Use DataBinding
        mMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        mMainBinding.bottomNavigationView.setItemIconTintList(null);

        //setting up toolbar to main activity
        setSupportActionBar(mMainBinding.toolbarMain);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }

        //Setting up of viewpager
        mMoviesPagerAdapter=new MoviesPagerAdapter(getSupportFragmentManager());
        mMainBinding.viewPagerMain.setAdapter(mMoviesPagerAdapter);

        //Handling the click listeners for the bottom navigation.
        mMainBinding.bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.popular_movies:
                    if(!NetworkUtils.isConnected(getApplicationContext()))
                        Toast.makeText(getApplicationContext(), R.string.connection_timeout, Toast.LENGTH_SHORT).show();
                    mMainBinding.viewPagerMain.setCurrentItem(0);
                    return true;
                case R.id.top_rated_movies:
                    if(!NetworkUtils.isConnected(getApplicationContext()))
                        Toast.makeText(getApplicationContext(), R.string.connection_timeout, Toast.LENGTH_SHORT).show();
                    mMainBinding.viewPagerMain.setCurrentItem(1);
                    return true;
                case R.id.fav_movies:
                    if(!NetworkUtils.isConnected(getApplicationContext()))
                        Toast.makeText(getApplicationContext(), R.string.connection_timeout, Toast.LENGTH_SHORT).show();
                    mMainBinding.viewPagerMain.setCurrentItem(2);
                    return true;
            }
            return false;
        });

        //Adding view page scroll listener
        mMainBinding.viewPagerMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.d(TAG, "onPageScrolled");
            }

            @Override
            public void onPageSelected(int position) {
                if(mPreviousMenuItem!=null) {
                    mPreviousMenuItem.setChecked(false);

                }else{
                    mMainBinding.bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                mMainBinding.bottomNavigationView.getMenu().getItem(position).setChecked(true);
                mPreviousMenuItem=mMainBinding.bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Log.d(TAG, "onPageScrollStateChanged");
            }
        });
    }
}
