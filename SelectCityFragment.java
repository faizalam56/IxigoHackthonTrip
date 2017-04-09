package com.example.faiyaz.ixigohackthontrip;

//import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.faiyaz.ixigohackthontrip.R;

/**
 * Created by Faiyaz on 08-Apr-17.
 */
public class SelectCityFragment extends Fragment {
    EditText cityName;
    Context context;
    Button button;
    String cityname;
    SubmitActionCommunicator communicator;
    void setSubmitActionCommunicator(SubmitActionCommunicator communicator){
        this.communicator=communicator;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selectcity_fragment, container, false);

         return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cityName= (EditText) getActivity().findViewById(R.id.entercity_name);
        button= (Button) getActivity().findViewById(R.id.submit_cityname);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityname = cityName.getText().toString();
                System.out.println("City "+cityname);
                communicator.submitCityName(cityname);
            }
        });

    }

    interface SubmitActionCommunicator{
       public void submitCityName(String cityname);

    }
}
