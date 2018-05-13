package com.innowave_technologies.githubapi;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GitHubTask extends AsyncTask<String, Void, String> {

    private OnGitHubTaskListener mOnGitHubTaskListener;
    private String auth_basic;

    public GitHubTask(){
        this.auth_basic = "";
    }

    protected String doInBackground(String... urls) {
        HttpURLConnection httpcon;
        StringBuilder response = new StringBuilder();

        final String basicAuth = auth_basic;
        try {
            httpcon = (HttpURLConnection) new URL(urls[0]).openConnection();
            if(!basicAuth.equals(""))
                httpcon.setRequestProperty ("Authorization", basicAuth);
            else
                httpcon.addRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader in = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    protected void onPostExecute(String data) {
        doEvent(data);
    }

    public void setOnEventListener(OnGitHubTaskListener listener) {
        mOnGitHubTaskListener = listener;
    }

    public void doEvent(String data) {
        if (mOnGitHubTaskListener != null) {
            mOnGitHubTaskListener.onGitHubTaskFinished(data);
        }
    }

    public interface OnGitHubTaskListener {
        void onGitHubTaskFinished(String data);
    }

    public void setAuth_basic(String auth_basic) {
        this.auth_basic = auth_basic;
    }

}