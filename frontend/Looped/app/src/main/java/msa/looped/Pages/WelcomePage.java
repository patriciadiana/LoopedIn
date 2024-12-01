package msa.looped.Pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import msa.looped.Data;
import msa.looped.Entities.ProjectsList;
import msa.looped.Entities.UserResponse;
import msa.looped.R;
import msa.looped.databinding.WelcomePageBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WelcomePage extends Fragment {

    private WelcomePageBinding binding;
    private String state;
    public OkHttpClient client;
    private String authorizationCode;
    private String apiUrl = Data.getInstance().getApiUrl();
    private String redirectUri="";
    private String clientId = "4e2b721afe4a3e4a5853efdf287b86cc";

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        client = new OkHttpClient();
        binding = WelcomePageBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.button.setOnClickListener(v ->
                NavHostFragment.findNavController(WelcomePage.this)
                        .navigate(R.id.action_HomePage_to_LoginPage)
        );

        if (getArguments() != null) {
            redirectUri = getArguments().getString("redirectUri");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences preferences = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        state = preferences.getString("state", null);

        Uri data = requireActivity().getIntent().getData();
        if (data != null && data.toString().startsWith("looped://callback")) {
            String authorizationCode = data.getQueryParameter("code");
            String returnedState = data.getQueryParameter("state");

            System.out.println(data);
            if (authorizationCode != null && state.equals(returnedState)) {
                getAccessTokenFromBackend(authorizationCode);
                NavHostFragment.findNavController(WelcomePage.this)
                        .navigate(R.id.action_WelcomePage_to_homePage);
            } else {
                Log.e("ProcessingFragment", "Authorization code or state mismatch.");
            }
        }
    }

    private void getAccessTokenFromBackend(String authorizationCode) {

        String backendUrl = apiUrl + "/oauth/token?code=" + authorizationCode;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(backendUrl)
                .post(RequestBody.create(null, new byte[0]))
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    Log.d("BackendResponse", "Token Response: " + responseBody);
                    fetchCurrentUserProfile();
                } else {
                    Log.e("BackendResponse", "Error: " + response.code());
                }
            }
        });
    }

    private void fetchCurrentUserProjects() {

        String url = apiUrl + "/main/user/" + Data.getCurrentUser().getUsername() +  "/projects";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();

                    Gson gson = new Gson();
                    ProjectsList projectList = gson.fromJson(responseData, ProjectsList.class);
                    Data.setProjectsList(projectList);

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
    private void fetchCurrentUserProfile() {

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
                    fetchCurrentUserProjects();
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}