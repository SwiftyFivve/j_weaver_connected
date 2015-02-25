package com.jordanweaver.j_weaver_conntectedapp;

import android.util.Log;

/**
 * Created by jordanweaver on 2/24/15.
 */
public class Reddit_Object {

    String title;
    String redditPicture;

    public Reddit_Object(String title, String redditPicture) {
        this.title = title;
        this.redditPicture = redditPicture;

        Log.e("Title", title+"");
        Log.e("picture", redditPicture+"");
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRedditPicture() {
        return redditPicture;
    }

    public void setRedditPicture(String redditPicture) {
        this.redditPicture = redditPicture;
    }
}
