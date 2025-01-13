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
    private List<Document> documentList;

    public SavedPatternsAdapter(Context context, List<Document> documentList) {
        this.context = context;
        this.documentList = documentList;
    }

    @Override
    public int getCount() {
        return documentList.size();
    }

    @Override
    public Object getItem(int position) {
        return documentList.get(position);
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

        Document document = documentList.get(position);

        TextView uploadTitle = convertView.findViewById(R.id.uploadTitle);
        uploadTitle.setText(document.getTitle());

        TextView authorName = convertView.findViewById(R.id.authorName);
        authorName.setText(document.getAuthorName());

        TextView craftName = convertView.findViewById(R.id.craftName);
        craftName.setText(document.getCraft());

        ImageView imageView = convertView.findViewById(R.id.imageViewDocumentType);
        Glide.with(context).load(R.drawable.placeholder_image).into(imageView);

        return convertView;
    }

}
