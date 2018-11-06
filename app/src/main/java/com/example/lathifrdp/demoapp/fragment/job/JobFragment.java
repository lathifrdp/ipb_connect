package com.example.lathifrdp.demoapp.fragment.job;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.adapter.JobLocationSpinner;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.JobLocation;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobFragment extends Fragment {

    Bundle bundle;
    private EditText title2;
    Button btnSearch;
    public String title;
    private Spinner spinner;
    private JobLocationSpinner adapterLocation;
    public String jobLocationId;
    ApiInterface apiService;
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_job,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        view.findViewById(R.id.jobFragment).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(),"Job Fragment",Toast.LENGTH_SHORT).show();
//            }
//        });
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Job Vacancy");
        sessionManager = new SessionManager(getActivity());
        loadDataProdi();
        bundle = new Bundle();
        title2 = (EditText) getView().findViewById(R.id.titleFragment);
        btnSearch = (Button) getView().findViewById(R.id.searchBTN);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = title2.getText().toString();
                Fragment newFragment = null;
                newFragment = new VacancyFragment();
                bundle.putString("title",title);
                bundle.putString("id_lokasi",jobLocationId);
                newFragment.setArguments(bundle);

                Toast.makeText(getActivity(), title, Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.screen_area, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
    private void loadDataProdi(){

        spinner = (Spinner) getView().findViewById(R.id.lokasiFragment);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        //ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<JobLocation>> call = apiService.getLocation("JWT "+ sessionManager.getKeyToken());
        call.enqueue(new Callback<List<JobLocation>>() {
            @Override
            public void onResponse(Call<List<JobLocation>> call, Response<List<JobLocation>> response) {
                if (response.isSuccessful()) {
                    List<JobLocation> alllokasi = response.body();
//                    List<String> listSpinner = new ArrayList<String>();
//                    for (int i = 0; i < allprodi.size(); i++){
//                        //nama_prodi.add(new StudyProgram(allprodi.get(i).getName()));
//                        listSpinner.add(allprodi.get(i).getName());
//                    }

                    //ArrayAdapter<StudyProgram> aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,listSpinner);
                    alllokasi.add(0, new JobLocation("0","Pilih Lokasi"));
                    adapterLocation = new JobLocationSpinner(getActivity(),android.R.layout.simple_spinner_dropdown_item, R.id.location_sp,alllokasi);

                    spinner.setAdapter(adapterLocation);

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            JobLocation studyProgram = (JobLocation) spinner.getSelectedItem();
                            jobLocationId = studyProgram.getId();
                            Toast.makeText(getActivity(), jobLocationId, Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getActivity(), studyProgram.getName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegisterActivity.this,
//                            android.R.layout.simple_spinner_item, listSpinner);
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinner.setAdapter(adapter);


                    //spinner.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, nama_prodi));
                }
            }

            @Override
            public void onFailure(Call<List<JobLocation>> call, Throwable t) {

            }
        });
    }
}
