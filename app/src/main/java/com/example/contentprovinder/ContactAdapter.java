package com.example.contentprovinder;


import android.app.AlertDialog;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactHolder>  {

    private String[] myData;
    private Context mContext;
    private List<Contact> myListContacts;
    private EditText edEditNameContact, edPhoneContact;
    private Button bConfirmEdit, bCancelEdit;
    public ContactAdapter(Context context, List<Contact> myContacts) {
        this.mContext = context;
        this.myListContacts = myContacts;
    }

    public ContactAdapter(Context context) {
        this.mContext = context;

    }

    public ContactAdapter(String[] mydata) {
        this.myData = mydata;
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_contact, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(final ContactHolder holder, final int position) {
        holder.tv_id.setText(String.valueOf(myListContacts.get(position).ID));
        holder.tvName.setText(myListContacts.get(position).namecontact);
        holder.tvPhone.setText(myListContacts.get(position).phonenumber);
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder dialogEditContact = new AlertDialog.Builder(mContext);
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogview = inflater.inflate(R.layout.dialog_edit_contact, null  );

                edEditNameContact =  dialogview.findViewById(R.id.ed_dialog_edit_name_contac);
                edPhoneContact =  dialogview.findViewById(R.id.ed_dialog_edit_phone_contac);
                bConfirmEdit =  dialogview.findViewById(R.id.b_dialog_edit_contact_confirm);
                bCancelEdit =  dialogview.findViewById(R.id.b_dialog_edit_contact_cancel);
                edEditNameContact.setText(myListContacts.get(position).namecontact);
                edPhoneContact.setText(myListContacts.get(position).phonenumber);

                dialogEditContact.setView(dialogview);
                dialogEditContact.setTitle("Sửa Liên Hệ");
                final AlertDialog alertDialogEditContact = dialogEditContact.create();
                alertDialogEditContact.show();
                bCancelEdit.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        alertDialogEditContact.dismiss();
                    }
                });
                bConfirmEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String where = ContactsContract.Data.DISPLAY_NAME + "=? AND " +
                                ContactsContract.Data.MIMETYPE + "=? AND " +
                                ContactsContract.CommonDataKinds.Phone.TYPE + "=?";

                        String[] params = new String[] {
                                myListContacts.get(position).namecontact,
                                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                                String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                        };

                        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

                        ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                                .withSelection(where, params)
                                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,"9999999999")
                                .build()
                        );
                    }
                });




            }
        });
        holder.tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        holder.tv_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentResolver contentResolver = mContext.getContentResolver();
                String where = ContactsContract.Data.DISPLAY_NAME + " = ? ";
                String[] params = new String[] {String.valueOf(myListContacts.get(position).namecontact)};
                ArrayList<ContentProviderOperation> ops = new ArrayList<>();

                ops.add(ContentProviderOperation.newDelete(((ContactsContract.RawContacts.CONTENT_URI))).withSelection(where, params).build());
                myListContacts.remove(position);
                notifyDataSetChanged();

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
        });
    }

    @Override
    public int getItemCount() {
        if (myListContacts == null) {
            return 0;
        }
        return myListContacts.size();

    }


}
