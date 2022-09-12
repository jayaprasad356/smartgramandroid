package com.jp.smartgram;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jp.smartgram.helper.Constant;


public class DoctorFragment extends Fragment {

    View root;
    EditText etName, etMobile, etAge, etDiseases, etPlace, etDescription, etHistory;
    Button btnBookappointment;

    public DoctorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_doctor, container, false);

        btnBookappointment = root.findViewById(R.id.btnBookappointment);
        etName = root.findViewById(R.id.etName);
        etMobile = root.findViewById(R.id.etMobile);
        etAge = root.findViewById(R.id.etAge);
        etDiseases = root.findViewById(R.id.etDiseases);
        etPlace = root.findViewById(R.id.etPlace);
        etDescription = root.findViewById(R.id.etDescription);
        etHistory = root.findViewById(R.id.etHistory);

        btnBookappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().toString().trim().equals("")) {
                    etName.setError("empty");
                    etName.requestFocus();


                } else if (etMobile.getText().toString().trim().equals("")) {
                    etMobile.setError("empty");
                    etMobile.requestFocus();

                } else if (etMobile.getText().length() != 10) {
                    etMobile.setError("Invaid");
                    etMobile.requestFocus();
                } else if (etAge.getText().toString().trim().equals("")) {
                    etAge.setError("empty");
                    etAge.requestFocus();

                } else if (etDiseases.getText().toString().trim().equals("")) {
                    etDiseases.setError("empty");
                    etDiseases.requestFocus();

                } else if (etPlace.getText().toString().trim().equals("")) {
                    etPlace.setError("empty");
                    etPlace.requestFocus();

                } else if (etDescription.getText().toString().trim().equals("")) {
                    etDescription.setError("empty");
                    etDescription.requestFocus();

                } else if (etHistory.getText().toString().trim().equals("")) {
                    etHistory.setError("empty");
                    etHistory.requestFocus();

                } else {
                    Intent intent = new Intent(getActivity(), DoctorListActivity.class);
                    intent.putExtra(Constant.NAME,etName.getText().toString().trim());
                    intent.putExtra(Constant.MOBILE,etMobile.getText().toString().trim());
                    intent.putExtra(Constant.AGE,etAge.getText().toString().trim());
                    intent.putExtra(Constant.DISEASE,etDiseases.getText().toString().trim());
                    intent.putExtra(Constant.PLACE,etPlace.getText().toString().trim());
                    intent.putExtra(Constant.DESCRIPTION,etDescription.getText().toString().trim());
                    intent.putExtra(Constant.HISTORY,etHistory.getText().toString().trim());
                    startActivity(intent);

                }

            }
        });

        return root;
    }
}