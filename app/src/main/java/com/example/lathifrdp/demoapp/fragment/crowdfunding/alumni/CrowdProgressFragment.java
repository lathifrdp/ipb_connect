package com.example.lathifrdp.demoapp.fragment.crowdfunding.alumni;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.adapter.ProgressList;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Progress;
import com.example.lathifrdp.demoapp.response.ProgressResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrowdProgressFragment extends Fragment {

    private String id_crowd;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView listView;
    ProgressList adapter;
    ApiInterface apiService;
    SessionManager sessionManager;
    private int page = 1;
    private boolean isRefresh = false;
    private List<Progress> listProgress;
    private int limitpage=0;
    Bundle bundle;
    TextView kosong;

    public static CrowdProgressFragment newInstance(String id) {

        Bundle args = new Bundle();
        CrowdProgressFragment fragment = new CrowdProgressFragment();
        args.putString("id",id);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crowd_progress_alumni,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView=(ListView)getView().findViewById(R.id.listProgressAlumni);
        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        kosong = (TextView)  getView().findViewById(R.id.progress_kosong);

        sessionManager = new SessionManager(getActivity());
        bundle = this.getArguments();

        if(bundle != null){
            id_crowd = bundle.getString("id");
        }
        else {
            Toast.makeText(getActivity(), "gagal bos", Toast.LENGTH_SHORT).show();
        }
        loadDataProgress();
        bundle = new Bundle();
        listProgress = new ArrayList<>();
        adapter= new ProgressList(listProgress,getActivity());
        listView.setAdapter(adapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page=1;
                loadDataProgress();
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == SCROLL_STATE_IDLE && listView.getLastVisiblePosition() ==
                        adapter.getSize() - 1){
                    if(page<limitpage+1) {
                        loadDataProgress();
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

    private void loadDataProgress(){

        //spinner = (Spinner) getView().findViewById(R.id.prodiFragment);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        //ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<ProgressResponse> call = apiService.getProgress("JWT "+ sessionManager.getKeyToken(),id_crowd);
        call.enqueue(new Callback<ProgressResponse>() {
            @Override
            public void onResponse(Call<ProgressResponse> call, Response<ProgressResponse> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    if(isRefresh) adapter.setList(response.body().getProgresses());
                    else adapter.addList(response.body().getProgresses());
                    isRefresh = false;
                    adapter.notifyDataSetChanged();

                    int total = response.body().getTotal();
                    int limit = response.body().getLimit();
                    limitpage = (int)Math.ceil((double)total/limit);
                    page++;

                    if(total == 0){
                        kosong.setText("Belum ada progress");
                    }

                }
            }

            @Override
            public void onFailure(Call<ProgressResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
