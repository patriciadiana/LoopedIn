package msa.looped.Entities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import msa.looped.Data;
import msa.looped.Pages.MyQueuePage;
import msa.looped.Pages.WelcomePage;
import msa.looped.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QueueProjectsAdapter extends BaseAdapter {
    private Context context;
    private List<QueuedPattern> queuedProjects;
    public OkHttpClient client;
    private String apiUrl = Data.getInstance().getApiUrl();

    public QueueProjectsAdapter(Context context, List<QueuedPattern> queuedProjects) {
        this.context = context;
        this.queuedProjects = queuedProjects;
        client = new OkHttpClient();
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
        int projectId = queuedProject.getId();

        convertView.findViewById(R.id.startButton).setOnClickListener(v -> startProject(queuedProject.getShort_pattern_name()));
        convertView.findViewById(R.id.playButtonText).setOnClickListener(v -> startProject(queuedProject.getShort_pattern_name()));

        convertView.findViewById(R.id.deleteButton).setOnClickListener(v -> deleteProject(projectId,position+1));
        convertView.findViewById(R.id.deleteButtonText).setOnClickListener(v -> deleteProject(projectId,position+1));

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

    public void startProject(String name)
    {
        System.out.println("vom incepe acest proiect pe nume " + name);
    }

    public void deleteProject(int projectId, int queuePosition)
    {
        System.out.println("vom sterge acest proiect pe pozitia " + queuePosition);
        deleteProjectFromQueue(projectId);
        Data.getQueuedProjects().deletePatternAtPosition(queuePosition);
    }

    private void deleteProjectFromQueue(int projectId) {

        String url = apiUrl + "/main/queue/delete?user_name="+ Data.getCurrentUser().getUsername() + "&project_id=" + projectId;

        System.out.println("backend url: " + url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();

                    System.out.println(responseData);


                }
                else
                {
                    final String responseData = response.body().string();

                    System.out.println(responseData);
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                String errorMessage = e.getMessage();
                System.out.println(errorMessage);
            }
        });
    }

}
