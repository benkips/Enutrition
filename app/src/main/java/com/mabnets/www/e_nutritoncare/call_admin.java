package com.mabnets.www.e_nutritoncare;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.app.Fragment;
import androidx.core.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class call_admin extends Fragment {

    private ProgressDialog progressDialog;
    private String phone;
    private SharedPreferences preferences;
    public call_admin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View callf= inflater.inflate(R.layout.fragment_call_admin, container, false);
        preferences=getActivity().getSharedPreferences("logininfo.conf",MODE_PRIVATE);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("contacting admin..");
        progressDialog.show();

        phone=preferences.getString("admin_phone","");
        Toast.makeText(getContext(), phone, Toast.LENGTH_SHORT).show();


                        Intent dailintent=new Intent(Intent.ACTION_CALL);
                        dailintent.setData(Uri.parse(phone));
                        try {
                            startActivity(dailintent);

                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(getContext(),
                                    "phone app failed, please try again later.", Toast.LENGTH_SHORT).show();
                        }


        progressDialog.dismiss();
        Fragment fragment=new home_menu();
        FragmentManager manager=getFragmentManager();
        manager.beginTransaction().replace(R.id.framelayout_index,fragment).addToBackStack(null).commit();
        return  callf;
    }

}
