package com.innowave_technologies.githubapi;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GitHubTask extends AsyncTask<String, Void, String> {

    private OnGitHubTaskListener mOnGitHubTaskListener;

    protected String doInBackground(String... urls) {
        HttpURLConnection httpcon;
        StringBuilder response = new StringBuilder();
        //TODO
        //final String basicAuth = "Basic " + Base64.encodeToString("username:pass".getBytes(), Base64.NO_WRAP);
        try {
            httpcon = (HttpURLConnection) new URL(urls[0]).openConnection();
            //httpcon.setRequestProperty ("Authorization", basicAuth);
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
        if (mOnGitHubTaskListener != null)
            mOnGitHubTaskListener.onGitHubTaskFinished(data);
    }

    public interface OnGitHubTaskListener {
        void onGitHubTaskFinished(String data);
    }
}