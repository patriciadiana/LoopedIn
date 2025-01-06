package msa.looped.Pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

import msa.looped.Entities.Friend;
import msa.looped.R;
import msa.looped.databinding.FriendprofilePageBinding;
import msa.looped.databinding.MyprofilePageBinding;

public class FriendProfilePage extends Fragment {

    private FriendprofilePageBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FriendprofilePageBinding.inflate(inflater, container, false);

        if (getArguments() != null) {
            Friend friend = (Friend) getArguments().getSerializable("friend");

            if (friend != null) {

                binding.userName.setText(friend.getFriend_username());
                binding.firstName.setText("miau first name");
                binding.location.setText("miau location name");
                binding.aboutMe.setText("miau about me name");

                if (friend.getFriend_avatar() != null && friend.getFriend_avatar().getLarge_photo_url() != null) {
                    Glide.with(this).load(friend.getFriend_avatar().getLarge_photo_url()).into(binding.profilePicture);
                }
                else
                {
                    Glide.with(this).load(R.drawable.placeholder_image).into(binding.profilePicture);
                }
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
