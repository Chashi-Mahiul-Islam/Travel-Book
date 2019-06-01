package com.example.user.last_try;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by User on 1/15/2018.
 */

public class MyAdapter_For_Emergency_Numbers extends RecyclerView.Adapter<MyAdapter_For_Emergency_Numbers.ViewHolder> {

    private List<recycler_item_for_emergency_numbers> listItems;
    private Context mContext;
    FirebaseDatabase database;
    public MyAdapter_For_Emergency_Numbers(List<recycler_item_for_emergency_numbers> listItems, Context mContext) {
        this.listItems = listItems;
        this.mContext = mContext;
        database = FirebaseDatabase.getInstance();


    }

    @Override
    public MyAdapter_For_Emergency_Numbers.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_for_emergency_numbers, parent, false);
        return new MyAdapter_For_Emergency_Numbers.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyAdapter_For_Emergency_Numbers.ViewHolder holder, final int position) {
        final recycler_item_for_emergency_numbers itemList = listItems.get(position);
        holder.Number.setText(String.valueOf(itemList.number));
        holder.DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String key=itemList.key;
                listItems.remove(position);//showpage theke delete
                notifyDataSetChanged();
                database.getReference(MainActivity.usernames+"last_emergency_numbers").child(key).setValue(null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView Number;
        public Button DeleteButton;
        public ViewHolder(View itemView) {
            super(itemView);
            Number=(TextView)itemView.findViewById(R.id.Number);
            DeleteButton=(Button)itemView.findViewById(R.id.DeleteButton);

        }
    }
}
