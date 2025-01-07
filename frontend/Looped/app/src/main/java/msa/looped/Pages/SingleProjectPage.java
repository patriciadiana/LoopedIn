package msa.looped.Pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;

import java.util.List;

import msa.looped.Entities.FirstPhoto;
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

                binding.singleProjectTitle.setText(project.getProject().getName());
                ProgressBar progressBar = binding.progressBar;

                binding.craftTextFetched.setText(project.getProject().getCraft_name());
                binding.madeForTextFetched.setText(project.getProject().getMade_for());
                binding.sizeTextFetched.setText(project.getProject().getSize());
                binding.yarnTextFetched.setText("miaumiauyarn");
                binding.needlesTextFetched.setText("miaumiauneedles");
                binding.colorwayTextFetched.setText("miaumiaucolorway");
                binding.statusTextFetched.setText(project.getProject().getStatus_name());

                TextView textProgress = binding.textViewPercentage;
                if(project.getProject().getStatus_name().equals("Finished")) {
                    progressBar.setProgress(100);
                    textProgress.setText("100%");
                }
                else if(project.getProject().getProgress() != 0){
                    progressBar.setProgress(project.getProject().getProgress());
                    textProgress.setText(project.getProject().getProgress() + "%");
                }
                else
                {
                    progressBar.setProgress(0);
                    textProgress.setText("0%");
                }

                List<FirstPhoto> photos = project.getProject().getPhotos();
                loadPhoto(photos, 0, binding.projectPhoto);
                loadPhoto(photos, 1, binding.secondProjectPhoto);
            }
        }

        binding.editButton.setOnClickListener(v ->
            NavHostFragment.findNavController(SingleProjectPage.this)
                    .navigate(R.id.action_singleProjectPage_to_editProjectPage)
        );

        return binding.getRoot();
    }

    private void loadPhoto(List<FirstPhoto> photos, int index, ImageView imageView) {
        if (photos != null && photos.size() > index && photos.get(index) != null) {
            Glide.with(this).load(photos.get(index).getThumbnailUrl()).into(imageView);
        } else {
            Glide.with(this).load(R.drawable.placeholder_image).into(imageView);
        }
    }

    private void loadFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.singleProjectPage, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
