package msa.looped.Pages;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationBarView;

import java.io.IOException;

import msa.looped.Data;
import msa.looped.R;
import msa.looped.databinding.MorePageBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MorePage extends Fragment {
    private MorePageBinding binding;
    public OkHttpClient client;
    private String apiUrl = Data.getInstance().getApiUrl();
    private String profilePicUrl = "";

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        client = new OkHttpClient();
        binding = MorePageBinding.inflate(inflater, container, false);

        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.menu_projects)
                {
                    menuItem.setChecked(true);
                    loadFragment(new MyProjectsPage());
                }
                else if(menuItem.getItemId() == R.id.menu_search)
                {
                    menuItem.setChecked(true);
                    loadFragment(new SearchPage());
                }
                else if(menuItem.getItemId() == R.id.menu_home)
                {
                    menuItem.setChecked(true);
                    loadFragment(new HomePage());
                }
                return false;
            }
        });
        preparePopup();
        return binding.getRoot();

    }

    private void loadFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.more_page, fragment);
        fragmentTransaction.commit();
    }

    public void preparePopup()
    {
        profilePicUrl = Data.getInstance().getProfilePicUrl();

        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.more_popup);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.popup_background);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);

        ImageView popupImage = dialog.findViewById(R.id.profilePicture);
        Glide.with(requireContext())
                .load(profilePicUrl)
                .placeholder(R.drawable.myprofile)
                .error(R.drawable.myprofile)
                .into(popupImage);

        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
