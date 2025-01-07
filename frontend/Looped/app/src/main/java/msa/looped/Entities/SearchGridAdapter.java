package msa.looped.Entities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import msa.looped.R;

public class SearchGridAdapter extends BaseAdapter {
    private Context context;
    private List<SearchPattern> searchPatterns;

    public SearchGridAdapter(Context context, List<SearchPattern> searchPatterns) {
        this.context = context;
        this.searchPatterns = searchPatterns;
    }

    @Override
    public int getCount() {
        return searchPatterns.size();
    }

    @Override
    public Object getItem(int position) {
        return searchPatterns.get(position);
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

        SearchPattern searchedPattern = searchPatterns.get(position);

        TextView projectTitle = convertView.findViewById(R.id.projectTitle);
        projectTitle.setText(searchedPattern.getPatternName());

        TextView authorName = convertView.findViewById(R.id.projectAuthor);
        authorName.setText(searchedPattern.getPatternAuthor().getName());

        ImageView imageView = convertView.findViewById(R.id.imageViewPhotoQueued);
        Glide.with(context).load(searchedPattern.getPatterPhoto().getThumbnailUrl()).into(imageView);

        return convertView;
    }

}
