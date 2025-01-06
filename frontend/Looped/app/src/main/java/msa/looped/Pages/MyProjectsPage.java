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
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import msa.looped.Data;
import msa.looped.Entities.Friend;
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

        List<Project> projectList = Data.getProjectsList().getProjects();

        ProjectAdapter adapter = new ProjectAdapter(getContext(), projectList);
        binding.listView.setAdapter(adapter);

        binding.listView.setOnItemClickListener((parent, view, position, id) -> {
            Project selectedProject = projectList.get(position);

            SingleProjectPage singleProjectFragment = new SingleProjectPage();

            Bundle bundle = new Bundle();
            bundle.putSerializable("project", selectedProject);
            singleProjectFragment.setArguments(bundle);

            loadFragment(singleProjectFragment);
        });

        binding.savedpatternsbutton.setOnClickListener(v ->{
            loadFragment(new MySavedPatterns());
        });

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
        ProjectAdapter adapter = new ProjectAdapter(getContext(), Data.getProjectsList().getProjects());
        binding.listView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
