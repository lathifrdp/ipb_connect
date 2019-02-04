package com.example.lathifrdp.demoapp.fragment.post.group;

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
import com.example.lathifrdp.demoapp.fragment.group.GroupFragment;
import com.example.lathifrdp.demoapp.helper.BaseModel;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.GroupDiscussion;
import com.example.lathifrdp.demoapp.response.PostGroupResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditGroupFragment extends Fragment{

    EditText judul,tanya;
    Button btn;
    SessionManager sessionManager;
    ApiInterface apiService;
    ProgressDialog pd;
    Bundle bundle;
    private String group_id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_group,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Ubah Diskusi");
        sessionManager = new SessionManager(getActivity());
        judul = (EditText) getView().findViewById(R.id.judul_pertanyaan);
        tanya = (EditText) getView().findViewById(R.id.pertanyaan);
        btn = (Button) getView().findViewById(R.id.submit_pertanyaan);

        bundle = this.getArguments();
        if(bundle != null){
            // handle your code here.
            group_id = bundle.getString("id_group");
            loadDataGroup();
        }
        else {
            Toast.makeText(getActivity(), "gagal bos", Toast.LENGTH_SHORT).show();
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(getActivity());
                pd.setMessage("Mengubah Diskusi ...");
                pd.setCancelable(false);
                pd.show();
                putGroup();
            }
        });

    }

    private void loadDataGroup(){

        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<GroupDiscussion> call = apiService.getDetailGroup("JWT "+ sessionManager.getKeyToken(),group_id);
        call.enqueue(new Callback<GroupDiscussion>() {
            @Override
            public void onResponse(Call<GroupDiscussion> call, final Response<GroupDiscussion> response) {
                //mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {

                    GroupDiscussion gd = response.body();

                    judul.setText(gd.getTitle());
                    tanya.setText(gd.getDescription());
                }
            }

            @Override
            public void onFailure(Call<GroupDiscussion> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                //mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void putGroup(){
        final String title = judul.getText().toString();
        final String description = tanya.getText().toString();
        final String createdBy = sessionManager.getKeyId();

        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<PostGroupResponse> ucall = apiService.putGroup("JWT "+ sessionManager.getKeyToken(),group_id,title,description,createdBy);
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
                        newFragment = new DetailGroupPostFragment();
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
            public void onFailure(Call<PostGroupResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Mohon maaf sedang terjadi gangguan", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }
}
