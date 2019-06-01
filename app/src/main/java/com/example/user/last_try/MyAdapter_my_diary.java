package com.example.user.last_try;

/**
 * Created by User on 11/9/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Collections;
import java.util.List;
public class MyAdapter_my_diary  extends RecyclerView.Adapter<MyAdapter_my_diary.ViewHolder> {
    FirebaseStorage storage;StorageReference mStorageRef;
    private List<nijer_journey_details> listItems;
    private Context mContext;
    public MyAdapter_my_diary(List<nijer_journey_details> listItems, Context mContext) {
        this.listItems = listItems;
        this.mContext = mContext;
        this.storage=FirebaseStorage.getInstance();
        this.mStorageRef=storage.getReference();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_my_diary, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final nijer_journey_details itemList = listItems.get(position);
        holder.txtTitle.setText(itemList.journey_title);
        holder.txtDescription.setText(itemList.kon_desh_e_geshi+"\t"+String.valueOf(itemList.koydin_thaksi)+"days");
        mStorageRef.child(itemList.database_path+"cover pic").getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.img.setImageBitmap(bitmap);
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                holder.img.setImageResource(R.drawable.pic2);
            }
        });
        // holder.img.setImageResource(R.drawable.pic2);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, list_tester_for_my_diary2.class);
                //  intent.putExtra("database_path_for_vote",);
                intent.putExtra("koydin",itemList.koydin_thaksi);
                intent.putExtra("database_address",itemList.database_path);
                Toast.makeText(mContext,"day"+String.valueOf(itemList.koydin_thaksi)+itemList.database_path, Toast.LENGTH_LONG).show();
                mContext.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return listItems.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView img;
        public TextView txtDescription;
        public TextView txtTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.img);;
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            txtTitle=(TextView)itemView.findViewById(R.id.txtTitle);
        }
    }
}

