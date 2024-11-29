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
import msa.looped.Entities.Project;
import msa.looped.Entities.ProjectsList;
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

        if(Data.getInstance().getProfilePicUrl().equals(""))
            fetchDataFromBackend();
        return binding.getRoot();
    }

    private void fetchDataFromBackend() {

        String url = apiUrl + "/main/current_user";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();

                    Gson gson = new Gson();
                    UserResponse userResponse  = gson.fromJson(responseData, UserResponse.class);
                    Data.setCurrentUser(userResponse.getUser());
                    System.out.println("s a facut get si set la current user pe nume " + Data.getCurrentUser().getUsername());
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                String errorMessage = e.getMessage();
//                getActivity().runOnUiThread(() -> binding.responseTextView.setText("Failed to connect: " + errorMessage));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
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
