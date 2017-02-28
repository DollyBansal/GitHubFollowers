package com.ymedialabs.githubfollowers;

import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ymedialabs.githubfollowers.model.JSONClient;
import com.ymedialabs.githubfollowers.model.JSONDataParser;

import org.json.JSONArray;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GridLayoutManager layoutManager;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutManager = new GridLayoutManager(MainActivity.this, 3);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    }


    private void getJSONOfUserFollower(final String userName) {
        new AsyncTask<Void, Void, Void>() {
            JSONArray data;

            @Override
            protected Void doInBackground(Void... params) {
                JSONClient jsonClient = new JSONClient();
                data = jsonClient.getUserFollowers(userName);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                List<String> user_name = JSONDataParser.getUserAllFollowersNames(data);
                List<String> user_image_url = JSONDataParser.getUserAllFollowersImageURL(data);
                RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(MainActivity.this, user_name, user_image_url);
                recyclerView.setAdapter(rcAdapter);
                rcAdapter.notifyItemInserted(user_name.size());
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.menu_search, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.menu_search);
        final SearchView searchView =
                (SearchView) searchMenuItem.getActionView();
        searchMenuItem.expandActionView();
        searchView.setQueryHint("user name");
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                if (query != null)
                    getJSONOfUserFollower(query);
                searchView.clearFocus();
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        return true;
    }
}
