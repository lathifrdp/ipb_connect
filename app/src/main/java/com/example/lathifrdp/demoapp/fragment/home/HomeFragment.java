package com.example.lathifrdp.demoapp.fragment.home;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.MainActivity;
import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.MyValueFormatter;
import com.example.lathifrdp.demoapp.response.CountResponse;
import com.example.lathifrdp.demoapp.helper.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ApiInterface apiService;
    SessionManager sessionManager;
    PieChart pieChart;
    PieChart pieChart2;
    public Integer laki;
    public Integer perempuan;
    public Integer alumni;
    public Integer mahasiswa;
    public Integer total;
    TextView HomeProdi;
    TextView HomeKet;
    TextView HomeTotal;
    TextView HomeTotalKet;
    SwipeRefreshLayout mSwipeRefreshLayout;
    //private TextView HomeProdi, HomeKet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Beranda");
        sessionManager = new SessionManager(getActivity());

        HomeProdi = (TextView) getView().findViewById(R.id.homeProdi);
        HomeKet = (TextView) getView().findViewById(R.id.homeKet);
        HomeTotal = (TextView) getView().findViewById(R.id.homeTotal);
        HomeTotalKet = (TextView) getView().findViewById(R.id.homeTotalKet);

        pieChart = (PieChart) getView().findViewById(R.id.piechart_gender);
        pieChart2 = (PieChart) getView().findViewById(R.id.piechart_account);
        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadCount();

            }
        });
        loadCount();
        //setPieChart();


//        view.findViewById(R.id.homeFragment).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(),"Home Fragment",Toast.LENGTH_SHORT).show();
//
//            }
//        });
    }

    private void loadCount(){

        apiService = ApiClient.getClient().create(ApiInterface.class);
        //String token = sessionManager.getKeyToken();
        //ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<CountResponse> call = apiService.getCount("JWT "+ sessionManager.getKeyToken());
        call.enqueue(new Callback<CountResponse>() {
            @Override
            public void onResponse(Call<CountResponse> call, Response<CountResponse> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {

                    CountResponse cr = response.body();
                    laki = cr.getCount().getGender().getLaki();
                    perempuan = cr.getCount().getGender().getPerempuan();
                    alumni = cr.getCount().getUserType().getAlumni();
                    mahasiswa = cr.getCount().getUserType().getMahasiswa();
                    total = cr.getTotal();

                    HomeProdi.setText(sessionManager.getKeyProdi());
                    HomeKet.setText("Anda adalah "+sessionManager.getKeyUsertype()+" dari Program Studi "+sessionManager.getKeyProdi());
                    HomeTotal.setText(total+" Pengguna");
                    HomeTotalKet.setText("Sebanyak "+total+" Alumni dan Mahasiswa yang telah terdaftar");

                    setPieChartGender();
                    setPieChartAccount();

//                    Toast.makeText(getActivity(), "laki: "+laki, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(), "perempuan: "+perempuan, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(), "alumni: "+alumni, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(), "mahasiswa: "+mahasiswa, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(), "total: "+total, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<CountResponse> call, Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    public void setPieChartGender() {

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(0,0,0,0);
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setDrawEntryLabels(false);
        pieChart.setCenterText("Jenis Kelamin");
        pieChart.animateY(2000, Easing.EasingOption.EaseInOutCubic);
        //pieChart.animateX(2000, Easing.EasingOption.EaseInBack);
        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(perempuan,"Perempuan"));
        yValues.add(new PieEntry(laki,"Laki-laki"));

        PieDataSet dataSet = new PieDataSet(yValues,"");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData pieData = new PieData((dataSet));
        pieData.setValueTextSize(10f);
        pieData.setValueFormatter(new MyValueFormatter());
        pieData.setValueTextColor(Color.WHITE);
        pieChart.setData(pieData);
        //PieChart Ends Here
    }

    public void setPieChartAccount() {

        pieChart2.setUsePercentValues(true);
        pieChart2.getDescription().setEnabled(false);
        pieChart2.setExtraOffsets(0,0,0,0);
        pieChart2.setDragDecelerationFrictionCoef(0.9f);
        pieChart2.setTransparentCircleRadius(61f);
        pieChart2.setHoleColor(Color.WHITE);
        pieChart2.setDrawEntryLabels(false);
        pieChart2.setCenterText("Pengguna");
        pieChart2.animateY(2000, Easing.EasingOption.EaseInOutCubic);
        //pieChart2.animateX(2000, Easing.EasingOption.EaseInBack);
        ArrayList<PieEntry> yValues2 = new ArrayList<>();
        yValues2.add(new PieEntry(alumni,"Alumni"));
        yValues2.add(new PieEntry(mahasiswa,"Mahasiswa"));

        PieDataSet dataSet2 = new PieDataSet(yValues2,"");
        dataSet2.setSliceSpace(3f);
        dataSet2.setSelectionShift(5f);
        dataSet2.setColors(ColorTemplate.PASTEL_COLORS);
        PieData pieData2 = new PieData((dataSet2));
        pieData2.setValueTextSize(10f);
        pieData2.setValueFormatter(new MyValueFormatter());
        pieData2.setValueTextColor(Color.WHITE);
        pieChart2.setData(pieData2);
        //PieChart Ends Here
    }
}
