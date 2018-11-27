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
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateSharingFragment extends Fragment {

    private Spinner spinner;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_sharing,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Create Knowledge Sharing");
        sessionManager = new SessionManager(getActivity());
        uploadFile = (LinearLayout) getView().findViewById(R.id.sharingET_upload);
        urlnya = (TextView) getView().findViewById(R.id.sharingET_url);
        judul = (EditText) getView().findViewById(R.id.sharingET_judulfile);
        deskripsi = (EditText) getView().findViewById(R.id.sharingET_deskripsi);
        btn = (Button) getView().findViewById(R.id.submit_sharing);

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
                pd = new ProgressDialog(getActivity());
                pd.setMessage("Create Knowledge Sharing...");
                pd.setCancelable(false);
                pd.show();
                postSharing();
            }
        });

    }

    private void getDocument(){
//        pd = new ProgressDialog(getActivity());
//        pd.setMessage("Buka Gallery...");
//        pd.setCancelable(false);
//        pd.show();
        //EasyImage.openDocuments(this, 0);
        //EasyImage.openChooserWithGallery(this, "Pick source", 0);

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

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new EasyImage.Callbacks() {
//            @Override
//            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
//                Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
//                //pd.dismiss();
//            }
//
//            @Override
//            public void onImagePicked(File imageFiles, EasyImage.ImageSource source, int type) {
//
//                pathDir = imageFiles.getAbsolutePath();
//                //onPhotosReturned(poto);
//                //Log.e("pathnya: ",pathDir);
//                Toast.makeText(getActivity(), "picked", Toast.LENGTH_SHORT).show();
//                urlnya.setText(pathDir);
//                //pd.dismiss();
//                //Toast.makeText(getActivity(), pathImage, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCanceled(EasyImage.ImageSource source, int type) {
//                Toast.makeText(getActivity(), "canceled", Toast.LENGTH_SHORT).show();
//                //pd.dismiss();
//            }
//        });
//    }

    private void loadKategori(){

        spinner = (Spinner) getView().findViewById(R.id.sharingET_kategori);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<KnowledgeSharingCategory>> call = apiService.getCategory("JWT "+ sessionManager.getKeyToken());
        call.enqueue(new Callback<List<KnowledgeSharingCategory>>() {
            @Override
            public void onResponse(Call<List<KnowledgeSharingCategory>> call, Response<List<KnowledgeSharingCategory>> response) {
                if (response.isSuccessful()) {
                    List<KnowledgeSharingCategory> allKategori = response.body();

                    allKategori.add(0, new KnowledgeSharingCategory("0","Pilih Kategori"));
                    adapterCategory = new CategorySpinner(getActivity(),android.R.layout.simple_spinner_dropdown_item, R.id.category_sp,allKategori);

                    spinner.setAdapter(adapterCategory);

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            KnowledgeSharingCategory kategori = (KnowledgeSharingCategory) spinner.getSelectedItem();
                            categoryId = kategori.getId();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

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

//        File filenya = new File(pathDir);
//        try {
//            compDoc = new Compressor(getActivity()).compressToFile(filenya);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
}
