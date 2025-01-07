package msa.looped.Pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;

import java.io.IOException;

import msa.looped.Data;
import msa.looped.Entities.SearchResults;
import msa.looped.Entities.SearchGridAdapter;
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
    private SearchGridAdapter adapter;

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

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int[] currentPage = {1};
        int pageSize = 50;

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchCurrentUserProjects(query, currentPage[0], pageSize);
                loadGrid(view);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        fetchCurrentUserProjects("", currentPage[0], pageSize);
        loadGrid(view);

        binding.buttonCurrentPage.setText(String.valueOf(currentPage[0]));

        binding.buttonNextPage.setOnClickListener(v -> {
            currentPage[0]++;
            String query = binding.searchView.getQuery().toString();
            fetchCurrentUserProjects(query, currentPage[0], pageSize);
            binding.buttonCurrentPage.setText(String.valueOf(currentPage[0]));
            loadGrid(view);

            binding.buttonBackPage.setEnabled(true);
        });

        binding.buttonBackPage.setOnClickListener(v -> {
            if (currentPage[0] > 1) {
                currentPage[0]--;
                String query = binding.searchView.getQuery().toString();
                fetchCurrentUserProjects(query, currentPage[0], pageSize);
                binding.buttonCurrentPage.setText(String.valueOf(currentPage[0]));
                loadGrid(view);

                if (currentPage[0] == 1) {
                    binding.buttonBackPage.setEnabled(false);
                }
            }
        });
    }

    private void loadGrid(View view) {
        view.postDelayed(() -> {
            if (isAdded() && binding != null) {
                if (searchResults.getPatterns() != null && !searchResults.getPatterns().isEmpty()) {
                    SearchGridAdapter adapter = new SearchGridAdapter(getContext(), searchResults.getPatterns());
                    if (!adapter.isEmpty())
                        binding.gridViewSearched.setAdapter(adapter);
                }
            }
        }, 1000);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.search_page, fragment);
        fragmentTransaction.commit();
    }

    private void fetchCurrentUserProjects(String query, int page, int page_size) {
        String url = apiUrl + "/main/patterns/search?query=" + query + "&page=" + page + "&page_size=" + page_size;

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

                    getActivity().runOnUiThread(() -> {
                        loadGrid(getView());
                    });
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
