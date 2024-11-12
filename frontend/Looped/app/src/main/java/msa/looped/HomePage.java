package msa.looped;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import msa.looped.databinding.HomePageBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomePage extends Fragment {
    private HomePageBinding binding;
    public OkHttpClient client;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = HomePageBinding.inflate(inflater, container, false);
        client = new OkHttpClient();

        binding.registerButton.setOnClickListener(v -> fetchDataFromBackend());

        return binding.getRoot();

    }

    private void fetchDataFromBackend() {
        String url = "https://localhost:7035/WeatherForecast";  // Replace with your actual URL

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> binding.responseTextView.setText("DA"));
                        //getActivity().runOnUiThread(() -> binding.responseTextView.setText(responseData));
                    }
                } else {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> binding.responseTextView.setText("Error: " + response.code()));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> binding.responseTextView.setText("Failed to connect"));
                }
            }
        });
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        binding.registerButton.setOnClickListener(v ->
//                NavHostFragment.findNavController(HomePage.this)
//                        .navigate(R.id.action_LoginPage_to_signUpPage)
//        );
    }
}
