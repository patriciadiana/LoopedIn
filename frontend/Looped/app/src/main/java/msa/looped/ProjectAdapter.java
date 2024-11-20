package msa.looped;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.sql.SQLOutput;
import java.util.List;

import kotlin.system.ProcessKt;

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

        ImageView imageView = convertView.findViewById(R.id.imageViewPhoto);
        Glide.with(context).load(project.getFirstPhoto().getThumbnailUrl()).into(imageView);

        return convertView;
    }
}
