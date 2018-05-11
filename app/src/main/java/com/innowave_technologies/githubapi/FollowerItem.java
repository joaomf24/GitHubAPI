package com.innowave_technologies.githubapi;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by creativewalkers on 09/05/16.
 */
public class FollowerItem {
    String mUsername;
    String mAvatar;
    TextView usernameView;

    public FollowerItem(Context ctx, String username, String avatar) {
        usernameView = new TextView(ctx);
        usernameView.setText(username);
        mUsername = usernameView.getText().toString();
        mAvatar = avatar;
    }
}
