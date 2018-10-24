package com.example.ajit.imageupload;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ajit.imageupload.apiBase.ApiClient;
import com.example.ajit.imageupload.apiBase.ApiInterface;

import com.example.ajit.imageupload.model.SucessModel;
import com.example.ajit.imageupload.util.Global;
import com.example.ajit.imageupload.util.ImageUtility;
import com.example.ajit.imageupload.util.PermissionFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.ic_camera)
    Button icCamera;
    @BindView(R.id.ic_gallery)
    Button icGallery;

    PermissionFile permissionFile;
    File destination;
    Uri outputFileUri;

    ImageUtility imageUtility;

    String licenseFile = "";
    @BindView(R.id.registerImage)
    ImageView registerImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        permissionFile = new PermissionFile(this);
        imageUtility=new ImageUtility(this);
        multiplePermission();
    }

    @OnClick({R.id.ic_camera, R.id.ic_gallery})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_camera:
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
                startActivityForResult(intent, Global.CameraPicker);
                break;
            case R.id.ic_gallery:
                Intent pickIntent = new Intent(Intent.ACTION_PICK);
                pickIntent.setType("image/*");
                startActivityForResult(pickIntent, Global.GalleryPicker);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && data != null && data.getData() != null) {

            if (requestCode == Global.GalleryPicker) {
                onCaptureImageResult(data, "gallery");
            }
        } else if (resultCode == RESULT_OK && requestCode == Global.CameraPicker) {
            onCaptureImageResult(data, "camera");
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case Global.REQUEST_CODE_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // logInApp();
                }
                break;

            case Global.REQUEST_CODE_STORAGE:
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
                Log.e("des",destination.getPath());

            } else {


                licenseFile = imageUtility.compressImage(imageUtility.getRealPathFromURI(this, data.getData()));

                Log.e("des_gallery",""+data.getData());
                Log.e("uri",""+imageUtility.getRealPathFromURI(this, data.getData()));

            }

            Log.e("licenseFile", licenseFile);

        } catch (Exception e) {
            e.printStackTrace();
        }

      //  registerIntoApp(licenseFile);


        // registerLicense.setImageBitmap(thumbnail);
    }


    void multiplePermission() {

        if (!permissionFile.checkLocStorgePermission(this)) {
            permissionFile.checkLocStorgePermission(this);
        }
    }
    void registerIntoApp( String license_image) {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        RequestBody imageToUpload = RequestBody.create(MediaType.parse("image/jpg"), new File(license_image));

        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), "27");
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
        RequestBody selected_communication_medium = RequestBody.create(MediaType.parse("text/plain"), "1");

        Call<SucessModel> call = apiService.signup(imageToUpload, user_id, token, patient_id, doctor_id, consultation_date, start_time, end_time,message
        ,isPaid,amount,payment_method,selected_communication_medium);
        call.enqueue(new Callback<SucessModel>() {
            @Override
            public void onResponse(Call<SucessModel> call, Response<SucessModel> response) {

                Toast.makeText(MainActivity.this, ""+response.body().getStatus_code(), Toast.LENGTH_SHORT).show();

                Glide.with(MainActivity.this).load(licenseFile).into(registerImage);

                //getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).add(R.id.loginContainer, new LoginScreen()).addToBackStack(null).commit();
            }

            @Override
            public void onFailure(Call<SucessModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("log", t.toString());
            }
        });
    }

}
