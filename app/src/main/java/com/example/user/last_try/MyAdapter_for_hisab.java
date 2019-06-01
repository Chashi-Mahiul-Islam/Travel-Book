package com.example.user.last_try;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by User on 12/23/2017.
 */



/**
 * Created by User on 11/9/2017.
 */

public class  MyAdapter_for_hisab extends RecyclerView.Adapter<MyAdapter_for_hisab.ViewHolder> {

    private List<hisab> listItems;
    private Context mContext;
    public MyAdapter_for_hisab(List<hisab> listItems, Context mContext) {
        this.listItems = listItems;
        this.mContext = mContext;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_for_hisab, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        hisab itemList = listItems.get(position);
        holder.txtKoyTakaDise.setText(String.valueOf(itemList.amount));
        holder.txtKeDise.setText(itemList.ke_taka_dise);
        holder.txtKiKajeDise.setText(itemList.ki_kaj);

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtKeDise;
        public TextView txtKiKajeDise;
        public TextView txtKoyTakaDise;
        public ViewHolder(View itemView) {
            super(itemView);
            txtKeDise=(TextView)itemView.findViewById(R.id.txtKeDise);
            txtKiKajeDise=(TextView)itemView.findViewById(R.id.txtKiKajeDise);
            txtKoyTakaDise=(TextView)itemView.findViewById(R.id.txtKoyTakaDise);
        }
    }
}
