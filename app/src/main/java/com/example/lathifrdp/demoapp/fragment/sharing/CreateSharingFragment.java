package com.example.lathifrdp.demoapp.fragment.sharing;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.adapter.CategorySpinner;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.KnowledgeSharingCategory;
import com.example.lathifrdp.demoapp.response.PostSharingResponse;
import com.obsez.android.lib.filechooser.ChooserDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

public class CreateSharingFragment extends Fragment {

    private CategorySpinner adapterCategory;
    public String categoryId;
    ApiInterface apiService;
    SessionManager sessionManager;
    String pathDir, msg, pathnya;
    LinearLayout uploadFile;
    TextView urlnya;
    EditText judul, deskripsi;
    File compDoc;
    Button btn;
    ProgressDialog pd;
    TextView kategorinya, pilih_kategori;
    SpinnerDialog spinnerDialog;
    ArrayList<String> items=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_sharing,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Bagikan Ilmu");
        sessionManager = new SessionManager(getActivity());
        uploadFile = (LinearLayout) getView().findViewById(R.id.sharingET_upload);
        urlnya = (TextView) getView().findViewById(R.id.sharingET_url);
        judul = (EditText) getView().findViewById(R.id.sharingET_judulfile);
        deskripsi = (EditText) getView().findViewById(R.id.sharingET_deskripsi);
        btn = (Button) getView().findViewById(R.id.submit_sharing);
        kategorinya = (TextView) getView().findViewById(R.id.kategorinya);
        pilih_kategori = (TextView) getView().findViewById(R.id.kategoriCombo);

        loadKategori();
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDocument();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cek();
            }
        });

        pilih_kategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialog.showSpinerDialog();
            }
        });

    }
    public void cek() {
        if (validate() == true) {
            return;
        }
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Membagikan Ilmu...");
        pd.setCancelable(false);
        pd.show();
        postSharing();
    }

    private void getDocument(){
        new ChooserDialog().with(getActivity())
                .withFilter(false, false, "ppt", "pptx")
                .withStartFile(pathnya)
                .withResources(R.string.title_choose_dict_file, R.string.title_choose, R.string.dialog_cancel)
                .withChosenListener(new ChooserDialog.Result() {
                    @Override
                    public void onChoosePath(String path, File pathFile) {
                        Toast.makeText(getActivity(), "FILE: " + path, Toast.LENGTH_SHORT).show();
                        compDoc = pathFile;
                        urlnya.setText(path);
                    }
                })
                .build()
                .show();
    }

    private void loadKategori(){
        apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<KnowledgeSharingCategory>> call = apiService.getCategory("JWT "+ sessionManager.getKeyToken());
        call.enqueue(new Callback<List<KnowledgeSharingCategory>>() {
            @Override
            public void onResponse(Call<List<KnowledgeSharingCategory>> call, Response<List<KnowledgeSharingCategory>> response) {
                if (response.isSuccessful()) {
                    final List<KnowledgeSharingCategory> allKategori = response.body();
                    categoryId = "";
                    kategorinya.setText("Belum memilih kategori");
                    items.clear();
                    for(int x=0;x<allKategori.size();x++){
                        items.add(allKategori.get(x).getName());
                    }

                    spinnerDialog=new SpinnerDialog(getActivity(),items,"Pilih Kategori",R.style.DialogAnimations_SmileWindow,"Tutup");
                    spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                        @Override
                        public void onClick(String item, int position) {

                            for(int x=0;x<allKategori.size();x++){
                                if(item.equals(allKategori.get(x).getName())) {
                                    categoryId = allKategori.get(x).getId();
                                }
                            }
                            kategorinya.setText(item);
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<List<KnowledgeSharingCategory>> call, Throwable t) {

            }
        });
    }

    private void postSharing(){

        final String title2 = judul.getText().toString();
        final String description2 = deskripsi.getText().toString();
        final String category2 = categoryId;
        final String createdBy2 = sessionManager.getKeyId();

        RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), compDoc);
        MultipartBody.Part file = MultipartBody.Part.createFormData("file",compDoc.getName(), reqFile);
        RequestBody title = RequestBody.create(MediaType.parse("text/plain"), title2);
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), description2);
        RequestBody category = RequestBody.create(MediaType.parse("text/plain"), category2);
        RequestBody createdBy = RequestBody.create(MediaType.parse("text/plain"), createdBy2);

        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<PostSharingResponse> ucall = apiService.postKnowledge("JWT "+ sessionManager.getKeyToken(),file,title,description,category,createdBy);
        ucall.enqueue(new Callback<PostSharingResponse>() {
            @Override
            public void onResponse(Call<PostSharingResponse> call, Response<PostSharingResponse> response) {

                if (response.isSuccessful()) {

                    PostSharingResponse sr = response.body();
                    msg = sr.getMessage();

                    if(sr.isSuccess()==false ){
                        Snackbar.make(getView(), msg, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else {
                        //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        Snackbar.make(getView(), msg, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        Fragment newFragment = null;
                        newFragment = new SharingFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.screen_area, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                    pd.dismiss();
                }
            }

            @Override
            public void onFailure(Call<PostSharingResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Mohon maaf, gagal posting", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }
    public boolean validate() {
        boolean valid = false;
        View focusView = null;

        int cekError = 0;

        judul.setError(null);
        deskripsi.setError(null);

        String title2 = judul.getText().toString();
        String description2 = deskripsi.getText().toString();
        String urlnya2 = urlnya.getText().toString();

        if(cekError==0) {
            if (title2.isEmpty()) {
                judul.setError("Judul tidak boleh kosong");
                focusView = judul;
                valid = true;
            } else {
                judul.setError(null);
                cekError=1;
            }
        }
        if(cekError==1) {
            if (description2.isEmpty()) {
                deskripsi.setError("Deskripsi tidak boleh kosong");
                focusView = deskripsi;
                valid = true;
            } else {
                deskripsi.setError(null);
                cekError=2;
            }
        }
        if(cekError==2) {
            if (urlnya2.isEmpty()) {
                Toast.makeText(getActivity(), "Dokumen tidak boleh kosong", Toast.LENGTH_SHORT).show();
                focusView = urlnya;
                valid = true;
            } else {
                cekError=3;
            }
        }
        if(cekError==3) {
            if (categoryId.isEmpty()) {
                Toast.makeText(getActivity(), "Kategori tidak boleh kosong", Toast.LENGTH_SHORT).show();
                focusView = pilih_kategori;
                valid = true;
            } else {
                cekError=4;
            }
        }
        if (valid) {
            focusView.requestFocus();
        }
        return valid;
    }
}
