package com.example.lathifrdp.demoapp.fragment.crowdfunding.mahasiswa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.adapter.CrowdAlumniList;
import com.example.lathifrdp.demoapp.adapter.CrowdMahasiswaList;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Crowdfunding;
import com.example.lathifrdp.demoapp.response.CrowdResponse;
import com.example.lathifrdp.demoapp.response.PinResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrowdMahasiswaFragment extends Fragment {

    private String title;
    TextView tv;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView listView;
    CrowdMahasiswaList adapter;
    ApiInterface apiService;
    SessionManager sessionManager;
    private int page = 1;
    private boolean isRefresh = false;
    private List<Crowdfunding> listCrowd;
    private int limitpage=0;
    Bundle bundle;
    TextView stat;
    String verified = "1";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crowd_mahasiswa,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Proposal Anda");
        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab_crowd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Create", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Fragment createFragment = null;
                createFragment = new CrowdCreateFragment();
                createFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.screen_area, createFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        listView=(ListView)getView().findViewById(R.id.listKontenMahasiswa);
        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        stat = (TextView) getView().findViewById(R.id.konten_kosong);

        sessionManager = new SessionManager(getActivity());

        if(bundle != null){
            page=1;
        }
        else {
            page=1;
        }

        loadDataCrowdMhs();

        bundle = new Bundle();
        listCrowd = new ArrayList<>();
        adapter= new CrowdMahasiswaList(listCrowd,getActivity());
        listView.setAdapter(adapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page=1;
                loadDataCrowdMhs();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment newFragment = null;
                newFragment = new CrowdMPagerFragment();
                bundle.putString("id",listCrowd.get(i).getId());
                newFragment.setArguments(bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.screen_area, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == SCROLL_STATE_IDLE && listView.getLastVisiblePosition() ==
                        adapter.getSize() - 1){
                    if(page<limitpage+1) {
                        loadDataCrowdMhs();
                        //Toast.makeText(getActivity(), "lanjut " + page, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //we don't need this method, so we leave it empty
            }
        });

    }
    private void loadDataCrowdMhs(){

        final String createdBy = sessionManager.getKeyId();
        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<CrowdResponse> call = apiService.getCrowdMahasiswa("JWT "+ sessionManager.getKeyToken(),createdBy);
        //Call<CrowdResponse> call = apiService.getCrowdMhs("JWT "+ sessionManager.getKeyToken(),page,creator);
        call.enqueue(new Callback<CrowdResponse>() {
            @Override
            public void onResponse(Call<CrowdResponse> call, Response<CrowdResponse> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {

                    CrowdResponse cr = response.body();

                    if(cr.getError() != null ){
                        Toast.makeText(getActivity(), cr.getError(), Toast.LENGTH_SHORT).show();
                        stat.setText("Anda tidak memiliki proposal");
                    }
                    if(page>0) {
                        //Toast.makeText(getActivity(), cr.getMessage(), Toast.LENGTH_SHORT).show();
                        stat.setText("");
                        if(isRefresh) adapter.setList(response.body().getCrowdfunding());
                        else adapter.addList(response.body().getCrowdfunding());
                        isRefresh = false;
                        adapter.notifyDataSetChanged();

                        int total = response.body().getTotal();
                        //int limit = response.body().getLimit();
                        //limitpage = (int)Math.ceil((double)total/limit);
                        page++;
                    }
                }
            }

            @Override
            public void onFailure(Call<CrowdResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), creator, Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
