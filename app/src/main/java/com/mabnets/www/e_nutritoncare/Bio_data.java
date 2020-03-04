package com.mabnets.www.e_nutritoncare;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.ImageLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


/**

// * A simple {@link Fragment} subclass.
 */
public class Bio_data extends Fragment {
    private EditText Patientfullnames;
    private EditText Patientfullnamestwo;
    private EditText Patientidno;
    private EditText Patientphone;
    private EditText PatientAge;
    private Spinner  Patientbenegrp;
    private Spinner  Patientgender;
    private Button   patientnxtbtn;
    private ImageButton Patient_uploadpic;
    private CheckBox cbbiodata;
    private ImageView Patient_iv;
    private String gendertxt;
    private String benetxt;
    private  CameraPhoto cameraphoto;
    private  String selctedphoto="";
    private final int CAMERA_REQUEST=13323;
    private Boolean checked;
    private String fullnames;
    private  String fullnamestwo;
    private String patientidno;
    private String patphone;
    private String patAge;
    private String none;

    public Bio_data() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
       View biodata=inflater.inflate(R.layout.fragment_bio_data, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

       Patientfullnames=(EditText)biodata.findViewById(R.id.patient_fullnames);
        Patientfullnamestwo=(EditText)biodata.findViewById(R.id.patient_fullnamestwo);
        Patientidno=(EditText)biodata.findViewById(R.id.patient_idno);
       Patientphone=(EditText)biodata.findViewById(R.id.patient_phone);
       PatientAge=(EditText)biodata.findViewById(R.id.patient_age);
       Patientbenegrp=(Spinner)biodata.findViewById(R.id.patient_benegrp);
       Patientgender=(Spinner)biodata.findViewById(R.id.patient_gender);
       patientnxtbtn=(Button)biodata.findViewById(R.id.biodata_next);
       Patient_uploadpic=(ImageButton)biodata.findViewById(R.id.patient_uploadbtn);
       Patient_iv=(ImageView)biodata.findViewById(R.id.patient_pic);
        cbbiodata=(CheckBox)biodata.findViewById(R.id.cbbiodata);

        PatientAge.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        none="none";
        checked=cbbiodata.isChecked();

        cbbiodata.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
               checked=b;
            }
        });

        Patientfullnames.setText("");
        Patientfullnamestwo.setText("");
        Patientidno.setText("");
        Patientphone.setText("");
        PatientAge.setText("");


        ArrayList<String> genderdata=new ArrayList<>();
        genderdata.add("female");
        genderdata.add("male");

        final ArrayList<String>  benedata=new ArrayList<>();
        benedata.add("Pregnant");
        benedata.add("Lactating");
        benedata.add("Child");

        ArrayAdapter<String> genderadapter=new ArrayAdapter<String>(getContext(),R.layout.spinner_layout_common,genderdata);
        ArrayAdapter<String> beneadapter=new ArrayAdapter<String>(getContext(),R.layout.spinner_layout_common,benedata);

        Patientgender.setAdapter(genderadapter);
        Patientbenegrp.setAdapter(beneadapter);


        Patient_uploadpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    /*Intent in=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(in,CAMERA_REQUEST);*/
                    cameraphoto=new CameraPhoto(getContext());
                    try {

                        startActivityForResult(cameraphoto.takePhotoIntent(),CAMERA_REQUEST);
                        cameraphoto.addToGallery();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else{
                    if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                        Toast.makeText(getContext(), "permissions needed", Toast.LENGTH_SHORT).show();
                    }
                    requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},CAMERA_REQUEST);
                }

            }
        });
        Patientgender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object genderitem = parent.getItemAtPosition(position);
                gendertxt=(String) genderitem;

            }
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "please chosose an a beneficiary group", Toast.LENGTH_SHORT).show();
            }
        });

        Patientbenegrp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object beneitem = parent.getItemAtPosition(position);
               benetxt=(String) beneitem;
               if(benetxt.equals("Child")){
                   Patientidno.setText("0");
                   Patientphone.setText("0");
                   Patientidno.setEnabled(false);
                   Patientphone.setEnabled(false);
               }else{
                   Patientidno.setEnabled(true);
                   Patientphone.setEnabled(true);
               }
            }
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "please chosose an a beneficiary group", Toast.LENGTH_SHORT).show();
            }
        });


        patientnxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (selctedphoto.isEmpty()) {
                        AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
                        alert.setMessage("please upload patient  picture for identification");
                        alert.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getContext(), "Good", Toast.LENGTH_SHORT).show();
                            }

                        });
                        alert.show();

                    }else if(benetxt.equals("Child")){
                        if (gendertxt != "" && benetxt != "") {
                            fullnames = Patientfullnames.getText().toString().trim();
                            fullnamestwo = Patientfullnamestwo.getText().toString().trim();
                            patientidno =  Patientidno.getText().toString().trim();
                            patphone = Patientphone.getText().toString().trim();
                            patAge = PatientAge.getText().toString().trim();

                            if (fullnames.equals("")) {
                                Patientfullnames.setError("name is invalid");
                                Patientfullnames.requestFocus();
                                return;
                            } else  if (fullnamestwo .equals("")) {
                                Patientfullnamestwo.setError("name is invalid");
                                Patientfullnamestwo.requestFocus();
                                return;
                            } else  if (patientidno .equals("")) {
                                Patientidno.setError("ID is invalid");
                                Patientidno.requestFocus();
                                return;
                            } else if (patphone.equals("")) {
                                Patientphone.setError("phone is invalid");
                                Patientphone.requestFocus();
                                return;
                            } else if (patAge.equals("")) {
                                PatientAge.setError("Age is invalid");
                                PatientAge.requestFocus();
                                return;

                            } else {
                                    Bundle patientbundle = new Bundle();
                                    patientbundle.putString("pnames", fullnames);
                                    patientbundle.putString("pnamestwo", fullnamestwo);
                                    patientbundle.putString("phone", patphone);
                                    patientbundle.putString("pidno", patientidno);
                                    patientbundle.putString("pAge", patAge);
                                    patientbundle.putString("pgender", gendertxt);
                                    patientbundle.putString("pbenefit", benetxt);
                                    patientbundle.putString("pselectedphoto", selctedphoto);
                                    Fragment fragment = new caregiver_data();
                                    fragment.setArguments(patientbundle);
                                    FragmentManager manager = getFragmentManager();
                                    manager.beginTransaction().replace(R.id.framelayout_index, fragment).addToBackStack(null).commit();

                            }

                        }

                    }else{
                        if (gendertxt != "" && benetxt != "") {
                             fullnames = Patientfullnames.getText().toString().trim();
                             fullnamestwo = Patientfullnamestwo.getText().toString().trim();
                            patientidno =  Patientidno.getText().toString().trim();
                             patphone = Patientphone.getText().toString().trim();
                             patAge = PatientAge.getText().toString().trim();

                            if (fullnames.equals("")) {
                                Patientfullnames.setError("name is invalid");
                                Patientfullnames.requestFocus();
                                return;
                            } else  if (fullnamestwo .equals("")) {
                                Patientfullnamestwo.setError("name is invalid");
                                Patientfullnamestwo.requestFocus();
                                return;
                            } else  if (patientidno .equals("")) {
                                Patientidno.setError("ID is invalid");
                                Patientidno.requestFocus();
                                return;
                            } else if (patphone.equals("")) {
                                Patientphone.setError("phone is invalid");
                                Patientphone.requestFocus();
                                return;
                            } else if (patAge.equals("")) {
                                PatientAge.setError("Age is invalid");
                                PatientAge.requestFocus();
                                return;
                            } else if (!isphone(patphone) || (patphone.length() != 10 || !patphone.startsWith("07"))) {

                                    Patientphone.setError("phone is invalid");
                                    Patientphone.requestFocus();
                                    return;

                            } else {
                                if(checked) {
                                    Bundle patientbundle = new Bundle();
                                    patientbundle.putString("pnames", fullnames);
                                    patientbundle.putString("pnamestwo", fullnamestwo);
                                    patientbundle.putString("phone", patphone);
                                    patientbundle.putString("pidno", patientidno);
                                    patientbundle.putString("pAge", patAge);
                                    patientbundle.putString("pgender", gendertxt);
                                    patientbundle.putString("pbenefit", benetxt);
                                    patientbundle.putString("pselectedphoto", selctedphoto);
                                    Fragment fragment = new caregiver_data();
                                    fragment.setArguments(patientbundle);
                                    FragmentManager manager = getFragmentManager();
                                    manager.beginTransaction().replace(R.id.framelayout_index, fragment).addToBackStack(null).commit();
                                }else{
                    Bundle caregiverbundle=new Bundle();
                    caregiverbundle.putString("cnames",fullnames);
                    caregiverbundle.putString("cphone", patphone);
                    caregiverbundle.putString("cAge", patAge);
                    caregiverbundle.putString("cgender",gendertxt);
                    caregiverbundle.putString("cbenefit",benetxt);
                    caregiverbundle.putString("cphoto",selctedphoto);
                    caregiverbundle.putString("cnametwo",fullnamestwo);
                    caregiverbundle.putString("cidno", patientidno);
                    caregiverbundle.putString("caregive_names",none);
                    caregiverbundle.putString("caregiver_fullnamestwo",none);
                    caregiverbundle.putString("caregiver_no",none);
                    caregiverbundle.putString("care_location",none);
                    caregiverbundle.putString("care_relationship",none);
                    caregiverbundle.putString("caregiver_idno",none);
                    Fragment fragment=new Arthroprometic_data();
                    fragment.setArguments(caregiverbundle);
                    FragmentManager manager=getFragmentManager();
                    manager.beginTransaction().replace(R.id.framelayout_index,fragment).addToBackStack(null).commit();
                                }
                            }

                }




                }
            }
        });


       return biodata;
    }
    public static boolean isphone(CharSequence target){

        return !TextUtils.isEmpty(target) && Patterns.PHONE.matcher(target).matches();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode ==RESULT_OK){
            if(requestCode== CAMERA_REQUEST){
                String picpath=cameraphoto.getPhotoPath();
                try {
                    Bitmap bitmap= ImageLoader.init().from(picpath).requestSize(120,120).getBitmap();
                    Patient_iv.setImageBitmap(bitmap);
                    selctedphoto=picpath;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
