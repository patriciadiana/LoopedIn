package msa.looped.Pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import msa.looped.Data;
import msa.looped.Entities.ProjectBigResult;

import msa.looped.R;
import msa.looped.databinding.EditprojectPageBinding;

import okhttp3.OkHttpClient;


public class EditProjectPage extends Fragment {
    private EditprojectPageBinding binding;
    public OkHttpClient client;
    private String apiUrl = Data.getInstance().getApiUrl();
    private ProjectBigResult project;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        client = new OkHttpClient();
        binding = EditprojectPageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            project = (ProjectBigResult) getArguments().getSerializable("project");
        }

        if (project != null) {
            binding.editTextCraft.setText(project.getProject().getCraft_name());
            binding.editTextMadeFor.setText(project.getProject().getMade_for());
            binding.editTextSize.setText(project.getProject().getSize());
            binding.projectTitle.setText(project.getProject().getName());
        }
    }

    private void loadFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.editproject_page, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
