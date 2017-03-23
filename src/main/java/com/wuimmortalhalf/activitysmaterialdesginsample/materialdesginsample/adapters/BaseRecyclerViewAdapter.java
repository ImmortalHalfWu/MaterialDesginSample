package com.wuimmortalhalf.activitysmaterialdesginsample.materialdesginsample.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wuimmortalhalf.activitysmaterialdesginsample.materialdesginsample.R;

import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */

public class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseRecyclerViewAdapter.MyViewHold> {

    private final List<String> urls;

    public BaseRecyclerViewAdapter(@NonNull List<String> urls) {
        this.urls = urls;
    }

    @Override
    public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHold(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHold holder, int position) {
        holder.imageViewl.setImageResource(R.mipmap.timg);
    }

    @Override
    public int getItemCount() {
//        return urls.size();
        return 15;
    }


    static class MyViewHold extends RecyclerView.ViewHolder{
        private final ImageView imageViewl;
        public MyViewHold(View itemView) {
            super(itemView);
            imageViewl = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
