package com.example.lathifrdp.demoapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.adapter.StudyProgramSpinner;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.model.StudyProgram;
import com.example.lathifrdp.demoapp.response.RegisterResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private RadioGroup radiojkGroup;
    private RadioButton radiojkButton;
    private RadioButton radiostatButton;
    private EditText mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    ApiInterface apiService;
    private Spinner spinner;
    private StudyProgramSpinner adapterProdik;
    private ArrayList<StudyProgram> nama_prodi;
    private ArrayAdapter<String> adapterProdi;
    private EditText etNamaLengkap;
    private EditText etEmail;
    private EditText etPass;
    private EditText etPassKonf;
    private EditText etNIM;
    private EditText etAngkatan;
    private String gender;
    private String userType;
    private String dateOfBirth;
    private String studyProgramId;
    Button butRegis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNamaLengkap = (EditText) findViewById(R.id.nama_lengkap);
        radiojkButton = (RadioButton) findViewById(R.id.laki2);
        etEmail = (EditText) findViewById(R.id.email);
        etPass = (EditText) findViewById(R.id.password);
        etPassKonf = (EditText) findViewById(R.id.konfirm_pass);
        etNIM = (EditText) findViewById(R.id.nim);
        radiostatButton = (RadioButton) findViewById(R.id.alumni);
        etAngkatan = (EditText) findViewById(R.id.angkatan);

        onRadioButtonJKClicked(radiojkButton);
        onRadioButtonStatClicked(radiostatButton);
        getTanggalLahir();

        loadDataProdi();

        butRegis = (Button) findViewById(R.id.register);
        butRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(studyProgramId) || studyProgramId.equals("0")){
                    Toast.makeText(RegisterActivity.this, "Silahkan pilih program studi", Toast.LENGTH_SHORT).show();
                    return;
                }
                cek();
            }
        });

        TextView kembali = findViewById(R.id.register_back);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(loginIntent);
            }
        });

    }

    public void cek() {
        if (validate() == true) {
            return;
        }
        daftarkuy();
    }

    public void daftarkuy(){
        apiService = ApiClient.getClient().create(ApiInterface.class);

        final String fullName = etNamaLengkap.getText().toString();
        final String email = etEmail.getText().toString();
        final String password = etPass.getText().toString();
        final String passkonf = etPassKonf.getText().toString();
        final String nim = etNIM.getText().toString();
        final String batch = etAngkatan.getText().toString();
        final Integer isVerified = 0;

        Call<RegisterResponse> ucall = apiService.registerRequest(fullName, gender, dateOfBirth, email, password, nim, userType, batch, studyProgramId, isVerified);
        ucall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                if (response.isSuccessful()) {

                    RegisterResponse rr = response.body();

                    if(rr.isSuccess()==false ){
                        Toast.makeText(RegisterActivity.this, rr.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent logIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                        Toast.makeText(RegisterActivity.this, rr.getMessage(), Toast.LENGTH_SHORT).show();
                        RegisterActivity.this.startActivity(logIntent);
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Mohon maaf sedang terjadi gangguan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getTanggalLahir(){
        mDisplayDate = (EditText) findViewById(R.id.tanggal_lahir);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        RegisterActivity.this,
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
                dateOfBirth = year + "-" + month + "-" + day;
                //Toast.makeText(RegisterActivity.this, dateOfBirth, Toast.LENGTH_SHORT).show();
                mDisplayDate.setText(date);
            }
        };
    }

    public void onRadioButtonJKClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.laki2:
                if (checked)
                    gender = "L";
                    //Toast.makeText(RegisterActivity.this, gender, Toast.LENGTH_SHORT).show();
                    break;
            case R.id.perempuan:
                if (checked)
                    gender = "P";
                    //Toast.makeText(RegisterActivity.this, gender, Toast.LENGTH_SHORT).show();
                    break;
        }
    }

    public void onRadioButtonStatClicked(View view) {
        // Is the button now checked?
        boolean checkedStat = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.alumni:
                if (checkedStat)
                    userType = "Alumni";
                    //Toast.makeText(RegisterActivity.this, userType, Toast.LENGTH_SHORT).show();
                break;
            case R.id.mahasiswa:
                if (checkedStat)
                    userType = "Mahasiswa";
                    //Toast.makeText(RegisterActivity.this, userType, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void loadDataProdi(){

        spinner = (Spinner) findViewById(R.id.prodi);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        //ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<StudyProgram>> call = apiService.getProdi();
        call.enqueue(new Callback<List<StudyProgram>>() {
            @Override
            public void onResponse(Call<List<StudyProgram>> call, Response<List<StudyProgram>> response) {
                if (response.isSuccessful()) {
                    List<StudyProgram> allprodi = response.body();
//                    List<String> listSpinner = new ArrayList<String>();
//                    for (int i = 0; i < allprodi.size(); i++){
//                        //nama_prodi.add(new StudyProgram(allprodi.get(i).getName()));
//                        listSpinner.add(allprodi.get(i).getName());
//                    }

                    //ArrayAdapter<StudyProgram> aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,listSpinner);
                    allprodi.add(0, new StudyProgram("0","Pilih Program Studi"));
                    adapterProdik = new StudyProgramSpinner(RegisterActivity.this,android.R.layout.simple_spinner_dropdown_item, R.id.prodi_sp,allprodi);
                    spinner.setAdapter(adapterProdik);

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            StudyProgram studyProgram = (StudyProgram) spinner.getSelectedItem();
                            studyProgramId = studyProgram.getFacultyId();
                            //Toast.makeText(RegisterActivity.this, studyProgram.getFacultyId(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(RegisterActivity.this, studyProgram.getName(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<List<StudyProgram>> call, Throwable t) {

            }
        });
    }

    public boolean validate() {
        boolean valid = false;
        View focusView = null;

        int cekError = 0;

        etEmail.setError(null);
        etPass.setError(null);
        etPassKonf.setError(null);
        etAngkatan.setError(null);
        etNamaLengkap.setError(null);
        mDisplayDate.setError(null);

        String uemail = etEmail.getText().toString();
        String upassword = etPass.getText().toString();
        String upasskonf = etPassKonf.getText().toString();
        String uangkatan = etAngkatan.getText().toString();
        String unama = etNamaLengkap.getText().toString();
        String udate = mDisplayDate.getText().toString();

        if(cekError==0) {
            if (unama.isEmpty()) {
                etNamaLengkap.setError("Nama tidak boleh kosong");
                focusView = etNamaLengkap;
                valid = true;
            } else {
                etNamaLengkap.setError(null);
                cekError=1;
            }
        }
        if(cekError==1) {
            if (udate.isEmpty()) {
                mDisplayDate.setError("Tanggal lahir tidak boleh kosong");
                focusView = mDisplayDate;
                valid = true;
            } else {
                mDisplayDate.setError(null);
                cekError=2;
            }
        }
        if(cekError==2) {
            if (uemail.isEmpty()) {
                etEmail.setError("Email tidak boleh kosong");
                focusView = etEmail;
                valid = true;
            } else if (!isEmailValid(uemail)) {
                etEmail.setError("Email tidak valid");
                focusView = etEmail;
                valid = true;
            } else {
                etEmail.setError(null);
                cekError = 3;
            }
        }
        if(cekError==3) {
            if (upassword.isEmpty()) {
                etPass.setError("Password tidak boleh kosong");
                focusView = etPass;
                valid = true;
            } else {
                etPass.setError(null);
                cekError=4;
            }
        }
        if(cekError==4) {
            if (upasskonf.isEmpty()) {
                etPassKonf.setError("Konfirmasi Password tidak boleh kosong");
                focusView = etPassKonf;
                valid = true;
            }
            else if (!upassword.equals(upasskonf)) {
//                if(upassword == upasskonf) {
//                    etPassKonf.setError(null);
//                    cekError=5;
//                }
//                else if(upassword != upasskonf) {
                    etPassKonf.setError("Password harus sama dengan yang atas");
                    focusView = etPassKonf;
                    valid = true;
//                }
            }
            else {
                etPassKonf.setError(null);
                cekError=5;
            }
        }
        if(cekError==5) {
            if (uangkatan.isEmpty()) {
                etAngkatan.setError("Angkatan tidak boleh kosong");
                focusView = etAngkatan;
                valid = true;
            } else {
                etAngkatan.setError(null);
                cekError=6;
            }
        }
        if (valid) {
            focusView.requestFocus();
        }
        return valid;
    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }
}
