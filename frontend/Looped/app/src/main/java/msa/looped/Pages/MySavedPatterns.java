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
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import msa.looped.Data;
import msa.looped.Entities.Document;
import msa.looped.Entities.DocumentsList;
import msa.looped.Entities.ProjectsList;
import msa.looped.Entities.QueuedPattern;
import msa.looped.Entities.SavedPatternsAdapter;
import msa.looped.Entities.SearchGridAdapter;
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

    private DocumentsList documentList;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        documentList = new DocumentsList();
        binding = MysavedpatternsPageBinding.inflate(inflater, container, false);
        client = new OkHttpClient();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fetchUserDocuments();
        loadGrid(view);

        binding.uploadPattern.setOnClickListener(v -> uploadPopUp());

        documentPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            documentUri = data.getData();

                        }
                    }
                }
        );
    }

    private void loadGrid(View view) {
        view.postDelayed(() -> {
            if (isAdded() && binding != null) {
                if (documentList.getDocumentsList() != null && !documentList.getDocumentsList().isEmpty()) {
                    SavedPatternsAdapter adapter = new SavedPatternsAdapter(getContext(), documentList.getDocumentsList());
                    if (!adapter.isEmpty())
                        binding.listRecentUploads.setAdapter(adapter);
                }
            }
        }, 1000);
    }

    public void openDocument(byte[] fileData, String fileName, String mimeType) {
        try {
            File file = new File(requireContext().getExternalFilesDir(null), fileName);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(fileData);
            }

            Uri fileUri = FileProvider.getUriForFile(
                    requireContext(),
                    "com.Looped.msa", // Replace with your app's package name
                    file
            );

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(fileUri, mimeType);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void fetchUserDocuments() {

        String url = apiUrl + "/documents/list?user_name="+ Data.getCurrentUser().getUsername();

        System.out.println("backend url: " + url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();

                    System.out.println(responseData);

                    Gson gson = new Gson();
                    documentList = gson.fromJson(responseData, DocumentsList.class);

                    getActivity().runOnUiThread(() -> {
                        loadGrid(getView());
                    });

                    System.out.println(documentList);

                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                String errorMessage = e.getMessage();
                System.out.println(errorMessage);
            }
        });
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
                        System.out.println("DA" + response);
//                        fetchUserDocuments();
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
