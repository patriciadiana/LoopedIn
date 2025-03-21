package msa.looped.Pages;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import msa.looped.Entities.Activity;
import msa.looped.Entities.HomeNewsAdapter;
import msa.looped.Entities.Project;
import msa.looped.Entities.ProjectAdapter;
import msa.looped.Entities.ProjectsList;
import msa.looped.Entities.SavedPatternsAdapter;
import msa.looped.Entities.User;
import msa.looped.Entities.UserResponse;
import msa.looped.R;
import msa.looped.databinding.HomePageBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomePage extends Fragment {
    private HomePageBinding binding;
    public OkHttpClient client;
    private String apiUrl = Data.getInstance().getApiUrl();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = HomePageBinding.inflate(inflater, container, false);
        client = new OkHttpClient();



        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.postDelayed(() -> {
            if (isAdded() && binding != null) {
                List<Activity> activityList = Data.getFriendsActivity().getActivities();
                if (activityList != null && !activityList.isEmpty()) {
                    HomeNewsAdapter adapter = new HomeNewsAdapter(getContext(), activityList);
                    if (!adapter.isEmpty())
                        binding.newsView.setAdapter(adapter);
                }
            }
        }, 5000);
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Activity> activityList = Data.getFriendsActivity().getActivities();
        if(activityList != null  && !activityList.isEmpty()) {
            HomeNewsAdapter adapter = new HomeNewsAdapter(getContext(), activityList);
            if(!adapter.isEmpty())
                binding.newsView.setAdapter(adapter);
        }
    }

    private void loadFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.home_page, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
