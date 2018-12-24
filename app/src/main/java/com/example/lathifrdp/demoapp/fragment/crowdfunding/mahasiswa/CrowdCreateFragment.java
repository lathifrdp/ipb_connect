package com.example.lathifrdp.demoapp.fragment.crowdfunding.mahasiswa;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.response.PostCrowdRequestResponse;
import com.example.lathifrdp.demoapp.response.PostCrowdfundingResponse;
import com.obsez.android.lib.filechooser.ChooserDialog;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrowdCreateFragment extends Fragment {

    ApiInterface apiService;
    SessionManager sessionManager;
    String pathDir, msg, pathnya;
    LinearLayout uploadFile;
    TextView urlnya;
    EditText judul, deskripsi, kontak, lokasi, proyek, biaya;
    File compDoc;
    Button btn;
    ProgressDialog pd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_crowdfunding,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Proposal");
        sessionManager = new SessionManager(getActivity());
        uploadFile = (LinearLayout) getView().findViewById(R.id.crowd_upload);
        urlnya = (TextView) getView().findViewById(R.id.crowd_url);
        judul = (EditText) getView().findViewById(R.id.crowd_judul_et);
        deskripsi = (EditText) getView().findViewById(R.id.crowd_deskripsi_et);
        kontak = (EditText) getView().findViewById(R.id.crowd_kontak_et);
        lokasi = (EditText) getView().findViewById(R.id.crowd_lokasi_et);
        proyek = (EditText) getView().findViewById(R.id.crowd_proyek_et);
        biaya = (EditText) getView().findViewById(R.id.crowd_biaya_et);
        btn = (Button) getView().findViewById(R.id.submit_crowd);

        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDocument();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(getActivity());
                pd.setMessage("Membagikan Proposal...");
                pd.setCancelable(false);
                pd.show();
                postCrowd();
            }
        });
    }
    private void getDocument(){
        new ChooserDialog().with(getActivity())
                .withFilter(false, false, "pdf", "doc","docx")
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
    private void postCrowd(){

        final String title2 = judul.getText().toString();
        final String description2 = deskripsi.getText().toString();
        final String contactPerson2 = kontak.getText().toString();
        final String location2 = lokasi.getText().toString();
        final String projectType2 = proyek.getText().toString();
        final String cost2 = biaya.getText().toString();
        final String createdBy2 = sessionManager.getKeyId();

        RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), compDoc);
        MultipartBody.Part file = MultipartBody.Part.createFormData("file",compDoc.getName(), reqFile);
        RequestBody title = RequestBody.create(MediaType.parse("text/plain"), title2);
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), description2);
        RequestBody contactPerson = RequestBody.create(MediaType.parse("text/plain"), contactPerson2);
        RequestBody location = RequestBody.create(MediaType.parse("text/plain"), location2);
        RequestBody projectType = RequestBody.create(MediaType.parse("text/plain"), projectType2);
        RequestBody cost = RequestBody.create(MediaType.parse("text/plain"), cost2);
        RequestBody createdBy = RequestBody.create(MediaType.parse("text/plain"), createdBy2);

        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<PostCrowdfundingResponse> ucall = apiService.postCrowd("JWT "+ sessionManager.getKeyToken(),file,title,description,contactPerson,location,projectType,cost,createdBy);
        ucall.enqueue(new Callback<PostCrowdfundingResponse>() {
            @Override
            public void onResponse(Call<PostCrowdfundingResponse> call, Response<PostCrowdfundingResponse> response) {

                if (response.isSuccessful()) {

                    PostCrowdfundingResponse cr = response.body();
                    msg = cr.getMessage();

                    if(cr.isSuccess()==false ){
                        Snackbar.make(getView(), msg, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else {
                        //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        Snackbar.make(getView(), msg, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        Fragment newFragment = null;
                        newFragment = new CrowdMahasiswaFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.screen_area, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                    pd.dismiss();
                }
            }

            @Override
            public void onFailure(Call<PostCrowdfundingResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Mohon maaf, gagal posting", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }
}
