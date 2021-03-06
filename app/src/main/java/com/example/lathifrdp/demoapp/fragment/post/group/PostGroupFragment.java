package com.example.lathifrdp.demoapp.fragment.post.group;

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
import com.example.lathifrdp.demoapp.adapter.GroupList;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.fragment.group.CreateGroupFragment;
import com.example.lathifrdp.demoapp.fragment.group.DetailGroupFragment;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.GroupDiscussion;
import com.example.lathifrdp.demoapp.response.GroupResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostGroupFragment extends Fragment{
    private String title;
    TextView tv;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView listView;
    GroupList adapter;
    ApiInterface apiService;
    SessionManager sessionManager;
    private int page = 1;
    private boolean isRefresh = false;
    private List<GroupDiscussion> listGroup;
    private int limitpage=0;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_group,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Grup Diskusi");
        listView=(ListView)getView().findViewById(R.id.listGroup);
        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        sessionManager = new SessionManager(getActivity());

        loadDataGroup();

        bundle = new Bundle();
        listGroup = new ArrayList<>();
        adapter= new GroupList(listGroup,getActivity());
        listView.setAdapter(adapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                page=1;
                loadDataGroup();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment newFragment = null;
                newFragment = new DetailGroupPostFragment();
                bundle.putString("id_group",listGroup.get(i).getId());
                newFragment.setArguments(bundle);
                //Toast.makeText(getActivity(), listGroup.get(i).getId(), Toast.LENGTH_SHORT).show();
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
                        loadDataGroup();
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

    private void loadDataGroup(){

        apiService = ApiClient.getClient().create(ApiInterface.class);
        final String createdBy = sessionManager.getKeyId();

        Call<GroupResponse> call = apiService.getGroupPost("JWT "+ sessionManager.getKeyToken(),createdBy,page);
        call.enqueue(new Callback<GroupResponse>() {
            @Override
            public void onResponse(Call<GroupResponse> call, Response<GroupResponse> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    if(isRefresh) adapter.setList(response.body().getGroupDiscussions());
                    else adapter.addList(response.body().getGroupDiscussions());
                    isRefresh = false;
                    adapter.notifyDataSetChanged();

//                    int total = response.body().getTotal();
//                    int limit = 1;
//                    limitpage = (int)Math.ceil((double)total/limit);
                    //page++;

                }
            }

            @Override
            public void onFailure(Call<GroupResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
