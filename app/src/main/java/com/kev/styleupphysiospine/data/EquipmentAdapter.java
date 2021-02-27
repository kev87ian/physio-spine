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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.ViewHolder> {
    private Context context;
    private ArrayList<EquipmentModel> equipmentModelsArrayList;

    public EquipmentAdapter(Context context, ArrayList<EquipmentModel> equipmentModelArrayList) {
        this.context = context;
        this.equipmentModelsArrayList = equipmentModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.sale_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EquipmentModel equipmentModel = equipmentModelsArrayList.get(position);
        String name  = equipmentModel.getName();
        String imgurl = equipmentModel.getImg_url();
        String price = equipmentModel.getPrice();

        Glide.with(context).load(imgurl).placeholder(R.drawable.logo).into(holder.imageView);
        holder.titleTv.setText(name);
        holder.priceTv.setText(price);

    }

    @Override
    public int getItemCount() {
        return equipmentModelsArrayList.size();
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
