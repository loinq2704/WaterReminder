package com.loinq.unnn.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loinq.unnn.R;
import com.loinq.unnn.util.Constant;

public class HomeFragment extends Fragment {

    private TextView txtTodayIntake;
    private ProgressBar progressBar;
    private EditText edtIntake;
    private Button btnAdd;

    private int expectedIntake = 2000;
    private int weight;
    private int todayIntake;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private void bindingView(){
        txtTodayIntake = getView().findViewById(R.id.txtTodayIntake);
        progressBar = getView().findViewById(R.id.progressBar);
        edtIntake = getView().findViewById(R.id.edtIntake);
        btnAdd = getView().findViewById(R.id.btnAdd);

        sharedPreferences = getActivity().getSharedPreferences(Constant.APP_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        todayIntake = sharedPreferences.getInt(Constant.TODAY_INTAKE, 0);
        weight = sharedPreferences.getInt(Constant.WEIGHT, 0);
        if (weight != 0){
            expectedIntake = weight * 35;

            progressBar.setProgress(todayIntake / expectedIntake * 100);
            String formattedString = getString(R.string.today_intake, todayIntake);
            txtTodayIntake.setText(formattedString + "/" + expectedIntake + "ml");
        }
    }

    private void bindingAction(){
        btnAdd.setOnClickListener(this::onBtnAddClick);
    }

    private void onBtnAddClick(View view) {
        String intake = edtIntake.getText().toString();
        int intakeInt = 0;
        try {
            intakeInt = Integer.parseInt(intake);
            int todayIntake = sharedPreferences.getInt(Constant.TODAY_INTAKE, 0);
            todayIntake += intakeInt;
            editor.putInt(Constant.TODAY_INTAKE, todayIntake);
            editor.apply();
            progressBar.setProgress(todayIntake / expectedIntake * 100);
            String formattedString = getString(R.string.today_intake, todayIntake);
            txtTodayIntake.setText(formattedString + "/" + expectedIntake + "ml");
        }catch (Exception e){
            throw e;
        }

    }

    public HomeFragment() {
        // Required empty public constructor
    }

    public HomeFragment(int weight) {
        // Required empty public constructor
        this.weight = weight;
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