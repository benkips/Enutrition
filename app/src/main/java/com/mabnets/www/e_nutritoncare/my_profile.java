package com.mabnets.www.e_nutritoncare;


import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.core.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class my_profile extends Fragment {
        private TextView myname;
        private TextView  myphone;
        private TextView   mylocation;
        private SharedPreferences preferences;
        private String phones;
        private String pass;
        private String locotxt;
        private String name;
    public my_profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View my_profile=inflater.inflate(R.layout.fragment_my_profile, container, false);

        preferences=getActivity().getSharedPreferences("logininfo.conf",MODE_PRIVATE);

        myname=(TextView) my_profile.findViewById(R.id.myname);
        myphone=(TextView)my_profile.findViewById(R.id.myphone);
        mylocation=(TextView)my_profile.findViewById(R.id.mylocation);



        phones=preferences.getString("phone","");
        pass=preferences.getString("pass","");
        locotxt=preferences.getString("location","");
        name=preferences.getString("name","");

        myname.setText(name);
        myphone.setText(phones);
        mylocation.setText(locotxt);

        return my_profile;
    }

}
