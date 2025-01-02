                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    package msa.looped.Entities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import msa.looped.R;

public class ItemGridAdapter extends BaseAdapter {
    private Context context;
    private List<QueuedPattern> queuedProjects;

    public ItemGridAdapter(Context context, List<QueuedPattern> queuedProjects) {
        this.context = context;
        this.queuedProjects = queuedProjects;
    }

    @Override
    public int getCount() {
        return queuedProjects.size();
    }

    @Override
    public Object getItem(int position) {
        return queuedProjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        }

        QueuedPattern queuedProject = queuedProjects.get(position);

        TextView projectTitle = convertView.findViewById(R.id.projectTitle);
        projectTitle.setText(queuedProject.getShort_pattern_name());

        TextView authorName = convertView.findViewById(R.id.projectAuthor);
        authorName.setText(queuedProject.getPattern_author_name());

        //ImageView queuedPhoto = convertView.findViewById(R.id.imageViewPhoto);
        //Glide.with(context).load(queuedProject.getBestPhoto().getThumbnailUrl()).into(queuedPhoto);

        return convertView;
    }
}
