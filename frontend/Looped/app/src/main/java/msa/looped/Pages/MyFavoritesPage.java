package msa.looped.Pages;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import msa.looped.Data;
import msa.looped.Entities.ItemGridAdapter;
import msa.looped.Entities.Project;
import msa.looped.Entities.ProjectAdapter;
import msa.looped.Entities.QueuedPattern;
import msa.looped.R;
import msa.looped.databinding.MyfavoritesPageBinding;
import okhttp3.OkHttpClient;

public class MyFavoritesPage extends Fragment {

    private MyfavoritesPageBinding binding;
    public OkHttpClient client;
    private String apiUrl = Data.getInstance().getApiUrl();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = MyfavoritesPageBinding.inflate(inflater, container, false);
        client = new OkHttpClient();

        List<QueuedPattern> queuedProjects = Data.getQueuedProjects().getQueuedPatterns();

        ItemGridAdapter adapter = new ItemGridAdapter(getContext(), queuedProjects);
        binding.gridView.setAdapter(adapter);

        return binding.getRoot();
    }

    private void loadFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.myfavorites_page, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        ProjectAdapter adapter = new ProjectAdapter(getContext(), Data.getProjectsList().getProjects());
        binding.gridView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
