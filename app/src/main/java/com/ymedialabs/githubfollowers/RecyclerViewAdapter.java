package com.ymedialabs.githubfollowers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ymedialabs.githubfollowers.model.JSONClient;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private List<String> userNameList;
    private List<String> avatarURLList;
    private Context mContext;

    public RecyclerViewAdapter(Context context, List<String> userNameList, List<String> avatarURLList) {
        this.userNameList = userNameList;
        this.avatarURLList = avatarURLList;
        this.mContext = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_view, null);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(layoutView);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final String followerName = userNameList.get(position);
        final String followerImageUrl = avatarURLList.get(position);
        holder.followers_user_name_textView.setText(followerName);

        fetchAndSetImage(holder, followerImageUrl);

        holder.followers_image_view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("user_name", followerName);
                intent.putExtra("avatar_url", followerImageUrl);
                mContext.startActivity(intent);
            }
        });

    }

    private void fetchAndSetImage(final RecyclerViewHolder holder, final String imageUrl) {
        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... params) {
                return JSONClient.fetchImageFromURL(imageUrl);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                holder.followers_image_view.setImageBitmap(bitmap);
            }
        }.execute();

    }

    @Override
    public int getItemCount() {
        return this.userNameList.size();
    }


    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView followers_user_name_textView;
        public ImageView followers_image_view;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            followers_user_name_textView = (TextView) itemView.findViewById(R.id.followers_username);
            followers_image_view = (ImageView) itemView.findViewById(R.id.followers_avatar);

        }
    }
}
