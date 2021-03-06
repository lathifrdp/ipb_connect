package com.example.lathifrdp.demoapp.fragment.post.job;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.lathifrdp.demoapp.adapter.VacancyList;
import com.example.lathifrdp.demoapp.adapter.VacancyPostList;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.fragment.job.DetailVacancyFragment;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Job;
import com.example.lathifrdp.demoapp.response.JobResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostJobFragment extends Fragment{

    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView listView;
    VacancyPostList adapter;
    ApiInterface apiService;
    SessionManager sessionManager;
    private int page = 1;
    private boolean isRefresh = false;
    private List<Job> listVacancy;
    private int limitpage=0;
    TextView kosong;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_job,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Daftar Lowongan");
        listView=(ListView)getView().findViewById(R.id.listVacancy);
        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        kosong = (TextView)  getView().findViewById(R.id.vacancy_isEmpty);

        sessionManager = new SessionManager(getActivity());

        if(bundle != null){
            page = 1;
        }
        else {
            page = 1;
        }

        bundle = new Bundle();
        listVacancy = new ArrayList<>();
        adapter= new VacancyPostList(listVacancy,getActivity());
        listView.setAdapter(adapter);
        //listView.smoothScrollToPosition(0);

        loadDataVacancy();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                //page=1;
                loadDataVacancy();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //final String str= listEvent.get(i).getFullName();

                Fragment newFragment = null;
                newFragment = new DetailVacancyPostFragment();

                //bundle.putString("nama",listUser.get(i).getFullName()); // Put anything what you want
                bundle.putString("id_vacancy",listVacancy.get(i).getId()); // Put anything what you want
                //bundle.putString("email",listUser.get(i).getEmail()); // Put anything what you want

                newFragment.setArguments(bundle);
                //Toast.makeText(getActivity(), listVacancy.get(i).getId(), Toast.LENGTH_SHORT).show();
//
                // consider using Java coding conventions (upper first char class names!!!)
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.screen_area, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();

            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == SCROLL_STATE_IDLE && listView.getLastVisiblePosition() ==
                        adapter.getSize() - 1){
                    if(page<limitpage+1) {
                        loadDataVacancy();
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
    private void loadDataVacancy(){

        //spinner = (Spinner) getView().findViewById(R.id.prodiFragment);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        //ApiService apiService = ApiClient.getClient().create(ApiService.class);
        final String createdBy = sessionManager.getKeyId();

        Call<JobResponse> call = apiService.getJobPost("JWT "+ sessionManager.getKeyToken(),createdBy,page);
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

                    if(total == 0){
                        kosong.setText("Mohon Maaf, data tidak ada");
                    }

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
