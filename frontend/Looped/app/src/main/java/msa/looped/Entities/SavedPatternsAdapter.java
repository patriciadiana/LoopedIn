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

public class SavedPatternsAdapter extends BaseAdapter {
    private Context context;
    private List<QueuedPattern> queuedProjects;

    public SavedPatternsAdapter(Context context, List<QueuedPattern> queuedProjects) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.saved_patterns_template, parent, false);
        }

        QueuedPattern queuedProject = queuedProjects.get(position);

        TextView uploadTitle = convertView.findViewById(R.id.uploadTitle);
        uploadTitle.setText(queuedProject.getShort_pattern_name());

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

        TextView uploadDate = convertView.findViewById(R.id.uploadDate);
        uploadDate.setText("queued on " + formattedDate);

        ImageView imageView = convertView.findViewById(R.id.imageViewDocumentType);
        Glide.with(context).load(queuedProject.getBestPhoto().getThumbnailUrl()).into(imageView);

        return convertView;
    }

}
