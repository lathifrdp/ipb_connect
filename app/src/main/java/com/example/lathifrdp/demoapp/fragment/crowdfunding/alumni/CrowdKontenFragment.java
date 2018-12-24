package com.example.lathifrdp.demoapp.fragment.crowdfunding.alumni;

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
import com.example.lathifrdp.demoapp.adapter.EventList;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.fragment.event.CreateEventFragment;
import com.example.lathifrdp.demoapp.fragment.event.DetailEventFragment;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.Crowdfunding;
import com.example.lathifrdp.demoapp.model.Event;
import com.example.lathifrdp.demoapp.response.CrowdResponse;
import com.example.lathifrdp.demoapp.response.EventResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrowdKontenFragment extends Fragment{

    private String title;
    TextView tv;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView listView;
    CrowdAlumniList adapter;
    ApiInterface apiService;
    SessionManager sessionManager;
    private int page = 1;
    private boolean isRefresh = false;
    private List<Crowdfunding> listCrowd;
    private int limitpage=0;
    Bundle bundle;
    String stat;
    String verified = "1";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crowd_konten_alumni,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Daftar Proposal");
        listView=(ListView)getView().findViewById(R.id.listKontenAlumni);
        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        sessionManager = new SessionManager(getActivity());
        //bundle = this.getArguments();

        if(bundle != null){
            page=1;
        }
        else {
            page=1;
        }
        //Toast.makeText(getActivity(), "stat " + stat, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getActivity(), "page " + page, Toast.LENGTH_SHORT).show();
        loadDataCrowdAlumni();

        bundle = new Bundle();
        listCrowd = new ArrayList<>();
        adapter= new CrowdAlumniList(listCrowd,getActivity());
        listView.setAdapter(adapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page=1;
                loadDataCrowdAlumni();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //final String str= listEvent.get(i).getFullName();

                Fragment newFragment = null;
                newFragment = new CrowdDetailFragment();

                //bundle.putString("nama",listUser.get(i).getFullName()); // Put anything what you want
                bundle.putString("id",listCrowd.get(i).getId()); // Put anything what you want
                //bundle.putString("email",listUser.get(i).getEmail()); // Put anything what you want

                newFragment.setArguments(bundle);
                //Toast.makeText(getActivity(), listEvent.get(i).getId(), Toast.LENGTH_SHORT).show();
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
                        loadDataCrowdAlumni();
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

    private void loadDataCrowdAlumni(){

        //spinner = (Spinner) getView().findViewById(R.id.prodiFragment);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        //ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<CrowdResponse> call = apiService.getCrowdAlumni("JWT "+ sessionManager.getKeyToken(),page,verified);
        call.enqueue(new Callback<CrowdResponse>() {
            @Override
            public void onResponse(Call<CrowdResponse> call, Response<CrowdResponse> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
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

            @Override
            public void onFailure(Call<CrowdResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

}
