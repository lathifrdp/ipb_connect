package com.example.lathifrdp.demoapp.fragment.memories;

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
import com.example.lathifrdp.demoapp.adapter.CommentList;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.BaseModel;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Comment;
import com.example.lathifrdp.demoapp.model.Liker;
import com.example.lathifrdp.demoapp.model.Memory;
import com.example.lathifrdp.demoapp.response.GetCommentResponse;
import com.example.lathifrdp.demoapp.response.PostCommentResponse;
import com.example.lathifrdp.demoapp.response.PostLikeResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMemoriesFragment extends Fragment{

    SessionManager sessionManager;
    TextView caption,comment,judul_komen,namanya, total_like, nama_capt;
    EditText tulis;
    ImageView foto, kirim, suka;
    String memo, msg;
    Bundle bundle;
    ApiInterface apiService;
    public List<Comment> commentsList;
    private RecyclerView recyclerViewCom;
    CommentList commentAdapter;
    CircleImageView pp;
    public boolean stateLike = false;
    String jumlahlike;

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
        recyclerViewCom.setHasFixedSize(true);

        bundle = this.getArguments();
        caption = (TextView) getView().findViewById(R.id.caption_memories);
        foto = (ImageView) getView().findViewById(R.id.foto_memories);
        namanya = (TextView) getView().findViewById(R.id.nama_posting_memory);
        tulis = (EditText) getView().findViewById(R.id.tulis_komentar);
        kirim = (ImageView) getView().findViewById(R.id.kirim_komentar);
        judul_komen = (TextView) getView().findViewById(R.id.judul_komen);
        pp = (CircleImageView) getView().findViewById(R.id.foto_posting_memory);
        suka = (ImageView) getView().findViewById(R.id.like_memories);
        total_like = (TextView) getView().findViewById(R.id.totalLike_memories);
        nama_capt = (TextView) getView().findViewById(R.id.nama_caption_memories);

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
        suka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stateLike==false) {
                    postLike();
                }
                else if(stateLike==true){
                    postUnlike();
                }
            }
        });
    }

    private void loadLike(){
        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<Memory> call = apiService.getDetailMemory("JWT "+ sessionManager.getKeyToken(),memo);
        call.enqueue(new Callback<Memory>() {
            @Override
            public void onResponse(Call<Memory> call, final Response<Memory> response) {
                if (response.isSuccessful()) {

                    Memory memory = response.body();
                    jumlahlike = memory.getTotalLike()+" likes";
                    total_like.setText(jumlahlike);
                }
            }

            @Override
            public void onFailure(Call<Memory> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                //mSwipeRefreshLayout.setRefreshing(false);
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
                    List<Liker> like = memory.getLiker();

                    int i = 0;
                    //likeSize = Integer.parseInt(ks.getTotalLike());
                    for(i=0;i<like.size();i++) {
                        if(like.get(i).getCreatedBy().getId().equals(sessionManager.getKeyId())){
                            suka.setColorFilter(getContext().getResources().getColor(R.color.merah));
                            stateLike=true;
                        }
                        else{
                            stateLike=false;
                        }
                    }
//
//                    commentAdapter = new CommentList(commentsList);
//                    recyclerViewCom.setAdapter(commentAdapter);
//                    recyclerViewCom.smoothScrollToPosition(0);

                    jumlahlike = memory.getTotalLike()+" likes";
                    nama_capt.setText(memory.getUser().getFullName());
                    caption.setText(memory.getCaption());
                    namanya.setText(memory.getUser().getFullName());
                    total_like.setText(jumlahlike);
//                    for(int i=0;i<commentsList.size();i++) {
//                        comment.setText("Comment: " + commentsList.get(i).getCreated());
//                    }
                    //comment.setText("Comment: " + like.size());
                    String url = new BaseModel().getMemoryUrl()+memory.getPhoto();
                    Picasso.get()
                            .load(url)
                            .placeholder(R.drawable.placegam)
                            .error(R.drawable.logoipb)
                            .into(foto);

                    String urlPP = new BaseModel().getProfileUrl()+memory.getUser().getUserProfile().getPhoto();
                    Picasso.get()
                            .load(urlPP)
                            .placeholder(R.drawable.placegam)
                            .error(R.drawable.logoipb)
                            .into(pp);
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

        Call<GetCommentResponse> call = apiService.getComment("JWT "+ sessionManager.getKeyToken(),memo);
        call.enqueue(new Callback<GetCommentResponse>() {
            @Override
            public void onResponse(Call<GetCommentResponse> call, final Response<GetCommentResponse> response) {
                //mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {

                    GetCommentResponse cgr = response.body();
                    commentsList = cgr.getComments();
                    judul_komen.setText("Comments ("+commentsList.size()+") :");
                    commentAdapter = new CommentList(commentsList);
                    recyclerViewCom.setAdapter(commentAdapter);
                    //recyclerViewCom.smoothScrollToPosition(0);
                }
            }

            @Override
            public void onFailure(Call<GetCommentResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                //mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void postComment() {
        apiService = ApiClient.getClient().create(ApiInterface.class);

        final String value = tulis.getText().toString();
        final String createdBy = sessionManager.getKeyId();

        Call<PostCommentResponse> ucall = apiService.postComment("JWT "+ sessionManager.getKeyToken(),value,createdBy,memo);
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

    private void postLike() {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        final String createdBy = sessionManager.getKeyId();

        Call<PostLikeResponse> ucall = apiService.postLikeMemories("JWT "+ sessionManager.getKeyToken(),createdBy,memo);
        ucall.enqueue(new Callback<PostLikeResponse>() {
            @Override
            public void onResponse(Call<PostLikeResponse> call, Response<PostLikeResponse> response) {

                if (response.isSuccessful()) {
                    PostLikeResponse lr = response.body();

                    if(lr.isSuccess()==false ){
                        Toast.makeText(getActivity(), lr.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getActivity(), lr.getMessage(), Toast.LENGTH_SHORT).show();
                        suka.setColorFilter(getContext().getResources().getColor(R.color.merah));
                        stateLike=true;
                        loadLike();
                    }
                }
            }

            @Override
            public void onFailure(Call<PostLikeResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Mohon maaf sedang terjadi gangguan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postUnlike() {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        final String createdBy = sessionManager.getKeyId();

        Call<PostLikeResponse> ucall = apiService.postUnlikeMemories("JWT "+ sessionManager.getKeyToken(),createdBy,memo);
        ucall.enqueue(new Callback<PostLikeResponse>() {
            @Override
            public void onResponse(Call<PostLikeResponse> call, Response<PostLikeResponse> response) {

                if (response.isSuccessful()) {
                    PostLikeResponse lr = response.body();

                    if(lr.isSuccess()==false ){
                        Toast.makeText(getActivity(), lr.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getActivity(), lr.getMessage(), Toast.LENGTH_SHORT).show();
                        suka.setColorFilter(getContext().getResources().getColor(R.color.abu));
                        stateLike=false;
                        loadLike();
                    }
                }
            }

            @Override
            public void onFailure(Call<PostLikeResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Mohon maaf sedang terjadi gangguan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
