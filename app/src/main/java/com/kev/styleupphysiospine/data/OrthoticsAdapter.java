package com.kev.styleupphysiospine.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kev.styleupphysiospine.R;

import java.util.ArrayList;

public class OrthoticsAdapter extends RecyclerView.Adapter<OrthoticsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<OrthoticsModel> orthoticsModelArrayList;

    public OrthoticsAdapter(Context context, ArrayList<OrthoticsModel> orthoticsModelArrayList) {
        this.context = context;
        this.orthoticsModelArrayList = orthoticsModelArrayList;
    }


    @NonNull
    @Override
    public OrthoticsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.sale_item, parent, false);
        return new OrthoticsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrthoticsAdapter.ViewHolder holder, int position) {
OrthoticsModel orthoticsModel = orthoticsModelArrayList.get(position);

String name  = orthoticsModel.getName();
String imgurl = orthoticsModel.getImg_url();
String price = orthoticsModel.getPrice();

        Glide.with(context).load(imgurl).placeholder(R.drawable.logo).into(holder.imageView);
        holder.titleTv.setText(name);
        holder.priceTv.setText(price);

    }

    @Override
    public int getItemCount() {
       return orthoticsModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView titleTv;
        public ImageView imageView;
        public TextView priceTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTv = (TextView) itemView.findViewById(R.id.item_title);
            imageView = (ImageView) itemView.findViewById(R.id.item_image);
            priceTv = (TextView) itemView.findViewById(R.id.item_price);
        }
    }

}
