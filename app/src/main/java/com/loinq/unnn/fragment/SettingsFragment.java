package com.loinq.unnn.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loinq.unnn.R;
import com.loinq.unnn.db.repository.DrinkRepository;
import com.loinq.unnn.db.viewModel.DrinkViewModel;
import com.loinq.unnn.util.Constant;

public class SettingsFragment extends Fragment {

    private int weight;

    private EditText editTextWeight;
    private Button buttonSave;
    private Button buttonExport;
    private TextView textViewStoredWeight;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private DrinkViewModel drinkViewModel;

    private void bindingView(){
        editTextWeight = getView().findViewById(R.id.editTextWeight);
        buttonSave = getView().findViewById(R.id.buttonSave);
        buttonExport = getView().findViewById(R.id.buttonExport);
        textViewStoredWeight = getView().findViewById(R.id.textViewStoredWeight);

        sharedPreferences = getActivity().getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        weight = sharedPreferences.getInt(Constant.WEIGHT, 0);
        textViewStoredWeight.setText("Cân nặng đã lưu: " + weight + " kg");
    }

    private void bindingAction() {
        buttonSave.setOnClickListener(this::onButtonSaveClick);
        buttonExport.setOnClickListener(this::onButtonExportClick);
    }

    private void onButtonExportClick(View view) {
        drinkViewModel.exportToCSV(requireContext());
        Toast.makeText(requireContext(),"Data exported to CSV",Toast.LENGTH_SHORT).show();
    }

    private void onButtonSaveClick(View view) {
        String weight = editTextWeight.getText().toString();
        if (!weight.isEmpty()) {
            textViewStoredWeight.setText("Cân nặng đã lưu: " + weight + " kg");
        }

        editor.putInt(Constant.WEIGHT, Integer.parseInt(weight));
        editor.apply();
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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Khởi tạo ViewModel với phạm vi của Fragment
        drinkViewModel = new ViewModelProvider(this).get(DrinkViewModel.class);

        // Sử dụng drinkViewModel ở đây
        drinkViewModel.addSampleData();
        return view;
    }
}
