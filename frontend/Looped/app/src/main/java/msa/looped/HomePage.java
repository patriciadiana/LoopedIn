package msa.looped;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import msa.looped.databinding.ActivityMainBinding;
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
//        binding.registerButton.setOnClickListener(v -> fetchDataFromBackend());
        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.menu_projects)
                {
                    menuItem.setChecked(true);
                    loadFragment(new MyProjectsPage());
                }
                else if(menuItem.getItemId() == R.id.menu_search)
                {
                    menuItem.setChecked(true);
                    loadFragment(new SearchPage());
                }
                else if(menuItem.getItemId() == R.id.menu_more)
                {
                    menuItem.setChecked(true);
                    loadFragment(new MorePage());
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

    @Override
    public void onResume() {
        super.onResume();
        fetchDataFromBackend();
    }

    private void loadFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.home_page, fragment);
        fragmentTransaction.commit();
    }

    private void fetchDataFromBackend() {

        String url = apiUrl + "/main/projects/current_user";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> binding.username.setText(responseData));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                String errorMessage = e.getMessage();
                getActivity().runOnUiThread(() -> binding.username.setText("Failed to connect: " + errorMessage));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

//    private void sendDataToBackend() throws IOException {
//        String url = "";
//
//        RequestBody formBody = new FormBody.Builder()
//                .add("username", "test")
//                .add("password", "test")
//                .build();
//
//        Request request = new Request.Builder()
//                .url(url)
//                .post(formBody)
//                .build();
//
//        Call call = client.newCall(request);
//        Response response = call.execute();
//
//    }
}
