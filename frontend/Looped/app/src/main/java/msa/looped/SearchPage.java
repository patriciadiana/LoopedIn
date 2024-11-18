package msa.looped;

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

import msa.looped.databinding.SearchPageBinding;

public class SearchPage extends Fragment {

    private SearchPageBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = SearchPageBinding.inflate(inflater, container, false);

        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.menu_projects)
                {
                    menuItem.setChecked(true);
                    loadFragment(new MyProjectsPage());
                }
                else if(menuItem.getItemId() == R.id.menu_home)
                {
                    menuItem.setChecked(true);
                    loadFragment(new HomePage());
                }
                else if(menuItem.getItemId() == R.id.menu_more)
                {
                    menuItem.setChecked(true);
                    loadFragment(new MorePage());
                }
                return false;
            }
        });

        return binding.getRoot();

    }

    private void loadFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.search_page, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
