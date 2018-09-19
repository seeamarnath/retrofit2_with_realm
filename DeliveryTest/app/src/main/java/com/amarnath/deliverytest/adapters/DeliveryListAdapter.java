package com.amarnath.deliverytest.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amarnath.deliverytest.R;
import com.amarnath.deliverytest.utils.models.Delivery;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class DeliveryListAdapter extends RealmRecyclerViewAdapter<Delivery, DeliveryListAdapter.MyViewHolder> {

    private Context context;

    public DeliveryListAdapter(Context context, OrderedRealmCollection<Delivery> data) {
        super(data, true);
        setHasStableIds(true);
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_delivery_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Delivery object = getData().get(position);

        holder.desc.setText(object.getDescription());
        holder.location.setText("@"+object.getLocation().getAddress());

        Glide.with(context)
                .load(object.getImageUrl())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.logo);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView logo;
        TextView desc, location;

        public MyViewHolder(View view) {
            super(view);
            logo = (ImageView) view.findViewById(R.id.iv_delivery_list_logo);
            desc = (TextView) view.findViewById(R.id.tv_delivery_list_desc);
            location = (TextView) view.findViewById(R.id.tv_delivery_list_location);

            logo.setClipToOutline(true);
        }
    }
}
