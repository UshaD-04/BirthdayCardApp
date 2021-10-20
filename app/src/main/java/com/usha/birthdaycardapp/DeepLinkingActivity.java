package com.usha.birthdaycardapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

public class DeepLinkingActivity extends AppCompatActivity {
    String appLinkAction = "";
    Uri appLinkData= null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_linking);
        Intent appLinkIntent = getIntent();
        appLinkAction = appLinkIntent.getAction();
        appLinkData = appLinkIntent.getData();
        Log.d("my app link data", appLinkData.getPath() + "action ==>" + appLinkAction);

        Intent intent = new Intent(this, BirthdayGreetingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data_list", null);
        intent.putExtra("DigitalMember", true);
        intent.putExtra("BUNDLE", bundle);
        startActivity(intent);
//        Intent intent = new Intent(this, BirthdayGreetingActivity.class);
//        handleFirebaseDynamicLinks(intent);




    }
//    private void handleFirebaseDynamicLinks(Intent Intent) {
//        FirebaseDynamicLinks.getInstance()
//                .getDynamicLink(getIntent())
//                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
//                    @Override
//                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
//                        // Get deep link from result (may be null if no link is found)
//                        Uri deepLink = null;
//                        if (pendingDynamicLinkData != null) {
//                            deepLink = pendingDynamicLinkData.getLink();
//                        }
//
//
//                        // Handle the deep link. For example, open the linked content,
//                        // or apply promotional credit to the user's account.
//                        // ...
//
//                        // ...
//                    }
//                })
//                .addOnFailureListener(this, new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("TAG", "getDynamicLink:onFailure", e);
//                    }
//                });
//        // 1
//    }


}