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
import android.widget.TextView;

import org.w3c.dom.Text;

public class SettingsFragment extends Fragment {

    private EditText editTextWeight;
    private Button buttonSave;
    private Button buttonExport;
    private TextView textViewStoredWeight;

    private void bindingView(){
        editTextWeight = getView().findViewById(R.id.editTextWeight);
        buttonSave = getView().findViewById(R.id.buttonSave);
        buttonExport = getView().findViewById(R.id.buttonExport);
        textViewStoredWeight = getView().findViewById(R.id.textViewStoredWeight);
    }

    private void bindingAction() {
        buttonSave.setOnClickListener(this::onButtonSaveClick);
        buttonExport.setOnClickListener(this::onButtonExportClick);
    }

    private void onButtonExportClick(View view) {

    }

    private void onButtonSaveClick(View view) {
        String weight = editTextWeight.getText().toString();
        if (!weight.isEmpty()) {
            textViewStoredWeight.setText("Cân nặng đã lưu: " + weight + " kg");
        }
    }

    public SettingsFragment() {
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
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
}