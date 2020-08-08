package com.example.contentprovinder;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button bLoadContacts;
    private RecyclerView rvContact, rvSms;
    private Button bLoadSms;
    private ContactAdapter mcontactAdapter;
    private SmsAdapter mSmsAdapter;
    private List<Contact> myListContacts;
    private List<Contact> myListSms;
    public Criteria criteria;
    private LocationListener mLocationListener;
    private LocationManager mLocationManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bLoadContacts = findViewById(R.id.b_activity_main_load_contact);
        rvContact = findViewById(R.id.lv_activity_main_contact);
        rvSms = findViewById(R.id.lv_activity_main_sms);
        bLoadSms = findViewById(R.id.b_activity_main_load_sms);
        mLocationManager = (LocationManager)  this.getSystemService(Context.LOCATION_SERVICE);
        bLoadContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               new Contact().requestPermissionContact(MainActivity.this);
                mLocationManager.removeUpdates(mLocationListener);
//

            }
        });
//        String bestProvider = String.valueOf(mLocationManager.getBestProvider(criteria, true)).toString();
//        Toast.makeText(MainActivity.this,bestProvider,Toast.LENGTH_LONG).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission
                            (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,},
                        10);
                return;
            }
        }
        bLoadSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Contact().requestPermissionSms(MainActivity.this);
                requestUpdate();

//                xuly("Hagsd");
//                contactAdapter = new ContactAdapter(MainActivity.this, myContacts);
//                contactAdapter.notifyDataSetChanged();
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
//
//                lvListContact.setLayoutManager(linearLayoutManager);
//                lvListContact.setAdapter(contactAdapter);
            }


//            @Override
//            public void onClick(View view) {
//             mySms = new ArrayList<>();
//
//                Cursor cursor = getContentResolver().query(Uri.parse("content://sms/"), null, null, null, null);
//                int total = cursor.getCount();
//                if(cursor.moveToFirst()){
//                   for(int i = 0; i < total; i++){
//                       character Sms = new character() ;
//
//                       Sms.setNamecontact_sms(cursor.getString(cursor.getColumnIndexOrThrow("address" )));
//                       Sms.setContent_sms(cursor.getString(cursor.getColumnIndexOrThrow("body")));
//                       mySms.add(Sms);
//                       cursor.moveToNext();
//                    };
//                }cursor.close();
//                smsAdapter = new SmsAdapter(MainActivity.this,mySms);
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
//                lvListSms.setLayoutManager(linearLayoutManager);
//                lvListSms.setAdapter(smsAdapter);
//            }
        });

    }



    public void xuly(String num) {

        ContentResolver contentResolver = getContentResolver();
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        String[] args = new String[] {num};
        ops.add(ContentProviderOperation.newDelete(((ContactsContract.RawContacts.CONTENT_URI))).withSelection(ContactsContract.Contacts.DISPLAY_NAME + "=?", args).build());
        try {
            contentResolver.applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.e("TAG", "xuly: Lỗi  1");
        } catch (OperationApplicationException e) {
            e.printStackTrace();
            Log.e("TAG", "xuly: Lỗi  2");
        }



    }
    public void requestUpdate() {
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                Toast.makeText(getApplicationContext(), "lat" + latitude + "lon" + longitude,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }
    public void stopOnClick(){
        mLocationManager.removeUpdates(mLocationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200) {
            String[] projection = new String[]{ContactsContract.Contacts._ID, ContactsContract.Data.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.PHOTO_URI};
                Cursor phones = getContentResolver()
                        .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection,
                                null,
                                null,
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
                myListContacts = new ArrayList<>();
                String lastPhoneName = "";
                if (phones.getCount() > 0) {
                    while (phones.moveToNext()) {
                        String nameContact = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String phoneNumberContact = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        String contactId = phones.getString(phones.getColumnIndex(ContactsContract.RawContacts._ID));
                        if (!nameContact.equalsIgnoreCase(lastPhoneName)) {
                            lastPhoneName = nameContact;
                            Contact user = new Contact();
                            user.setNamecontact(phones.getString(phones.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                            user.setPhonenumber(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                            user.setID(phones.getString(phones.getColumnIndex(ContactsContract.Contacts._ID)));
                            myListContacts.add(user);
                            Log.d("getContactsList", nameContact + "---" + phoneNumberContact + " -- " + contactId );
                        }
                    }
                }
                phones.close();

                mcontactAdapter = new ContactAdapter(MainActivity.this, myListContacts);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                rvContact.setLayoutManager(linearLayoutManager);
                rvContact.setAdapter(mcontactAdapter);
        }

        else if (requestCode == 100) {
            myListSms = new ArrayList<>();

            Cursor cursor = getContentResolver().query(Uri.parse("content://sms/"), null, null, null, null);
            int total = cursor.getCount();
            if(cursor.moveToFirst()){
                for(int i = 0; i < total; i++){
                    Contact Sms = new Contact() ;
                    Sms.setNamecontact_sms(cursor.getString(cursor.getColumnIndexOrThrow("address" )));
                    Sms.setContent_sms(cursor.getString(cursor.getColumnIndexOrThrow("body")));
                    myListSms.add(Sms);
                    cursor.moveToNext();
                };
            }cursor.close();
            mSmsAdapter = new SmsAdapter(MainActivity.this, myListSms);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
            rvSms.setLayoutManager(linearLayoutManager);
            rvSms.setAdapter(mSmsAdapter);
        }
        else if (requestCode == 10 ){
requestUpdate();
        }

    }
    private static long getContactID(ContentResolver contactHelper, String
            number) {
        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number));
        String[] projection = { ContactsContract.PhoneLookup._ID };
        Cursor cursor = null;
        try {
            cursor = contactHelper.query(contactUri, projection, null, null,null);
            if (cursor.moveToFirst()) {
                int personID = cursor.getColumnIndex(ContactsContract.PhoneLookup._ID);
                return cursor.getLong(personID);
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
        return -1;
    }
    public void getLocation(){

    }
}


