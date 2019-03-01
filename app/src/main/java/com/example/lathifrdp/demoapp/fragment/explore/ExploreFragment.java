package com.example.lathifrdp.demoapp.fragment.explore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.adapter.StudyProgramSpinner;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.StudyProgram;
import com.example.lathifrdp.demoapp.model.User;
import com.example.lathifrdp.demoapp.response.UserResponse;

import java.util.ArrayList;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreFragment extends Fragment{

    ApiInterface apiService;
    //private Spinner spinner;
    private StudyProgramSpinner adapterProdik;
    public String studyProgramId;
    Button btnExplore;
    private EditText fullName2;
    private EditText batch2;
    //Spinner prodi;
    String isVerified;
    SessionManager sessionManager;
    UserResponse ur;
    Bundle bundle;
    TextView tv;
    public String fullName, batch, prod;
    SpinnerDialog spinnerDialog;
    ArrayList<String> items=new ArrayList<>();
    TextView prodinya, pilih_prodi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_explore,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Mencari Alumni");

        loadDataProdi();
        bundle = new Bundle();
        fullName2 = (EditText) getView().findViewById(R.id.tesfullNameFragment);
        batch2 = (EditText) getView().findViewById(R.id.tesbatchFragment);
        prodinya = (TextView) getView().findViewById(R.id.prodinya);
        pilih_prodi = (TextView) getView().findViewById(R.id.prodiCombo);

        isVerified = "1";
        sessionManager = new SessionManager(getActivity());
        btnExplore = (Button) getView().findViewById(R.id.exploreBTN);
        btnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullName = fullName2.getText().toString();
                batch = batch2.getText().toString();

                Fragment newFragment = null;
                newFragment = new UserFragment();

                bundle.putString("fullname",fullName); // Put anything what you want
                bundle.putString("batch",batch.toString()); // Put anything what you want
                bundle.putString("study",studyProgramId); // Put anything what you want
                bundle.putString("isVerified",isVerified.toString()); // Put anything what you want

                newFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.screen_area, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        pilih_prodi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialog.showSpinerDialog();
            }
        });

    }

    private void loadDataProdi(){
        apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<StudyProgram>> call = apiService.getProdi();
        call.enqueue(new Callback<List<StudyProgram>>() {
            @Override
            public void onResponse(Call<List<StudyProgram>> call, Response<List<StudyProgram>> response) {
                if (response.isSuccessful()) {
                    final List<StudyProgram> allprodi = response.body();
                    studyProgramId = "";
                    prodinya.setText("Semua program studi");
                    items.clear();
                    for(int x=0;x<allprodi.size();x++){
                        items.add(allprodi.get(x).getName());
                    }

                    spinnerDialog=new SpinnerDialog(getActivity(),items,"Pilih Program Studi",R.style.DialogAnimations_SmileWindow,"Tutup");
                    spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                        @Override
                        public void onClick(String item, int position) {

                            for(int x=0;x<allprodi.size();x++){
                                if(item.equals(allprodi.get(x).getName())) {
                                    studyProgramId = allprodi.get(x).getFacultyId();
                                }
                            }
                            prodinya.setText(item);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<StudyProgram>> call, Throwable t) {

            }
        });
    }
}
