package com.example.lathifrdp.demoapp.fragment.post.sharing;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.adapter.CategorySpinner;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.fragment.sharing.SharingFragment;
import com.example.lathifrdp.demoapp.helper.BaseModel;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Bookmark;
import com.example.lathifrdp.demoapp.model.KnowledgeSharing;
import com.example.lathifrdp.demoapp.model.KnowledgeSharingCategory;
import com.example.lathifrdp.demoapp.model.Liker;
import com.example.lathifrdp.demoapp.response.PostSharingResponse;
import com.obsez.android.lib.filechooser.ChooserDialog;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditSharingFragment extends Fragment {

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
    String sharing_id;
    Bundle bundle;
    String kategorinya;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_sharing,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Berbagi Ilmu");
        sessionManager = new SessionManager(getActivity());
        uploadFile = (LinearLayout) getView().findViewById(R.id.sharingET_upload);
        urlnya = (TextView) getView().findViewById(R.id.sharingET_url);
        judul = (EditText) getView().findViewById(R.id.sharingET_judulfile);
        deskripsi = (EditText) getView().findViewById(R.id.sharingET_deskripsi);
        btn = (Button) getView().findViewById(R.id.submit_sharing);

        bundle = this.getArguments();
        if(bundle != null){
            sharing_id = bundle.getString("id_sharing");
            loadDataKnowledge();
        }
        else {
            Toast.makeText(getActivity(), "gagal bos", Toast.LENGTH_SHORT).show();
        }
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
                pd.setMessage("Mengubah...");
                pd.setCancelable(false);
                pd.show();
                putSharing();
            }
        });

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

    private void loadDataKnowledge(){

        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<KnowledgeSharing> call = apiService.getDetailKnowledge("JWT "+ sessionManager.getKeyToken(),sharing_id);
        call.enqueue(new Callback<KnowledgeSharing>() {
            @Override
            public void onResponse(Call<KnowledgeSharing> call, final Response<KnowledgeSharing> response) {
                //mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {

                    KnowledgeSharing ks = response.body();

                    judul.setText(ks.getTitle());
                    deskripsi.setText(ks.getDescription());
                    kategorinya = ks.getCategory().getId();

                    final String filenya = new BaseModel().getKnowledgeUrl()+ks.getFile();
                    urlnya.setText(filenya);
                    loadKategori();
                }
            }

            @Override
            public void onFailure(Call<KnowledgeSharing> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                //mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

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
                    int pos=0;
                    int i=0;
                    for (KnowledgeSharingCategory jb : allKategori){
                        if(jb.getId().equals(kategorinya)){
                            pos=i;
                            break;
                        }
                        i++;
                    }
                    spinner.setSelection(pos, false);

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

    private void putSharing(){

        if(kategorinya!=null){
            categoryId = kategorinya;
        }

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

        Call<PostSharingResponse> ucall = apiService.putKnowledge("JWT "+ sessionManager.getKeyToken(),sharing_id,file,title,description,category,createdBy);
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
                        newFragment = new DetailSharingPostFragment();
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
            public void onFailure(Call<PostSharingResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Mohon maaf, gagal posting", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }
}
