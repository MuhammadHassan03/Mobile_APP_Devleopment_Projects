package com.palmsolutions.farmconnect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Farmer_Chat_Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FullScreenUtil.hideSystemUI(requireActivity().getWindow().getDecorView());
        View view = inflater.inflate(R.layout.chat_layout, container, false);



        return view;
    }
}
