package msa.looped;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.io.IOException;

import msa.looped.databinding.HomePageBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomePage extends Fragment {

    private HomePageBinding binding;
    private String state;
    private String authorizationCode;
    private String redirectUri="";
    private String clientId = "4e2b721afe4a3e4a5853efdf287b86cc";

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = HomePageBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.button.setOnClickListener(v ->
                NavHostFragment.findNavController(HomePage.this)
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

        // Aici procesăm autorizația
        Uri data = requireActivity().getIntent().getData();
        if (data != null && data.toString().startsWith("looped://callback")) {
            String authorizationCode = data.getQueryParameter("code");
            String returnedState = data.getQueryParameter("state");

            System.out.println(data);
            if (authorizationCode != null && state.equals(returnedState)) {
                getAccessTokenFromBackend(authorizationCode);
                NavHostFragment.findNavController(HomePage.this)
                        .navigate(R.id.action_HomePage_to_dupaLogin);
            } else {
                Log.e("ProcessingFragment", "Authorization code or state mismatch.");
            }
        }
    }

    private void getAccessTokenFromBackend(String authorizationCode) {
        // Logică pentru obținerea tokenului din backend
        Log.d("getAccessTokenFromBackend", "facem get la backend");
        String backendUrl = "https://localhost:7035/api/oauth/token?code=" + authorizationCode;
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
                } else {
                    Log.e("BackendResponse", "Error: " + response.code());
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}