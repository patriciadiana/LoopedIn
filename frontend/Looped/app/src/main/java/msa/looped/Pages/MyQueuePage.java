package msa.looped.Pages;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import msa.looped.Data;
import msa.looped.Entities.QueueProjectsAdapter;
import msa.looped.Entities.QueuedPattern;
import msa.looped.R;
import msa.looped.databinding.MyqueuePageBinding;
import okhttp3.OkHttpClient;

public class MyQueuePage extends Fragment {

    private MyqueuePageBinding binding;
    public OkHttpClient client;
    private String apiUrl = Data.getInstance().getApiUrl();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = MyqueuePageBinding.inflate(inflater, container, false);
        client = new OkHttpClient();

        List<QueuedPattern> queuedProjects = Data.getQueuedProjects().getQueuedPatterns();

        QueueProjectsAdapter adapter = new QueueProjectsAdapter(getContext(), queuedProjects);
        binding.listViewQueue.setAdapter(adapter);

        return binding.getRoot();
    }

    private void loadFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.myQueuePage, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        QueueProjectsAdapter adapter = new QueueProjectsAdapter(getContext(), Data.getQueuedProjects().getQueuedPatterns());
        binding.listViewQueue.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
