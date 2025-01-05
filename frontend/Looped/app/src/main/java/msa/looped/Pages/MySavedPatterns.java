package msa.looped.Pages;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import msa.looped.Data;
import msa.looped.Entities.QueuedPattern;
import msa.looped.Entities.SavedPatternsAdapter;
import msa.looped.R;
import msa.looped.databinding.MysavedpatternsPageBinding;
import okhttp3.OkHttpClient;

public class MySavedPatterns extends Fragment {
    private MysavedpatternsPageBinding binding;
    public OkHttpClient client;
    private String apiUrl = Data.getInstance().getApiUrl();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = MysavedpatternsPageBinding.inflate(inflater, container, false);
        client = new OkHttpClient();

        List<QueuedPattern> queuedProjects = Data.getQueuedProjects().getQueuedPatterns();

        SavedPatternsAdapter adapter = new SavedPatternsAdapter(getContext(), queuedProjects);
        binding.listRecentUploads.setAdapter(adapter);

        binding.uploadPattern.setOnClickListener(v -> preparePopUp());

        return binding.getRoot();
    }

    private void preparePopUp() {

        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.upload_popup);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.upload_background);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);

        dialog.show();
    }

    private void loadFragment(androidx.fragment.app.Fragment fragment)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.mySavedPatterns, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        SavedPatternsAdapter adapter = new SavedPatternsAdapter(getContext(), Data.getQueuedProjects().getQueuedPatterns());
        binding.listRecentUploads.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
