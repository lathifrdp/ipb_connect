package com.example.lathifrdp.demoapp.fragment.group;

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
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.response.PostGroupResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateGroupFragment extends Fragment{

    EditText judul,tanya;
    Button btn;
    SessionManager sessionManager;
    ApiInterface apiService;
    ProgressDialog pd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_group,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Tanya Jawab");
        sessionManager = new SessionManager(getActivity());
        judul = (EditText) getView().findViewById(R.id.judul_pertanyaan);
        tanya = (EditText) getView().findViewById(R.id.pertanyaan);
        btn = (Button) getView().findViewById(R.id.submit_pertanyaan);

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
        pd.setMessage("Membuat Diskusi ...");
        pd.setCancelable(false);
        pd.show();
        postGroup();
    }
    private void postGroup(){
        final String title = judul.getText().toString();
        final String description = tanya.getText().toString();
        final String createdBy = sessionManager.getKeyId();

        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<PostGroupResponse> ucall = apiService.postGroup("JWT "+ sessionManager.getKeyToken(),title,description,createdBy);
        ucall.enqueue(new Callback<PostGroupResponse>() {
            @Override
            public void onResponse(Call<PostGroupResponse> call, Response<PostGroupResponse> response) {

                if (response.isSuccessful()) {

                    PostGroupResponse gr = response.body();

                    if(gr.isSuccess()==false ){
                        Snackbar.make(getView(), gr.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else {
                        //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        Snackbar.make(getView(), gr.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        Fragment newFragment = null;
                        newFragment = new GroupFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.screen_area, newFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                    pd.dismiss();
                }
            }

            @Override
            public void onFailure(Call<PostGroupResponse> call, Throwable t) {
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
        tanya.setError(null);

        String title = judul.getText().toString();
        String description = tanya.getText().toString();

        if(cekError==0) {
            if (title.isEmpty()) {
                judul.setError("Judul tidak boleh kosong");
                focusView = judul;
                valid = true;
            } else {
                judul.setError(null);
                cekError=1;
            }
        }
        if(cekError==1) {
            if (description.isEmpty()) {
                tanya.setError("Pertanyaan tidak boleh kosong");
                focusView = tanya;
                valid = true;
            } else {
                tanya.setError(null);
                cekError=2;
            }
        }
        if (valid) {
            focusView.requestFocus();
        }
        return valid;
    }
}
