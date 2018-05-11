package com.innowave_technologies.githubapi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.loopj.android.http.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private SearchView search;
    private String searchText;
    private ProgressBar loader;
    private GitHubTask gitTask;
    private EditText username, password;
    private boolean isSearchingByEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if( this.getActionBar() != null)
            this.getActionBar().setDisplayHomeAsUpEnabled(true);

        searchText = "";

        search = (SearchView) findViewById(R.id.search);
        loader = (ProgressBar) findViewById(R.id.loader);
        username = (EditText) findViewById(R.id.yourUser);
        password = (EditText) findViewById(R.id.yourPass);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String userToSearch, basicAuth;
                searchText = query;
                loader.setVisibility(View.VISIBLE);

                if(!username.getText().toString().equals("") && !password.getText().toString().equals("")) {
                    basicAuth = "Basic " + Base64.encodeToString((username.getText().toString() + ":" + password.getText().toString()).getBytes(), Base64.NO_WRAP);
                    gitTask.setAuth_basic(basicAuth);
                }

                if(isEmailValid(query)) {
                    isSearchingByEmail = true;
                    userToSearch = "";
                }
                else {
                    isSearchingByEmail = false;
                    userToSearch = "/" + query;
                }

                gitTask.execute("https://api.github.com/users" + userToSearch);
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
        isSearchingByEmail = false;
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
                    try {
                        if(data.startsWith("[") && data.endsWith("]")){
                            JSONArray jsonArray = new JSONArray(data);
                            int length = jsonArray.length();
                            for (int i = 0; i<length; i++) {
                                JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                                final GitHubTask singleUserTask = new GitHubTask();
                                if(!username.getText().toString().equals("") && !password.getText().toString().equals("")) {
                                    String basicAuth = "Basic " + Base64.encodeToString((username.getText().toString() + ":" + password.getText().toString()).getBytes(), Base64.NO_WRAP);
                                    singleUserTask.setAuth_basic(basicAuth);
                                }
                                singleUserTask.setOnEventListener(new GitHubTask.OnGitHubTaskListener() {
                                    @Override
                                    public void onGitHubTaskFinished(String data) {
                                        try {
                                            JSONObject json = new JSONObject(data);
                                            if(isSearchingByEmail && json.getString("email").equals(searchText)){
                                                showUserDetails(data);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                singleUserTask.execute(jsonObj.getString("url"));
                            }
                        }
                        else{
                            showUserDetails(data);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void showUserDetails(String data){
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

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
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
