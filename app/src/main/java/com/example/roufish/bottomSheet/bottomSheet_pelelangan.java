package com.example.roufish.bottomSheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.roufish.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class bottomSheet_pelelangan extends BottomSheetDialogFragment {

    public bottomSheet_pelelangan() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bottom_sheet_pelelangan, container, false);
    }
}

