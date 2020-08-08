package com.example.contentprovinder;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Contact {
    public String namecontact;
    public String ID;
    public String phonenumber;
    public String namecontact_sms;
    public String Content_sms;
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNamecontact() {
        return namecontact;
    }

    public void setNamecontact(String namecontact) {
        this.namecontact = namecontact;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getNamecontact_sms() {
        return namecontact_sms;
    }

    public void setNamecontact_sms(String namecontact_sms) {
        this.namecontact_sms = namecontact_sms;
    }
    public String getContent_sms() {
        return Content_sms;
    }

    public void setContent_sms(String content_sms) {
        Content_sms = content_sms;
    }

    public void requestPermissionContact(Activity context) {
        boolean i = true;
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) + ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context,new String[] {Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS},200);
        }
        else {

        }

    };
    public void requestPermissionSms(Activity context) {
        boolean i = true;
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) + ContextCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context,new String[] {Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS},100);
        }
        else {

        }

    };
}
