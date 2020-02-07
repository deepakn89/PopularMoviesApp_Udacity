/*
 *  Copyright 2018 Soojeong Shin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.example.popularmoviesapp_latest.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The {@link TrailerResponse} object includes information related to the movie trailers
 */
public class TrailerResponse {

    @SerializedName("id")
    private int mId;

    @SerializedName("results")
    private List<Trailers> mVideoResults;

    public void setId(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public void setVideoResults(List<Trailers> videoResults) {
        mVideoResults = videoResults;
    }

    public List<Trailers> getVideoResults() {
        return mVideoResults;
    }
}
