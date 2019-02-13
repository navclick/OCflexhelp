package com.example.naveed.ocf.Activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.naveed.ocf.Base.BaseActivity;
import com.example.naveed.ocf.Base.GeneralCallBack;
import com.example.naveed.ocf.Helper.Constants;
import com.example.naveed.ocf.Helper.GeneralHelper;
import com.example.naveed.ocf.Models.GetUserResponse;
import com.example.naveed.ocf.Models.OrderDetails;
import com.example.naveed.ocf.Models.StatusUpdateRequest;
import com.example.naveed.ocf.Models.StatusUpdateResponse;
import com.example.naveed.ocf.Models.UserUpdateRequest;
import com.example.naveed.ocf.Models.UserUpdateResponse;
import com.example.naveed.ocf.Network.RestClient;
import com.example.naveed.ocf.R;
import com.example.naveed.ocf.Utility.ValidationUtility;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

EditText edit_fullname,edit_email,edit_phone,edit_account_title,edit_account_number,edit_bank_name,edit_swift,edit_address;
TextView txt_uploadpic;
public ImageView img_photo;
Button btn_update_profile;
    public static String imgBase64;
    public String TokenString, mediaPath;

    private AsyncTask mMyTask, updateTask;

public Bitmap bitmap = null;
public String file="";
    public static final int GET_FROM_GALLERY = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_profile);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_profile);
        navigationView.setNavigationItemSelectedListener(this);


        View header = navigationView.getHeaderView(0);
        TextView t = (TextView) header.findViewById(R.id.txt_main_name);
        TextView tEmail = (TextView) header.findViewById(R.id.txt_email);
        ImageView profile_img= (ImageView) header.findViewById(R.id.img_nav_profile);
        tEmail.setText(tokenHelper.GetUserEmail());

        t.setText(tokenHelper.GetUserName());
        Log.d(Constants.TAG,tokenHelper.GetUserPhoto());
        profile_img.setBackground(getResources().getDrawable(R.drawable.profile_image_border));
        Picasso.with(this).load(tokenHelper.GetUserPhoto()).resize(110, 110).centerCrop().into(profile_img);



        edit_fullname= (EditText) findViewById(R.id.edit_fullname) ;
        edit_email= (EditText) findViewById(R.id.edit_email) ;
        edit_phone= (EditText) findViewById(R.id.edit_phone) ;
        edit_account_title= (EditText) findViewById(R.id.edit_account_title) ;
        edit_account_number= (EditText) findViewById(R.id.edit_account_number) ;
        edit_bank_name= (EditText) findViewById(R.id.edit_bank_name) ;
        edit_swift= (EditText) findViewById(R.id.edit_swift) ;
        edit_address= (EditText) findViewById(R.id.edit_address) ;

        btn_update_profile = (Button) findViewById(R.id.btn_update_profile);

        txt_uploadpic= (TextView) findViewById(R.id.txt_uploadpic) ;

        img_photo= (ImageView) findViewById(R.id.img_photo) ;
        getProfile();
        txt_uploadpic.setOnClickListener(this);
        btn_update_profile.setOnClickListener(this);
    }


    public void getProfile(){


showProgress();
        RestClient.getAuthAdapterToekn(tokenHelper.GetToken()).GetProfile().enqueue(new GeneralCallBack<GetUserResponse>(this) {
            @Override
            public void onSuccess(GetUserResponse response) {
                Gson gson = new Gson();
                String Reslog = gson.toJson(response);
                Log.d("test", Reslog);


                hideProgress();

                if (!response.getIserror()) {

                    //  showMessageDailog(getString(R.string.app_name),Constants.MSG_SERVICE_STATUS_UPDATED);

                    edit_fullname.setText(response.getValue().getFullName());
                    edit_email.setText(response.getValue().getEmail());
                    edit_phone.setText(response.getValue().getPhoneNumber());
                    edit_account_title.setText(response.getValue().getAccountTitle());
                    edit_account_number.setText(response.getValue().getAccountNumber());
                    edit_bank_name.setText(response.getValue().getBankName());
                    edit_swift.setText(response.getValue().getSwift());
                    edit_address.setText(response.getValue().getAddressOne());
                    Picasso.with(img_photo.getContext()).load(response.getValue().getImage()).resize(250, 250).centerCrop().into(img_photo);
                } else {

                    //showMessageDailog(getString(R.string.app_name),response.getMessage());

                }


            }

            @Override
            public void onFailure(Throwable throwable) {
                //onFailure implementation would be in GeneralCallBack class

                showMessageDailog(getString(R.string.app_name), throwable.getMessage().toString());
                Toast.makeText(getApplicationContext(), "Failed",
                        Toast.LENGTH_LONG).show();

                hideProgress();
                Log.d("test", "failed");

            }


        });







    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_uploadpic:

                openPhotos();

                break;

            case R.id.btn_update_profile:

                UpdatUserProfile();

                break;



        }


    }


    public void openPhotos(){
        //startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

/*
        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();


            Log.d(Constants.TAG,"OK");
            Log.d(Constants.TAG,selectedImage.getPath());

            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                Log.d(Constants.TAG,picturePath);
                //File f = new File(picturePath);
               // Picasso.with(this).load(f).into(img_photo);

                file=picturePath;
               // Picasso.with(img_photo.getContext()).load("file://"+picturePath).config(Bitmap.Config.RGB_565).fit().centerCrop().into(img_photo);

                img_photo.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        */
        try{
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {

                case 1:
                    Uri selectedImage = data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    Log.d("filepath", mediaPath);


                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        img_photo.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
            }
        }
    }
    catch(Exception e){



    }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_profile);



        if (id == R.id.menu_myorders) {
            // Handle the camera action
            mDrawerLayout.closeDrawers();
            // openActivityWithFinish(AboutActivity.class);
            BaseActivity.startActivity(this,MyOrdersActivity.class);


        }  else if (id == R.id.menu_profile) {
            mDrawerLayout.closeDrawers();
            // openActivityProfile();
            //MenuHandler.smsTracking(this);
            //MenuHandler.callUs(this);
            //ActivityManager.showPopup(BookingActivity.this, Constant.CALL_NOW_DESCRIPTION, Constant.CALL_NOW_HEADING, Constant.CANCEL_BUTTON, Constant.CALL_NOW_BUTTON, Constant.CALL_BUTTON, Constant.PopupType.INFORMATION.ordinal());

            BaseActivity.startActivity(this,EditProfileActivity.class);
        }

        else if (id == R.id.menu_payments) {
            mDrawerLayout.closeDrawers();

            BaseActivity.startActivity(this,PaymentActivity.class);
            // openActivity(ShoppingListActivity.class);
            //MenuHandler.smsTracking(this);
            //MenuHandler.callUs(this);
            //ActivityManager.showPopup(BookingActivity.this, Constant.CALL_NOW_DESCRIPTION, Constant.CALL_NOW_HEADING, Constant.CANCEL_BUTTON, Constant.CALL_NOW_BUTTON, Constant.CALL_BUTTON, Constant.PopupType.INFORMATION.ordinal());
        }

        else if (id == R.id.menu_Schedules) {
            mDrawerLayout.closeDrawers();
            // openActivity(AllCatActivity.class);

            //MenuHandler.smsTracking(this);
            //MenuHandler.callUs(this);
            //ActivityManager.showPopup(BookingActivity.this, Constant.CALL_NOW_DESCRIPTION, Constant.CALL_NOW_HEADING, Constant.CANCEL_BUTTON, Constant.CALL_NOW_BUTTON, Constant.CALL_BUTTON, Constant.PopupType.INFORMATION.ordinal());
            BaseActivity.startActivity(this, OrderActivity.class);
        }


        else if (id == R.id.menu_logout) {
            mDrawerLayout.closeDrawers();
            // openActivity(AllCatActivity.class);

            //MenuHandler.smsTracking(this);
            //MenuHandler.callUs(this);
            //ActivityManager.showPopup(BookingActivity.this, Constant.CALL_NOW_DESCRIPTION, Constant.CALL_NOW_HEADING, Constant.CANCEL_BUTTON, Constant.CALL_NOW_BUTTON, Constant.CALL_BUTTON, Constant.PopupType.INFORMATION.ordinal());
            logOut();
        }

        return  true;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     /*   getMenuInflater().inflate(R.menu.toolbarmenu, menu);
        MenuItem item = menu.findItem(R.id.badge);
        MenuItemCompat.setActionView(item, R.layout.menu_cart);
        RelativeLayout notifCount = (RelativeLayout)   MenuItemCompat.getActionView(item);
        i =notifCount.findViewById(R.id.actionbar_notifcation_img);
        tv = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);
        //tv.setText("12");
        tv.setText("0");
        //   i.setOnClickListener(this);
        //  tv.setOnClickListener(this);
       */
        return super.onCreateOptionsMenu(menu);
    }

    public  String ConvertBitmapToString(Bitmap bitmap){
        String encodedImage = "";

      /*  ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        try {
            encodedImage= URLEncoder.encode(Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
*/


        img_photo.buildDrawingCache();
        Bitmap imgbit = img_photo.getDrawingCache();

        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        imgbit.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] image=stream.toByteArray();


        String img_str = Base64.encodeToString(image, 0);

        encodedImage=img_str;
        return encodedImage;
    }


    public  String getFileToByte(String filePath){
        Bitmap bmp = null;
        ByteArrayOutputStream bos = null;
        byte[] bt = null;
        String encodeString = null;
        try{
            bmp = BitmapFactory.decodeFile(filePath);
            bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bt = bos.toByteArray();
            encodeString = Base64.encodeToString(bt, Base64.DEFAULT);
        }catch (Exception e){
            e.printStackTrace();
        }
        return encodeString;
    }

    private boolean isValidate(){


        if(!ValidationUtility.EditTextValidator(edit_fullname, edit_phone, edit_address, edit_bank_name,edit_account_title,edit_account_number)){
            GeneralHelper.ShowToast(this, "Require field can not be empty!");
            return false;
        }else{
            return true;
        }
    }


    public void UpdatUserProfile() {
if(!isValidate()){

    return;

}

showProgress();
        if(bitmap != null){
          // requestObj.Image= ConvertBitmapToString(bitmap);
           // Log.d(Constants.TAG,getFileToByte(file));
            //requestObj.Image= getFileToByte(file);

            updateTask = new AsyncTaskLoad().execute(mediaPath);


        }
        else{

            setProfileOnServer();

        }





/*
        showProgress();

        Gson g = new Gson();
        String userJson = g.toJson(requestObj);
        Log.d("test", userJson);
        RestClient.getAuthAdapterToekn(tokenHelper.GetToken()).updateProfile(requestObj).enqueue(new GeneralCallBack<UserUpdateResponse>(this) {
            @Override
            public void onSuccess(UserUpdateResponse response) {
                Gson gson = new Gson();
                String Reslog = gson.toJson(response);
                Log.d("test", Reslog);


                hideProgress();

                if (!response.getIserror()) {

                    //  showMessageDailog(getString(R.string.app_name),Constants.MSG_SERVICE_STATUS_UPDATED);

                } else {

                    //showMessageDailog(getString(R.string.app_name),response.getMessage());

                }


            }

            @Override
            public void onFailure(Throwable throwable) {
                //onFailure implementation would be in GeneralCallBack class

                showMessageDailog(getString(R.string.app_name), throwable.getMessage().toString());
                Toast.makeText(getApplicationContext(), "Failed",
                        Toast.LENGTH_LONG).show();

                hideProgress();
                Log.d("test", "failed");

            }


        });
*/
    }


    private void setProfileOnDevice(){
        RestClient.getAuthAdapterToekn(tokenHelper.GetToken()).GetProfile().enqueue(new GeneralCallBack<GetUserResponse>(this) {
            @Override
            public void onSuccess(GetUserResponse response) {
                Gson gson = new Gson();
                String Reslog = gson.toJson(response);
                Log.d("test", Reslog);




                if (!response.getIserror()) {

                    //  showMessageDailog(getString(R.string.app_name),Constants.MSG_SERVICE_STATUS_UPDATED);


                    tokenHelper.SetUserName(response.getValue().getFullName());
                    tokenHelper.SetUserEmail(response.getValue().getEmail());
                    tokenHelper.SetUserPhoto(response.getValue().getImage());
// OpenActivity(ServicesListActivity.class);
                    hideProgress();
                    OpenActivity(OrderActivity.class);


                } else {

                    //showMessageDailog(getString(R.string.app_name),response.getMessage());

                }


            }

            @Override
            public void onFailure(Throwable throwable) {
                //onFailure implementation would be in GeneralCallBack class

                showMessageDailog(getString(R.string.app_name), throwable.getMessage().toString());
                Toast.makeText(getApplicationContext(), "Failed",
                        Toast.LENGTH_LONG).show();


                Log.d("test", "failed");

            }


        });



    }



    private void setProfileOnServer(){



        UserUpdateRequest requestObj = new UserUpdateRequest();
        requestObj.setFullName(edit_fullname.getText().toString());
        requestObj.setAddressOne(edit_address.getText().toString());
        requestObj.setPhoneNumber(edit_phone.getText().toString());
        requestObj.setAccountNumber(edit_account_number.getText().toString());

        requestObj.setAccountTitle(edit_account_title.getText().toString());
        requestObj.setBankName(edit_bank_name.getText().toString());
        requestObj.setSwift(edit_swift.getText().toString());



        if(bitmap != null) {
            requestObj.setImage(imgBase64);
        }
        Gson g = new Gson();
        String userJson = g.toJson(requestObj);
        Log.d("test", userJson);
        RestClient.getAuthAdapterToekn(tokenHelper.GetToken()).updateProfile(requestObj).enqueue(new GeneralCallBack<UserUpdateResponse>(this) {
            @Override
            public void onSuccess(UserUpdateResponse response) {
                Gson gson = new Gson();
                String Reslog = gson.toJson(response);
                Log.d("test", Reslog);


                hideProgress();

                if (!response.getIserror()) {

                    //  showMessageDailog(getString(R.string.app_name),Constants.MSG_SERVICE_STATUS_UPDATED);

                    setProfileOnDevice();

                } else {

                    //showMessageDailog(getString(R.string.app_name),response.getMessage());

                }


            }

            @Override
            public void onFailure(Throwable throwable) {
                //onFailure implementation would be in GeneralCallBack class

                showMessageDailog(getString(R.string.app_name), throwable.getMessage().toString());
                Toast.makeText(getApplicationContext(), "Failed",
                        Toast.LENGTH_LONG).show();

                hideProgress();
                Log.d("test", "failed");

            }


        });





    }


    public class AsyncTaskLoad  extends AsyncTask<String, String, String> {
        private final static String TAG = "AsyncTaskLoadImage";

        @Override
        protected String doInBackground(String... params) {

            String encodedImage = "";
            if(mediaPath == null || mediaPath.equals(""))
            {
                encodedImage = "";
            }else{
                Bitmap bm = BitmapFactory.decodeFile(mediaPath);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] byteArrayImage = baos.toByteArray();
                encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
                encodedImage = encodedImage.replace("\n","");
            }
            return encodedImage;
        }
        @Override
        protected void onPostExecute(String base64) {
            imgBase64 = base64;

            setProfileOnServer();
            Log.d("ss","ss");
        }
    }

    @Override
    public void onBackPressed() {
        OpenActivity(OrderActivity.class);
        finish();
        //System.exit(0);
    }

}
