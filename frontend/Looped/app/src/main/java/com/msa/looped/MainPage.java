package com.msa.looped;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.msa.looped.databinding.MainPageBinding;

public class MainPage extends Fragment {
    private MainPageBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = MainPageBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonGetStarted.setOnClickListener(v ->
                NavHostFragment.findNavController(MainPage.this)
                        .navigate(R.id.action_main_page_to_login_page)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
