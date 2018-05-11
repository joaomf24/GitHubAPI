package com.innowave_technologies.githubapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import agency.tango.android.avatarview.IImageLoader;
import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;

public class FollowersListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<FollowerItem> mFollowerItems;
    private IImageLoader imageLoader;

    public FollowersListAdapter(Context context, ArrayList<FollowerItem> followerItems) {
        mContext = context;
        mFollowerItems = followerItems;
        imageLoader = new PicassoLoader();
    }

    @Override
    public int getCount() {
        return mFollowerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mFollowerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.followers_item, null);
        }
        else {
            view = convertView;
        }

        TextView usernameView = (TextView) view.findViewById(R.id.usernameFollower);
        AvatarView avatarView = (AvatarView) view.findViewById(R.id.avatarFollower);

        usernameView.setText( mFollowerItems.get(position).mUsername );
        imageLoader.loadImage(avatarView,  mFollowerItems.get(position).mAvatar, "");

        return view;
    }
}
