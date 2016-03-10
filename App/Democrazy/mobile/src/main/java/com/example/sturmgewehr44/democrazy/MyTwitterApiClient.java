package com.example.sturmgewehr44.democrazy;

import com.google.gson.annotations.SerializedName;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;
import retrofit.http.Query;

import retrofit.http.GET;

/**
 * Created by Sturmgewehr44 on 3/9/16.
 */
public class MyTwitterApiClient extends TwitterApiClient {

    public MyTwitterApiClient(Session session) {
        super(session);
    }

    /**
     * Provide FriendsService with ids
     */
    public FriendsService getFriendsService() {
        return getService(FriendsService.class);
    }

    public interface FriendsService {
        @GET("/1.1/statuses/user_timeline.json")
        void ids(@Query("user_id") Long userId,
                 @Query("screen_name") String screenName,
                 @Query("since_id") Long since_id,
                 @Query("count") Integer count,
                 @Query("max_id") Long max_id,
                 @Query("trim_user") Boolean trim_user,
                 @Query("exclude_replies") Boolean exclude_replies,
                 @Query("contributor_details") Boolean contributor_details,
                 @Query("include_rts") Boolean include_rts,
                 Callback<TweetArray> cb);

        void idsByUserId(@Query("screen_name") String screenName,
                         Callback<TweetArray> cb);
    }

    public class TweetArray {
//        @SerializedName("previous_cursor")
//        public final Long previousCursor;
//
//        @SerializedName("ids")
//        public final Long[] ids;
//
//        @SerializedName("next_cursor")
//        public final Long nextCursor;

        @SerializedName("tweets")
        public final Tweet[] tweets;


        public TweetArray(Tweet[] tweets) {
            this.tweets = tweets;
//            this.previousCursor = previousCursor;
//            this.ids = ids;
//            this.nextCursor = nextCursor;
        }
    }
}

