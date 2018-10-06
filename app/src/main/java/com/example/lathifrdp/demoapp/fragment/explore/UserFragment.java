package com.example.lathifrdp.demoapp.fragment.explore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.adapter.UserList;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.User;
import com.example.lathifrdp.demoapp.response.UserResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.support.v4.widget.SwipeRefreshLayout;

public class UserFragment extends Fragment{

    ArrayList<User> dataModels;
    ListView listView;
    UserList adapter;
    ApiInterface apiService;
    SessionManager sessionManager;
    Bundle bundle;
    private String x3,full,batch;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private int page = 1;
    private boolean isRefresh = false;
    private List<User> listUser;
    private int limitpage=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView=(ListView)getView().findViewById(R.id.list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

//
//        dataModels= new ArrayList<User>();
//        adapter= new UserList(dataModels,getActivity());
//
//        listView.setAdapter(adapter);
        sessionManager = new SessionManager(getActivity());

        bundle = this.getArguments();

        if(bundle != null){
            // handle your code here.
            //String x = bundle.getString("limit");
            //String x2 = bundle.getString("limit2");
            x3 = bundle.getString("isVerified");
            full = bundle.getString("fullname");
            batch = bundle.getString("batch");
            //Toast.makeText(getActivity(), full, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getActivity(), batch, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getActivity(), x3, Toast.LENGTH_SHORT).show();
            loadDataUser();
        }
        else {
            Toast.makeText(getActivity(), "gagal bos", Toast.LENGTH_SHORT).show();
        }

        listUser = new ArrayList<>();
        adapter= new UserList(listUser,getActivity());

        listView.setAdapter(adapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                //page=1;
                loadDataUser();
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == SCROLL_STATE_IDLE && listView.getLastVisiblePosition() ==
                        adapter.getSize() - 1){
                    if(page<limitpage+1) {
                        loadDataUser();
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
    private void loadDataUser(){

        //spinner = (Spinner) getView().findViewById(R.id.prodiFragment);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        //ApiService apiService = ApiClient.getClient().create(ApiService.class);

        final String fullName = full;
        final String isVerified = x3;

        Call<UserResponse> call = apiService.getUser("JWT "+ sessionManager.getKeyToken(),fullName,page,isVerified);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {

                    if(isRefresh) adapter.setList(response.body().getUser());
                    else adapter.addList(response.body().getUser());
                    isRefresh = false;
                    adapter.notifyDataSetChanged();
                    //ur = response.body();
//                    bundle.putString("fullname",fullName); // Put anything what you want
//                    bundle.putString("batch",batch); // Put anything what you want
                    //bundle.putString("limits",ur.getLimit().toString()); // Put anything what you want
                    //UserResponse ur = response.body();
                    int total = response.body().getTotal();
                    int limit = response.body().getLimit();
                    limitpage = (int)Math.ceil((double)total/limit);

                    //Log.e("limitpage: ",String.valueOf(limitpage));


                    page++;
//                    for (int i=0;i<usr.size();i++)
//                    {
//                        Toast.makeText(getActivity(), usr.get(i).getFullName(), Toast.LENGTH_SHORT).show();
//                    }
                    //Toast.makeText(getActivity(),usr.size() , Toast.LENGTH_LONG).show();
//                    List<String> listSpinner = new ArrayList<String>();
//                    for (int i = 0; i < allprodi.size(); i++){
//                        //nama_prodi.add(new StudyProgram(allprodi.get(i).getName()));
//                        listSpinner.add(allprodi.get(i).getName());
//                    }

                    //ArrayAdapter<StudyProgram> aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,listSpinner);
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegisterActivity.this,
//                            android.R.layout.simple_spinner_item, listSpinner);
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinner.setAdapter(adapter);


                    //spinner.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, nama_prodi));
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

}
