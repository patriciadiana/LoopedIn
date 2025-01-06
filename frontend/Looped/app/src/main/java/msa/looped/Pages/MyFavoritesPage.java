package msa.looped.Pages;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import msa.looped.Data;
import msa.looped.Entities.ItemGridAdapter;
import msa.looped.Entities.QueuedPattern;
import msa.looped.R;
import msa.looped.databinding.MyfavoritesPageBinding;

public class MyFavoritesPage extends Fragment {

    private MyfavoritesPageBinding binding;
    private ItemGridAdapter adapter;
    private List<QueuedPattern> favoritesProjects = new ArrayList<>();
    private List<QueuedPattern> filteredProjects = new ArrayList<>();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = MyfavoritesPageBinding.inflate(inflater, container, false);

        favoritesProjects = Data.getQueuedProjects().getQueuedPatterns();
        filteredProjects.addAll(favoritesProjects);

        adapter = new ItemGridAdapter(getContext(), filteredProjects);
        binding.gridViewFavorites.setAdapter(adapter);

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterProjects(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterProjects(newText);
                return false;
            }
        });

        return binding.getRoot();
    }

    private void filterProjects(String query) {
        filteredProjects.clear();
        if (TextUtils.isEmpty(query)) {
            filteredProjects.addAll(favoritesProjects);
        } else {
            for (QueuedPattern project : favoritesProjects) {
                if (project.getShort_pattern_name().toLowerCase().contains(query.toLowerCase())) {
                    filteredProjects.add(project);
                }
            }
        }
        adapter.notifyDataSetChanged();
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
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
