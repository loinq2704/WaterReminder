package com.loinq.unnn;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeFragment extends Fragment {

    private EditText edtIntake;
    private Button btnAdd;

    private void bindingView(){
        edtIntake = getView().findViewById(R.id.edtIntake);
        btnAdd = getView().findViewById(R.id.btnAdd);
    }

    private void bindingAction(){
        btnAdd.setOnClickListener(this::onBtnAddClick);
    }

    private void onBtnAddClick(View view) {
        String intake = edtIntake.getText().toString();
        int intakeInt = 0;
        try {
            intakeInt = Integer.parseInt(intake);
            Toast.makeText(getContext(), intakeInt, Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getContext(), R.string.edt_intake, Toast.LENGTH_SHORT).show();
        }

    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindingView();
        bindingAction();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}