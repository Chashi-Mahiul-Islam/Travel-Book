package com.example.user.last_try;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.PopupMenu;

import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by User on 11/1/2017.
 */
//eta hoilo upload er somoy user jokhon nijer diary dekhe
public class MyAdapter3  extends RecyclerView.Adapter<MyAdapter3.ViewHolder> {
    FirebaseDatabase database; FirebaseStorage storage;StorageReference mStorageRef;
    DatabaseReference myRef,myRef2;
    String database_key;
    int database_kondin;
    private List<RecyclerItem2> listItems;
    private Context mContext;

    public MyAdapter3(List<RecyclerItem2> listItems, Context mContext) {//class
        this.listItems = listItems;
        this.mContext = mContext; storage=FirebaseStorage.getInstance();
        mStorageRef=storage.getReference();
        // database = FirebaseDatabase.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef=database.getReference(eventadd.databasepath_ki);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item3, parent, false);//recycler item 3 xml
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final RecyclerItem2 itemList = listItems.get(position);
        holder.txtDescription.setText(itemList.getDescription());
        mStorageRef.child(itemList.getImage_path()).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {



                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.img.setImageBitmap(bitmap);



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {


            }
        });
        holder.DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItems.remove(position);//showpage theke delete
                notifyDataSetChanged();
                myRef.child(itemList.getDatabase_path()).setValue(null);//database delete
                myRef2=database.getReference(itemList.getAddress());
                myRef2.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

                        final ekta_event Ekta = dataSnapshot.getValue(ekta_event.class);
                        if(Ekta.image_path.equals(itemList.getImage_path())){
                            myRef2.child(dataSnapshot.getKey()).setValue(null);
                            return;
                        }
                    }
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                        //Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                mStorageRef.child(itemList.getImage_path()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(mContext,"failed"+exception, Toast.LENGTH_LONG).show();

                    }
                });


                //  Toast.makeText(mContext,itemList.getImage_path()+String.valueOf(itemList.getLatitude()+" "+itemList.getLongitude()), Toast.LENGTH_LONG).show();


            }
        });


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{//recycler item sajanor joinno ki ki lage
        public ImageView img;
        public ImageButton DeleteButton;
        public TextView txtDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            DeleteButton=(ImageButton) itemView.findViewById(R.id.DeleteButton);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);

            img=(ImageView) itemView.findViewById(R.id.img);;
        }
    }
}
