package com.example.lathifrdp.demoapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import id.zelory.compressor.Compressor;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.EasyImage;
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
    ImageView gambar,gallery,camera;
    ProgressDialog pd;
    public String pathImage,msg;
    public File poto, compoto;
    String iddia;
    TextView prodinyaa, pilih_prodi;
    SpinnerDialog spinnerDialog;
    ArrayList<String> items = new ArrayList<>();

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
        gambar = (ImageView) findViewById(R.id.regis_gambar);
        gallery = (ImageView) findViewById(R.id.regis_gallery);
        camera = (ImageView) findViewById(R.id.regis_camera);
        prodinyaa = (TextView) findViewById(R.id.prodinya);
        pilih_prodi = (TextView) findViewById(R.id.prodiCombo);

        onRadioButtonJKClicked(radiojkButton);
        onRadioButtonStatClicked(radiostatButton);
        getTanggalLahir();

        loadDataProdi();

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageGallery();
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageCamera();
            }
        });

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

        pilih_prodi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialog.showSpinerDialog();
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
        pd = new ProgressDialog(this);
        pd.setMessage("Melakukan registrasi...");
        pd.setCancelable(false);
        pd.show();
        daftarkuy();
    }

    private void getImageGallery(){
        pd = new ProgressDialog(this);
        pd.setMessage("Membuka Galeri...");
        pd.setCancelable(false);
        pd.show();
        EasyImage.openGallery(this, 0);
        //EasyImage.openChooserWithGallery(this, "Pick source", 0);
    }

    private void getImageCamera(){
        pd = new ProgressDialog(this);
        pd.setMessage("Membuka Kamera...");
        pd.setCancelable(false);
        pd.show();
        EasyImage.openCamera(this, 0);
        //EasyImage.openChooserWithGallery(this, "Pick source", 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new EasyImage.Callbacks() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                Toast.makeText(RegisterActivity.this, "error", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }

            @Override
            public void onImagePicked(File imageFiles, EasyImage.ImageSource source, int type) {
                try {
                    poto = new Compressor(RegisterActivity.this).compressToFile(imageFiles);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ;
                pathImage = imageFiles.getAbsolutePath();
                onPhotosReturned(poto);
                Toast.makeText(RegisterActivity.this, "picked", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                //Toast.makeText(getActivity(), pathImage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                Toast.makeText(RegisterActivity.this, "canceled", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }

    private void onPhotosReturned(File returnedPhotos) {
        Picasso.get()
                .load(returnedPhotos)
                .placeholder(R.drawable.placegam)
                .error(R.drawable.logoipb)
                .into(gambar);
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

        Call<RegisterResponse> ucall = apiService.postRegister(fullName, gender, dateOfBirth, email, password, nim, userType, batch, studyProgramId, isVerified);
        ucall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                if (response.isSuccessful()) {

                    RegisterResponse rr = response.body();

                    if(rr.isSuccess()==false ){
                        Toast.makeText(RegisterActivity.this, rr.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        iddia = rr.getUser().getId();
                        uploadFoto();
//                        Toast.makeText(RegisterActivity.this, rr.getUser().getId(), Toast.LENGTH_SHORT).show();
//                        Intent logIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                        Toast.makeText(RegisterActivity.this, rr.getMessage(), Toast.LENGTH_SHORT).show();
//                        RegisterActivity.this.startActivity(logIntent);
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Mohon maaf sedang terjadi gangguan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void uploadFoto(){
        apiService = ApiClient.getClient().create(ApiInterface.class);

        File filenya = new File(pathImage);
        try {
            compoto = new Compressor(this).compressToFile(filenya);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), compoto);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("photo", poto.getName(), reqFile);
        //RequestBody id = RequestBody.create(MediaType.parse("text/plain"), id2);
        //final String id = id2;

        Call<RegisterResponse> ucall = apiService.uploadPhoto(iddia,photo);
        ucall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                if (response.isSuccessful()) {

                    RegisterResponse mr = response.body();
                    msg = mr.getMessage();

                    if(mr.isSuccess()==false ){
                        Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                        Intent logIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                        RegisterActivity.this.startActivity(logIntent);
                    }
                    pd.dismiss();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Mohon maaf sedang terjadi gangguan", Toast.LENGTH_SHORT).show();
                pd.dismiss();
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
        apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<StudyProgram>> call = apiService.getProdi();
        call.enqueue(new Callback<List<StudyProgram>>() {
            @Override
            public void onResponse(Call<List<StudyProgram>> call, Response<List<StudyProgram>> response) {
                if (response.isSuccessful()) {
                    final List<StudyProgram> allprodi = response.body();
                    studyProgramId = "";
                    prodinyaa.setText("Belum memilih program studi");
                    items.clear();
                    for(int x=0;x<allprodi.size();x++){
                        items.add(allprodi.get(x).getName());
                    }

                    spinnerDialog=new SpinnerDialog(RegisterActivity.this,items,"Pilih Program Studi",R.style.DialogAnimations_SmileWindow,"Tutup");
                    spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                        @Override
                        public void onClick(String item, int position) {

                            for(int x=0;x<allprodi.size();x++){
                                if(item.equals(allprodi.get(x).getName())) {
                                    studyProgramId = allprodi.get(x).getFacultyId();
                                }
                            }
                            prodinyaa.setText(item);
                        }
                    });
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent c = new Intent(Intent.ACTION_MAIN);
        c.addCategory(Intent.CATEGORY_HOME);
        c.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(c);
    }
}
