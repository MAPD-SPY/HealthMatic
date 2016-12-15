package com.spy.healthmatic.Doctor.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.spy.healthmatic.Doctor.Adapters.TestsAdapter;
import com.spy.healthmatic.Global.RecyclerItemClickListener;
import com.spy.healthmatic.Model.LabTest;
import com.spy.healthmatic.R;

import java.util.ArrayList;
import java.util.List;

import static com.spy.healthmatic.R.id.large_image;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-11-01.
 */
public class TestsFragment extends Fragment {

    private String doctorName;
    private TestsAdapter testsAdapter;
    private static List<LabTest> labTests;

    public TestsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            labTests = (ArrayList<LabTest>) getArguments().getSerializable("PATIENT_TESTS_OBJ");
            doctorName = getArguments().getString("DOCTOR_NAME");
            testsAdapter = new TestsAdapter(getActivity(), labTests);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tests, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvTests);
        recyclerView.setAdapter(testsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            //
            @Override
            public void onItemClick(View view, int position) {
                zoomImage(position);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        return view;
    }

    /**
     * Show the image selected in the dialog
     * @param position index of the image selected
     */
    private void zoomImage(int position) {
        // Get the LabTest object
        final LabTest labTest = labTests.get(position);

        // Show the image only if something is saved
        if (labTest.getImageResult() != null && !"".equals(labTest.getImageResult().trim())) {

            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.lab_image);
            final ImageView imageViewTest = (ImageView) dialog.findViewById(large_image);
            final ProgressBar  progressBar = (ProgressBar) dialog.findViewById(R.id.lab_progress_dialog);
            Glide.with(getActivity())
                    .load(labTest.getImageResult())
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            dialog.dismiss();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                    }).into(imageViewTest);
            dialog.show();
            stretchDialog(dialog);
        }
    }

    /**
     * Stretch the window based to closely match the parent's width and height
     * @param dialog - dialog display
     */
    public void stretchDialog(Dialog dialog) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        lp = null;
        window = null;
    }


    public void reloadFragment() {
        testsAdapter.notifyDataSetChanged();
    }
}
