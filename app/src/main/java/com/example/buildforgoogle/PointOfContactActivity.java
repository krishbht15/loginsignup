package com.example.buildforgoogle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.buildforgoogle.databinding.ActivityPointOfContactBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class PointOfContactActivity extends AppCompatActivity {
private UserPojo userPojo;
    private static final String TAG = "PointOfContactActivity";
private ActivityPointOfContactBinding activityPointOfContactBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPointOfContactBinding= DataBindingUtil.setContentView(this,R.layout.activity_point_of_contact);
        userPojo= (UserPojo) getIntent().getSerializableExtra("user");
        activityPointOfContactBinding.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(activityPointOfContactBinding.firstNameText.getText().toString().length()>0
                       && activityPointOfContactBinding.firstPhoneEdit.getText().toString().length()>0
                       && activityPointOfContactBinding.firstRelationEdit.getText().toString().length()>0
                       && activityPointOfContactBinding.secondNameEdit.getText().toString().length()>0
                       && activityPointOfContactBinding.secondPhoneEdit.getText().toString().length()>0
                       && activityPointOfContactBinding.secondRelationEdit.getText().toString().length()>0) {

                    POCPojo pocPojo=new POCPojo(activityPointOfContactBinding.firstNameText.getText().toString(),
                            activityPointOfContactBinding.firstPhoneEdit.getText().toString(),
                            activityPointOfContactBinding.firstRelationEdit.getText().toString(),
                            activityPointOfContactBinding.secondNameEdit.getText().toString(),
                            activityPointOfContactBinding.secondPhoneEdit.getText().toString(),
                            activityPointOfContactBinding.secondRelationEdit.getText().toString());
FinalUserPojo finalUserPojo=new FinalUserPojo(userPojo,pocPojo);
addData(finalUserPojo);
               }
               else {
                   Toast.makeText(PointOfContactActivity.this, "Please Fill ALl Details", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }
    private void addData(FinalUserPojo usersPojo){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        Task<Void> myRef = database.getReference().child("users").child(currentUser.getUid()).setValue(usersPojo);
        myRef.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "onComplete: hoyga");

                    Toast.makeText(PointOfContactActivity.this, "HOjygaaa", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.d(TAG, "onComplete: nhi hua "+task.getException().getMessage());
                }
            }
        });
    }
}
