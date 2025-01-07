package msa.looped.Pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

import msa.looped.Entities.Friend;
import msa.looped.Entities.Project;
import msa.looped.Entities.ProjectBig;
import msa.looped.Entities.ProjectBigResult;
import msa.looped.R;
import msa.looped.databinding.SingleprojectPageBinding;

public class SingleProjectPage extends Fragment {
    private SingleprojectPageBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = SingleprojectPageBinding.inflate(inflater, container, false);

        if (getArguments() != null) {
            ProjectBigResult project = (ProjectBigResult) getArguments().getSerializable("project");
            System.out.println(project);

            if (project != null) {

//                binding.singleProjectTitle.setText(project.getName());
//                ProgressBar progressBar = binding.progressBar;

//                TextView textProgress = binding.textViewPercentage;
//                if(project.getStatus().equals("Finished")) {
//                    progressBar.setProgress(100);
//                    textProgress.setText("100%");
//                }
//                else if(project.getProgress() != 0){
//                    progressBar.setProgress(project.getProgress());
//                    textProgress.setText(project.getProgress() + "%");
//                }
//                else
//                {
//                    progressBar.setProgress(0);
//                    textProgress.setText("0%");
//                }
//
//                if (project.getFirstPhoto() != null && project.getFirstPhoto().getThumbnailUrl() != null) {
//                    Glide.with(this).load(project.getFirstPhoto().getThumbnailUrl()).into(binding.projectPhoto);
//                }
//                else
//                {
//                    Glide.with(this).load(R.drawable.placeholder_image).into(binding.projectPhoto);
//                }
            }
        }

        return binding.getRoot();
    }

    private void loadFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.friendprofile_page, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
