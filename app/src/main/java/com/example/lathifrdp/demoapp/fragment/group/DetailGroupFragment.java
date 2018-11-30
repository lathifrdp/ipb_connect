package com.example.lathifrdp.demoapp.fragment.group;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.adapter.DiscussionList;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Comment;
import com.example.lathifrdp.demoapp.model.GroupDiscussion;
import com.example.lathifrdp.demoapp.response.GetCommentResponse;
import com.example.lathifrdp.demoapp.response.PostCommentResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailGroupFragment extends Fragment {

    SessionManager sessionManager;
    Bundle bundle;
    ApiInterface apiService;
    public List<Comment> commentsList;
    private RecyclerView rvDiscuss;
    DiscussionList discussionAdapter;
    public String group_id;
    TextView nama_posting, judul, deskripsi, judul_jawaban;
    EditText tulis;
    ImageView kirim;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_group,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Diskusi");
        sessionManager = new SessionManager(getActivity());

        rvDiscuss= (RecyclerView) getView().findViewById(R.id.jawaban_discussion);
        rvDiscuss.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDiscuss.setHasFixedSize(true);

        nama_posting = (TextView) getView().findViewById(R.id.nama_posting_group);
        judul = (TextView) getView().findViewById(R.id.judul_group);
        deskripsi = (TextView) getView().findViewById(R.id.deskripsi_group);
        judul_jawaban = (TextView) getView().findViewById(R.id.jawaban_group);
        tulis = (EditText) getView().findViewById(R.id.jawaban_anda);
        kirim = (ImageView) getView().findViewById(R.id.kirim_jawaban);

        bundle = this.getArguments();
        if(bundle != null){
            // handle your code here.
            group_id = bundle.getString("id_group");
        }
        else {
            Toast.makeText(getActivity(), "gagal bos", Toast.LENGTH_SHORT).show();
        }

        loadDataGroup();
        loadDiscuss();

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postDiscussion();
                //commentAdapter.notifyDataSetChanged();
                //loadComment();
                //tulis.getText().clear();//clears the text
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

                    nama_posting.setText(gd.getCreatedBy().getFullName());
                    judul.setText(gd.getTitle());
                    deskripsi.setText(gd.getDescription());
                }
            }

            @Override
            public void onFailure(Call<GroupDiscussion> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                //mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void loadDiscuss(){

        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<GetCommentResponse> call = apiService.getDiscussion("JWT "+ sessionManager.getKeyToken(),group_id);
        call.enqueue(new Callback<GetCommentResponse>() {
            @Override
            public void onResponse(Call<GetCommentResponse> call, final Response<GetCommentResponse> response) {
                //mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {

                    GetCommentResponse cgr = response.body();
                    commentsList = cgr.getComments();
                    judul_jawaban.setText(commentsList.size()+" Jawaban");
                    discussionAdapter = new DiscussionList(commentsList);
                    rvDiscuss.setAdapter(discussionAdapter);
                    rvDiscuss.smoothScrollToPosition(0);
                }
            }

            @Override
            public void onFailure(Call<GetCommentResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                //mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }
    private void postDiscussion() {
        apiService = ApiClient.getClient().create(ApiInterface.class);

        final String value = tulis.getText().toString();
        final String createdBy = sessionManager.getKeyId();


        Call<PostCommentResponse> ucall = apiService.postDiscussion("JWT "+ sessionManager.getKeyToken(),value,createdBy,group_id);
        ucall.enqueue(new Callback<PostCommentResponse>() {
            @Override
            public void onResponse(Call<PostCommentResponse> call, Response<PostCommentResponse> response) {

                if (response.isSuccessful()) {

                    PostCommentResponse cr = response.body();

                    if(cr.isSuccess()==false ){
                        Toast.makeText(getActivity(), cr.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getActivity(), cr.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    loadDiscuss();
                    tulis.getText().clear();
                }
            }

            @Override
            public void onFailure(Call<PostCommentResponse> call, Throwable t) {

                Toast.makeText(getActivity(), "Mohon maaf sedang terjadi gangguan", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
