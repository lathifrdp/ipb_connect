package com.example.lathifrdp.demoapp.fragment.job;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
import com.example.lathifrdp.demoapp.adapter.JobLocationSpinner;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.JobLocation;

import java.util.ArrayList;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobFragment extends Fragment {

    Bundle bundle;
    private EditText title2;
    Button btnSearch;
    public String title;
    public String jobLocationId;
    ApiInterface apiService;
    SessionManager sessionManager;
    SpinnerDialog spinnerDialog;
    ArrayList<String> items=new ArrayList<>();
    TextView lokasinya, pilih_lokasi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_job,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Mencari Lowongan");
        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab_vacancy);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment createFragment = null;
                createFragment = new CreateVacancyFragment();
                createFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.screen_area, createFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        sessionManager = new SessionManager(getActivity());
        loadDataLocation();
        bundle = new Bundle();
        lokasinya = (TextView) getView().findViewById(R.id.lokasinya);
        title2 = (EditText) getView().findViewById(R.id.titleFragment);
        btnSearch = (Button) getView().findViewById(R.id.searchBTN);
        pilih_lokasi = (TextView) getView().findViewById(R.id.lokasiCombo);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = title2.getText().toString();
                Fragment newFragment = null;
                newFragment = new VacancyFragment();
                bundle.putString("title",title);
                bundle.putString("id_lokasi",jobLocationId);
                newFragment.setArguments(bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.screen_area, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        pilih_lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialog.showSpinerDialog();
            }
        });
    }
    private void loadDataLocation(){
        apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<JobLocation>> call = apiService.getLocation("JWT "+ sessionManager.getKeyToken());
        call.enqueue(new Callback<List<JobLocation>>() {
            @Override
            public void onResponse(Call<List<JobLocation>> call, Response<List<JobLocation>> response) {
                if (response.isSuccessful()) {
                    final List<JobLocation> alllokasi = response.body();
                    jobLocationId = "";
                    lokasinya.setText("Semua lokasi");
                    items.clear();
                    for(int x=0;x<alllokasi.size();x++){
                        items.add(alllokasi.get(x).getName());
                    }

                    spinnerDialog=new SpinnerDialog(getActivity(),items,"Pilih Lokasi",R.style.DialogAnimations_SmileWindow,"Tutup");
                    spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                        @Override
                        public void onClick(String item, int position) {

                            for(int x=0;x<alllokasi.size();x++){
                                if(item.equals(alllokasi.get(x).getName())) {
                                    jobLocationId = alllokasi.get(x).getId();
                                }
                            }
                            lokasinya.setText(item);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<JobLocation>> call, Throwable t) {

            }
        });
    }
}
