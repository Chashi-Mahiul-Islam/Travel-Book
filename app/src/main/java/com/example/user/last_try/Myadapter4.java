package com.example.user.last_try;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by User on 11/4/2017.
 */
//eta hoilo ekta jaygar asepase ki ki ase sob dekhabe
public class Myadapter4 extends RecyclerView.Adapter<Myadapter4.ViewHolder> {
    FirebaseDatabase database; FirebaseStorage storage;StorageReference mStorageRef;
    DatabaseReference myRef;
    static double latitude;
    static double longitude;
    static String address;

    private List<RecyclerItem4> listItems;
    private Context mContext;

    public Myadapter4(List<RecyclerItem4> listItems, Context mContext,String database_address) {
        this.listItems = listItems;
        this.mContext = mContext; storage=FirebaseStorage.getInstance();
        mStorageRef=storage.getReference();
        database = FirebaseDatabase.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(database_address);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item4, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final RecyclerItem4 itemList = listItems.get(position);
        holder.txtTitle.setText(itemList.getKe_dise());
        holder.category.setText(itemList.getCategory());
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
                //Toast.makeText(getApplicationContext(), exception.toString() + "!!!", Toast.LENGTH_SHORT).show();
            }
        });
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                latitude=itemList.getLatitude();
                longitude=itemList.getLongitude();
                address=itemList.getAddress();
                Intent intent=new Intent(mContext,map_for_show_2.class);
                mContext.startActivity(intent);
                Toast.makeText(mContext,"ok", Toast.LENGTH_LONG).show();

            }
        });
        holder.txtComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(mContext,list_tester_for_comments.class);
                intent2.putExtra("address",itemList.getImage_path()+"comments");
                mContext.startActivity(intent2);


            }
        });

        database.getReference(itemList.getImage_path()+"votes").addValueEventListener(new ValueEventListener() {
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
        database.getReference(itemList.getImage_path()+"comments").addValueEventListener(new ValueEventListener() {
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
                database.getReference(itemList.getImage_path()+"votes").child(MainActivity.usernames).setValue(1);

            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView img;//title ke likhse
        public TextView category;
        public TextView txtDescription;
        public ImageButton txtComment;
        public TextView txtTitle;
        public Button commentSongkha;
        public ImageButton vote_button;
        public Button voteSongkha;
        public ViewHolder(View itemView) {
            super(itemView);
            commentSongkha=(Button)itemView.findViewById(R.id.commentSongkha);
            voteSongkha=(Button)itemView.findViewById(R.id.voteSongkha);
            category=(TextView)itemView.findViewById(R.id.category);
            txtTitle=(TextView)itemView.findViewById(R.id.txtTitle);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            txtComment=(ImageButton)itemView.findViewById(R.id.txtComment);
            img=(ImageView) itemView.findViewById(R.id.img);
            vote_button=(ImageButton)itemView.findViewById(R.id.vote_button);
        }
    }
}
