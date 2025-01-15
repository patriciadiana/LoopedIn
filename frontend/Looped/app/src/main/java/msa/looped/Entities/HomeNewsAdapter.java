package msa.looped.Entities;

import android.content.Context;
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

public class HomeNewsAdapter extends BaseAdapter {
    private Context context;
    private List<Activity> friendsActivity;

    public HomeNewsAdapter(Context context, List<Activity> friendsActivity) {
        this.context = context;
        this.friendsActivity = friendsActivity;
    }

    @Override
    public int getCount() {
        return friendsActivity.size();
    }

    @Override
    public Object getItem(int position) {
        return friendsActivity.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_news_cards_template, parent, false);
        }

        Activity activity = friendsActivity.get(position);

        String user = activity.getUser().getUsername();

        String rawDate = activity.getCreated_at();

        String formattedDate = "";
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z");
            Date date = inputFormat.parse(rawDate);

            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy");
            formattedDate = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView mainText = convertView.findViewById(R.id.startedProject);
        mainText.setText(user + " favorited a new project on\n" + formattedDate);

        TextView textProjectTitle = convertView.findViewById(R.id.projectTitle);
        textProjectTitle.setText(activity.getTitle());

        ImageView userImage = convertView.findViewById(R.id.newsProfilePhoto);
        Glide.with(context).load(activity.getUser().getPhoto_url()).into(userImage);

        ImageView projectImage = convertView.findViewById(R.id.newsProjectPhoto);
        Glide.with(context).load(activity.getActivityPhoto().getThumbnailUrl()).into(projectImage);

        return convertView;
    }

}
