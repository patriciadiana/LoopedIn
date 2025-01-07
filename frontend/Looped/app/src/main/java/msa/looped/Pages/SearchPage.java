package msa.looped.Pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;

import java.io.IOException;

import msa.looped.Data;
import msa.looped.Entities.ProjectsList;
import msa.looped.Entities.QueuedProjects;
import msa.looped.Entities.SearchResults;
import msa.looped.R;
import msa.looped.databinding.SearchPageBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchPage extends Fragment {
    private SearchPageBinding binding;
    public OkHttpClient client;
    private String apiUrl = Data.getInstance().getApiUrl();
    private SearchResults searchResults;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        searchResults = new SearchResults();
        binding = SearchPageBinding.inflate(inflater, container, false);
        client = new OkHttpClient();
        return binding.getRoot();

    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchCurrentUserProjects("cardigan", 1, 5);
    }

    private void loadFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.search_page, fragment);
        fragmentTransaction.commit();
    }

    private void fetchCurrentUserProjects(String query, int page, int page_size) {

        String url = apiUrl + "/main/patterns/search?query=" + query +  "&page=" + page + "&page_size=" + page_size;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();

                    Gson gson = new Gson();
                    searchResults = gson.fromJson(responseData, SearchResults.class);
                    System.out.println(searchResults);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
