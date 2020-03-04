package com.mabnets.www.e_nutritoncare;


import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.core.app.Fragment;
import androidx.core.app.FragmentManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class Arthroprometic_data extends Fragment {
    private String finalpatient_name;
    private String finalpatient_nametwo;
    private String finalpatient_idno;
    private String finalpatient_phone;
    private String finalpatientage;
    private String finalpatientgender;
    private String finalpatientbenefit;
    private String finalpatientphoto;
    private String finalcaregivernumber;
    private String finalcaregivername;
    private String finalcaregiverlocation;
    private String finalcaregiverrelationship;
    private String finalcaregivernametwo;
    private String finalcaregiveridno;
    private EditText weightt;
    private TextView status;
    private TextView zstatus;
    private EditText heightt;
    private EditText muacc;
    private Button nextartho;
    private ProgressBar PD;
    private ProgressBar PD8;
    boolean foundelement;
    InputStream inputStream;
    InputStream inputStreamb;
    final String Tag = this.getClass().getName();

    public Arthroprometic_data() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View arthroprometic = inflater.inflate(R.layout.fragment_arthroprometic_data, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        inputStream = getActivity().getResources().openRawResource(R.raw.boys);
        inputStreamb = getActivity().getResources().openRawResource(R.raw.girls);

        Bundle bundle = getArguments();
        if (bundle != null) {
            finalpatient_name = bundle.getString("cnames");
            finalpatient_phone = bundle.getString("cphone");
            finalpatientage = bundle.getString("cAge");
            finalpatientgender = bundle.getString("cgender");
            finalpatientbenefit = bundle.getString("cbenefit");
            finalpatientphoto = bundle.getString("cphoto");
            finalpatient_nametwo = bundle.getString("cnametwo");
            finalpatient_idno = bundle.getString("cidno");
            finalcaregivernumber = bundle.getString("caregiver_no");
            finalcaregivername = bundle.getString("caregive_names");
            finalcaregiverlocation = bundle.getString("care_location");
            finalcaregiverrelationship = bundle.getString("care_relationship");
            finalcaregivernametwo = bundle.getString("caregiver_fullnamestwo");
            finalcaregiveridno = bundle.getString("caregiver_idno");


        }

        /*  Toast.makeText(getContext(), finalpatientgender, Toast.LENGTH_SHORT).show();*/
        Log.d(Tag, finalpatientgender);
        Log.d(Tag, finalpatient_name);
        Log.d(Tag, finalpatient_phone);
        Log.d(Tag, finalpatientage);
        Log.d(Tag, finalpatientphoto);
        Log.d(Tag, finalcaregivernumber);
        Log.d(Tag, finalcaregivername);
        Log.d(Tag, finalcaregiverlocation);
        Log.d(Tag, finalcaregiverrelationship);

        weightt = (EditText) arthroprometic.findViewById(R.id.patient_weight);
        heightt = (EditText) arthroprometic.findViewById(R.id.patient_height);
        muacc = (EditText) arthroprometic.findViewById(R.id.patient_muac);
        status = (TextView) arthroprometic.findViewById(R.id.status);
        zstatus = (TextView) arthroprometic.findViewById(R.id.zscore);
        nextartho = (Button) arthroprometic.findViewById(R.id.nextartho);
        PD = arthroprometic.findViewById(R.id.progressBar);
        PD8 = arthroprometic.findViewById(R.id.progressBar8);

        heightt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);


        weightt.setText("");
        heightt.setText("");
        muacc.setText("");
        heightt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String h = heightt.getText().toString().trim();
                if (h.equals("")) {
                    heightt.setError("height is invalid");
                    heightt.requestFocus();
                    return;
                } else {

                        zscorecalc(h);


                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        muacc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String maucc = muacc.getText().toString().trim();
                if (maucc.equals("")) {
                    muacc.setError("maucc is invalid");
                    muacc.requestFocus();
                    return;
                } else {
                    Double dblLbs = Double.parseDouble(maucc);
                    Log.d(Tag, String.valueOf(dblLbs));
                    Log.d(Tag, finalpatientbenefit);
                    if (finalpatientbenefit.equals("Child")) {
                        if (dblLbs < 11.5) {
                            PD.getProgressDrawable().setColorFilter(
                                    getResources().getColor(R.color.colorRed), PorterDuff.Mode.SRC_IN);
                            status.setText("Severe malnutrition: Eligible for care");
                        } else if (dblLbs >= 11.5 && dblLbs < 12.5) {
                            PD.getProgressDrawable().setColorFilter(
                                    getResources().getColor(R.color.coloryellow), PorterDuff.Mode.SRC_IN);
                            status.setText("Acute Malnutrition:Eligible for care");
                        } else {
                            status.setText("condition:perfect health");
                            PD.getProgressDrawable().setColorFilter(
                                    getResources().getColor(R.color.colorGreen), PorterDuff.Mode.SRC_IN);
                        }

                    }
                    if (finalpatientbenefit.equals("Pregnant") || finalpatientbenefit.equals("Lactating")) {
                        if (dblLbs < 21.0) {
                            PD.getProgressDrawable().setColorFilter(
                                    getResources().getColor(R.color.colorRed), PorterDuff.Mode.SRC_IN);
                            status.setText("Severe malnutrition: Eligible for care");
                        } else if (dblLbs >= 21.0 && dblLbs < 23.0) {
                            PD.getProgressDrawable().setColorFilter(
                                    getResources().getColor(R.color.coloryellow), PorterDuff.Mode.SRC_IN);
                            status.setText("Acute Malnutrition:Eligible for care");
                        } else {
                            status.setText("condition:perfect health");
                            PD.getProgressDrawable().setColorFilter(
                                    getResources().getColor(R.color.colorGreen), PorterDuff.Mode.SRC_IN);
                        }

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        nextartho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String weight = weightt.getText().toString().trim();
                final String height = heightt.getText().toString().trim();
                final String maucc = muacc.getText().toString().trim();

                if (weight.isEmpty()) {
                    weightt.setError("weight is invalid");
                    weightt.requestFocus();
                    return;
                } else if (height.isEmpty()) {
                    heightt.setError("height is invalid");
                    heightt.requestFocus();
                    return;
                } else if (maucc.isEmpty()) {
                    muacc.setError("maucc is invalid");
                    muacc.requestFocus();
                    return;
                } else {
                    Bundle othercond = new Bundle();
                    othercond.putString("pname", finalpatient_name);
                    othercond.putString("pnametwo", finalpatient_nametwo);
                    othercond.putString("pphone", finalpatient_phone);
                    othercond.putString("ppidno", finalpatient_idno);
                    othercond.putString("pAge", finalpatientage);
                    othercond.putString("pgen", finalpatientgender);
                    othercond.putString("pbene", finalpatientbenefit);
                    othercond.putString("pphoto", finalpatientphoto);
                    othercond.putString("careno", finalcaregivernumber);
                    othercond.putString("carename", finalcaregivername);
                    othercond.putString("carenametwo", finalcaregivernametwo);
                    othercond.putString("carelocation", finalcaregiverlocation);
                    othercond.putString("carerel", finalcaregiverrelationship);
                    othercond.putString("careidno", finalcaregiveridno);
                    othercond.putString("weight", weight);
                    othercond.putString("height", height);
                    othercond.putString("muac", maucc);
                    Fragment fragment = new otherconditions();
                    fragment.setArguments(othercond);
                    FragmentManager manager = getFragmentManager();
                    manager.beginTransaction().replace(R.id.framelayout_index, fragment).addToBackStack(null).commit();
                }
            }
        });


        return arthroprometic;
    }

    public static boolean isphone(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.PHONE.matcher(target).matches();
    }

    private void zscorecalc(String height) {
        double target=Double.parseDouble(height);
        if((target-(int)target)==0.5){
            if(finalpatientgender.equals("male")) {
                if(finalpatientbenefit.equals("Child")){
                    double[] heightlist={65,65.5,66,66.5,67,67.5,68,68.5,69,69.5,70,70.5,71,71.5,72,72.5,73,73.5,74,74.5,75,75.5,76,76.5,77,77.5,78,78.5,79,79.5,80,80.5,81,81.5,82,82.5,
                            83,83.5,84,84.5,85,85.5,86,86.5,87,87.5,88,88.5,89,89.5,90,90.5,91,91.5,92,92.5,93,93.5,94,94.5,95,95.5,96,96.5,97,97.5,98,98.5,99,99.5,100,100.5,101,101.5,102,102.5,103,103.5,
                            104,104.5,105,105.5,106,106.5,107,107.5,108,108.5,109,109.5,110,110.5,111,111.5,112,112.5,113,113.5,114,114.5,115,115.5,116,116.5,117,117.5,118,118.5,119,119.5,120};
                    double[] boysmean={7.4327,7.5504,7.6673,7.7834,7.8986,8.0132,8.1272,8.241,8.3547,8.468,8.5808,8.6927,8.8036,8.9135,9.0221,9.1292,9.2347,9.339,9.442,9.5438,9.644,
                            9.7425,9.8392,9.9341,10.0274,10.1194,10.2105,10.3012,10.3923,10.4845,10.5781,10.6737,10.7718,10.8728,10.9772,11.0851,11.1966,11.3114,11.429,11.549,11.6707,11.7937,11.9173,12.0411,12.1645,
                            12.2871,12.4089,12.5298,12.6495,12.7683,12.8864,13.0038,13.1209,13.2376,13.3541,13.4705,13.587,13.7041,13.8217,13.9403,14.06,14.1811,14.3037,14.4282,14.5547,14.6832,14.814,
                            14.9468,15.0818,15.2187,15.3576,15.4985,15.6412,15.7857,15.932,16.0801,16.2298,16.3812,16.5342,16.6889,16.8454,17.0036,17.1637,17.3256,17.4894,17.655,17.8226,17.9924,18.1645,
                            18.339,18.5158,18.6948,18.8759,19.059,19.2439,19.4304,19.6185,19.8081,19.999,20.1912,20.3846,20.5789,20.7741,20.97,21.1666,21.3636,21.5611,21.7588,21.9568,22.1549,22.353};
                    double[] boysd={0.08217,0.08214,0.08212,0.08212,0.08213,0.08214,0.08217,0.08221,0.08226,0.08231,0.08237,0.08243,0.0825,0.08257,0.08264,0.08272,0.08278,0.08285,0.08292,
                            0.08298,0.08303,0.08308,0.08312,0.08315,0.08317,0.08318,0.08317,0.08315,0.08311,0.08305,0.08298,0.0829,0.08279,0.08268,0.08255,0.08241,0.08225,0.08209,0.08191,0.08174,0.08156,0.08138,
                            0.08121,0.08105,0.0809,0.08076,0.08064,0.08054,0.08045,0.08038,0.08032,0.08028,0.08025,0.08024,0.08025,0.08027,0.08031,0.08036,0.08043,0.08051,0.0806,0.08071,0.08083,0.08097,0.08112,
                            0.08129,0.08146,0.08165,0.08185,0.08206,0.08229,0.08252,0.08277,0.08302,0.08328,0.08354,0.08381,0.08408,0.08436,0.08464,0.08493,0.08521,0.08551,0.0858,0.08611,0.08641,0.08673,0.08704,
                            0.08736,0.08768,0.088,0.08832,0.08864,0.08896,0.08928,0.0896,0.08991,0.09022,0.09054,0.09085,0.09116,0.09147,0.09177,0.09208,0.09239,0.0927,0.093,0.09331,0.09362,0.09393,0.09424};
                    foundelement=false;
                               for( int index=0;index < heightlist.length ; index++){
                                   if(heightlist[index] == target){
                                       foundelement=true;
                                       Log.d(Tag, String.valueOf(target)+"index"+index);
                                       double z=(((target/10)-boysmean[index])/(boysd[index]*10));
                                       zstatus.setText(String.valueOf(String.format("%.4f",z)));
                                       	if(z > -2.0) {
                                           PD8.getProgressDrawable().setColorFilter(
                                                   getResources().getColor(R.color.colorGreen), PorterDuff.Mode.SRC_IN);
                                       } else if (z <= -2.0 && z > -3.0) {
                                           PD8.getProgressDrawable().setColorFilter(
                                                   getResources().getColor(R.color.coloryellow), PorterDuff.Mode.SRC_IN);
                                       } else if(z <= -3.0) {
                                           PD8.getProgressDrawable().setColorFilter(
                                                   getResources().getColor(R.color.colorRed), PorterDuff.Mode.SRC_IN);
                                       }
                                       break;
                                   }
                               }
                               if(!foundelement){
                                   Log.d(Tag, "not found"+target);
                               }
                }else{
                    zstatus.setText("not for "+finalpatientbenefit+" mothers");
                }
            }else{
                if(finalpatientbenefit.equals("Child")){
                    double[] heightlistgirls={65,65.5,66,66.5,67,67.5,68,68.5,69,69.5,70,70.5,71,71.5,72,72.5,73,73.5,74,74.5,75,75.5,76,76.5,77,77.5,78,78.5,79,79.5,80,80.5,81,81.5,82,82.5,
                            83,83.5,84,84.5,85,85.5,86,86.5,87,87.5,88,88.5,89,89.5,90,90.5,91,91.5,92,92.5,93,93.5,94,94.5,95,95.5,96,96.5,97,97.5,98,98.5,99,99.5,100,100.5,101,101.5,102,102.5,103,103.5,
                            104,104.5,105,105.5,106,106.5,107,107.5,108,108.5,109,109.5,110,110.5,111,111.5,112,112.5,113,113.5,114,114.5,115,115.5,116,116.5,117,117.5,118,118.5,119,119.5,120};
                    double[] girlsmean={7.2402,7.3523,7.463,7.5724,7.6806,7.7874,7.893,7.9976,8.1012,8.2039,8.3058,8.4071,8.5078,8.6078,8.707,8.8053,8.9025,8.9983,9.0928,9.1862,9.2786,9.3703,9.4617,9.5533,
                            9.6456,9.739,9.8338,9.9303,10.0289,10.1298,10.2332,10.3393,10.4477,10.5586,10.6719,10.7874,10.9051,11.0248,11.1462,11.2691,11.3934,11.5186,11.6444,11.7705,11.8965,12.0223,12.1478,12.2729,12.3976,
                            12.522,12.6461,12.77,12.8939,13.0177,13.1415,13.2654,13.3896,13.5142,13.6393,13.765,13.8914,14.0186,14.1466,14.2757,14.4059,14.5376,14.671,14.8062,14.9434,15.0828,15.2246,
                            15.3687,15.5154,15.6646,15.8164,15.9707,16.1276,16.287,16.4488,16.6131,16.78,16.9496,17.122,17.2973,17.4755,17.6567,17.8407,18.0277,18.2174,18.4096,18.6043,18.8015,19.0009,
                            19.2024,19.406,19.6116,19.819,20.028,20.2385,20.4502,20.6629,20.8766,21.0909,21.3059,21.5213,21.737,21.9529,22.169,22.3851,22.6012,22.8173};
                    double[] girlsd={0.09113,0.09109,0.09104,0.09099,0.09094,0.09088,0.09083,0.09077,0.09071,0.09065,0.09059,0.09053,0.09047,0.09041,0.09035,
                            0.09028,0.09022,0.09016,0.09009,0.09003,0.08996,0.08989,0.08983,0.08976,0.08969,0.08963,0.08956,0.0895,0.08943,0.08937,0.08932,0.08926,0.08921,0.08916,0.08912,0.08908,0.08905,0.08902,0.08899,
                            0.08897,0.08896,0.08895,0.08895,0.08895,0.08896,0.08897,0.08899,0.08901,0.08904,0.08907,0.08911,0.08915,0.0892,0.08925,0.08931,0.08937,0.08944,0.08951,0.08959,0.08967,0.08975,0.08984,0.08994,
                            0.09004,0.09015,0.09026,0.09037,0.09049,0.09062,0.09075,0.09088,0.09102,0.09116,0.09131,0.09146,0.09161,0.09177,0.09193,0.09209,0.09226,0.09243,0.09261,0.09278,0.09296,0.09315,0.09333,0.09352,
                            0.09371,0.0939,0.09409,0.09428,0.09448,0.09467,0.09487,0.09507,0.09527,0.09546,0.09566,0.09586,0.09606,0.09626,0.09646,0.09666,0.09686,0.09707,0.09727,0.09747,0.09767,0.09788,0.09808,0.09828};
                    foundelement=false;
                    for( int index=0;index < heightlistgirls.length ; index++){
                        if(heightlistgirls[index] == target){
                            foundelement=true;
                            Log.d(Tag, String.valueOf(target)+"index"+index);
                            double z=(((target/10)-girlsmean[index])/(girlsd[index]*10));
                            zstatus.setText(String.valueOf(String.format("%.4f",z)));
                            if(z > -2.0) {
                                PD8.getProgressDrawable().setColorFilter(
                                        getResources().getColor(R.color.colorGreen), PorterDuff.Mode.SRC_IN);
                            } else if (z <= -2.0 && z > -3.0) {
                                PD8.getProgressDrawable().setColorFilter(
                                        getResources().getColor(R.color.coloryellow), PorterDuff.Mode.SRC_IN);
                            } else if(z <= -3.0) {
                                PD8.getProgressDrawable().setColorFilter(
                                        getResources().getColor(R.color.colorRed), PorterDuff.Mode.SRC_IN);
                            }

                                break;
                        }
                    }
                    if(!foundelement){
                        Log.d(Tag, "not found"+target);
                    }
                }else{
                    zstatus.setText("not for "+ finalpatientbenefit+" mothers");
                }
            }
        }else {
            if ((target - (int) target) >= 0.5) {
                double targettwo = Math.ceil(target);
                if (finalpatientgender.equals("male")) {
                    if (finalpatientbenefit.equals("Child")) {
                        double[] heightlist = {65, 65.5, 66, 66.5, 67, 67.5, 68, 68.5, 69, 69.5, 70, 70.5, 71, 71.5, 72, 72.5, 73, 73.5, 74, 74.5, 75, 75.5, 76, 76.5, 77, 77.5, 78, 78.5, 79, 79.5, 80, 80.5, 81, 81.5, 82, 82.5,
                                83, 83.5, 84, 84.5, 85, 85.5, 86, 86.5, 87, 87.5, 88, 88.5, 89, 89.5, 90, 90.5, 91, 91.5, 92, 92.5, 93, 93.5, 94, 94.5, 95, 95.5, 96, 96.5, 97, 97.5, 98, 98.5, 99, 99.5, 100, 100.5, 101, 101.5, 102, 102.5, 103, 103.5,
                                104, 104.5, 105, 105.5, 106, 106.5, 107, 107.5, 108, 108.5, 109, 109.5, 110, 110.5, 111, 111.5, 112, 112.5, 113, 113.5, 114, 114.5, 115, 115.5, 116, 116.5, 117, 117.5, 118, 118.5, 119, 119.5, 120};
                        double[] boysmean = {7.4327, 7.5504, 7.6673, 7.7834, 7.8986, 8.0132, 8.1272, 8.241, 8.3547, 8.468, 8.5808, 8.6927, 8.8036, 8.9135, 9.0221, 9.1292, 9.2347, 9.339, 9.442, 9.5438, 9.644,
                                9.7425, 9.8392, 9.9341, 10.0274, 10.1194, 10.2105, 10.3012, 10.3923, 10.4845, 10.5781, 10.6737, 10.7718, 10.8728, 10.9772, 11.0851, 11.1966, 11.3114, 11.429, 11.549, 11.6707, 11.7937, 11.9173, 12.0411, 12.1645,
                                12.2871, 12.4089, 12.5298, 12.6495, 12.7683, 12.8864, 13.0038, 13.1209, 13.2376, 13.3541, 13.4705, 13.587, 13.7041, 13.8217, 13.9403, 14.06, 14.1811, 14.3037, 14.4282, 14.5547, 14.6832, 14.814,
                                14.9468, 15.0818, 15.2187, 15.3576, 15.4985, 15.6412, 15.7857, 15.932, 16.0801, 16.2298, 16.3812, 16.5342, 16.6889, 16.8454, 17.0036, 17.1637, 17.3256, 17.4894, 17.655, 17.8226, 17.9924, 18.1645,
                                18.339, 18.5158, 18.6948, 18.8759, 19.059, 19.2439, 19.4304, 19.6185, 19.8081, 19.999, 20.1912, 20.3846, 20.5789, 20.7741, 20.97, 21.1666, 21.3636, 21.5611, 21.7588, 21.9568, 22.1549, 22.353};
                        double[] boysd = {0.08217, 0.08214, 0.08212, 0.08212, 0.08213, 0.08214, 0.08217, 0.08221, 0.08226, 0.08231, 0.08237, 0.08243, 0.0825, 0.08257, 0.08264, 0.08272, 0.08278, 0.08285, 0.08292,
                                0.08298, 0.08303, 0.08308, 0.08312, 0.08315, 0.08317, 0.08318, 0.08317, 0.08315, 0.08311, 0.08305, 0.08298, 0.0829, 0.08279, 0.08268, 0.08255, 0.08241, 0.08225, 0.08209, 0.08191, 0.08174, 0.08156, 0.08138,
                                0.08121, 0.08105, 0.0809, 0.08076, 0.08064, 0.08054, 0.08045, 0.08038, 0.08032, 0.08028, 0.08025, 0.08024, 0.08025, 0.08027, 0.08031, 0.08036, 0.08043, 0.08051, 0.0806, 0.08071, 0.08083, 0.08097, 0.08112,
                                0.08129, 0.08146, 0.08165, 0.08185, 0.08206, 0.08229, 0.08252, 0.08277, 0.08302, 0.08328, 0.08354, 0.08381, 0.08408, 0.08436, 0.08464, 0.08493, 0.08521, 0.08551, 0.0858, 0.08611, 0.08641, 0.08673, 0.08704,
                                0.08736, 0.08768, 0.088, 0.08832, 0.08864, 0.08896, 0.08928, 0.0896, 0.08991, 0.09022, 0.09054, 0.09085, 0.09116, 0.09147, 0.09177, 0.09208, 0.09239, 0.0927, 0.093, 0.09331, 0.09362, 0.09393, 0.09424};
                        foundelement = false;
                        for (int index = 0; index < heightlist.length; index++) {
                            if (heightlist[index] == targettwo) {
                                foundelement = true;
                                Log.d(Tag, String.valueOf(targettwo) + "index" + index);
                                double z = (((targettwo / 10) - boysmean[index]) / (boysd[index] * 10));
                                zstatus.setText(String.valueOf(String.format("%.4f", z)));
                                if(z > -2.0) {
                                    PD8.getProgressDrawable().setColorFilter(
                                            getResources().getColor(R.color.colorGreen), PorterDuff.Mode.SRC_IN);
                                } else if (z <= -2.0 && z > -3.0) {
                                    PD8.getProgressDrawable().setColorFilter(
                                            getResources().getColor(R.color.coloryellow), PorterDuff.Mode.SRC_IN);
                                } else if(z <= -3.0) {
                                    PD8.getProgressDrawable().setColorFilter(
                                            getResources().getColor(R.color.colorRed), PorterDuff.Mode.SRC_IN);
                                }
                                break;
                            }
                        }
                        if (!foundelement) {
                            Log.d(Tag, "not found" + targettwo);
                        }
                    } else {
                        zstatus.setText("not for " + finalpatientbenefit + " mothers");
                    }
                } else {
                    if (finalpatientbenefit.equals("Child")) {
                        double[] heightlistgirls = {65, 65.5, 66, 66.5, 67, 67.5, 68, 68.5, 69, 69.5, 70, 70.5, 71, 71.5, 72, 72.5, 73, 73.5, 74, 74.5, 75, 75.5, 76, 76.5, 77, 77.5, 78, 78.5, 79, 79.5, 80, 80.5, 81, 81.5, 82, 82.5,
                                83, 83.5, 84, 84.5, 85, 85.5, 86, 86.5, 87, 87.5, 88, 88.5, 89, 89.5, 90, 90.5, 91, 91.5, 92, 92.5, 93, 93.5, 94, 94.5, 95, 95.5, 96, 96.5, 97, 97.5, 98, 98.5, 99, 99.5, 100, 100.5, 101, 101.5, 102, 102.5, 103, 103.5,
                                104, 104.5, 105, 105.5, 106, 106.5, 107, 107.5, 108, 108.5, 109, 109.5, 110, 110.5, 111, 111.5, 112, 112.5, 113, 113.5, 114, 114.5, 115, 115.5, 116, 116.5, 117, 117.5, 118, 118.5, 119, 119.5, 120};
                        double[] girlsmean = {7.2402, 7.3523, 7.463, 7.5724, 7.6806, 7.7874, 7.893, 7.9976, 8.1012, 8.2039, 8.3058, 8.4071, 8.5078, 8.6078, 8.707, 8.8053, 8.9025, 8.9983, 9.0928, 9.1862, 9.2786, 9.3703, 9.4617, 9.5533,
                                9.6456, 9.739, 9.8338, 9.9303, 10.0289, 10.1298, 10.2332, 10.3393, 10.4477, 10.5586, 10.6719, 10.7874, 10.9051, 11.0248, 11.1462, 11.2691, 11.3934, 11.5186, 11.6444, 11.7705, 11.8965, 12.0223, 12.1478, 12.2729, 12.3976,
                                12.522, 12.6461, 12.77, 12.8939, 13.0177, 13.1415, 13.2654, 13.3896, 13.5142, 13.6393, 13.765, 13.8914, 14.0186, 14.1466, 14.2757, 14.4059, 14.5376, 14.671, 14.8062, 14.9434, 15.0828, 15.2246,
                                15.3687, 15.5154, 15.6646, 15.8164, 15.9707, 16.1276, 16.287, 16.4488, 16.6131, 16.78, 16.9496, 17.122, 17.2973, 17.4755, 17.6567, 17.8407, 18.0277, 18.2174, 18.4096, 18.6043, 18.8015, 19.0009,
                                19.2024, 19.406, 19.6116, 19.819, 20.028, 20.2385, 20.4502, 20.6629, 20.8766, 21.0909, 21.3059, 21.5213, 21.737, 21.9529, 22.169, 22.3851, 22.6012, 22.8173};
                        double[] girlsd = {0.09113, 0.09109, 0.09104, 0.09099, 0.09094, 0.09088, 0.09083, 0.09077, 0.09071, 0.09065, 0.09059, 0.09053, 0.09047, 0.09041, 0.09035,
                                0.09028, 0.09022, 0.09016, 0.09009, 0.09003, 0.08996, 0.08989, 0.08983, 0.08976, 0.08969, 0.08963, 0.08956, 0.0895, 0.08943, 0.08937, 0.08932, 0.08926, 0.08921, 0.08916, 0.08912, 0.08908, 0.08905, 0.08902, 0.08899,
                                0.08897, 0.08896, 0.08895, 0.08895, 0.08895, 0.08896, 0.08897, 0.08899, 0.08901, 0.08904, 0.08907, 0.08911, 0.08915, 0.0892, 0.08925, 0.08931, 0.08937, 0.08944, 0.08951, 0.08959, 0.08967, 0.08975, 0.08984, 0.08994,
                                0.09004, 0.09015, 0.09026, 0.09037, 0.09049, 0.09062, 0.09075, 0.09088, 0.09102, 0.09116, 0.09131, 0.09146, 0.09161, 0.09177, 0.09193, 0.09209, 0.09226, 0.09243, 0.09261, 0.09278, 0.09296, 0.09315, 0.09333, 0.09352,
                                0.09371, 0.0939, 0.09409, 0.09428, 0.09448, 0.09467, 0.09487, 0.09507, 0.09527, 0.09546, 0.09566, 0.09586, 0.09606, 0.09626, 0.09646, 0.09666, 0.09686, 0.09707, 0.09727, 0.09747, 0.09767, 0.09788, 0.09808, 0.09828};
                        foundelement = false;
                        for (int index = 0; index < heightlistgirls.length; index++) {
                            if (heightlistgirls[index] == targettwo) {
                                foundelement = true;
                                Log.d(Tag, String.valueOf(targettwo) + "index" + index);
                                double z = (((targettwo / 10) - girlsmean[index]) / (girlsd[index] * 10));
                                zstatus.setText(String.valueOf(String.format("%.4f", z)));
                                if(z > -2.0) {
                                    PD8.getProgressDrawable().setColorFilter(
                                            getResources().getColor(R.color.colorGreen), PorterDuff.Mode.SRC_IN);
                                } else if (z <= -2.0 && z > -3.0) {
                                    PD8.getProgressDrawable().setColorFilter(
                                            getResources().getColor(R.color.coloryellow), PorterDuff.Mode.SRC_IN);
                                } else if(z <= -3.0) {
                                    PD8.getProgressDrawable().setColorFilter(
                                            getResources().getColor(R.color.colorRed), PorterDuff.Mode.SRC_IN);
                                }
                                break;
                            }
                        }
                        if (!foundelement) {
                            Log.d(Tag, "not found" + targettwo);
                        }
                    } else {
                        zstatus.setText("not for " + finalpatientbenefit + " mothers");
                    }
                }
            }else if((target-(int)target)<=0.5){
                double targettwo = Math.floor(target);
                if (finalpatientgender.equals("male")) {
                    if (finalpatientbenefit.equals("Child")) {
                        double[] heightlist = {65, 65.5, 66, 66.5, 67, 67.5, 68, 68.5, 69, 69.5, 70, 70.5, 71, 71.5, 72, 72.5, 73, 73.5, 74, 74.5, 75, 75.5, 76, 76.5, 77, 77.5, 78, 78.5, 79, 79.5, 80, 80.5, 81, 81.5, 82, 82.5,
                                83, 83.5, 84, 84.5, 85, 85.5, 86, 86.5, 87, 87.5, 88, 88.5, 89, 89.5, 90, 90.5, 91, 91.5, 92, 92.5, 93, 93.5, 94, 94.5, 95, 95.5, 96, 96.5, 97, 97.5, 98, 98.5, 99, 99.5, 100, 100.5, 101, 101.5, 102, 102.5, 103, 103.5,
                                104, 104.5, 105, 105.5, 106, 106.5, 107, 107.5, 108, 108.5, 109, 109.5, 110, 110.5, 111, 111.5, 112, 112.5, 113, 113.5, 114, 114.5, 115, 115.5, 116, 116.5, 117, 117.5, 118, 118.5, 119, 119.5, 120};
                        double[] boysmean = {7.4327, 7.5504, 7.6673, 7.7834, 7.8986, 8.0132, 8.1272, 8.241, 8.3547, 8.468, 8.5808, 8.6927, 8.8036, 8.9135, 9.0221, 9.1292, 9.2347, 9.339, 9.442, 9.5438, 9.644,
                                9.7425, 9.8392, 9.9341, 10.0274, 10.1194, 10.2105, 10.3012, 10.3923, 10.4845, 10.5781, 10.6737, 10.7718, 10.8728, 10.9772, 11.0851, 11.1966, 11.3114, 11.429, 11.549, 11.6707, 11.7937, 11.9173, 12.0411, 12.1645,
                                12.2871, 12.4089, 12.5298, 12.6495, 12.7683, 12.8864, 13.0038, 13.1209, 13.2376, 13.3541, 13.4705, 13.587, 13.7041, 13.8217, 13.9403, 14.06, 14.1811, 14.3037, 14.4282, 14.5547, 14.6832, 14.814,
                                14.9468, 15.0818, 15.2187, 15.3576, 15.4985, 15.6412, 15.7857, 15.932, 16.0801, 16.2298, 16.3812, 16.5342, 16.6889, 16.8454, 17.0036, 17.1637, 17.3256, 17.4894, 17.655, 17.8226, 17.9924, 18.1645,
                                18.339, 18.5158, 18.6948, 18.8759, 19.059, 19.2439, 19.4304, 19.6185, 19.8081, 19.999, 20.1912, 20.3846, 20.5789, 20.7741, 20.97, 21.1666, 21.3636, 21.5611, 21.7588, 21.9568, 22.1549, 22.353};
                        double[] boysd = {0.08217, 0.08214, 0.08212, 0.08212, 0.08213, 0.08214, 0.08217, 0.08221, 0.08226, 0.08231, 0.08237, 0.08243, 0.0825, 0.08257, 0.08264, 0.08272, 0.08278, 0.08285, 0.08292,
                                0.08298, 0.08303, 0.08308, 0.08312, 0.08315, 0.08317, 0.08318, 0.08317, 0.08315, 0.08311, 0.08305, 0.08298, 0.0829, 0.08279, 0.08268, 0.08255, 0.08241, 0.08225, 0.08209, 0.08191, 0.08174, 0.08156, 0.08138,
                                0.08121, 0.08105, 0.0809, 0.08076, 0.08064, 0.08054, 0.08045, 0.08038, 0.08032, 0.08028, 0.08025, 0.08024, 0.08025, 0.08027, 0.08031, 0.08036, 0.08043, 0.08051, 0.0806, 0.08071, 0.08083, 0.08097, 0.08112,
                                0.08129, 0.08146, 0.08165, 0.08185, 0.08206, 0.08229, 0.08252, 0.08277, 0.08302, 0.08328, 0.08354, 0.08381, 0.08408, 0.08436, 0.08464, 0.08493, 0.08521, 0.08551, 0.0858, 0.08611, 0.08641, 0.08673, 0.08704,
                                0.08736, 0.08768, 0.088, 0.08832, 0.08864, 0.08896, 0.08928, 0.0896, 0.08991, 0.09022, 0.09054, 0.09085, 0.09116, 0.09147, 0.09177, 0.09208, 0.09239, 0.0927, 0.093, 0.09331, 0.09362, 0.09393, 0.09424};
                        foundelement = false;
                        for (int index = 0; index < heightlist.length; index++) {
                            if (heightlist[index] == targettwo) {
                                foundelement = true;
                                Log.d(Tag, String.valueOf(targettwo) + "index" + index);
                                double z = (((targettwo / 10) - boysmean[index]) / (boysd[index] * 10));
                                zstatus.setText(String.valueOf(String.format("%.4f", z)));
                                if(z > -2.0) {
                                    PD8.getProgressDrawable().setColorFilter(
                                            getResources().getColor(R.color.colorGreen), PorterDuff.Mode.SRC_IN);
                                } else if (z <= -2.0 && z > -3.0) {
                                    PD8.getProgressDrawable().setColorFilter(
                                            getResources().getColor(R.color.coloryellow), PorterDuff.Mode.SRC_IN);
                                } else if(z <= -3.0) {
                                    PD8.getProgressDrawable().setColorFilter(
                                            getResources().getColor(R.color.colorRed), PorterDuff.Mode.SRC_IN);
                                }
                                break;
                            }
                        }
                        if (!foundelement) {
                            Log.d(Tag, "not found" + targettwo);
                        }
                    } else {
                        zstatus.setText("not for " + finalpatientbenefit + " mothers");
                    }
                } else {
                    if (finalpatientbenefit.equals("Child")) {
                        double[] heightlistgirls = {65, 65.5, 66, 66.5, 67, 67.5, 68, 68.5, 69, 69.5, 70, 70.5, 71, 71.5, 72, 72.5, 73, 73.5, 74, 74.5, 75, 75.5, 76, 76.5, 77, 77.5, 78, 78.5, 79, 79.5, 80, 80.5, 81, 81.5, 82, 82.5,
                                83, 83.5, 84, 84.5, 85, 85.5, 86, 86.5, 87, 87.5, 88, 88.5, 89, 89.5, 90, 90.5, 91, 91.5, 92, 92.5, 93, 93.5, 94, 94.5, 95, 95.5, 96, 96.5, 97, 97.5, 98, 98.5, 99, 99.5, 100, 100.5, 101, 101.5, 102, 102.5, 103, 103.5,
                                104, 104.5, 105, 105.5, 106, 106.5, 107, 107.5, 108, 108.5, 109, 109.5, 110, 110.5, 111, 111.5, 112, 112.5, 113, 113.5, 114, 114.5, 115, 115.5, 116, 116.5, 117, 117.5, 118, 118.5, 119, 119.5, 120};
                        double[] girlsmean = {7.2402, 7.3523, 7.463, 7.5724, 7.6806, 7.7874, 7.893, 7.9976, 8.1012, 8.2039, 8.3058, 8.4071, 8.5078, 8.6078, 8.707, 8.8053, 8.9025, 8.9983, 9.0928, 9.1862, 9.2786, 9.3703, 9.4617, 9.5533,
                                9.6456, 9.739, 9.8338, 9.9303, 10.0289, 10.1298, 10.2332, 10.3393, 10.4477, 10.5586, 10.6719, 10.7874, 10.9051, 11.0248, 11.1462, 11.2691, 11.3934, 11.5186, 11.6444, 11.7705, 11.8965, 12.0223, 12.1478, 12.2729, 12.3976,
                                12.522, 12.6461, 12.77, 12.8939, 13.0177, 13.1415, 13.2654, 13.3896, 13.5142, 13.6393, 13.765, 13.8914, 14.0186, 14.1466, 14.2757, 14.4059, 14.5376, 14.671, 14.8062, 14.9434, 15.0828, 15.2246,
                                15.3687, 15.5154, 15.6646, 15.8164, 15.9707, 16.1276, 16.287, 16.4488, 16.6131, 16.78, 16.9496, 17.122, 17.2973, 17.4755, 17.6567, 17.8407, 18.0277, 18.2174, 18.4096, 18.6043, 18.8015, 19.0009,
                                19.2024, 19.406, 19.6116, 19.819, 20.028, 20.2385, 20.4502, 20.6629, 20.8766, 21.0909, 21.3059, 21.5213, 21.737, 21.9529, 22.169, 22.3851, 22.6012, 22.8173};
                        double[] girlsd = {0.09113, 0.09109, 0.09104, 0.09099, 0.09094, 0.09088, 0.09083, 0.09077, 0.09071, 0.09065, 0.09059, 0.09053, 0.09047, 0.09041, 0.09035,
                                0.09028, 0.09022, 0.09016, 0.09009, 0.09003, 0.08996, 0.08989, 0.08983, 0.08976, 0.08969, 0.08963, 0.08956, 0.0895, 0.08943, 0.08937, 0.08932, 0.08926, 0.08921, 0.08916, 0.08912, 0.08908, 0.08905, 0.08902, 0.08899,
                                0.08897, 0.08896, 0.08895, 0.08895, 0.08895, 0.08896, 0.08897, 0.08899, 0.08901, 0.08904, 0.08907, 0.08911, 0.08915, 0.0892, 0.08925, 0.08931, 0.08937, 0.08944, 0.08951, 0.08959, 0.08967, 0.08975, 0.08984, 0.08994,
                                0.09004, 0.09015, 0.09026, 0.09037, 0.09049, 0.09062, 0.09075, 0.09088, 0.09102, 0.09116, 0.09131, 0.09146, 0.09161, 0.09177, 0.09193, 0.09209, 0.09226, 0.09243, 0.09261, 0.09278, 0.09296, 0.09315, 0.09333, 0.09352,
                                0.09371, 0.0939, 0.09409, 0.09428, 0.09448, 0.09467, 0.09487, 0.09507, 0.09527, 0.09546, 0.09566, 0.09586, 0.09606, 0.09626, 0.09646, 0.09666, 0.09686, 0.09707, 0.09727, 0.09747, 0.09767, 0.09788, 0.09808, 0.09828};
                        foundelement = false;
                        for (int index = 0; index < heightlistgirls.length; index++) {
                            if (heightlistgirls[index] == targettwo) {
                                foundelement = true;
                                Log.d(Tag, String.valueOf(targettwo) + "index" + index);
                                double z = (((targettwo / 10) - girlsmean[index]) / (girlsd[index] * 10));
                                zstatus.setText(String.valueOf(String.format("%.4f", z)));
                                if(z > -2.0) {
                                    PD8.getProgressDrawable().setColorFilter(
                                            getResources().getColor(R.color.colorGreen), PorterDuff.Mode.SRC_IN);
                                } else if (z <= -2.0 && z > -3.0) {
                                    PD8.getProgressDrawable().setColorFilter(
                                            getResources().getColor(R.color.coloryellow), PorterDuff.Mode.SRC_IN);
                                } else if(z <= -3.0) {
                                    PD8.getProgressDrawable().setColorFilter(
                                            getResources().getColor(R.color.colorRed), PorterDuff.Mode.SRC_IN);
                                }
                                break;
                            }
                        }
                        if (!foundelement) {
                            Log.d(Tag, "not found" + targettwo);
                        }
                    } else {
                        zstatus.setText("not for " + finalpatientbenefit + " mothers");
                    }
                }
            }
        }
    }

    boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

