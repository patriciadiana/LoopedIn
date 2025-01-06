package msa.looped.Entities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import msa.looped.R;

public class FriendsAdapter extends BaseAdapter {
    private Context context;
    private List<Friend> friendsList;

    public FriendsAdapter(Context context, List<Friend> friendsList) {
        this.context = context;
        this.friendsList = friendsList;
    }

    @Override
    public int getCount() {
        return friendsList.size();
    }

    @Override
    public Object getItem(int position) {
        return friendsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.friends_list_template, parent, false);
        }

        Friend friend = friendsList.get(position);

        TextView username = convertView.findViewById(R.id.friendUsername);
        username.setText(friend.getFriend_username());

        String rawDate = friend.getCreated_at();

        String formattedDate = "";
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z");
            Date date = inputFormat.parse(rawDate);

            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy");
            formattedDate = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView friendsDate = convertView.findViewById(R.id.friendsSinceDate);
        friendsDate.setText("friends since " + formattedDate);

        ImageView imageView = convertView.findViewById(R.id.imageViewfriendAvatar);
        if (friend.getFriend_avatar() != null && friend.getFriend_avatar().getLarge_photo_url() != null) {
            Glide.with(context).load(friend.getFriend_avatar().getLarge_photo_url()).into(imageView);
        } else {
            // Load a placeholder image or leave empty
            Glide.with(context).load(R.drawable.placeholder_image).into(imageView);
        }

        return convertView;
    }

}
