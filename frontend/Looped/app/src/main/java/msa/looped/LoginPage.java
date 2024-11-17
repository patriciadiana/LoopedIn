package msa.looped;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationRequest.Builder;

import java.io.IOException;
import java.util.UUID;

import msa.looped.databinding.LoginPageBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginPage extends Fragment {

    private AuthorizationService authService;
    private ActivityResultLauncher<Intent> loginActivityResultLauncher;
    private String clientId = "4e2b721afe4a3e4a5853efdf287b86cc";
    private String redirectUri = "looped://callback";
    private String RESPONSE_TYPE_CODE = "code";
    private String state;
    private LoginPageBinding binding;

    private static final String TAG = "LoginPage";  // Log tag

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Log.d(TAG, "onCreateView: Inflating LoginPage view");
        binding = LoginPageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated: Initializing view components");

        // Buton pentru a începe procesul de login
        binding.raverlyButton.setOnClickListener(v -> {
            Log.d(TAG, "onViewCreated: Ravelry button clicked");
            initiateLogin();
        });
    }

    private void initiateLogin() {
        state = generateRandomState();

        SharedPreferences preferences = requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        preferences.edit().putString("state", state).apply();

        AuthorizationServiceConfiguration config = new AuthorizationServiceConfiguration(
                Uri.parse("https://www.ravelry.com/oauth2/auth"),
                Uri.parse("https://www.ravelry.com/oauth2/token")
        );

        AuthorizationRequest authRequest = new AuthorizationRequest.Builder(
                config,
                clientId,
                RESPONSE_TYPE_CODE,
                Uri.parse(redirectUri)
        )
                .setState(state)
                .setScopes("offline")
                .build();

        AuthorizationService authService = new AuthorizationService(requireContext());
        Intent authIntent = authService.getAuthorizationRequestIntent(authRequest);

        // Trimite utilizatorul la browser pentru autentificare
        startActivity(authIntent);
    }

    private String generateRandomState() {
        // Generăm un string random de 8 caractere (poți să-l modifici după cum vrei)
        String randomState = UUID.randomUUID().toString().substring(0, 8);
        Log.d(TAG, "generateRandomState: Generated random state: " + randomState);
        return randomState;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: Cleaning up authorization service");

        if (authService != null) {
            authService.dispose();
        }
    }
}
