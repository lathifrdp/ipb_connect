package com.example.lathifrdp.demoapp.fragment.bookmark.sharing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.adapter.SharingAdapter;
import com.example.lathifrdp.demoapp.adapter.SharingPostAdapter;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.KnowledgeSharing;
import com.example.lathifrdp.demoapp.response.BookmarkResponse;
import com.example.lathifrdp.demoapp.response.SharingResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookmarkSharingFragment extends Fragment{

    private RecyclerView recyclerView;
    private List<KnowledgeSharing> sharingArrayList;
    TextView Disconnected, kosong;
    private KnowledgeSharing mov;
    //ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;
    ApiInterface apiService;
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_sharing,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Berbagi Ilmu");

        sessionManager = new SessionManager(getActivity());
        kosong = (TextView) getActivity().findViewById(R.id.kosongnya);
        initViewsTerbaru();
        swipeContainer = (SwipeRefreshLayout) getView().findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadKnowledge();
                //Toast.makeText(getActivity(),"Terbaru List Refreshed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViewsTerbaru(){
//        pd = new ProgressDialog(getActivity());
//        pd.setMessage("Fetching Data...");
//        pd.setCancelable(false);
//        pd.show();
        recyclerView=(RecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.smoothScrollToPosition(0);
        loadKnowledge();
    }

    private void loadKnowledge() {
        Disconnected = (TextView) getView().findViewById(R.id.disconnected);
        try {
            apiService = ApiClient.getClient().create(ApiInterface.class);
            final String user = sessionManager.getKeyId();

            Call<BookmarkResponse> call = apiService.getBookmark("JWT "+ sessionManager.getKeyToken(),user);
            call.enqueue(new Callback<BookmarkResponse>() {

                @Override
                public void onResponse(Call<BookmarkResponse> call, final Response<BookmarkResponse> response) {
                    if (response.isSuccessful()) {

//                        if(response.body().getMessage()!=null) {
//                            kosong.setText("Tidak ada data");
//                        }
                        if(response.body().getTotal()!="0"){
                        sharingArrayList = response.body().getKnowledgeSharings();
                        recyclerView.setAdapter(new SharingAdapter(getActivity(), sharingArrayList));
                        recyclerView.smoothScrollToPosition(0);
                        //Toast.makeText(getActivity(), movieArrayList.toString(), Toast.LENGTH_SHORT).show();
                        swipeContainer.setRefreshing(false);
                        //pd.hide();
                        }

                    }
                }

                @Override
                public void onFailure(Call<BookmarkResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(getActivity(), "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                    Disconnected.setVisibility(View.VISIBLE);
                    //pd.hide();
                }

            });

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
