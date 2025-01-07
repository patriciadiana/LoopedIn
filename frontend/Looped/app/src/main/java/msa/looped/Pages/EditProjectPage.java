package msa.looped.Pages;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;

import java.io.IOException;

import msa.looped.Data;
import msa.looped.R;
import msa.looped.databinding.EditmyprofilePageBinding;
import msa.looped.databinding.EditprojectPageBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditProjectPage extends Fragment {
    private EditprojectPageBinding binding;
    public OkHttpClient client;
    private String apiUrl = Data.getInstance().getApiUrl();
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        client = new OkHttpClient();
        binding = EditprojectPageBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        String profilePicUrl = Data.getCurrentUser().getLarge_photo_url();
//        String userName = Data.getCurrentUser().getUsername();
//        String firstName = Data.getCurrentUser().getFirst_name();
//        String location = Data.getCurrentUser().getLocation();
//        String aboutMe = Data.getCurrentUser().getAbout_me();
//        String faveColors = Data.getCurrentUser().getFave_colors();
//
//        ImageView popupImage = binding.profilePicture;
//        Glide.with(requireContext())
//                .load(profilePicUrl)
//                .placeholder(R.drawable.myprofile)
//                .error(R.drawable.myprofile)
//                .into(popupImage);
//
//        TextView textView = binding.editTextUsername;
//        textView.setText(userName);
//
//        textView = binding.editTextFirstName;
//        textView.setText(firstName);
//
//        textView = binding.editTextLocation;
//        textView.setText(location);
//
//        textView = binding.editTextAboutMe;
//        textView.setText(aboutMe);
//
//        textView = binding.editFaveColors;
//        textView.setText(faveColors);
//
//        binding.savechangesButton.setOnClickListener(v ->
//                {
//                    NavHostFragment.findNavController(EditProjectPage.this)
//                            .navigate(R.id.action_editMyProfilePage_to_myProfilePage);
//                    String newFirstName = binding.editTextFirstName.getText().toString();
//                    String newLocation = binding.editTextLocation.getText().toString();
//                    String newAboutMe = binding.editTextAboutMe.getText().toString();
//                    String newFaveColors = binding.editFaveColors.getText().toString();
//                    sendEditedProfile(userName, newFirstName, newLocation, newAboutMe, newFaveColors);
//                    Data.getCurrentUser().setFirst_name(newFirstName);
//                    Data.getCurrentUser().setLocation(newLocation);
//                    Data.getCurrentUser().setAbout_me(newAboutMe);
//                    Data.getCurrentUser().setFave_colors(newFaveColors);
//                }
//
//        );
    }

    private void sendEditedProfile(String userName, String firstName, String location, String aboutMe, String faveColors) {

        String backendUrl = apiUrl + "/main/update_profile?user_name="+ userName + "&firstName=" + firstName +
                "&location=" + location + "&aboutMe=" + aboutMe + "&faveColors=" + faveColors;
        System.out.println("backend url: " + backendUrl);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(backendUrl)
                .post(RequestBody.create(null, new byte[0]))
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    Log.d("BackendResponse", "Token Response: " + responseBody);

                } else {
                    Log.e("BackendResponse", "Error: " + response.code());
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
