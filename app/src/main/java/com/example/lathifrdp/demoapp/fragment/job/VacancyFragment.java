package com.example.lathifrdp.demoapp.fragment.job;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.adapter.VacancyList;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Job;
import com.example.lathifrdp.demoapp.response.JobResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VacancyFragment extends Fragment {

    Bundle bundle;
    private String title;
    TextView tv;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView listView;
    VacancyList adapter;
    ApiInterface apiService;
    SessionManager sessionManager;
    private int page = 1;
    private boolean isRefresh = false;
    private List<Job> listVacancy;
    private int limitpage=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vacancy,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Vacancy List");
        listView=(ListView)getView().findViewById(R.id.listVacancy);
        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        sessionManager = new SessionManager(getActivity());
        bundle = this.getArguments();
        //tv = (TextView) getView().findViewById(R.id.tes);

        if(bundle != null){
            title = bundle.getString("title");
            if(title.isEmpty()) page =1;
            //tv.setText(title);
            loadDataVacancy();
        }
        else {
            Toast.makeText(getActivity(), "gagal bos", Toast.LENGTH_SHORT).show();
        }

        listVacancy = new ArrayList<>();
        adapter= new VacancyList(listVacancy,getActivity());
        listView.setAdapter(adapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                //page=1;
                loadDataVacancy();
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == SCROLL_STATE_IDLE && listView.getLastVisiblePosition() ==
                        adapter.getSize() - 1){
                    if(page<limitpage+1) {
                        loadDataVacancy();
                        Toast.makeText(getActivity(), "lanjut " + page, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //we don't need this method, so we leave it empty
            }
        });

    }

    private void loadDataVacancy(){

        //spinner = (Spinner) getView().findViewById(R.id.prodiFragment);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        //ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<JobResponse> call = apiService.getJob("JWT "+ sessionManager.getKeyToken(),title,page);
        call.enqueue(new Callback<JobResponse>() {
            @Override
            public void onResponse(Call<JobResponse> call, Response<JobResponse> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    if(isRefresh) adapter.setList(response.body().getJob());
                    else adapter.addList(response.body().getJob());
                    isRefresh = false;
                    adapter.notifyDataSetChanged();

                    int total = response.body().getTotal();
                    int limit = response.body().getLimit();
                    limitpage = (int)Math.ceil((double)total/limit);
                    page++;

                }
            }

            @Override
            public void onFailure(Call<JobResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
