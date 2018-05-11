package com.innowave_technologies.githubapi;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Scanner;

import agency.tango.android.avatarview.IImageLoader;
import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;

public class DetailsActivity extends AppCompatActivity {

    private AvatarView avatarView;
    private TextView username;
    private TextView email;
    private ListView followersList;
    private ArrayList<FollowerItem> mFollowerItems;
    private IImageLoader imageLoader;
    private String profileData[];
    private Profile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        avatarView = (AvatarView) findViewById(R.id.avatar);
        username = (TextView) findViewById(R.id.username);
        email = (TextView) findViewById(R.id.email);
        followersList = (ListView) findViewById(R.id.followersList);

        imageLoader = new PicassoLoader();

    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle b = getIntent().getExtras();
        if(b != null) {
            profileData = b.getStringArray("profile");
            if(profileData != null) {
                userProfile = new Profile(profileData[0], profileData[1],profileData[2],profileData[3]);
                loadProfile(userProfile);
                loadFollowers(userProfile.getFollowers());
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void loadProfile(Profile profile){
        String usernameStr = "Username: " + profile.getUserName();
        String emailStr = "Email: " + profile.getEmail();
        imageLoader.loadImage(avatarView, profile.getAvatar(), "");
        username.setText(usernameStr);
        email.setText(emailStr);
    }

    public void loadFollowers(String followersLink){
        mFollowerItems = new ArrayList<FollowerItem>();
        GitHubTask gitTask = new GitHubTask();

        gitTask.setOnEventListener(new GitHubTask.OnGitHubTaskListener() {
            @Override
            public void onGitHubTaskFinished(String data) {

            if(!data.equals("") && !data.equals("[]")) {

                try {
                    JSONArray jsonArray = new JSONArray(data);
                    int length = jsonArray.length();
                    for (int i = 0; i<length; i++) {
                        JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                        mFollowerItems.add(new FollowerItem(DetailsActivity.this, jsonObj.getString("login"), jsonObj.getString("avatar_url")));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                FollowersListAdapter adapter = new FollowersListAdapter(DetailsActivity.this, mFollowerItems);
                followersList.setAdapter(adapter);

            }
            }
        });

        gitTask.execute(followersLink);

    }
}
