package com.example.lathifrdp.demoapp.fragment.explore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lathifrdp.demoapp.R;
import com.example.lathifrdp.demoapp.adapter.StudyProgramSpinner;
import com.example.lathifrdp.demoapp.api.ApiClient;
import com.example.lathifrdp.demoapp.api.ApiInterface;
import com.example.lathifrdp.demoapp.helper.SessionManager;
import com.example.lathifrdp.demoapp.model.StudyProgram;
import com.example.lathifrdp.demoapp.model.User;
import com.example.lathifrdp.demoapp.response.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreFragment extends Fragment{

    ApiInterface apiService;
    private Spinner spinner;
    private StudyProgramSpinner adapterProdik;
    public String studyProgramId;
    Button btnExplore;
    private EditText fullName2;
    private EditText batch2;
    //Spinner prodi;
    String isVerified;
    SessionManager sessionManager;
    UserResponse ur;
    Bundle bundle;
    TextView tv;
    public String fullName, batch, prod;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_explore,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        view.findViewById(R.id.exploreFragment).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(),"Explore Fragment",Toast.LENGTH_SHORT).show();
//            }
//        });

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Explore Alumni");

        loadDataProdi();
        bundle = new Bundle();
        //tv = (TextView) getView().findViewById(R.id.testext);

        fullName2 = (EditText) getView().findViewById(R.id.tesfullNameFragment);
        batch2 = (EditText) getView().findViewById(R.id.tesbatchFragment);

        isVerified = "1";
        //prod = studyProgramId;


        sessionManager = new SessionManager(getActivity());



        //tv.setText(fullName);

        //loadDataUser();

        btnExplore = (Button) getView().findViewById(R.id.exploreBTN);
        btnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (TextUtils.isEmpty(studyProgramId) || studyProgramId.equals("0")){
//                    Toast.makeText(getActivity(), "Silahkan pilih program studi", Toast.LENGTH_SHORT).show();
//                    return;
//                }


                fullName = fullName2.getText().toString();
                batch = batch2.getText().toString();

                Fragment newFragment = null;
                newFragment = new UserFragment();

                bundle.putString("fullname",fullName); // Put anything what you want
                bundle.putString("batch",batch.toString()); // Put anything what you want
                bundle.putString("study",studyProgramId); // Put anything what you want
                bundle.putString("isVerified",isVerified.toString()); // Put anything what you want

                newFragment.setArguments(bundle);

//                Toast.makeText(getActivity(), "nama: "+fullName, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(), "batch: "+batch, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), "idprodi: "+studyProgramId, Toast.LENGTH_SHORT).show();
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


    }

    private void loadDataProdi(){

        spinner = (Spinner) getView().findViewById(R.id.prodiFragment);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        //ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<StudyProgram>> call = apiService.getProdi();
        call.enqueue(new Callback<List<StudyProgram>>() {
            @Override
            public void onResponse(Call<List<StudyProgram>> call, Response<List<StudyProgram>> response) {
                if (response.isSuccessful()) {
                    List<StudyProgram> allprodi = response.body();
//                    List<String> listSpinner = new ArrayList<String>();
//                    for (int i = 0; i < allprodi.size(); i++){
//                        //nama_prodi.add(new StudyProgram(allprodi.get(i).getName()));
//                        listSpinner.add(allprodi.get(i).getName());
//                    }

                    //ArrayAdapter<StudyProgram> aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,listSpinner);
                    allprodi.add(0, new StudyProgram("0","Pilih Program Studi"));
                    adapterProdik = new StudyProgramSpinner(getActivity(),android.R.layout.simple_spinner_dropdown_item, R.id.prodi_sp,allprodi);

                    spinner.setAdapter(adapterProdik);

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            StudyProgram studyProgram = (StudyProgram) spinner.getSelectedItem();
                            studyProgramId = studyProgram.getFacultyId();
//                            Toast.makeText(getActivity(), studyProgramId, Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getActivity(), studyProgram.getName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegisterActivity.this,
//                            android.R.layout.simple_spinner_item, listSpinner);
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinner.setAdapter(adapter);


                    //spinner.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, nama_prodi));
                }
            }

            @Override
            public void onFailure(Call<List<StudyProgram>> call, Throwable t) {

            }
        });
    }
}
