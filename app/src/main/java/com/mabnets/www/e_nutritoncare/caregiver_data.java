package com.mabnets.www.e_nutritoncare;


import android.os.Bundle;
import androidx.core.app.Fragment;
import androidx.core.app.FragmentManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class caregiver_data extends Fragment {
   private EditText caregiver_fullnames;
   private EditText caregiver_fullnamestwo;
   private EditText caregiver_idno;
   private EditText caregive_phone;
   private EditText caregiver_location;
   private Spinner caregiver_relationship;
   private Button caregiver_nxtbtn;
   private String patientcname;
   private String patientcnametwo;
   private String patientcnameidno;
   private String patientcphone;
   private String patientcAge;
   private String patientcgender;
   private String patientcbenefit;
   private String patientcphoto;
   private String care_relationshipp;

    public caregiver_data() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View caregiver= inflater.inflate(R.layout.fragment_caregiver_data, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        caregiver_fullnames=(EditText)caregiver.findViewById(R.id.caregiver_fullnames);
        caregiver_fullnamestwo=(EditText)caregiver.findViewById(R.id.caregiver_fullnamestwo);
        caregiver_idno=(EditText)caregiver.findViewById(R.id.caregiver_idno);
        caregive_phone=(EditText)caregiver.findViewById(R.id.caregiver_phonenumber);
        caregiver_location=(EditText)caregiver.findViewById(R.id.caregiver_Location);
        caregiver_relationship=(Spinner) caregiver.findViewById(R.id.caregiver_relationship);
        caregiver_nxtbtn=(Button) caregiver.findViewById(R.id.caregiver_nextbtn);



        caregiver_fullnames.setText("");
        caregive_phone.setText("");
        caregiver_location.setText("");
        caregiver_fullnamestwo.setText("");
        caregiver_idno.setText("");


        Bundle bundle=getArguments();
        if(bundle!=null){
            patientcname=bundle.getString("pnames");
             patientcphone=bundle.getString("phone");
             patientcAge=bundle.getString("pAge");
             patientcgender=bundle.getString("pgender");
             patientcbenefit=bundle.getString("pbenefit");
             patientcphoto=bundle.getString("pselectedphoto");
            patientcnametwo=bundle.getString("pnamestwo");
            patientcnameidno=bundle.getString("pidno");


        }

        ArrayList<String> relashonship=new ArrayList<>();
        relashonship.add("Father");
        relashonship.add("Mother");
        relashonship.add("Sister");
        relashonship.add("Brother");
        relashonship.add("Aunt");
        relashonship.add("Uncle");
        relashonship.add("Cousin");
        relashonship.add("Grand parent");
        relashonship.add("Good samaritan");



        ArrayAdapter<String> genderadapter=new ArrayAdapter<String>(getContext(),R.layout.spinner_layout_common,relashonship);

        caregiver_relationship.setAdapter(genderadapter);
        caregiver_relationship.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object beneitem = parent.getItemAtPosition(position);
              care_relationshipp=(String) beneitem;
            }
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "please chosose an a beneficiary group", Toast.LENGTH_SHORT).show();
            }
        });


        caregiver_nxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String caregive_names=caregiver_fullnames.getText().toString().trim();
                String caregiver_fullnamestwoo=caregiver_fullnamestwo.getText().toString().trim();
                String caregiver_idnoo=caregiver_idno.getText().toString().trim();
                String caregiver_no=caregive_phone.getText().toString().trim();
                String care_location=caregiver_location.getText().toString().trim();









                if(caregive_names.isEmpty()){
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(2)
                            .playOn(caregiver_fullnames);
                    caregiver_fullnames.setError("invalid name");
                    caregiver_fullnames.requestFocus();
                    return;
                } else  if(caregiver_fullnamestwoo.isEmpty()){
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(2)
                            .playOn(caregiver_fullnamestwo);
                    caregiver_fullnamestwo.setError("invalid name");
                    caregiver_fullnamestwo.requestFocus();
                    return;
                } else  if(caregiver_idnoo.isEmpty()){
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(2)
                            .playOn(caregiver_idno);
                    caregiver_idno.setError("invalid idnumber");
                    caregiver_idno.requestFocus();
                    return;
                }else if(care_relationshipp.isEmpty()){
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(2)
                            .playOn(caregiver_relationship);
                    caregiver_relationship.requestFocus();
                }else 	 if( caregiver_no.isEmpty()){
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(2)
                            .playOn(caregive_phone);
                    caregive_phone.setError("inavalid number");
                    caregive_phone.requestFocus();
                    return;
                }else	if(care_location.isEmpty()){
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .repeat(2)
                            .playOn(caregiver_location);
                    caregiver_location.setError("invalid location");
                    caregiver_location.requestFocus();
                }else{
                    if(!isphone(caregive_phone.getText().toString()) || (caregiver_no.length()!=10 || !caregiver_no.startsWith("07"))) {
                        YoYo.with(Techniques.Shake)
                                .duration(700)
                                .repeat(2)
                                .playOn(caregive_phone);
                        caregive_phone.setError("invalid number");
                        caregive_phone.requestFocus();
                        return;
                    }else{
                      Bundle caregiverbundle=new Bundle();
                        caregiverbundle.putString("cnames",patientcname);
                        caregiverbundle.putString("cphone", patientcphone);
                        caregiverbundle.putString("cAge", patientcAge);
                        caregiverbundle.putString("cgender", patientcgender);
                        caregiverbundle.putString("cbenefit", patientcbenefit);
                        caregiverbundle.putString("cphoto", patientcphoto);
                        caregiverbundle.putString("cnametwo",patientcnametwo);
                        caregiverbundle.putString("cidno", patientcnameidno);
                        caregiverbundle.putString("caregive_names",caregive_names);
                        caregiverbundle.putString("caregiver_fullnamestwo",caregiver_fullnamestwoo);
                        caregiverbundle.putString("caregiver_no",caregiver_no);
                        caregiverbundle.putString("care_location",care_location);
                        caregiverbundle.putString("care_relationship",care_relationshipp);
                        caregiverbundle.putString("caregiver_idno",caregiver_idnoo);
                        Fragment fragment=new Arthroprometic_data();
                        fragment.setArguments(caregiverbundle);
                        FragmentManager manager=getFragmentManager();
                        manager.beginTransaction().replace(R.id.framelayout_index,fragment).addToBackStack(null).commit();

                    }

                }
            }
        });


        return caregiver;
    }
    public static boolean isphone(CharSequence target){
        return !TextUtils.isEmpty(target) && Patterns.PHONE.matcher(target).matches();
    }
}
