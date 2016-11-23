package com.spy.healthmatic.Admin.Fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.spy.healthmatic.API.PatientsListAPI;
import com.spy.healthmatic.Admin.Adapters.AddPatientNurseAdapter;
import com.spy.healthmatic.Admin.AdminAddPatient;
import com.spy.healthmatic.Global.GlobalConst;
import com.spy.healthmatic.Model.Nurse;
import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.Model.Tab;
import com.spy.healthmatic.R;

import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientNurseFragment extends Fragment implements GlobalConst {

    ArrayList<Tab> tabs;
    ViewPager mViewPager;
    ArrayList<Nurse> nurses;
    @Bind(R.id.nurse_list)
    RecyclerView mNurseRecyclerView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private Patient patient;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    public PatientNurseFragment() {
        // Required empty public constructor
    }

    public static PatientNurseFragment newInstance(Patient patient, ArrayList<Tab> tabs) {
        PatientNurseFragment fragment = new PatientNurseFragment();
        Bundle args = new Bundle();
        args.putSerializable(TABS, tabs);
        args.putSerializable(PATIENT, patient);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tabs = (ArrayList<Tab>) getArguments().getSerializable(TABS);
            patient = (Patient) getArguments().getSerializable(PATIENT);
        }
        mViewPager = ((AdminAddPatient) getActivity()).getViewPagerObject();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_patient_nurse, container, false);
        ButterKnife.bind(this, rootView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        if (patient.getNurses() != null && !patient.getNurses().isEmpty()) {
            progressBar.setVisibility(View.GONE);
            nurses = patient.getNurses();
            setView();
        }
        return rootView;
    }

    private void setView() {
        mAdapter = new AddPatientNurseAdapter(nurses);
        mNurseRecyclerView.setLayoutManager(mLayoutManager);
        mNurseRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.fab_nurse_add)
    public void addNurse() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_add_doctor);
        TextView dislogTitle = (TextView) dialog.findViewById(R.id.dialog_title);
        dislogTitle.setText("Select a Nurse");
        final TextInputEditText nurseName = (TextInputEditText) dialog.findViewById(R.id.doctor);
        nurseName.setHint("Nurse");
        final AppCompatButton saveToolButton = (AppCompatButton) dialog.findViewById(R.id.add_doctor);
        saveToolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nurse = nurseName.getText().toString();
                if (nurse.equals("")) {
                    nurseName.setError("Please provide a value");
                    nurseName.requestFocus();
                    return;
                }
                Nurse nurse1 = new Nurse(nurse, "Male", 1);
                saveNurse(nurse1);
                dialog.dismiss();
            }
        });
        dialog.show();
        strechDailog(dialog);
    }

    private void saveNurse(Nurse nurse) {
        if (nurses == null) {
            nurses = new ArrayList<>();
            setView();
            progressBar.setVisibility(View.GONE);
        }
        nurses.add(nurse);
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.fab_patient_save)
    public void savePatient() {
        if (nurses == null || nurses.isEmpty()) {
            return;
        }
        patient.setNurses(nurses);
        //TODO SAVE PATIENT IN SERVER
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PatientsListAPI patientsListAPICall = retrofit.create(PatientsListAPI.class);

        Call<Patient> call = patientsListAPICall.createPatient(patient);
        String patientString = new Gson().toJson(patient);
        call.enqueue(new Callback<Patient>() {

            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("RETROFIT", "ADD PATIENT RETROFIT FAILURE jObjError.getString(message) >>>>> " + jObjError.getString("message"));
                        Toast.makeText(getContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    Log.d("RETROFIT", "ADD PATIENT RETROFIT FAILURE >>>>> " + response.errorBody());
                    Toast.makeText(getActivity(), "Was not able to ADD Patient. Please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
                Patient patient = response.body();
                Toast.makeText(getActivity(), " Patient Added", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Log.d("RETROFIT", "ADD PATIENT RETROFIT FAILURE >>>>> " + t.toString());
                Toast.makeText(getActivity(), "Was not able to fetch data. Please try again.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void strechDailog(Dialog dialog) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        lp = null;
        window = null;
    }
}
