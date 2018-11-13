package com.example.lathifrdp.demoapp.fragment.sharing;

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
import com.example.lathifrdp.demoapp.adapter.CommentKnowledgeList;
import com.example.lathifrdp.demoapp.adapter.CommentList;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Comment;
import com.example.lathifrdp.demoapp.model.KnowledgeSharing;
import com.example.lathifrdp.demoapp.response.GetCommentResponse;
import com.example.lathifrdp.demoapp.response.PostCommentResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailSharingFragment extends Fragment{

    SessionManager sessionManager;
    TextView deskripsi,judul_sharing,judul_komen,namanya;
    EditText tulis;
    ImageView foto, kirim;
    String sharing_id, msg;
    Bundle bundle;
    ApiInterface apiService;
    public List<Comment> commentsList;
    private RecyclerView recyclerViewCom;
    CommentKnowledgeList commentAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_sharing,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Detail Knowledge");
        sessionManager = new SessionManager(getActivity());

        recyclerViewCom= (RecyclerView) getView().findViewById(R.id.sharing_detail_comment);
        recyclerViewCom.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewCom.setHasFixedSize(true);

        bundle = this.getArguments();
        deskripsi = (TextView) getView().findViewById(R.id.sharing_detail_deskripsi);
        foto = (ImageView) getView().findViewById(R.id.sharing_detail_foto);
        namanya = (TextView) getView().findViewById(R.id.sharing_nama_posting);
        tulis = (EditText) getView().findViewById(R.id.sharing_tulis_komentar);
        kirim = (ImageView) getView().findViewById(R.id.sharing_kirim_komentar);
        judul_komen = (TextView) getView().findViewById(R.id.sharing_judul_komen);
        judul_sharing = (TextView) getView().findViewById(R.id.sharing_judul_detail);

        if(bundle != null){
            // handle your code here.
            sharing_id = bundle.getString("id_sharing");
        }
        else {
            Toast.makeText(getActivity(), "gagal bos", Toast.LENGTH_SHORT).show();
        }
        loadDataKnowledge();
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
    private void loadDataKnowledge(){

        //spinner = (Spinner) getView().findViewById(R.id.prodiFragment);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        //ApiService apiService = ApiClient.getClient().create(ApiService.class);

        //final String fullName = full;
        //final String isVerified = x3;

        Call<KnowledgeSharing> call = apiService.getDetailKnowledge("JWT "+ sessionManager.getKeyToken(),sharing_id);
        call.enqueue(new Callback<KnowledgeSharing>() {
            @Override
            public void onResponse(Call<KnowledgeSharing> call, final Response<KnowledgeSharing> response) {
                //mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {

                    KnowledgeSharing ks = response.body();
                    //commentsList = memory.getComment();
//                    List<Liker> like = memory.getLiker();
//
//                    commentAdapter = new CommentList(commentsList);
//                    recyclerViewCom.setAdapter(commentAdapter);
//                    recyclerViewCom.smoothScrollToPosition(0);

                    judul_sharing.setText(ks.getTitle());
                    namanya.setText(ks.getUser().getFullName());
                    deskripsi.setText(ks.getDescription());
//                    for(int i=0;i<commentsList.size();i++) {
//                        comment.setText("Comment: " + commentsList.get(i).getCreated());
//                    }
                    //comment.setText("Comment: " + like.size());
                    String url = "http://api.ipbconnect.cs.ipb.ac.id/uploads/knowledgesharing/"+ks.getCover();
                    Picasso.get()
                            .load(url)
                            .placeholder(R.drawable.placegam)
                            .error(R.drawable.logoipb)
                            .into(foto);
                }
            }

            @Override
            public void onFailure(Call<KnowledgeSharing> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                //mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void loadComment(){

        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<KnowledgeSharing> call = apiService.getCommentKnowledge("JWT "+ sessionManager.getKeyToken(),sharing_id);
        call.enqueue(new Callback<KnowledgeSharing>() {
            @Override
            public void onResponse(Call<KnowledgeSharing> call, final Response<KnowledgeSharing> response) {
                //mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {

                    KnowledgeSharing ks = response.body();
                    if(ks.isSuccess()==false && ks.getMessage() == null){
                        commentsList = ks.getComment();
                        judul_komen.setText("Comments (" + commentsList.size() + ") :");
                        commentAdapter = new CommentKnowledgeList(commentsList);
                        recyclerViewCom.setAdapter(commentAdapter);
                        //Toast.makeText(getActivity(),"1 "+ks.getMessage(),Toast.LENGTH_SHORT).show();
                        //recyclerViewCom.smoothScrollToPosition(0);
                    }
                    else {
                        judul_komen.setText("Comments (0) :");
                        //Toast.makeText(getActivity(),"0 "+ks.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<KnowledgeSharing> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                //mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void postComment() {
        apiService = ApiClient.getClient().create(ApiInterface.class);

        final String value = tulis.getText().toString();
        final String createdBy = sessionManager.getKeyId();


        Call<PostCommentResponse> ucall = apiService.postCommentKnowledge("JWT "+ sessionManager.getKeyToken(),value,createdBy,sharing_id);
        ucall.enqueue(new Callback<PostCommentResponse>() {
            @Override
            public void onResponse(Call<PostCommentResponse> call, Response<PostCommentResponse> response) {

                if (response.isSuccessful()) {

                    PostCommentResponse cr = response.body();

                    if(cr.isSuccess()==false ){
                        Toast.makeText(getActivity(), cr.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        msg = cr.getMessage();

                        Toast.makeText(getActivity(), cr.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    loadComment();
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
