package com.febi.mydata;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by flock on 17/1/17.
 */

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {
    private ArrayList<MyData> mMyDataList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView mCardView;
        TextView mAmountText;
        TextView mDateText;
        TextView mDescriptionText;

        ViewHolder(View v) {
            super(v);

            mCardView       = (CardView) v.findViewById(R.id.card_view);
            mAmountText     = (TextView) v.findViewById(R.id.id_amount_text);
            mDateText       = (TextView) v.findViewById(R.id.id_date_text);
            mDescriptionText= (TextView) v.findViewById(R.id.id_desc_text);
        }
    }

    MyListAdapter(Context context, ArrayList<MyData> myDataList) {
        mMyDataList = myDataList;
        mContext    = context;
    }

    @Override
    public MyListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View view               = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_view_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        MyData mydata = mMyDataList.get(position);
        if(mydata != null) {
            holder.mAmountText.setText("Rs. " + mydata.getAmount());
            holder.mDateText.setText(mydata.getDate());
            holder.mDescriptionText.setText(mydata.getQuantity() + " Litres from "
                    + mydata.getPlace());

            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) mContext).onListClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mMyDataList.size();
    }
}
