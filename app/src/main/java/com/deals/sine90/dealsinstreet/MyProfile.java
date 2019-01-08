package com.deals.sine90.dealsinstreet;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.deals.sine90.dealsinstreet.LoginHelpers.UserFunctions;
import com.deals.sine90.dealsinstreet.SessionManagement.SessionManagement;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MyProfile extends AppCompatActivity {
       EditText name,email,mobilenum,city;
    TextView editprofile;
    ImageView prof_image;
    SessionManagement session;
    String sname,semail,smobilenum,scity,customerid,image;
    Bitmap thumbnail,thumbnail1;
    private ProgressDialog pDialog;
    String ba1,ba3;
    byte [] ba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        session=new SessionManagement(getApplicationContext());
        sname=session.getUserDetails().get("name");
        semail=session.getUserDetails().get("email");
        smobilenum=session.getUserDetails().get("mobilenum");
        scity=session.getUserDetails().get("city");
        customerid=session.getUserDetails().get("customerid");
        image=session.getUserDetails().get("prof_image");
        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        mobilenum=(EditText)findViewById(R.id.mobilenum);
        city=(EditText)findViewById(R.id.city);
        editprofile=(TextView)findViewById(R.id.edit_profile);
        prof_image=(ImageView)findViewById(R.id.profile_image);
       /* Picasso.with(this)
                .load(image)
                .placeholder(R.drawable.profile3)
                .into(prof_image);*/
        name.setText(sname);
        email.setText(semail);
        mobilenum.setText(smobilenum);
        city.setText(scity);
        name.setCursorVisible(false);
        email.setCursorVisible(false);
        mobilenum.setCursorVisible(false);
        city.setCursorVisible(false);
        name.setFocusableInTouchMode(false);
        name.setFocusable(false);
        email.setFocusableInTouchMode(false);
        email.setFocusable(false);
        city.setFocusableInTouchMode(false);
        city.setFocusable(false);
        mobilenum.setFocusableInTouchMode(false);
        mobilenum.setFocusable(false);

        prof_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editprofile.getText().toString().equals("Edit Profile")){

                }else if (editprofile.getText().toString().equals("Save Profile"))
                    selectImage();
            }
        });
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editprofile.getText().toString().equals("Edit Profile"))
                {
                    name.setCursorVisible(true);
                    email.setCursorVisible(true);
                    mobilenum.setCursorVisible(true);
                    city.setCursorVisible(true);
                    name.setFocusableInTouchMode(true);
                    name.setFocusable(true);
                    email.setFocusableInTouchMode(true);
                    email.setFocusable(true);
                    city.setFocusableInTouchMode(true);
                    city.setFocusable(true);
                    mobilenum.setFocusableInTouchMode(true);
                    mobilenum.setFocusable(true);
                    editprofile.setText("Save Profile");

                }
                else if (editprofile.getText().toString().equals("Save Profile"))
                {
                    name.setFocusableInTouchMode(false);
                    name.setFocusable(false);
                    email.setFocusableInTouchMode(false);
                    email.setFocusable(false);
                    city.setFocusableInTouchMode(false);
                    city.setFocusable(false);
                    mobilenum.setFocusableInTouchMode(false);
                    mobilenum.setFocusable(false);
                    name.setCursorVisible(false);
                    email.setCursorVisible(false);
                    mobilenum.setCursorVisible(false);
                    city.setCursorVisible(false);
                    editprofile.setText("Edit Profile");
                    new UpdateProfile().execute();

                }

            }
        });
    }
    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Choose from Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == 1) {
                    thumbnail1 = (Bitmap) data.getExtras().get("data");
                    prof_image.setImageBitmap(thumbnail1);
                }
                else if (requestCode == 2) {

                    Uri selectedImage = data.getData();
                    try {
                        //thumbnail = (BitmapFactory.decodeFile(picturePath));
                        thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        thumbnail1=Bitmap.createScaledBitmap(thumbnail, 300, 300, true);

                        prof_image.setImageBitmap(thumbnail1);

                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        catch (Exception e) {
            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG)
                    .show();
        }}
    private class UpdateProfile extends AsyncTask<String, String, JSONObject> {
        String str_name,str_email,str_mobilenum,str_city;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            str_name=name.getText().toString();
            str_email=email.getText().toString();
            str_mobilenum=mobilenum.getText().toString();
            str_city=city.getText().toString();
            pDialog = new ProgressDialog(MyProfile.this);
            pDialog.setTitle("Contacting Servers");
            pDialog.setMessage("Updating profile ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            thumbnail1.compress(Bitmap.CompressFormat.JPEG, 100, bao);
            ba = bao.toByteArray();
            ba3 = Base64.encodeToString(ba, 0);
            Log.e("ba1",ba3);
            UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.UpdateProfile(str_name,str_city,str_email,str_mobilenum,ba3,customerid,"","");
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            Log.e("Mytag", "" + json);
            if (pDialog.isShowing()){
                pDialog.dismiss();
            }
            try {
                if (json.getString("success").equals("1")){
                    JSONObject user=json.optJSONObject("user");
                    String fname=user.getString("fname");
                    String email=user.getString("email");
                    String mobile=user.getString("mobile_no");
                    String city=user.getString("city");
                    String image=user.getString("image");
                    session.createLoginSession(fname, email, mobile, city,customerid,image);
                    Intent intent=new Intent(MyProfile.this,MyProfile.class);
                    startActivity(intent);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }


    @Override
    public void onBackPressed() {
        //Execute your code here
        finish();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();
                onBackPressed();
                break;
        }
        return true;
    }
}
