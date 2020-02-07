package com.example.popularmoviesapp_latest.utils;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    //For singleton instantiation
    private static final Object LOCK=new Object();
    private static AppExecutors sInstance;
    private final Executor mDiskIO;
    private final Executor mMainThread;
    private final Executor mNetwrokIO;


    public AppExecutors(Executor diskIO, Executor mainThread, Executor netwrokIO) {
        this.mDiskIO = diskIO;
        this.mMainThread = mainThread;
        this.mNetwrokIO = netwrokIO;
    }

    public static AppExecutors getInstance(){
        if(sInstance==null){
            synchronized (LOCK) {
                sInstance = new AppExecutors(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }
        return sInstance;
    }

    public Executor getDiskIO() {
        return mDiskIO;
    }

    public Executor getMainThread() {
        return mMainThread;
    }

    public Executor getNetwrokIO() {
        return mNetwrokIO;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler handler=new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
                handler.post(command);
        }
    }
}
