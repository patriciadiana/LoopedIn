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

import msa.looped.Entities.Project;
import msa.looped.R;

public class ProjectAdapter extends BaseAdapter {
    private Context context;
    private List<Project> projectsList;

    public ProjectAdapter(Context context, List<Project> projectList) {
        this.context = context;
        this.projectsList = projectList;
    }

    @Override
    public int getCount() {
        return projectsList.size();
    }

    @Override
    public Object getItem(int position) {
        return projectsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view, parent, false);
        }

        Project project = projectsList.get(position);

        TextView textView = convertView.findViewById(R.id.textViewName);
        textView.setText(project.getName());

        ProgressBar progressBar = convertView.findViewById(R.id.progressBar);

        TextView textProgress = convertView.findViewById(R.id.textViewPercentage);
        if(project.getStatus().equals("Finished")) {
            progressBar.setProgress(100);
            textProgress.setText("100%");
        }
        else if(project.getProgress() != 0){
            progressBar.setProgress(project.getProgress());
            textProgress.setText(project.getProgress() + "%");
        }
        else
        {
            progressBar.setProgress(0);
            textProgress.setText("0%");
        }

        TextView textLikes = convertView.findViewById(R.id.textViewLikeCount);
        textLikes.setText(project.getLikes());

        ImageView imageView = convertView.findViewById(R.id.imageViewPhoto);
        if (project.getFirstPhoto() != null && project.getFirstPhoto().getThumbnailUrl() != null) {
            Glide.with(context).load(project.getFirstPhoto().getThumbnailUrl()).into(imageView);
        } else {
            // Load a placeholder image or leave empty
            Glide.with(context).load(R.drawable.placeholder_image).into(imageView);
        }

        return convertView;
    }
}
