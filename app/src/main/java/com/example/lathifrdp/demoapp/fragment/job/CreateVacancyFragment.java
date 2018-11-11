package com.example.lathifrdp.demoapp.fragment.job;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.adapter.JobLocationSpinner;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.JobLocation;
import com.example.lathifrdp.demoapp.response.PostJobResponse;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateVacancyFragment extends Fragment {

    EditText judul, nama, alamat, gajimin, gajimax, tglakhir, profil, kualifikasi, deskripsi, email2, subjek,keterangan;
    private Spinner spinner;
    private JobLocationSpinner adapterLocation;
    public String jobLocationId;
    ApiInterface apiService;
    SessionManager sessionManager;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String closeDate;
    Button btn;
    public String title,subject,company,email,companyProfile,jobQualification,jobDescription,salaryMax,salaryMin,file,address;
    ProgressDialog pd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_vacancy,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Create Vacancy");
        sessionManager = new SessionManager(getActivity());

        judul = (EditText) getView().findViewById(R.id.judul);
        nama = (EditText) getView().findViewById(R.id.nama_perusahaan);
        alamat = (EditText) getView().findViewById(R.id.alamat_perusahaan);
        gajimin = (EditText) getView().findViewById(R.id.gaji_min);
        gajimax = (EditText) getView().findViewById(R.id.gaji_max);
        profil = (EditText) getView().findViewById(R.id.profil_perusahaan);
        kualifikasi = (EditText) getView().findViewById(R.id.kualifikasi);
        deskripsi = (EditText) getView().findViewById(R.id.deskripsi_pekerjaan);
        email2 = (EditText) getView().findViewById(R.id.email_vc);
        subjek = (EditText) getView().findViewById(R.id.email_subject_vc);
        keterangan = (EditText) getView().findViewById(R.id.berkas_lamaran);
        btn = (Button) getView().findViewById(R.id.submit_vc);

        loadDataProdi();
        getTanggalAkhir();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(getActivity());
                pd.setMessage("Insert Vacancy...");
                pd.setCancelable(false);
                pd.show();
                postJob();
            }
        });

    }
    private void loadDataProdi(){

        spinner = (Spinner) getView().findViewById(R.id.lokasi_vc);
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
    public void getTanggalAkhir(){
        tglakhir = (EditText) getView().findViewById(R.id.tanggal_berakhir);

        tglakhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "-" + month + "-" + year;
                closeDate = year + "-" + month + "-" + day;
                //Toast.makeText(RegisterActivity.this, dateOfBirth, Toast.LENGTH_SHORT).show();
                tglakhir.setText(date);
            }
        };
    }

    public void postJob(){
        apiService = ApiClient.getClient().create(ApiInterface.class);

        final String createdBy = sessionManager.getKeyId();
        title = judul.getText().toString();
        company = nama.getText().toString();
        address = alamat.getText().toString();
        salaryMin = gajimin.getText().toString();
        salaryMax = gajimax.getText().toString();
        companyProfile = profil.getText().toString();
        jobQualification = nama.getText().toString();
        jobDescription = deskripsi.getText().toString();
        email = email2.getText().toString();
        subject = subjek.getText().toString();
        file = keterangan.getText().toString();

        Call<PostJobResponse> ucall = apiService.postJob("JWT "+ sessionManager.getKeyToken(), title, company, jobLocationId, address, salaryMin, salaryMax, closeDate, companyProfile,jobQualification,jobDescription,email,subject,file,createdBy);
        ucall.enqueue(new Callback<PostJobResponse>() {
            @Override
            public void onResponse(Call<PostJobResponse> call, Response<PostJobResponse> response) {

                if (response.isSuccessful()) {

                    PostJobResponse pjr = response.body();

                    if(pjr.isSuccess()==false ){
                        Snackbar.make(getView(), pjr.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else {
                        Snackbar.make(getView(), pjr.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        Fragment newFragment = null;
                        newFragment = new JobFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.screen_area, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                    pd.dismiss();
                }
            }

            @Override
            public void onFailure(Call<PostJobResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Mohon maaf sedang terjadi gangguan", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }
}
