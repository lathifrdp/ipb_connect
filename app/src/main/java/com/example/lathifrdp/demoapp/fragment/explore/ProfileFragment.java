package com.example.lathifrdp.demoapp.fragment.explore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.User;
import com.example.lathifrdp.demoapp.model.UserProfile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    Bundle bundle;
    private String id_user,full;
    TextView nama_profil, alamat_profil, nomor_profil;
    ApiInterface apiService;
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile");
        sessionManager = new SessionManager(getActivity());
        nama_profil = (TextView) getView().findViewById(R.id.profil_nama);
        alamat_profil = (TextView) getView().findViewById(R.id.profil_alamat);
        nomor_profil = (TextView) getView().findViewById(R.id.profil_mobilenumber);
        bundle = this.getArguments();

        if(bundle != null){
            // handle your code here.
            //String x = bundle.getString("limit");
            //String x2 = bundle.getString("limit2");
            id_user = bundle.getString("id");
            full = bundle.getString("fullname");

            loadDataProfile();
            //batch = bundle.getString("batch");
            //Toast.makeText(getActivity(), full, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getActivity(), batch, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getActivity(), id_user, Toast.LENGTH_SHORT).show();
            //loadDataUser();
        }
        else {
            Toast.makeText(getActivity(), "gagal bos", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDataProfile(){

        //spinner = (Spinner) getView().findViewById(R.id.prodiFragment);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        //ApiService apiService = ApiClient.getClient().create(ApiService.class);

        //final String fullName = full;
        //final String isVerified = x3;

        Call<UserProfile> call = apiService.getProfile("JWT "+ sessionManager.getKeyToken(),id_user);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, final Response<UserProfile> response) {
                //mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {

                    UserProfile userProfile = response.body();


                    nama_profil.setText(full);
                    alamat_profil.setText(userProfile.getAddress());
                    nomor_profil.setText(userProfile.getMobileNumber());
                    //Toast.makeText(getActivity(), userProfile.getAddress(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getActivity(), userProfile.getMobileNumber(), Toast.LENGTH_SHORT).show();
                    //if(isRefresh) adapter.setList(response.body().getUser());
                    //else adapter.addList(response.body().getUser());
                    //isRefresh = false;
                    //adapter.notifyDataSetChanged();
                    //ur = response.body();
//                    bundle.putString("fullname",fullName); // Put anything what you want
//                    bundle.putString("batch",batch); // Put anything what you want
                    //bundle.putString("limits",ur.getLimit().toString()); // Put anything what you want
                    //UserResponse ur = response.body();
                    //int total = response.body().getTotal();
                    //int limit = response.body().getLimit();
                    //limitpage = (int)Math.ceil((double)total/limit);

                    //Log.e("limitpage: ",String.valueOf(limitpage));


                    //page++;

//                    listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                            User user = (User) listView.getSelectedItem();
//                            //List<User> uus = response.body().getUser();
//
//                            //final String item = (String) adapterView.getItemAtPosition(i);
//                            //studyProgramId = studyProgram.getFacultyId();
//                            //Toast.makeText(RegisterActivity.this, studyProgram.getFacultyId(), Toast.LENGTH_SHORT).show();
//                            //final String str= listUser.get(i).getFullName();
//                            Toast.makeText(getActivity(), user.getFullName(), Toast.LENGTH_SHORT).show();
////                            Snackbar.make(getView(), "Nama " +user.getFullName(), Snackbar.LENGTH_LONG)
////                                    .setAction("No action", null).show();
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> adapterView) {
//
//                        }
//                    });


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
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
                //mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

}
