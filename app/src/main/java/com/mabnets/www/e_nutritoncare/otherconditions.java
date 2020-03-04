package com.mabnets.www.e_nutritoncare;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.core.app.Fragment;
import androidx.core.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.kosalgeek.android.caching.FileCacher;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class otherconditions extends Fragment {

    private Spinner Tuberculosis;
    private Spinner Hiv;
    private Spinner Malaria;
    private CheckBox cbnonee;
    private CheckBox cbnoneet;
    private CheckBox cbnoneem;
    private CheckBox cbnoneeh;
    private Boolean checked;
    private Boolean checkedh;
    private Boolean checkedt;
    private Boolean checkedm;


    private EditText descriptionnh;
    private EditText descriptionnm;
    private EditText descriptionnt;
    private Button saveandrecord;
    private String otherdiseasesss;
    private String otherdiseasesssh;
    private String otherdiseasesssm;
    private String otherdiseasessst;
    private String encodedstring;
    private String ffinalpatient_name;
    private String ffinalpatient_nametwo;
    private String ffinalpatient_idno;
    private String ffinalpatient_phone;
    private String  ffinalpatientage;
    private String  ffinalpatientgender;
    private String  ffinalpatientbenefit;
    private String  ffinalpatientphoto;
    private String  ffinalcaregivernumber;
    private String  ffinalcaregivername;
    private String  ffinalcaregiverlocation;
    private String  ffinalcaregiverrelationship;
    private String  ffinalcaregivernametwo;
    private String  ffinalcaregiveridno;
    private Mycommand mycommand;
    private String  specialistlocation;
    private String specialistpass;
    private String specialistphone;
    private String fweight;
    private String fheight;
    private String fmuacc;
    private FileCacher<String> offline_cacher;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ProgressDialog progressDialog;
    final String Tag=this.getClass().getName();
    private String  description;
    private String  descriptionh;
    private String  descriptiont;
    private String  descriptionm;
    ArrayList<String> diseases;
    ArrayAdapter<String> genderadapter;
    public otherconditions() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View othrdis= inflater.inflate(R.layout.fragment_otherconditions, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);







        Hiv=(Spinner) othrdis.findViewById(R.id.patients_otherdetailh);
        Malaria=(Spinner) othrdis.findViewById(R.id.patients_otherdetailsm);
        Tuberculosis=(Spinner) othrdis.findViewById(R.id.patients_otherdetailt);
        cbnonee=(CheckBox)othrdis.findViewById(R.id.cbnone);
        cbnoneet=(CheckBox)othrdis.findViewById(R.id.cbnonet);
        cbnoneem=(CheckBox)othrdis.findViewById(R.id.cbnonem);
        cbnoneeh=(CheckBox)othrdis.findViewById(R.id.cbnoneh);
        descriptionnh=(EditText)othrdis.findViewById(R.id.petient_descriptionsh);
        descriptionnt=(EditText)othrdis.findViewById(R.id.petient_descriptionst);
        descriptionnm=(EditText)othrdis.findViewById(R.id.petient_descriptionsm);
        saveandrecord=(Button)othrdis.findViewById(R.id.patient_saveandrecord);

        checked=cbnonee.isChecked();
        offline_cacher=new FileCacher<>(getContext(),"offline.txt");

        preferences=getActivity().getSharedPreferences("logininfo.conf",MODE_PRIVATE);
        specialistphone=preferences.getString("phone","");
        specialistpass=preferences.getString("pass","");
        specialistlocation=preferences.getString("location","");

        Hiv.setEnabled(false);
        Malaria.setEnabled(false);
        Tuberculosis.setEnabled(false);
        descriptionnh.setEnabled(false);
        descriptionnt.setEnabled(false);
        descriptionnm.setEnabled(false);

        otherdiseasesssh="";
        otherdiseasesssm="";
        otherdiseasessst="";
        descriptionh="";
        descriptiont="";
        descriptionm="";


        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("saving...");
        mycommand=new Mycommand(getContext());
        Bundle bundle=getArguments();

        if(bundle!=null){
            ffinalpatient_name=bundle.getString("pname");
            ffinalpatient_nametwo=bundle.getString("pnametwo");
            ffinalpatient_idno=bundle.getString("ppidno");
            ffinalpatient_phone=bundle.getString("pphone");
            ffinalpatientage=bundle.getString("pAge");
            ffinalpatientgender=bundle.getString("pgen");
            ffinalpatientbenefit=bundle.getString("pbene");
            ffinalpatientphoto=bundle.getString("pphoto");
            ffinalcaregivernumber=bundle.getString("careno");
            ffinalcaregivername=bundle.getString("carename");
            ffinalcaregiverlocation=bundle.getString("carelocation");
            ffinalcaregiverrelationship=bundle.getString("carerel");
            ffinalcaregivernametwo=bundle.getString("carenametwo");
            ffinalcaregiveridno= bundle.getString("careidno");
             fweight=bundle.getString("weight");
             fheight=bundle.getString("height");
             fmuacc=bundle.getString("muac");

        }







       diseases=new ArrayList<>();
        diseases.add("not selected");
        diseases.add("positive");
        diseases.add("negative");



        genderadapter=new ArrayAdapter<String>(getContext(),R.layout.spinner_layout_common,diseases);

        Tuberculosis.setAdapter(genderadapter);
        Hiv.setAdapter(genderadapter);
        Malaria.setAdapter(genderadapter);
        Tuberculosis.setSelected(false);  // must
        Tuberculosis.setSelection(0,true);

        Hiv.setSelected(false);  // must
        Hiv.setSelection(0,true);

        Malaria.setSelected(false);  // must
        Malaria.setSelection(0,true);






        Tuberculosis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object beneitem = parent.getItemAtPosition(position);
                otherdiseasessst=(String) beneitem;

                if(otherdiseasessst.equals("not selected")){
                    diseases.remove(0);
                    genderadapter.notifyDataSetChanged();
                }else{
                otherdiseasessst="Tuberculosis "+otherdiseasessst ;
                cbnonee.setEnabled(false);}
            }
            public void onNothingSelected(AdapterView<?> parent) {
                 /*   Hiv.setEnabled(false);
                Malaria.setEnabled(false);
                descriptionnh.setEnabled(false);
                descriptionnm.setEnabled(false);*/
                cbnonee.setEnabled(false);
                Toast.makeText(getContext(), "please chosose an a beneficiary group", Toast.LENGTH_SHORT).show();
                return;
            }
        });
        Hiv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object beneitem = parent.getItemAtPosition(position);
                otherdiseasesssh=(String) beneitem;
                if(otherdiseasesssh.equals("not selected")){
                    diseases.remove(0);
                    genderadapter.notifyDataSetChanged();
                }else{
              /* beneitem = "Hiv "+parent.getItemAtPosition(position);*/
                otherdiseasesssh="Hiv "+otherdiseasesssh ;
         /*       Tuberculosis.setEnabled(false);
                Malaria.setEnabled(false);
                descriptionnt.setEnabled(false);
                descriptionnm.setEnabled(false);*/
                cbnonee.setEnabled(false);}
            }
            public void onNothingSelected(AdapterView<?> parent) {
           /*     descriptionnt.setEnabled(false);
                descriptionnm.setEnabled(false);
                Tuberculosis.setEnabled(false);
                Malaria.setEnabled(false);*/
                cbnonee.setEnabled(false);
                Toast.makeText(getContext(), "please chosose an a beneficiary group", Toast.LENGTH_SHORT).show();
                return;
            }
        });
        Malaria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object beneitem = parent.getItemAtPosition(position);
                otherdiseasesssm=(String) beneitem;

                if(otherdiseasesssm.equals("not selected")){
                    diseases.remove(0);
                    genderadapter.notifyDataSetChanged();
                }else{

                otherdiseasesssm="Malaria "+otherdiseasesssm;
                /*Tuberculosis.setEnabled(false);
                Hiv.setEnabled(false);
                descriptionnh.setEnabled(false);
                descriptionnt.setEnabled(false);*/
                cbnonee.setEnabled(false);}
            }
            public void onNothingSelected(AdapterView<?> parent) {
                /*descriptionnh.setEnabled(false);
                descriptionnt.setEnabled(false);
                Tuberculosis.setEnabled(false);
                Hiv.setEnabled(false);*/
                cbnonee.setEnabled(false);
                Toast.makeText(getContext(), "please chosose an a beneficiary group", Toast.LENGTH_SHORT).show();
                return;
            }
        });
        cbnonee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                checked=b;
                if(checked){
                    Hiv.setEnabled(false);
                    Malaria.setEnabled(false);
                    Tuberculosis.setEnabled(false);
                    descriptionnh.setEnabled(false);
                    descriptionnt.setEnabled(false);
                    descriptionnm.setEnabled(false);
                    otherdiseasesss="none";
                    cbnoneeh.setEnabled(false);
                    cbnoneem.setEnabled(false);
                    cbnoneet.setEnabled(false);

                }else {
                    Hiv.setEnabled(true);
                    Malaria.setEnabled(true);
                    Tuberculosis.setEnabled(true);
                    descriptionnh.setEnabled(true);
                    descriptionnt.setEnabled(true);
                    descriptionnm.setEnabled(true);
                    cbnoneeh.setEnabled(true);
                    cbnoneem.setEnabled(true);
                    cbnoneet.setEnabled(true);
                }
            }
        });
        cbnoneeh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                 checkedh=b;
                if(checkedh){
                    Hiv.setEnabled(true);
                    descriptionnh.setEnabled(true);


                }else {
                    Hiv.setEnabled(false);
                    descriptionnh.setEnabled(false);
                }
            }
        });
        cbnoneet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                checkedt=b;
                if(checkedt){
                    Tuberculosis.setEnabled(true);
                    descriptionnt.setEnabled(true);


                }else {
                    Tuberculosis.setEnabled(false);
                    descriptionnt.setEnabled(false);
                }
            }
        });
        cbnoneem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                checkedm=b;
                if(checkedm){
                    Malaria.setEnabled(true);
                    descriptionnm.setEnabled(true);


                }else {
                    Malaria.setEnabled(false);
                    descriptionnm.setEnabled(false);
                }
            }
        });

        saveandrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otherdiseasesss=otherdiseasesssh+"<br>"+otherdiseasessst+"<br>"+otherdiseasesssm;
                if(descriptionnh.isEnabled()){
                  descriptionh=descriptionnh.getText().toString().trim();
                }
                if(descriptionnt.isEnabled()){
                   descriptiont=descriptionnt.getText().toString().trim();
                }
                if (descriptionnm.isEnabled()){
                    descriptionm=descriptionnm.getText().toString().trim();
                }
                description=descriptionh+"<br>"+descriptiont+"<br>"+descriptionm;


                if(description.equals("")){
                    if(descriptionnh.isEnabled()){
                        YoYo.with(Techniques.Shake)
                                .duration(700)
                                .repeat(2)
                                .playOn(descriptionnh);
                        descriptionnh.setError("description cannot be empty");
                        descriptionnh.requestFocus();
                    }
                    if(descriptionnt.isEnabled()){
                        YoYo.with(Techniques.Shake)
                                .duration(700)
                                .repeat(2)
                                .playOn(descriptionnt);
                        descriptionnt.setError("description cannot be empty");
                        descriptionnt.requestFocus();
                    }
                    if(descriptionnm.isEnabled()){
                        YoYo.with(Techniques.Shake)
                                .duration(700)
                                .repeat(2)
                                .playOn(descriptionnm);
                        descriptionnm.setError("description cannot be empty");
                        descriptionnm.requestFocus();
                    }


                }else{
                    Bitmap bitmap= null;
                    try {
                        bitmap = ImageLoader.init().from(ffinalpatientphoto).requestSize(512,512).getBitmap();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    encodedstring= ImageBase64.encode(bitmap);

                    String url="http://sadiq.mabnets.com/saving_all_details.php";
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            /* Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();*/
                            AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
                            alert.setMessage(response);
                            alert.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Fragment fragment=new Bio_data();
                                    FragmentManager manager=getFragmentManager();
                                    manager.beginTransaction().replace(R.id.framelayout_index,fragment).addToBackStack(null).commit();
                                }

                            });
                            alert.show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            if (error instanceof TimeoutError) {
                                Toast.makeText(getContext(), "error time out ", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                ArrayList title = new ArrayList<String>();

                                title.add(ffinalpatient_name);
                                title.add(ffinalpatient_phone);
                                title.add(ffinalpatientage);
                                title.add(ffinalpatientgender);
                                title.add(ffinalpatientbenefit);
                                title.add(encodedstring);
                                title.add(ffinalcaregivernumber);
                                title.add(ffinalcaregivername);
                                title.add(ffinalcaregiverlocation);
                                title.add(ffinalcaregiverrelationship);
                                title.add(fweight);
                                title.add(fheight);
                                title.add(fmuacc);
                                title.add(otherdiseasesss);
                                title.add(description);
                                title.add(specialistlocation);
                                title.add(specialistpass);
                                title.add(specialistphone);
                                title.add(ffinalcaregivernametwo);
                                title.add(ffinalcaregiveridno);
                                title.add(ffinalpatient_nametwo);
                                title.add(ffinalpatient_idno);

                                /* Log.d(Tag, String.valueOf(title)+":");*/
                                try {
                                    offline_cacher.appendOrWriteCache(String.valueOf(title));

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if(offline_cacher.hasCache()){
                                    //read cache
//                                    try {
//                                        String t=offline_cacher.readCache();
//                                        Log.d(Tag, t);
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
                                    try {
                                        List<String> list =offline_cacher.getAllCaches();
                                        for(String text : list){
                                            //do something with text

                                        }
                                        Log.d(Tag, String.valueOf(list));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
                                    alert.setMessage("Data was successfull saved offline ");
                                    alert.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Fragment fragment=new Bio_data();

                                            FragmentManager manager=getFragmentManager();
                                            manager.beginTransaction().replace(R.id.framelayout_index,fragment).addToBackStack(null).commit();
                                        }

                                    });
                                    alert.show();
                                }else{
                                    Log.d(Tag, "no cache");
                                }

                            } else if (error instanceof NoConnectionError) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "error no connection", Toast.LENGTH_SHORT).show();
                                ArrayList title = new ArrayList<String>();

                                title.add(ffinalpatient_name);
                                title.add(ffinalpatient_phone);
                                title.add(ffinalpatientage);
                                title.add(ffinalpatientgender);
                                title.add(ffinalpatientbenefit);
                                title.add(encodedstring);
                                title.add(ffinalcaregivernumber);
                                title.add(ffinalcaregivername);
                                title.add(ffinalcaregiverlocation);
                                title.add(ffinalcaregiverrelationship);
                                title.add(fweight);
                                title.add(fheight);
                                title.add(fmuacc);
                                title.add(otherdiseasesss);
                                title.add(description);
                                title.add(specialistlocation);
                                title.add(specialistpass);
                                title.add(specialistphone);
                                title.add(ffinalcaregivernametwo);
                                title.add(ffinalcaregiveridno);
                                title.add(ffinalpatient_nametwo);
                                title.add(ffinalpatient_idno);

                                /* Log.d(Tag, String.valueOf(title)+":");*/
                                try {
                                    offline_cacher.appendOrWriteCache(String.valueOf(title));

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if(offline_cacher.hasCache()){
                                    //read cache
//                                    try {
//                                        String t=offline_cacher.readCache();
//                                        Log.d(Tag, t);
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
                                    try {
                                        List<String> list =offline_cacher.getAllCaches();
                                        for(String text : list){
                                            //do something with text

                                        }
                                        Log.d(Tag, String.valueOf(list));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
                                    alert.setMessage("Data was successfull saved offline ");
                                    alert.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Fragment fragment=new Bio_data();

                                            FragmentManager manager=getFragmentManager();
                                            manager.beginTransaction().replace(R.id.framelayout_index,fragment).addToBackStack(null).commit();
                                        }

                                    });
                                    alert.show();
                                }else{
                                    Log.d(Tag, "no cache");
                                }

                            } else if (error instanceof NetworkError) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "error network error", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof AuthFailureError) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "errorin Authentication", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ParseError) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "error while parsing", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ServerError) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "error  in server", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ClientError) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "error with Client", Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.dismiss();
                                /*Toast.makeText(getContext(), "error while loading", Toast.LENGTH_SHORT).show();*/
                                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params=new HashMap<>();
                            params.put("patientname",ffinalpatient_name);
                            params.put("patientphone",ffinalpatient_phone);
                            params.put("patientage",ffinalpatientage);
                            params.put("patientgender",ffinalpatientgender);
                            params.put("patientbenift",ffinalpatientbenefit);
                            params.put("patientphoto",encodedstring);
                            params.put("caregivernumber",ffinalcaregivernumber);
                            params.put("caregivername",ffinalcaregivername);
                            params.put("caregiverlocation",ffinalcaregiverlocation);
                            params.put("caregiverelationship",ffinalcaregiverrelationship);
                            params.put("weight",fweight);
                            params.put("height",fheight);
                            params.put("muac",fmuacc);
                            params.put("otherdiseases",otherdiseasesss);
                            params.put("description",description);
                            params.put("specialistlocation",specialistlocation);
                            params.put("specialistpass",specialistpass);
                            params.put("specialistphone",specialistphone);
                            params.put("patientsname",ffinalpatient_nametwo);
                            params.put("patientid",ffinalpatient_idno);
                            params.put("caregiverid",ffinalcaregiveridno);
                            params.put("caregiversname",ffinalcaregivernametwo);

                            return  params;
                        }
                    };
                    mycommand.add(stringRequest);
                    progressDialog.show();
                    mycommand.execute();
                    mycommand.remove(stringRequest);


                }
            }
        });


        return othrdis;
    }

}
