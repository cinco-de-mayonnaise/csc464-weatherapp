package com.abdullah.beginner.weatherapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.abdullah.beginner.weatherapp.databinding.FragmentSwipeBinding;
import com.abdullah.beginner.weatherapp.databinding.FragmentSwipechildSummaryBinding;

public class SummaryFragment__swipechild extends Fragment {

    FragmentSwipechildSummaryBinding binding;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSwipechildSummaryBinding.inflate(inflater, container, false);
        // set navcontroller
        //navcontroller = NavHostFragment.findNavController(SummaryFragment__swipechild.this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // programmatically enable marquee if cityname text is too long
        if (isTooLongTextForTextView(binding.textViewCityName, binding.textViewCityName.getText().toString()))
            binding.textViewCityName.setSelected(true);
        else
            binding.textViewCityName.setSelected(false);

//        binding.textViewCityName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                binding.textViewCityName.append("i");
//                if (isTooLongTextForTextView(binding.textViewCityName, binding.textViewCityName.getText().toString()))
//                    binding.textViewCityName.setSelected(true);
//                else
//                    binding.textViewCityName.setSelected(false);
//            }
//        });
    }

    private boolean isTooLongTextForTextView(TextView t, String text) {
        float widthOfTheText = t.getPaint().measureText(text);
        return widthOfTheText > t.getWidth();
    }
}
