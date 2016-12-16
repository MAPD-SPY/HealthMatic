package com.spy.healthmatic.Admin.Fragments;

//Team Name: Team SPY

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.spy.healthmatic.API.PatientsListAPI;
import com.spy.healthmatic.API.StaffAPI;
import com.spy.healthmatic.Admin.Adapters.AddPatientNurseAdapter;
import com.spy.healthmatic.Admin.AdminAddPatient;
import com.spy.healthmatic.Admin.AdminMainActivity;
import com.spy.healthmatic.Global.GlobalConst;
import com.spy.healthmatic.Model.Doctor;
import com.spy.healthmatic.Model.Hospital;
import com.spy.healthmatic.Model.Nurse;
import com.spy.healthmatic.Model.Patient;
import com.spy.healthmatic.Model.PatientRef;
import com.spy.healthmatic.Model.Staff;
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
//    ArrayList<Nurse> nurses;
    @Bind(R.id.nurse_list)
    RecyclerView mNurseRecyclerView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private Patient patient, updatepatient;
    private PatientRef updatePaientRef;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    ArrayList<Staff> selectedNurses;
    ArrayAdapter<String> nurseAdapter;
    ArrayList<String> nurseNames;
    public ProgressDialog mProgressDialog;

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
        setupAdapter();
        if (patient.getNurses() != null && !patient.getNurses().isEmpty()) {
            setupSelectedNurses();
        }else if(selectedNurses!=null && !selectedNurses.isEmpty()){
            progressBar.setVisibility(View.GONE);
            setView();
        }
        return rootView;
    }

    private void setupAdapter(){
        nurseNames = new ArrayList<>();
        for(Staff staff : AdminAddPatient.nurses){
            nurseNames.add(staff.getFirstName() +" "+staff.getLastName());
        }
        nurseAdapter =  new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, nurseNames);
    }

    private void setupSelectedNurses(){
        for(Nurse nurse : patient.getNurses()){
            for (Staff staff : AdminAddPatient.nurses){
                if(staff.get_id().equals(nurse.getId())){
                    selectedNurses.add(staff);
                    nurseAdapter.remove(staff.getFirstName() + " " + staff.getLastName());
                    break;
                }
            }
        }
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "Click on button below to save any changes.", Toast.LENGTH_LONG).show();
        setView();
    }

    private void setView() {
        mAdapter = new AddPatientNurseAdapter(selectedNurses);
        mNurseRecyclerView.setLayoutManager(mLayoutManager);
        mNurseRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.fab_nurse_add)
    public void addNurse() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_add_doctor);
        TextView dislogTitle = (TextView) dialog.findViewById(R.id.dialog_title);
        dislogTitle.setText("Select a Nurse");
        final AutoCompleteTextView nurseName = (AutoCompleteTextView) dialog.findViewById(R.id.doctor);
        nurseName.setAdapter(nurseAdapter);
        nurseName.setHint("Nurse");
        final AppCompatButton saveToolButton = (AppCompatButton) dialog.findViewById(R.id.add_doctor);
        saveToolButton.setEnabled(false);
        nurseName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                saveToolButton.setEnabled(true);
            }
        });
        saveToolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nurse = nurseName.getText().toString();
                if (nurse.equals("")) {
                    nurseName.setError("Please provide a value");
                    nurseName.requestFocus();
                    return;
                }
                Staff staff = AdminAddPatient.nurses.get(nurseNames.indexOf(nurse));
                nurseAdapter.remove(nurse);
                saveNurse(staff);
                dialog.dismiss();
            }
        });
        dialog.show();
        strechDailog(dialog);
    }

    private void saveNurse(Staff nurse) {
        if (selectedNurses == null) {
            selectedNurses = new ArrayList<>();
            setView();
            progressBar.setVisibility(View.GONE);
        }
        selectedNurses.add(nurse);
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.fab_patient_save)
    public void savePatient() {
        if (selectedNurses == null || selectedNurses.isEmpty()) {
            return;
        }
        ArrayList<Nurse> nurses = new ArrayList<>();
        for(Staff staff: selectedNurses){
            nurses.add(new Nurse(staff.get_id(), staff.getFirstName(), staff.getLastName(), ""));
        }
        patient.setNurses(nurses);

        savePatientInServer();
        //TODO SAVE PATIENT IN SERVER
    }

    private void savePatientInServer(){
        showProgressDialog();
        Call<Patient> call = PATIENTS_LIST_API.createPatient(patient);
        String patientString = new Gson().toJson(patient);
        call.enqueue(new Callback<Patient>() {

            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("RETROFIT", "ADD PATIENT RETROFIT FAILURE jObjError.getString(message) >>>>> " + jObjError.getString("message"));
                        showToast(jObjError.getString("message"));
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    return;
                }
                updatepatient = response.body();
                updatePaientRef = new PatientRef(updatepatient.get_id(), new String[]{});
                String updatedPatientString = new Gson().toJson(patient);
                updateDoctors();
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Log.d("RETROFIT", "ADD PATIENT RETROFIT FAILURE >>>>> " + t.toString());
                showToast("Was not able to connect with server. Please try again.");
            }
        });
    }

    private void updateDoctors(){
        ArrayList<Staff> staffs = new ArrayList<>();
        for(Doctor doctor : patient.getDoctors()){
            for (Staff staff : AdminAddPatient.doctors){
                if(staff.get_id().equals(doctor.getId())){
                    staffs.add(staff);
                    break;
                }
            }
        }
        if(staffs.get(0).getPatientRefs()==null){
            staffs.get(0).setPatientRefs(new ArrayList<PatientRef>());
        }
        staffs.get(0).getPatientRefs().add(updatePaientRef);
        Call<Staff> call = STAFF_API.updateStaff(staffs.get(0).get_id(), staffs.get(0));
        String doctorString = new Gson().toJson(staffs.get(0));
        call.enqueue(new Callback<Staff>() {

            @Override
            public void onResponse(Call<Staff> call, Response<Staff> response) {
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("RETROFIT", "UPDATE DOCTOR RETROFIT FAILURE jObjError.getString(message) >>>>> " + jObjError.getString("message"));
                        showToast(jObjError.getString("message"));
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    return;
                }
                updateRoom();
            }

            @Override
            public void onFailure(Call<Staff> call, Throwable t) {
                Log.d("RETROFIT", "ADD PATIENT RETROFIT FAILURE >>>>> " + t.toString());
                showToast("Was not able to connect with server. Please try again.");
            }
        });
    }

    private void updateRoom(){
        AdminAddPatient.selectedRoom.setAvailability(false);
        Call<Hospital> call = HOSPITAL_API.updateHospitalRoom(AdminAddPatient.hospitalId, AdminAddPatient.selectedRoom);
        call.enqueue(new Callback<Hospital>() {
            @Override
            public void onResponse(Call<Hospital> call, Response<Hospital> response) {
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("RETROFIT", "UPDATE DOCTOR RETROFIT FAILURE jObjError.getString(message) >>>>> " + jObjError.getString("message"));
                        showToast(jObjError.getString("message"));
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    return;
                }
                updateNurse();
            }

            @Override
            public void onFailure(Call<Hospital> call, Throwable t) {
                Log.d("RETROFIT", "ADD PATIENT RETROFIT FAILURE >>>>> " + t.toString());
                showToast("Was not able to connect with server. Please try again.");
            }
        });
    }

    private void updateNurse(){
        if(selectedNurses.get(0).getPatientRefs()==null){
            selectedNurses.get(0).setPatientRefs(new ArrayList<PatientRef>());
        }
        selectedNurses.get(0).getPatientRefs().add(updatePaientRef);
        Call<Staff> call = STAFF_API.updateStaff(selectedNurses.get(0).get_id(), selectedNurses.get(0));
        String nurseString = new Gson().toJson(selectedNurses.get(0));
        call.enqueue(new Callback<Staff>() {

            @Override
            public void onResponse(Call<Staff> call, Response<Staff> response) {
                if (!response.isSuccessful()) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d("RETROFIT", "UPDATE DOCTOR RETROFIT FAILURE jObjError.getString(message) >>>>> " + jObjError.getString("message"));
                        showToast(jObjError.getString("message"));
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    return;
                }
                showToast("Patient added");
                Intent intent = new Intent(getActivity(), AdminMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Staff> call, Throwable t) {
                Log.d("RETROFIT", "ADD PATIENT RETROFIT FAILURE >>>>> " + t.toString());
                showToast("Was not able to connect with server. Please try again.");
            }
        });
    }

    private void showToast(String msg){
        hideProgressDialog();
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
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

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Creating Patient…");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
