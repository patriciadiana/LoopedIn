package msa.looped.Pages;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.io.IOException;

import msa.looped.Data;
import msa.looped.Entities.Project;
import msa.looped.R;
import msa.looped.databinding.AddprojectPageBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddProjectPage extends Fragment {
    private AddprojectPageBinding binding;
    private Spinner dropdown;
    public OkHttpClient client;
    private String apiUrl = Data.getInstance().getApiUrl();
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        client = new OkHttpClient();
        binding = AddprojectPageBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    int findPosition(String[] craftTypes, String craft)
    {
        int position = -1;
        for (int i = 0; i < craftTypes.length; i++) {
            if (craftTypes[i].equals(craft)) {
                position = i;
                break;
            }
        }
        return position + 1;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dropdown = binding.spinnerCraftType;

        String[] craftTypes = {"Crochet", "Knitting", "Loom Knitting", "Machine Knitting", "Weaving", "Spinning"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                craftTypes
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dropdown.setAdapter(adapter);

        binding.cancelProject.setOnClickListener(v ->
                NavHostFragment.findNavController(AddProjectPage.this)
                        .navigate(R.id.action_addProjectPage_to_homePage)
        );

        binding.createProject.setOnClickListener(v ->
            {
                NavHostFragment.findNavController(AddProjectPage.this)
                    .navigate(R.id.action_addProjectPage_to_myProjectsPage);
                String craft = dropdown.getSelectedItem().toString();
                int craftId = findPosition(craftTypes, craft);
                String projectName = binding.editTextProjectName.getText().toString();
                String usedAPattern = String.valueOf(binding.radioButtonIUsedPattern.isChecked());
                String patternUsed = binding.editTextPatternName.getText().toString();
                String madeFor = binding.enterMadeFor.getText().toString();
                sendAddedProject(Data.getCurrentUser().getUsername(), craftId, projectName, usedAPattern,
                        patternUsed, madeFor);
                Data.getProjectsList().addProjectToList(new Project(projectName, 0,"0",
                        madeFor,"",patternUsed, craftId,null));
            }

        );
    }

    private void sendAddedProject(String userName, int craft, String projectName, String usedAPattern,
                                  String patternUsed, String madeFor) {

        String backendUrl = apiUrl + "/main/add_project?user_name="+ userName + "&craft=" + craft +
                "&projectName=" + projectName + "&usedAPattern=" + usedAPattern + "&patternUsed=" +
                patternUsed+ "&madeFor=" + madeFor;
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
