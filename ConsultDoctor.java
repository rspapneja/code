package com.example.shivam.patientapp.bookAnAppointment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shivam.patientapp.common.Constant;
import com.example.shivam.patientapp.common.Global1;
import com.example.shivam.patientapp.common.ImageUtility;
import com.example.shivam.patientapp.common.PermissionFile;
import com.example.shivam.patientapp.preferences.SharedPreferenceManager;
import com.example.shivam.patientapp.R;
import com.example.shivam.patientapp.tabLayout.TabActivity;
import com.example.shivam.patientapp.apiBase.ApiClient;
import com.example.shivam.patientapp.apiBase.ApiInterface;
import com.example.shivam.patientapp.doctor_schudle.Constants;
import com.example.shivam.patientapp.doctor_schudle.L;
import com.example.shivam.patientapp.doctor_schudle.VolleySingleton;
import com.example.shivam.patientapp.model.SucessModel;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;


public class  ConsultDoctor extends AppCompatActivity implements View.OnClickListener/*,ResponseListener*/{

    private EditText message1;
    private ImageView video,voice,text,back_icon;
    private TextView header_text;
    private Intent intent;
    private final String TAG=ConsultDoctor.class.getSimpleName();
    private VolleySingleton volleySingleton;
    private SharedPreferenceManager sharedPreferenceManager;
    private Context context=this;
    private String mFilePath,mFileEducation;
    private String doctorID,startTime,EndTime,consulationDate,amount;
    private Button upload_attachment;
    private Uri mImageUrl;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private Button confirm_booking;
    private int SELECT_FILE_EDUCATION=103;
    private VolleySingleton mVolleySingleton;

    String selected_communication_medium1="1";

    // Ravinder

    String  doctorImage="",doctorRating="",mDoctorName="";

    PermissionFile permissionFile;
    File destination;
    Uri outputFileUri;

    ImageUtility imageUtility;

    String licenseFile = "";

    ImageView upload_image;

    Call<SucessModel> call;

    TextView  rating,tvDoctorName,date,time;
    CircleImageView ivDoctorImage;
    RequestBody imageToUpload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_doctor);

        permissionFile = new PermissionFile(this);
        imageUtility=new ImageUtility(this);
        multiplePermission();

        intent=getIntent();
        intent.getExtras();


        header_text=(TextView)findViewById(R.id.text);
        header_text.setText("Book an Appointment");
        back_icon=(ImageView)findViewById(R.id.back_icon);

        back_icon.setVisibility(View.VISIBLE);

        upload_attachment=(Button) findViewById(R.id.upload_attachment);
        upload_attachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProfileImageEducation();
            }
        });
        upload_attachment.setVisibility(View.GONE                           );
        upload_image=(ImageView)findViewById(R.id.upload_image);

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setProfileImageEducation();

            }
        });


        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,DoctorSchudle.class);
                startActivity(intent);
            }
        });

        sharedPreferenceManager=new SharedPreferenceManager(context);

        volleySingleton=VolleySingleton.getInstance();

        doctorID=intent.getBundleExtra("bundle").getString(Constants.KEY_ID);
        consulationDate=intent.getBundleExtra("bundle").getString("consultation_date");
        startTime=intent.getBundleExtra("bundle").getString("start_time");
        EndTime=intent.getBundleExtra("bundle").getString("end_time");
        amount=intent.getBundleExtra("bundle").getString("cost");

        Log.e("bundle is",""+intent.getBundleExtra("bundle").getString(Constants.KEY_ID));

        initViews();
        makeButtonsClickable();
        getDoctorDetails();
    }

    private void makeButtonsClickable() {

        confirm_booking.setOnClickListener(this);
        video.setOnClickListener(this);
        voice.setOnClickListener(this);
        text.setOnClickListener(this);
    }

    private void initViews() {

        message1=(EditText)findViewById(R.id.message);
        video=(ImageView)findViewById(R.id.video);
        text=(ImageView)findViewById(R.id.text_chat);
        voice=(ImageView)findViewById(R.id.voice);

        confirm_booking=(Button)findViewById(R.id.confirm_booking);

        rating=(TextView) findViewById(R.id.rating);
        tvDoctorName=(TextView)findViewById(R.id.tvDoctorName);
        date=(TextView)findViewById(R.id.date);
        time=(TextView)findViewById(R.id.time);
        ivDoctorImage=(CircleImageView) findViewById(R.id.ivDoctorImage);

        consulationDate=intent.getBundleExtra("bundle").getString("consultation_date");
        startTime=intent.getBundleExtra("bundle").getString("start_time");
        EndTime=intent.getBundleExtra("bundle").getString("end_time");
        amount=intent.getBundleExtra("bundle").getString("cost");

        date.setText(consulationDate);
        time.setText(startTime+" - "+EndTime);
    }
    void multiplePermission() {

        if (!permissionFile.checkLocStorgePermission(this)) {
            permissionFile.checkLocStorgePermission(this);
        }
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.confirm_booking:

               L.showToast(ConsultDoctor.this,"Test");


               if(message1.getText().toString().trim().equals(""))
               {
                   L.showToast(ConsultDoctor.this,"Please Enter Message");


               }else
               {
                   bookAppointment(licenseFile);
               }
                break;
            case R.id.text_chat:
                video.setImageResource(R.drawable.videodisable_grey);
                text.setImageResource(R.drawable.message_enable_blue);
                voice.setImageResource(R.drawable.calldisable);
                selected_communication_medium1="1";
                break;
            case R.id.video:
                video.setImageResource(R.drawable.video_enable);
                text.setImageResource(R.drawable.message_disable_grey);
                voice.setImageResource(R.drawable.calldisable);
                selected_communication_medium1="3";
                break;
            case R.id.voice:
                video.setImageResource(R.drawable.videodisable_grey);
                text.setImageResource(R.drawable.message_disable_grey);
                voice.setImageResource(R.drawable.callenable);
                selected_communication_medium1="2";
                break;
        }
    }

    public void setProfileImageEducation() {
        final CharSequence[] items = {"Take Photo","Gallery","Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ConsultDoctor.this);
        builder.setTitle("Patient App");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {


                    openCamera();


                } if (items[item].equals("Gallery")) {

                    pickGallery();


                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void openCamera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final File root = permissionFile.getFile();
        root.mkdirs();
        String filename = permissionFile.getUniqueImageFilename();
        destination = new File(root, filename);
        outputFileUri= FileProvider.getUriForFile(
                this,
                getApplicationContext()
                        .getPackageName() + ".provider", destination);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, Global1.CameraPicker);

    }
    public void pickGallery() {
      Intent pickIntent = new Intent(Intent.ACTION_PICK);
      pickIntent.setType("image/*");
      startActivityForResult(pickIntent, Global1.GalleryPicker);
  }
        /*this is used to fetch doctor details*/

    public void getDoctorDetails() {
        L.showProgresssBar(context);
        StringRequest myReq = new StringRequest(Request.Method.POST,
                Constant.WEB_URL +"get-doctor-profile1",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        L.hideProgressBar(context);
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("success") && jsonObject.getString("status_code").equals("200")) {

                                JSONObject result = jsonObject.getJSONObject("result");
                                tvDoctorName.setText(result.getString("doctor_name"));
                                doctorImage= result.getString("profile_image");
                                Picasso.with(ConsultDoctor.this).load(result.getString("profile_image")).into(ivDoctorImage);

                                amount=result.getString("amount");
                                confirm_booking.setText("Pay $ "+amount);
                                Log.e("amount","amount"+amount);
                                rating.setText(result.getString("rating"));

                            }
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        L.showToast(getApplicationContext(), "error");
                        L.hideProgressBar(context);

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("doctor_id", doctorID);
                params.put("user_id",sharedPreferenceManager.getValue(SharedPreferenceManager.USER_ID) );
                params.put("token", sharedPreferenceManager.getValue(SharedPreferenceManager.TOKEN));

                Log.e("params are", "" + params);
                return params;
            }
        };
        //add request to volley queue
        VolleySingleton.getInstance().addToRequestQueue(myReq, TAG);
    }
// end
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {

            if (requestCode == Global1.GalleryPicker) {
                onCaptureImageResult(data, "gallery");
            }
        } else if (resultCode == RESULT_OK && requestCode == Global1.CameraPicker) {
            onCaptureImageResult(data, "camera");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case Global1.REQUEST_CODE_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // logInApp();
                }
                break;

            case Global1.REQUEST_CODE_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    //sessionManager.selectImage(getActivity(), this, getString(R.string.RegisterScreen));
                }
                break;

            // mapReady();

        }


    }


    private void onCaptureImageResult(Intent data, String imageType) {

        Bitmap thumbnail = null;
        try {
            if (imageType.equals("camera")) {
                licenseFile = imageUtility.compressImage(destination.getPath());
            } else {
                licenseFile = imageUtility.compressImage(imageUtility.getRealPathFromURI(this, data.getData()));
            }
            Log.e("licenseFile", licenseFile);

            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(destination.getAbsolutePath(),bmOptions);
         //   bitmap = Bitmap.createScaledBitmap(bitmap,parent.getWidth(),parent.getHeight(),true);
            upload_image.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void bookAppointment( String license_image) {
        String splittedAmount[]=amount.split(" ");
        Log.e("check","check");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        if(license_image.equals(""))
        {}
        else {
             imageToUpload = RequestBody.create(MediaType.parse("image/jpg"), new File(license_image));
        }
/*        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), "27");
        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), "2746efe3372f40289099537c00e19b734a30966b6719f6a60a814781f5692e374ac5cdf26659a792");
        RequestBody patient_id = RequestBody.create(MediaType.parse("text/plain"), "27");
        RequestBody doctor_id = RequestBody.create(MediaType.parse("text/plain"), "5");
        RequestBody consultation_date = RequestBody.create(MediaType.parse("text/plain"), "27-07-2017");
        RequestBody start_time = RequestBody.create(MediaType.parse("text/plain"), "9:00");
        RequestBody end_time = RequestBody.create(MediaType.parse("text/plain"), "9:20");
        RequestBody message = RequestBody.create(MediaType.parse("text/plain"), "afd");
        RequestBody isPaid = RequestBody.create(MediaType.parse("text/plain"), "1");
        RequestBody amount = RequestBody.create(MediaType.parse("text/plain"), "32");
        RequestBody payment_method = RequestBody.create(MediaType.parse("text/plain"), "234");
        RequestBody selected_communication_medium = RequestBody.create(MediaType.parse("text/plain"), "1");*/

        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), sharedPreferenceManager.getValue(SharedPreferenceManager.USER_ID));
        RequestBody token = RequestBody.create(MediaType.parse("text/plain"),sharedPreferenceManager.getValue(SharedPreferenceManager.TOKEN) );
        RequestBody patient_id = RequestBody.create(MediaType.parse("text/plain"), sharedPreferenceManager.getValue(SharedPreferenceManager.USER_ID));
        RequestBody doctor_id = RequestBody.create(MediaType.parse("text/plain"), doctorID);
        RequestBody consultation_date = RequestBody.create(MediaType.parse("text/plain"), consulationDate);
        RequestBody start_time = RequestBody.create(MediaType.parse("text/plain"), startTime);
        RequestBody end_time = RequestBody.create(MediaType.parse("text/plain"), EndTime);
        RequestBody message = RequestBody.create(MediaType.parse("text/plain"), message1.getText().toString());
        RequestBody isPaid = RequestBody.create(MediaType.parse("text/plain"), "1");
        RequestBody amount1 = RequestBody.create(MediaType.parse("text/plain"), amount);
        RequestBody payment_method = RequestBody.create(MediaType.parse("text/plain"), "1");
        RequestBody selected_communication_medium = RequestBody.create(MediaType.parse("text/plain"),selected_communication_medium1);

try {
    Log.e("user_id", "user_id" + sharedPreferenceManager.getValue(SharedPreferenceManager.USER_ID));
    Log.e("token", "token" + sharedPreferenceManager.getValue(SharedPreferenceManager.TOKEN));
    Log.e("patient_id", "patient_id" + sharedPreferenceManager.getValue(SharedPreferenceManager.USER_ID));
    Log.e("doctor_id", "doctor_id" + doctorID);
    Log.e("consultation_date", "consultation_date" + consulationDate);
    Log.e("start_time", "start_time" + startTime);
    Log.e("end_time", "end_time" + EndTime);
    Log.e("message", "message" + message1.getText().toString());
    Log.e("isPaid", "isPaid" + "1");
    Log.e("amount", "amount" + amount);
    Log.e("payment_method", "payment_method" + "1");
    Log.e("selected_commu_medium", "selected_commu_medium" + selected_communication_medium);

}
catch (Exception e)
{

}
        if(license_image.equals(""))
        {
            call = apiService.signup1(user_id, token, patient_id, doctor_id, consultation_date, start_time, end_time,message
                    ,isPaid,amount1,payment_method,selected_communication_medium);
        }
        else {
             call = apiService.signup(imageToUpload, user_id, token, patient_id, doctor_id, consultation_date, start_time, end_time,message
                    ,isPaid,amount1,payment_method,selected_communication_medium);
        }
        L.showProgresssBar(context);

        call.enqueue(new Callback<SucessModel>() {
            @Override
            public void onResponse(Call<SucessModel> call, retrofit2.Response<SucessModel> response) {

                if(response.body().getStatus_code()==200)
                {
                    L.hideProgressBar(context);

                    L.showToast(getApplicationContext(),"Appointment Created Successfully");
                    Intent intent=new Intent(context,TabActivity.class);
                    intent.putExtra("upcoming","upcoming");
                    startActivity(intent);
                    finish();
                }
                else
                {


                }
            }

            @Override
            public void onFailure(Call<SucessModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("log", t.toString());
            }
        });
    }
}


