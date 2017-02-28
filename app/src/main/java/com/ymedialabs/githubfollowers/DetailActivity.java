package com.ymedialabs.githubfollowers;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.ymedialabs.githubfollowers.model.JSONClient;

import org.json.JSONException;
import org.json.JSONObject;

import static com.ymedialabs.githubfollowers.model.JSONDataParser.TAG_EMAIL;
import static com.ymedialabs.githubfollowers.model.JSONDataParser.TAG_FOLLOWERS;
import static com.ymedialabs.githubfollowers.model.JSONDataParser.TAG_FOLLOWING;
import static com.ymedialabs.githubfollowers.model.JSONDataParser.TAG_LOCATION;
import static com.ymedialabs.githubfollowers.model.JSONDataParser.TAG_LOGIN;
import static com.ymedialabs.githubfollowers.model.JSONDataParser.TAG_NAME;
import static com.ymedialabs.githubfollowers.model.JSONDataParser.TAG_REPOS;

public class DetailActivity extends AppCompatActivity {
    private TextView name, user_name, user_email, user_location, repo_count, following_count, follower_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_view);

        initializeUiElement();

        String userName = getIntent().getStringExtra("user_name");
        String userImageURL = getIntent().getStringExtra("avatar_url");
        getJSONOfUserFollowerDetails(userName, userImageURL);
    }


    private void getJSONOfUserFollowerDetails(final String userName, final String user_image_url) {
        new AsyncTask<Void, Void, Bitmap>() {
            JSONObject data;

            @Override
            protected Bitmap doInBackground(Void... params) {
                JSONClient jsonClient = new JSONClient();
                data = jsonClient.getUserDetails(userName);
                return JSONClient.fetchImageFromURL(user_image_url);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                try {
                    setDetails(data);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ImageView imageView = (ImageView) findViewById(R.id.detail_followers_avatar);
                imageView.setImageBitmap(bitmap);
            }
        }.execute();

    }

    private void setDetails(JSONObject data) throws JSONException {

        user_name.setText(data.getString(TAG_LOGIN));
        name.setText(data.getString(TAG_NAME));

        following_count.setText(data.getString(TAG_FOLLOWING));
        repo_count.setText(data.getString(TAG_REPOS));
        follower_count.setText(data.getString(TAG_FOLLOWERS));

        String location = data.getString(TAG_LOCATION);
        if (location != null && !location.equals("null") && !location.isEmpty()) {
            user_location.setText(location);
        }

        String email_id = data.getString(TAG_EMAIL);
        if (!email_id.equals("null") && !email_id.isEmpty()) {
            user_email.setText(data.getString(TAG_EMAIL));
        }
    }

    private void initializeUiElement() {
        user_name = (TextView) findViewById(R.id.detail_followers_username);
        name = (TextView) findViewById(R.id.detail_followers_name);
        following_count = (TextView) findViewById(R.id.detail_following_count);
        repo_count = (TextView) findViewById(R.id.detail_repositories_count);
        follower_count = (TextView) findViewById(R.id.detail_followers_count);
        user_location = (TextView) findViewById(R.id.detail_user_location);
        user_email = (TextView) findViewById(R.id.detail_user_email);
    }
}
