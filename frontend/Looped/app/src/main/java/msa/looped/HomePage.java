package msa.looped;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.IOException;

import msa.looped.databinding.HomePageBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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
        String url = "http://10.0.2.2:5298/WeatherForecast";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> binding.responseTextView.setText(responseData));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                String errorMessage = e.getMessage();
                getActivity().runOnUiThread(() -> binding.responseTextView.setText("Failed to connect: " + errorMessage));
            }
        });
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
