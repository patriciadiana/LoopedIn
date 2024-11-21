package msa.looped.Pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import msa.looped.Data;
import msa.looped.Entities.Project;
import msa.looped.Entities.ProjectAdapter;
import msa.looped.Entities.ProjectsList;
import msa.looped.R;
import msa.looped.databinding.MyprojectsPageBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyProjectsPage extends Fragment {
    private MyprojectsPageBinding binding;

    public OkHttpClient client;
    private String apiUrl = Data.getInstance().getApiUrl();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = MyprojectsPageBinding.inflate(inflater, container, false);
        client = new OkHttpClient();

        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.menu_more)
                {
                    menuItem.setChecked(true);
                    loadFragment(new MorePage());
                }
                else if(menuItem.getItemId() == R.id.menu_search)
                {
                    menuItem.setChecked(true);
                    loadFragment(new SearchPage());
                }
                else if(menuItem.getItemId() == R.id.menu_home)
                {
                    menuItem.setChecked(true);
                    loadFragment(new HomePage());
                }
                return false;
            }
        });
        fetchDataFromBackend();
        return binding.getRoot();

    }

    private void loadFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.myprojects_page, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchDataFromBackend();
    }

    private void fetchDataFromBackend() {

        String url = apiUrl + "/main/current_user/projects";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();

                    Gson gson = new Gson();
                    ProjectsList projectsList = gson.fromJson(responseData, ProjectsList.class);

                    List<Project> projectList = projectsList.getProjects();

                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            ProjectAdapter adapter = new ProjectAdapter(getContext(), projectList);
                            binding.listView.setAdapter(adapter);
                        });
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                String errorMessage = e.getMessage();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
