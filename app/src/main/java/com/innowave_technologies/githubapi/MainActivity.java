package com.innowave_technologies.githubapi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private SearchView search;
    private ProgressBar loader;
    private GitHubTask gitTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if( this.getActionBar() != null)
            this.getActionBar().setDisplayHomeAsUpEnabled(true);

        search = (SearchView) findViewById(R.id.search);
        loader = (ProgressBar) findViewById(R.id.loader);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loader.setVisibility(View.VISIBLE);
                gitTask.execute("https://api.github.com/users/" + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        createNewGitHubTask();
    }

    @Override
    public void onResume(){ super.onResume(); }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    public void createNewGitHubTask(){
        gitTask = new GitHubTask();

        gitTask.setOnEventListener(new GitHubTask.OnGitHubTaskListener() {
            @Override
            public void onGitHubTaskFinished(String data) {
                loader.setVisibility(View.GONE);
                if(data.equals("")){
                    showDialog(MainActivity.this);
                }
                else {
                    String userProfile[] = new String[4];
                    try {
                        JSONObject json = new JSONObject(data);
                        userProfile[0] = json.getString("login");
                        userProfile[1] = json.getString("email");
                        userProfile[2] = json.getString("avatar_url");
                        userProfile[3] = json.getString("followers_url");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    final Intent mainIntent = new Intent(MainActivity.this, DetailsActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                    Bundle b = new Bundle();
                    b.putStringArray("profile", userProfile);
                    mainIntent.putExtras(b);
                    startActivity(mainIntent);
                }
            }
        });
    }

    public void showDialog(final Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Search Failed");
        alertDialog.setMessage("Couldn't find user on GitHub!");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        createNewGitHubTask();
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
