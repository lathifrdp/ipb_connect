package com.example.lathifrdp.demoapp.fragment.post.job;

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
import android.util.Log;
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
import com.example.lathifrdp.demoapp.fragment.job.JobFragment;
import com.example.lathifrdp.demoapp.helper.BaseModel;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Job;
import com.example.lathifrdp.demoapp.model.JobLocation;
import com.example.lathifrdp.demoapp.response.PostJobResponse;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditVacancyFragment extends Fragment {

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
    public String title3,subject3,company3,email3,companyProfile3,jobQualification3,jobDescription3,salaryMax3,salaryMin3,file3,address3;
    ProgressDialog pd;
    public String strmin,strmax;
    public int numbermin,numbermax;
    Bundle bundle;
    private String id_vacancy;
    public String lokasinya;
    public String id_job, closenya2;
    public SimpleDateFormat closenya;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_vacancy,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Ubah Lowongan");
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

        bundle = this.getArguments();

        if(bundle != null){
            id_vacancy = bundle.getString("id_vacancy");
            loadDataEdit();
        }
        else {
            Toast.makeText(getActivity(), "gagal bos", Toast.LENGTH_SHORT).show();
        }

        getTanggalAkhir();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cek();
            }
        });

    }
    public void cek() {
        if (validate() == true) {
            return;
        }
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Mengubah Lowongan...");
        pd.setCancelable(false);
        pd.show();
        putJob();
    }

    private void loadDataEdit(){
        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<Job> call = apiService.getDetailVacancy("JWT "+ sessionManager.getKeyToken(),id_vacancy);
        call.enqueue(new Callback<Job>() {
            @Override
            public void onResponse(Call<Job> call, final Response<Job> response) {
                //mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {

                    Job job = response.body();

                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    SimpleDateFormat outputFormat = new SimpleDateFormat("d-M-yyyy");
                    closenya = new SimpleDateFormat("yyyy-M-d");
                    Date date = null;
                    try {
                        date = inputFormat.parse(job.getCloseDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String formattedDate = outputFormat.format(date);
                    closenya2 = closenya.format(date);

                    alamat.setText(job.getAddress());
                    judul.setText(job.getTitle());
                    gajimin.setText(job.getSalaryMin());
                    gajimax.setText(job.getSalaryMax());
                    deskripsi.setText(job.getJobDescription());
                    nama.setText(job.getCompany());
                    subjek.setText(job.getSubject());
                    tglakhir.setText(formattedDate);
                    //closeDate = formatted2Date;
                    email2.setText(job.getEmail());
                    profil.setText(job.getCompanyProfile());
                    kualifikasi.setText(job.getJobQualification());
                    deskripsi.setText(job.getJobDescription());
                    //alamat.setText(job.getJobLocation().getName());
                    keterangan.setText(job.getFile());
                    lokasinya = job.getJobLocation().getId();
                    id_job = job.getId();
                    loadDataLocation();
                }
            }

            @Override
            public void onFailure(Call<Job> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                //mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void loadDataLocation(){

        spinner = (Spinner) getView().findViewById(R.id.lokasi_vc);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        //ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<JobLocation>> call = apiService.getLocation("JWT "+ sessionManager.getKeyToken());
        call.enqueue(new Callback<List<JobLocation>>() {
            @Override
            public void onResponse(Call<List<JobLocation>> call, Response<List<JobLocation>> response) {
                if (response.isSuccessful()) {
                    List<JobLocation> alllokasi = response.body();
                    alllokasi.add(0, new JobLocation("0","Pilih Lokasi"));

                    adapterLocation = new JobLocationSpinner(getActivity(),android.R.layout.simple_spinner_dropdown_item, R.id.location_sp,alllokasi);
                    Toast.makeText(getActivity(), lokasinya, Toast.LENGTH_SHORT).show();
                    spinner.setAdapter(adapterLocation);
                    int pos=0;
                    int i=0;
                    for (JobLocation jb : alllokasi){
                        if(jb.getId().equals(lokasinya)){
                            pos=i;
                            break;
                        }
                        i++;
                    }
                    spinner.setSelection(pos, false);

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            JobLocation studyProgram = (JobLocation) spinner.getSelectedItem();
                            lokasinya = null;
                            jobLocationId = studyProgram.getId();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
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

    public void putJob(){
        apiService = ApiClient.getClient().create(ApiInterface.class);

        final String createdBy = sessionManager.getKeyId();
        title = judul.getText().toString();
        company = nama.getText().toString();
        address = alamat.getText().toString();
        salaryMin = gajimin.getText().toString();
        salaryMax = gajimax.getText().toString();
        companyProfile = profil.getText().toString();
        jobQualification = kualifikasi.getText().toString();
        jobDescription = deskripsi.getText().toString();
        email = email2.getText().toString();
        subject = subjek.getText().toString();
        file = keterangan.getText().toString();
        if(lokasinya!=null){
            jobLocationId = lokasinya;
        }
        if(closeDate==null){
            closeDate=closenya2;
        }

        Call<PostJobResponse> ucall = apiService.putJob("JWT "+ sessionManager.getKeyToken(), id_vacancy,title, company, jobLocationId, address, salaryMin, salaryMax, closeDate, companyProfile,jobQualification,jobDescription,email,subject,file,createdBy);
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
                        newFragment = new DetailVacancyPostFragment();
                        bundle.putString("id_vacancy",id_job);
                        newFragment.setArguments(bundle);
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
    public boolean validate() {
        boolean valid = false;
        View focusView = null;

        int cekError = 0;

        judul.setError(null);
        nama.setError(null);
        alamat.setError(null);
        gajimin.setError(null);
        gajimax.setError(null);
        profil.setError(null);
        kualifikasi.setError(null);
        deskripsi.setError(null);
        email2.setError(null);
        subjek.setError(null);
        keterangan.setError(null);


        title3 = judul.getText().toString();
        company3 = nama.getText().toString();
        address3 = alamat.getText().toString();
        salaryMin3 = gajimin.getText().toString();
        salaryMax3 = gajimax.getText().toString();
        companyProfile3 = profil.getText().toString();
        jobQualification3 = kualifikasi.getText().toString();
        jobDescription3 = deskripsi.getText().toString();
        email3 = email2.getText().toString();
        subject3 = subjek.getText().toString();
        file3 = keterangan.getText().toString();

        if(cekError==0) {
            if (title3.isEmpty()) {
                judul.setError("Judul tidak boleh kosong");
                focusView = judul;
                valid = true;
            } else {
                judul.setError(null);
                cekError=1;
            }
        }
        if(cekError==1) {
            if (company3.isEmpty()) {
                nama.setError("Nama perusahaan tidak boleh kosong");
                focusView = nama;
                valid = true;
            } else {
                nama.setError(null);
                cekError=3;
            }
        }
        if(cekError==2) {
            if (jobLocationId.isEmpty()) {
                Toast.makeText(getActivity(), "Lokasi kerja tidak boleh kosong", Toast.LENGTH_SHORT).show();
                //focusView = pilih_lokasi;
                valid = true;
            } else {
                //pilih_lokasi.setError(null);
                cekError = 3;
            }
        }
        if(cekError==3) {
            if (address3.isEmpty()) {
                alamat.setError("Alamat tidak boleh kosong");
                focusView = alamat;
                valid = true;
            } else {
                alamat.setError(null);
                cekError = 4;
            }
        }
        if(cekError==4) {
            if (salaryMin3.isEmpty()) {
                //Toast.makeText(getActivity(), "Gaji minimal tidak boleh kosong", Toast.LENGTH_SHORT).show();
                gajimin.setError("Gaji minimal tidak boleh kosong");
                focusView = gajimin;
                valid = true;
            }
            else {
                gajimin.setError(null);
                cekError=5;
            }
        }
        if(cekError==5) {
            if (salaryMax3.isEmpty()) {
                //Toast.makeText(getActivity(), "Gaji maksimal tidak boleh kosong", Toast.LENGTH_SHORT).show();
                gajimax.setError("Gaji maksimal tidak boleh kosong");
                focusView = gajimax;
                valid = true;
            } else {
                gajimax.setError(null);
                cekError = 6;
            }
        }
        if(cekError==6) {
            if (closenya2 == null) {
                Toast.makeText(getActivity(), "Tanggal berakhir tidak boleh kosong", Toast.LENGTH_SHORT).show();
                tglakhir.setError("Tanggal berakhir tidak boleh kosong");
                focusView = tglakhir;
                valid = true;
            } else {
                tglakhir.setError(null);
                cekError=7;
            }
        }
        if(cekError==7) {
            if (companyProfile3.isEmpty()) {
                profil.setError("Profil perusahaan tidak boleh kosong");
                focusView = profil;
                valid = true;
            }
            else {
                profil.setError(null);
                cekError=8;
            }
        }
        if(cekError==8) {
            if (jobQualification3.isEmpty()) {
                kualifikasi.setError("Kualifikasi dan syarat pekerjaan tidak boleh kosong");
                focusView = kualifikasi;
                valid = true;
            } else {
                kualifikasi.setError(null);
                cekError=9;
            }
        }
        if(cekError==9) {
            if (jobDescription3.isEmpty()) {
                deskripsi.setError("Deskripsi tidak boleh kosong");
                focusView = deskripsi;
                valid = true;
            } else {
                deskripsi.setError(null);
                cekError=10;
            }
        }
        if(cekError==10) {
            if (email3.isEmpty()) {
                email2.setError("Email tidak boleh kosong");
                focusView = email2;
                valid = true;
            } else if (!isEmailValid(email3)) {
                email2.setError("Email tidak valid");
                focusView = email2;
                valid = true;
            } else {
                email2.setError(null);
                cekError=11;
            }
        }
        if(cekError==11) {
            if (subject3.isEmpty()) {
                subjek.setError("Subjek tidak boleh kosong");
                focusView = subjek;
                valid = true;
            } else {
                subjek.setError(null);
                cekError=12;
            }
        }
        if(cekError==12) {
            if (file3.isEmpty()) {
                keterangan.setError("Keterangan berkas lamaran tidak boleh kosong");
                focusView = keterangan;
                valid = true;
            } else {
                keterangan.setError(null);
                cekError=13;
            }
        }
        if (valid) {
            focusView.requestFocus();
        }
        return valid;
    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@") && email.contains(".");
    }
}
