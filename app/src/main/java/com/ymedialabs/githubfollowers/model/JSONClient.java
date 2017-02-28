package com.ymedialabs.githubfollowers.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Service class to call github endpoint and parse the output
 */
public class JSONClient {
    final String hostname = "https://api.github.com";

    public static String httpCall(String endPoint) {
        try {
            URL url = new URL(endPoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer buffer = new StringBuffer();
            String line;

            while ((line = reader.readLine()) != null)
                buffer.append(line).append("\n");
            reader.close();

            return buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap fetchImageFromURL(String endPoint) {
        try {
            URL url = new URL(endPoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            return BitmapFactory.decodeStream(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Given the username, call the relevant endpoint and parse the output
     *
     * @param userName
     * @return
     * @throws MalformedURLException
     * @throws JSONException
     */
    public JSONArray getUserFollowers(String userName) {
        try {
            String endPoint = hostname + "/users/" + userName + "/followers";
            String result = httpCall(endPoint);
            if (result != null) {
                return new JSONArray(result);
            } else return null;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Given userName, get its details
     *
     * @param userName
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public JSONObject getUserDetails(String userName) {
        try {
            String endPoint = hostname + "/users/" + userName;
            String result = httpCall(endPoint);
            if (result != null) {
                return new JSONObject(result);
            } else return null;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}