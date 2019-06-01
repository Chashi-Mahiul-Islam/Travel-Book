package com.example.user.last_try;
import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by User on 11/9/2017.
 */

public class MyAdapter_for_comments extends RecyclerView.Adapter<MyAdapter_for_comments.ViewHolder> {

    private List<recycler_item_for_comments> listItems;
    private Context mContext;

    public MyAdapter_for_comments(List<recycler_item_for_comments> listItems, Context mContext) {
        this.listItems = listItems;
        this.mContext = mContext;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_for_comments, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        recycler_item_for_comments itemList = listItems.get(position);
        holder.txtKeCommentKorse.setText(itemList.ke_comment_korse);
        holder.txtKiCommentKorse.setText(itemList.ki_comment_korse);

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtKeCommentKorse;
        public TextView txtKiCommentKorse;

        public ViewHolder(View itemView) {
            super(itemView);
            txtKeCommentKorse=(TextView)itemView.findViewById(R.id.txtKeCommentKorse);
            txtKiCommentKorse=(TextView)itemView.findViewById(R.id.txtKiCommentKorse);

        }
    }
}
