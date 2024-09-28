package com.codebyashish.foodui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.codebyashish.foodui.R;
import com.codebyashish.foodui.models.BinsInventoryModel;

import java.util.ArrayList;

public class BinsAdapter extends RecyclerView.Adapter<BinsAdapter.HolderView> {
    Context context;
    ArrayList<BinsInventoryModel> modelArrayList;
    private OnBinsItemClick onBinsItemClick;
    private String country_currency;

    public interface OnBinsItemClick {

        void OnPlusClick(int id, String name, int inventory, int qtySelected, BinsInventoryModel model);
        void OnMinusClick(int id,  String name, int inventory, int qtySelected, BinsInventoryModel model);
    }
    public BinsAdapter(FragmentActivity mContext, ArrayList<BinsInventoryModel> arrayList, OnBinsItemClick onBinsItemClick) {
        this.context = mContext;
        this.modelArrayList = arrayList;
        this.onBinsItemClick = onBinsItemClick;

    }

    @androidx.annotation.NonNull
    @Override
    public HolderView onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food_in_cart, parent, false);
        return new HolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        BinsInventoryModel model = modelArrayList.get(position);

        Log.i("TAG", "onBindViewHolder: " + model);

        holder.binName.setText(model.getBinType());
        holder.tvBinRate.setText( "₹ " + model.getRate());
        holder.tvQty.setText(String.valueOf(model.getSelectedQuantity()));
        holder.binImg.setImageResource(model.getImageUrl());


        holder.add.setOnClickListener(view -> {
                model.setSelectedQuantity(model.getSelectedQuantity() + 1);
                holder.tvQty.setText(String.valueOf(model.getSelectedQuantity()));
                onBinsItemClick.OnPlusClick(model.getId(), model.getBinType(), model.getTotalStock(), model.getSelectedQuantity(), model);

        });
        holder.minus.setOnClickListener(view -> {
            if (model.getSelectedQuantity() > 1) {
                model.setSelectedQuantity(model.getSelectedQuantity() - 1);
                holder.tvQty.setText(String.valueOf(model.getSelectedQuantity()));
                onBinsItemClick.OnMinusClick(model.getId(), model.getBinType(), model.getTotalStock(), model.getSelectedQuantity(), model);

            }
        });


    }


    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public static class HolderView extends RecyclerView.ViewHolder {
        public AppCompatTextView binName, tvQty, tvBinRate;
       public AppCompatImageView binImg, add, minus;

        public HolderView(@NonNull View itemView) {
            super(itemView);

            binImg = itemView.findViewById(R.id.ivFoodType);
            binName = itemView.findViewById(R.id.tvFoodName);
            tvBinRate = itemView.findViewById(R.id.tvFoodPrice);
//            tvCurrentStock = itemView.findViewById(R.id.tvBinCurrentStock);
            tvQty = itemView.findViewById(R.id.tvCounts);
            add = itemView.findViewById(R.id.ivAdd);
            minus = itemView.findViewById(R.id.ivMinus);
        }
    }
}