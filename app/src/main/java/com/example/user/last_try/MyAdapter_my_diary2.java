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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class MyAdapter_my_diary2 extends RecyclerView.Adapter<MyAdapter_my_diary2.ViewHolder> {
    static double latitude,longitude;
    static String address;
    private List<recycler_item_my_diary2> listItems;FirebaseDatabase database;
    private Context mContext;
    FirebaseStorage storage;StorageReference mStorageRef;
    public MyAdapter_my_diary2(List<recycler_item_my_diary2> listItems, Context mContext) {
        this.listItems = listItems;
        this.mContext = mContext;
        storage=FirebaseStorage.getInstance();
        mStorageRef=storage.getReference();
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item2, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final recycler_item_my_diary2 itemList = listItems.get(position);

        holder.txtDescription.setText(itemList.description);
        mStorageRef.child(itemList.image_path).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
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
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                latitude=itemList.latitude;
                longitude=itemList.longitude;
                address=itemList.place_name;
                Intent intent=new Intent(mContext,MapsActivity_for_amar_nijer_journey_ami_nijei_Dekhbo.class);
                mContext.startActivity(intent);

            }
        });
        holder.txtComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(mContext,list_tester_for_comments.class);
                intent2.putExtra("address",itemList.image_path+"comments");
                mContext.startActivity(intent2);


            }
        });
        database.getReference(itemList.image_path+"votes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() == false || dataSnapshot == null ||dataSnapshot.getChildren() == null) {
                    holder.voteSongkha.setText("0");
                }else{
                    int child=(int)dataSnapshot.getChildrenCount();
                    holder.voteSongkha.setText(String.valueOf(child));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

        database.getReference(itemList.image_path+"comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() == false || dataSnapshot == null ||dataSnapshot.getChildren() == null) {
                    holder.commentSongkha.setText("0");
                }else{
                    int child=(int)dataSnapshot.getChildrenCount();
                    holder.commentSongkha.setText(String.valueOf(child));
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
        holder.vote_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.getReference(itemList.image_path+"votes").child(MainActivity.usernames).setValue(1);

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
        public ImageButton txtComment;
        public Button commentSongkha;
        public ImageButton vote_button;
        public Button voteSongkha;
        public ViewHolder(View itemView) {
            super(itemView);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            txtComment = (ImageButton) itemView.findViewById(R.id.txtComment);
            img=(ImageView) itemView.findViewById(R.id.img);
            commentSongkha=(Button)itemView.findViewById(R.id.commentSongkha);
            voteSongkha=(Button)itemView.findViewById(R.id.voteSongkha);
            vote_button=(ImageButton)itemView.findViewById(R.id.vote_button);


        }
    }
}

