package com.example.lathifrdp.demoapp.fragment.memories;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.adapter.CommentList;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.fragment.event.DetailEventFragment;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Comment;
import com.example.lathifrdp.demoapp.model.Liker;
import com.example.lathifrdp.demoapp.model.Memory;
import com.example.lathifrdp.demoapp.response.CommentGetResponse;
import com.example.lathifrdp.demoapp.response.CommentResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMemoriesFragment extends Fragment{

    SessionManager sessionManager;
    TextView caption,comment,judul_komen;
    EditText tulis;
    ImageView foto, kirim;
    String memo, msg;
    Bundle bundle;
    ApiInterface apiService;
    public List<Comment> commentsList;
    private RecyclerView recyclerViewCom;
    CommentList commentAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_memories,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Detail Memories");
        sessionManager = new SessionManager(getActivity());

        recyclerViewCom= (RecyclerView) getView().findViewById(R.id.recy_comment);
        recyclerViewCom.setLayoutManager(new LinearLayoutManager(getActivity()));

        bundle = this.getArguments();
        caption = (TextView) getView().findViewById(R.id.caption_memories);
        foto = (ImageView) getView().findViewById(R.id.foto_memories);
        comment = (TextView) getView().findViewById(R.id.comment_memories);
        tulis = (EditText) getView().findViewById(R.id.tulis_komentar);
        kirim = (ImageView) getView().findViewById(R.id.kirim_komentar);
        judul_komen = (TextView) getView().findViewById(R.id.judul_komen);

        if(bundle != null){
            // handle your code here.
            memo = bundle.getString("id_memory");
        }
        else {
            Toast.makeText(getActivity(), "gagal bos", Toast.LENGTH_SHORT).show();
        }
        loadDataMemories();
        loadComment();
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postComment();
                //commentAdapter.notifyDataSetChanged();
                //loadComment();
                //tulis.getText().clear();//clears the text
            }
        });
    }

    private void loadDataMemories(){

        //spinner = (Spinner) getView().findViewById(R.id.prodiFragment);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        //ApiService apiService = ApiClient.getClient().create(ApiService.class);

        //final String fullName = full;
        //final String isVerified = x3;

        Call<Memory> call = apiService.getDetailMemory("JWT "+ sessionManager.getKeyToken(),memo);
        call.enqueue(new Callback<Memory>() {
            @Override
            public void onResponse(Call<Memory> call, final Response<Memory> response) {
                //mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {

                    Memory memory = response.body();
                    //commentsList = memory.getComment();
//                    List<Liker> like = memory.getLiker();
//
//                    commentAdapter = new CommentList(commentsList);
//                    recyclerViewCom.setAdapter(commentAdapter);
//                    recyclerViewCom.smoothScrollToPosition(0);

                    caption.setText("Caption: "+memory.getCaption());
//                    for(int i=0;i<commentsList.size();i++) {
//                        comment.setText("Comment: " + commentsList.get(i).getCreated());
//                    }
                    //comment.setText("Comment: " + like.size());
                    String url = "http://api.ipbconnect.cs.ipb.ac.id/uploads/memory/"+memory.getPhoto();
                    Picasso.get()
                            .load(url)
                            .placeholder(R.drawable.alumni2)
                            .error(R.drawable.logoipb)
                            .into(foto);
                }
            }

            @Override
            public void onFailure(Call<Memory> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                //mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void loadComment(){

        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<CommentGetResponse> call = apiService.getComment("JWT "+ sessionManager.getKeyToken(),memo);
        call.enqueue(new Callback<CommentGetResponse>() {
            @Override
            public void onResponse(Call<CommentGetResponse> call, final Response<CommentGetResponse> response) {
                //mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {

                    CommentGetResponse cgr = response.body();
                    commentsList = cgr.getComments();
                    judul_komen.setText("Comments ("+commentsList.size()+") :");
                    commentAdapter = new CommentList(commentsList);
                    recyclerViewCom.setAdapter(commentAdapter);
                    recyclerViewCom.smoothScrollToPosition(0);
                }
            }

            @Override
            public void onFailure(Call<CommentGetResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                //mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void postComment() {
        apiService = ApiClient.getClient().create(ApiInterface.class);

        final String value = tulis.getText().toString();
        final String createdBy = sessionManager.getKeyId();


        Call<CommentResponse> ucall = apiService.commentRequest("JWT "+ sessionManager.getKeyToken(),value,createdBy,memo);
        ucall.enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {

                if (response.isSuccessful()) {

                    CommentResponse cr = response.body();

                    if(cr.isSuccess()==false ){
                        Toast.makeText(getActivity(), cr.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        msg = cr.getMessage();

                        Toast.makeText(getActivity(), cr.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    loadComment();
                }
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {

                Toast.makeText(getActivity(), "Mohon maaf sedang terjadi gangguan", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
