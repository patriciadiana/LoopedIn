package msa.looped.Pages;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;

import msa.looped.Data;
import msa.looped.R;
import msa.looped.databinding.MyprofilePageBinding;

public class MyProfilePage extends Fragment {

    private MyprofilePageBinding binding;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = MyprofilePageBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String profilePicUrl = Data.getCurrentUser().getLarge_photo_url();
        String userName = Data.getCurrentUser().getUsername();
        String firstName = Data.getCurrentUser().getFirst_name();
        String location = Data.getCurrentUser().getLocation();
        String aboutMe = Data.getCurrentUser().getAbout_me();
        String projectsCount = Data.getProjectsList().getProjectsListSize() + "\nprojects";
        String queueCount = Data.getQueuedProjects().getQueuedPatternCount() + "\nqueued";

        ImageView popupImage = binding.profilePicture;
        Glide.with(requireContext())
                .load(profilePicUrl)
                .placeholder(R.drawable.myprofile)
                .error(R.drawable.myprofile)
                .into(popupImage);

        TextView textView = binding.userName;
        textView.setText(userName);

        textView = binding.firstName;
        textView.setText(firstName);

        textView = binding.location;
        textView.setText(location);

        textView = binding.aboutMe;
        textView.setText(aboutMe);

        textView = binding.projectsButton;
        textView.setText(projectsCount);

        textView = binding.queuedButton;
        textView.setText(queueCount);

        binding.editprofileButton.setOnClickListener(v ->
                NavHostFragment.findNavController(MyProfilePage.this)
                        .navigate(R.id.action_myProfilePage_to_editMyProfilePage)
        );

        binding.favoritesButton.setOnClickListener(v ->
                NavHostFragment.findNavController(MyProfilePage.this)
                        .navigate(R.id.action_myProfilePage_to_myFavoritesPage)
        );
    }

    private void loadFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.myprofile_page, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
