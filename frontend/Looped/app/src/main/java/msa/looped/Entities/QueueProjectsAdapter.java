package msa.looped.Entities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import msa.looped.R;

public class QueueProjectsAdapter extends BaseAdapter {
    private Context context;
    private List<QueuedPattern> queuedProjects;

    public QueueProjectsAdapter(Context context, List<QueuedPattern> queuedProjects) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.queue_item, parent, false);
        }

        QueuedPattern queuedProject = queuedProjects.get(position);

        TextView itemNumber = convertView.findViewById(R.id.itemNumber);
        itemNumber.setText((position + 1) + ".");

        TextView projectTitle = convertView.findViewById(R.id.projectTitle);
        projectTitle.setText(queuedProject.getShort_pattern_name());

        TextView authorName = convertView.findViewById(R.id.projectAuthor);
        authorName.setText(queuedProject.getPattern_author_name());

        String rawDate = queuedProject.getCreated_at_date();

        String formattedDate = "";
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z");
            Date date = inputFormat.parse(rawDate);

            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy");
            formattedDate = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView dateQueued = convertView.findViewById(R.id.queuedDate);
        dateQueued.setText("queued on " + formattedDate);

        ImageView imageView = convertView.findViewById(R.id.imageViewPhotoQueued);
        Glide.with(context).load(queuedProject.getBestPhoto().getThumbnailUrl()).into(imageView);

        return convertView;
    }

}
