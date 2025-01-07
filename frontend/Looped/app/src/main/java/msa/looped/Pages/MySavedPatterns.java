package msa.looped.Pages;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import msa.looped.Data;
import msa.looped.Entities.QueuedPattern;
import msa.looped.Entities.SavedPatternsAdapter;
import msa.looped.R;
import msa.looped.databinding.MysavedpatternsPageBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MySavedPatterns extends Fragment {
    private MysavedpatternsPageBinding binding;
    public OkHttpClient client;
    private String apiUrl = Data.getInstance().getApiUrl();
    private ActivityResultLauncher<Intent> documentPickerLauncher;
    private Uri documentUri;
    private String fileName, authorName, craft, userName;

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

        binding.uploadPattern.setOnClickListener(v -> uploadPopUp());

        documentPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            documentUri = data.getData();

                            // Handle the selected document
                        }
                    }
                }
        );

        return binding.getRoot();
    }

    private byte[] readFileFromUri(Uri uri) throws IOException {
        InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, length);
        }

        inputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadFile(Uri fileUri, String fileName, String authorName, String craft, String username) {
        try {
            fileName = fileName.replace(" ", "_");
            authorName = authorName.replace(" ", "_");
            String backendUrl = apiUrl + "/documents/upload?fileName="+fileName +
                    "&authorName=" + authorName + "&craft=" + craft + "&username=" + username;
            // Read the file from the URI

            System.out.println("backend url: " + backendUrl);
            byte[] fileBytes = readFileFromUri(fileUri);

            // Create RequestBody for the file
            RequestBody fileBody = RequestBody.create(fileBytes, MediaType.parse("application/octet-stream"));

            // Build the multipart request
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", fileName, fileBody);

            MultipartBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", fileName, fileBody)
                    .build();

            // Build the request
            Request request = new Request.Builder()
                    .url(backendUrl) // Replace with your backend URL
                    .post(requestBody)
                    .build();

            // Create OkHttpClient
            OkHttpClient client = new OkHttpClient();

            // Make the request asynchronously
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    System.out.println("NU");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        System.out.println("DA"  +response);
                    } else {
                        System.out.println("nu prea");
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void uploadPopUp() {

        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.upload_popup);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.upload_background);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);

        Button browseButton = dialog.findViewById(R.id.browseAFile);
        browseButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*"); // Allow all document types
            documentPickerLauncher.launch(intent);

            browsePopUp();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void browsePopUp() {

        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.browse_popup);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.upload_background);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);

        Spinner craftType = dialog.findViewById(R.id.spinnerCraftType);

        String[] craftTypes = {"Crochet", "Knitting", "Loom Knitting", "Machine Knitting", "Weaving", "Spinning"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                craftTypes
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        craftType.setAdapter(adapter);

        dialog.show();

        dialog.findViewById(R.id.addPatternToLibrary).setOnClickListener(v ->
                {
                    TextView textView = dialog.findViewById(R.id.editTextProjectNameBrowse);
                    fileName = textView.getText().toString();

                    textView = dialog.findViewById(R.id.editTextAuthorNameBrowse);
                    authorName = textView.getText().toString();

                    craft = craftType.getSelectedItem().toString();

                    userName = Data.getCurrentUser().getUsername();
                    uploadFile(documentUri, fileName, authorName, craft, userName);
                    dialog.dismiss();
                }
                );

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
