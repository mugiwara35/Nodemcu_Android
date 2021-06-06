package com.example.led;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpRequestTask extends AsyncTask<String, Void, String> {
    private String serverAdress;
    private String serverResponse;
    private String input;
    public AsyncResponse response = null;

    public HttpRequestTask(String serverAdress, String input) {
        this.serverAdress = serverAdress;
        this.input = input;
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            URL url2 = new URL("http://" + serverAdress + "/" + input);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url2.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            serverResponse = bufferedReader.readLine();
            inputStream.close();

        }
        catch (MalformedURLException e)
        {
            return "gagal";
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Log.i("catch2", String.valueOf(e));
            return "gagal";
        }
        return serverResponse;
    }

    @Override
    protected void onPostExecute(String s) {
//        Log.i("HTTP", s);
        response.processFinish(s);
    }

    @Override
    protected void onPreExecute() {
    }
}
