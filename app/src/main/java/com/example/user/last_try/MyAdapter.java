package com.example.user.last_try;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
//eta hoilo search page er sob user ke koi gese
/**
 * Created by User on 10/31/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    FirebaseDatabase database;DateFormat dateFormat;
    DatabaseReference myRef;
    static String database_key;
    static int database_koydin;static String kon_desh;
    private List<RecyclerItem> listItems;
    private Context mContext;FirebaseStorage storage;StorageReference mStorageRef;
    public MyAdapter(List<RecyclerItem> listItems, Context mContext) {
        this.listItems = listItems;
        this.mContext = mContext;
        database = FirebaseDatabase.getInstance();
        this.storage=FirebaseStorage.getInstance();
        this.mStorageRef=storage.getReference();
        this.myRef=database.getReference(MainActivity.usernames+"search history");
        this.dateFormat= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);//xml
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final RecyclerItem itemList = listItems.get(position);
        holder.txtTitle.setText(itemList.getJourney_title());//gettitle kar journey
        holder.txtDescription.setText("creator "+itemList.getTitle()+" "+itemList.getKon_desh()+"\t"+itemList.getDescription()+" days");//koydiner journey
        mStorageRef.child(itemList.getCover_image_path()).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.img.setImageBitmap(bitmap);
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                holder.img.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.pic2));
            }
        });
        holder.txtDetails.setText("");
        database.getReference(itemList.getTitle().toString()+itemList.getKon_desh()+itemList.getJourney_title()+"specificday").addChildEventListener( new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                if (dataSnapshot.exists() == false || dataSnapshot == null ||dataSnapshot.getChildren() == null) {return;}//kono search history nai sob dekhao
                final String kondin=dataSnapshot.getKey();
                if(holder.txtDetails.getText().toString().contains(kondin)){return;}
                final String kon_jayga = dataSnapshot.getValue(String.class);
                holder.txtDetails.setText(holder.txtDetails.getText()+"day "+kondin+" : "+kon_jayga);

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
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
        holder.txtContributors.setText("contributors"+" "+String.valueOf(itemList.getContributors()));
        holder.txtVotes.setText("votes "+String.valueOf(itemList.getVotes()));
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database_key=itemList.getTitle().toString()+itemList.getKon_desh()+itemList.getJourney_title();
                database_koydin=Integer.parseInt(itemList.getDescription().toString());
                Date date = new Date();
                if(searchpage.age_kon_desh_search_korsi.equals(itemList.getKon_desh())){
                }else{
                    myRef.setValue(new previous_search_history(date,itemList.getKon_desh()));
                }

                if(itemList.getContributors()==0) {
                    kon_desh=itemList.getKon_desh();
                    Intent intent = new Intent(mContext, list_tester2.class);
                    intent.putExtra("database_path_for_vote",itemList.getDatabase_path_for_vote());
                    mContext.startActivity(intent);
                    Toast.makeText(mContext,"database key"+database_key, Toast.LENGTH_LONG).show();
                    Toast.makeText(mContext, itemList.getDatabase_path_for_vote(), Toast.LENGTH_LONG).show();
                }else{
                    kon_desh=itemList.getKon_desh();
                    Intent intent = new Intent(mContext, list_tester_for_multiple_contributor_journey.class);
                    intent.putExtra("main_person",itemList.getTitle());
                    intent.putExtra("database_path_for_vote",itemList.getDatabase_path_for_vote());
                    mContext.startActivity(intent);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return listItems.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView txtTitle;
        public TextView txtDescription;
        public TextView txtContributors;
        public TextView txtVotes;
        public TextView txtDetails;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            img=(ImageView) itemView.findViewById(R.id.img);;
            txtContributors=(TextView)itemView.findViewById(R.id.txtContributors);
            txtVotes=(TextView)itemView.findViewById(R.id.txtVotes);
            txtDetails=(TextView)itemView.findViewById(R.id.txtDetails);
        }
    }
}
