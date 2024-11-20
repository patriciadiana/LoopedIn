package msa.looped.Pages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.AuthorizationRequest;

import java.util.UUID;

import msa.looped.R;
import msa.looped.databinding.LoginPageBinding;

public class LoginPage extends Fragment {

    private LoginPageBinding binding;
    private String clientId = "4e2b721afe4a3e4a5853efdf287b86cc";
    private String redirectUri = "looped://callback";
    private String RESPONSE_TYPE_CODE = "code";
    private String state;
    private String apiUrl = "";

    private static final String TAG = "LoginPage";  // Log tag

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = LoginPageBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.signupButton.setOnClickListener(v ->
                NavHostFragment.findNavController(LoginPage.this)
                        .navigate(R.id.action_LoginPage_to_signUpPage)
        );

        binding.loginButton.setOnClickListener(v ->
                NavHostFragment.findNavController(LoginPage.this)
                        .navigate(R.id.action_LoginPage_to_homePage)
        );

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

        startActivity(authIntent);
    }

    private String generateRandomState() {
        String randomState = UUID.randomUUID().toString().substring(0, 8);
        Log.d(TAG, "generateRandomState: Generated random state: " + randomState);
        return randomState;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}