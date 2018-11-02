package com.example.lathifrdp.demoapp.fragment.sharing;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.KnowledgeSharing;
import com.example.lathifrdp.demoapp.response.SharingResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DesainFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<KnowledgeSharing> sharingArrayList;
    TextView Disconnected;
    private KnowledgeSharing mov;
    ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;
    ApiInterface apiService;
    SessionManager sessionManager;

    public static DesainFragment newInstance() {

        Bundle args = new Bundle();
        DesainFragment fragment = new DesainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_desain,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sessionManager = new SessionManager(getActivity());
        initViewsDesain();
        //https://guides.codepath.com/android/Implementing-Pull-to-Refresh-Guide
        swipeContainer = (SwipeRefreshLayout) getView().findViewById(R.id.swipeContainer);

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDesain();
                Toast.makeText(getActivity(),"Desain List Refreshed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initViewsDesain(){
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Fetching Data...");
        pd.setCancelable(false);
        pd.show();
        recyclerView=(RecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.smoothScrollToPosition(0);
        loadDesain();
    }

    private void loadDesain() {
        Disconnected = (TextView) getView().findViewById(R.id.disconnected);
        try {
            apiService = ApiClient.getClient().create(ApiInterface.class);
            String id_desain = "5a2e80e63a30e04e33c5d761";

            Call<SharingResponse> call = apiService.getDesain("JWT "+ sessionManager.getKeyToken(),id_desain);
            call.enqueue(new Callback<SharingResponse>() {

                @Override
                public void onResponse(Call<SharingResponse> call, final Response<SharingResponse> response) {
                    if (response.isSuccessful()) {

                        sharingArrayList = response.body().getKnowledgeSharings();
                        recyclerView.setAdapter(new SharingAdapter(getActivity(), sharingArrayList));
                        recyclerView.smoothScrollToPosition(0);
                        //Toast.makeText(getActivity(), movieArrayList.toString(), Toast.LENGTH_SHORT).show();
                        swipeContainer.setRefreshing(false);
                        pd.hide();
                    }
                }

                @Override
                public void onFailure(Call<SharingResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(getActivity(), "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                    Disconnected.setVisibility(View.VISIBLE);
                    pd.hide();
                }

            });

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
