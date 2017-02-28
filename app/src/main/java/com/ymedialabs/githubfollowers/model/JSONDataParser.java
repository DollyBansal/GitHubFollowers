package com.ymedialabs.githubfollowers.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONDataParser {

    // JSON Node names
    public static final String TAG_LOGIN = "login";
    public static final String TAG_NAME = "name";
    public static final String TAG_AVATAR = "avatar_url";
    public static final String TAG_FOLLOWERS = "followers";
    public static final String TAG_FOLLOWING = "following";
    public static final String TAG_REPOS = "public_repos";
    public static final String TAG_LOCATION = "location";
    public static final String TAG_EMAIL = "email";

    public static List<String> getUserAllFollowersNames(final JSONArray data) {
        List<String> userNameList = new ArrayList<>();
        try {
            for (int i = 0; i < data.length(); i++) {
                JSONObject object = data.getJSONObject(i);
                String userName = object.getString(TAG_LOGIN);
                userNameList.add(userName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return userNameList;
    }

    public static List<String> getUserAllFollowersImageURL(final JSONArray data) {
        List<String> userNameListImage = new ArrayList<>();
        try {
            for (int i = 0; i < data.length(); i++) {
                JSONObject object = data.getJSONObject(i);
                String imageURL = object.getString(TAG_AVATAR);
                userNameListImage.add(imageURL);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return userNameListImage;
    }
}
