package com.jordanweaver.j_weaver_conntectedapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;

/**
 * Created by jordanweaver on 2/24/15.
 */
public class MyAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Reddit_Object> mReddits;
    private static final int ID_CONSTANT = 0x01000000;

    public MyAdapter(Context mContext, ArrayList<Reddit_Object> mReddits) {
        this.mContext = mContext;
        this.mReddits = mReddits;
    }


    @Override
    public int getCount() {
        if (mReddits != null){
            return mReddits.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if(mReddits != null && position < mReddits.size() && position >= 0){
            return mReddits.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        if (mReddits != null){
            return ID_CONSTANT + position;
        } else {
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.custom_layout, parent, false);
        }

        Reddit_Object item = (Reddit_Object) getItem(position);

        ((SmartImageView)convertView.findViewById(R.id.my_image)).setImageUrl(item.redditPicture);
        ((TextView)convertView.findViewById(R.id.titleLabel)).setText(item.title);

        return convertView;
    }
}
