package com.example.buildforgoogle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.buildforgoogle.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.UUID;

public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private ActivityRegisterBinding activityRegisterBinding;
    private Uri filePath;

    private static final String TAG = "RegisterActivity";
    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;
    private final int PICK_IMAGE_REQUEST = 71;

    private boolean checkingImgIschange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        checkingImgIschange = false;
        activityRegisterBinding.dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");

            }
        });
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        activityRegisterBinding.editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityRegisterBinding.editImage.setVisibility(View.VISIBLE);
                activityRegisterBinding.uploadImage.setVisibility(View.VISIBLE);
                activityRegisterBinding.capturedImage.setVisibility(View.INVISIBLE);
            }
        });
        activityRegisterBinding.uploadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setActivityTitle("Crop")
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(RegisterActivity.this);

            }
        });
        activityRegisterBinding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkingImgIschange && activityRegisterBinding.nameEdit.getText().toString().length()>0
                && activityRegisterBinding.nameEdit.getText().toString().length()>0
                && activityRegisterBinding.dateEdit.getText().toString().length()>0
                && activityRegisterBinding.phoneEdit.getText().toString().length()>0
                && activityRegisterBinding.complexityEdit.getText().toString().length()>0
                && activityRegisterBinding.heightEdit.getText().toString().length()>0
                && activityRegisterBinding.weightEdit.getText().toString().length()>0
                ){
                   uploadImage();
                }
                else
                    {
                        if(!checkingImgIschange){
                            Toast.makeText(RegisterActivity.this, "Please Upload Image", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Please Fill all details", Toast.LENGTH_SHORT).show();
                        }
                    }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
//                  checkingImgIschange = String.valueOf(result.getUri());
                checkingImgIschange = true;
                filePath=result.getUri();
                activityRegisterBinding.uploadImage.setVisibility(View.GONE);
                activityRegisterBinding.capturedImage.setVisibility(View.VISIBLE);
                activityRegisterBinding.capturedImage.setImageURI(result.getUri());
                activityRegisterBinding.editImage.setVisibility(View.VISIBLE);
            }
        }
    }
    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                          taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                              @Override
                              public void onComplete(@NonNull Task<Uri> task) {
                                  //                String name, String dob, String phone, String imgUrl, String complexion, String height, String weight
                                  UserPojo userPojo=new UserPojo(activityRegisterBinding.nameEdit.getText().toString(),
                                          activityRegisterBinding.dateEdit.getText().toString(),
                                          activityRegisterBinding.phoneEdit.getText().toString(),
                                          task.getResult().toString(),
                                          activityRegisterBinding.complexityEdit.getText().toString(),
                                          activityRegisterBinding.heightEdit.getText().toString(),
                                          activityRegisterBinding.weightEdit.getText().toString());
//                                  addData(userPojo);
                                  Intent intent=new Intent(RegisterActivity.this,PointOfContactActivity.class);
                                  intent.putExtra("user",userPojo);
                                  startActivity(intent);
                                  finish();
                                  Log.d(TAG, "onComplete: "+task.getResult().toString());

                              }
                          });
                            Toast.makeText(RegisterActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        activityRegisterBinding.dateEdit.setText(dayOfMonth +"/"+(month+1)+"/"+year);
    }
}
