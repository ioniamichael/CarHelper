package com.enjoyapp.carhelper.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enjoyapp.carhelper.Models.Garage;
import com.enjoyapp.carhelper.R;
import com.enjoyapp.carhelper.Utils.FragmentCommunication;

import java.util.List;

public class GaragesAdapter extends RecyclerView.Adapter<GaragesAdapter.GarageViewHolder> {

    private Context context;
    private List<Garage.GarageObject> garageObjects;
    private Garage garage;
    private OnPhoneNumberClickListener onPhoneNumberClickListener;
    private int selected = -1;


    public interface OnPhoneNumberClickListener {
        void onPhoneNumberClick(int position, String phone);
    }

    public void setOnPhoneNumberClickListener(OnPhoneNumberClickListener onPhoneNumberClickListener) {
        this.onPhoneNumberClickListener = onPhoneNumberClickListener;
    }

    public GaragesAdapter(Context context, List<Garage.GarageObject> garageObjects) {
        this.context = context;
        this.garageObjects = garageObjects;
    }

    @NonNull
    @Override
    public GarageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.garage_item, parent, false);
        return new GarageViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final GarageViewHolder holder, int position) {
        holder.mGarageItemRootView.setTag(position);
        holder.mGarageName.setText(garageObjects.get(position).garageName);
        holder.mGarageAddress.setText(garageObjects.get(position).garageAddress + context.getString(R.string.comma) + garageObjects.get(position).garageCity);
        holder.mGaragePhoneNumber.setText(garageObjects.get(position).garagePhone);
        holder.mGarageType.setText(garageObjects.get(position).garageType);

        holder.mGarageItemRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                selected = position;
                if (onPhoneNumberClickListener != null) {
                    onPhoneNumberClickListener.onPhoneNumberClick(position, holder.mGaragePhoneNumber.getText().toString().trim());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (garageObjects == null) {
            return 0;
        }
        return garageObjects.size();
    }

    public class GarageViewHolder extends RecyclerView.ViewHolder {

        private TextView mGarageName;
        private TextView mGaragePhoneNumber;
        private TextView mGarageType;
        private TextView mGarageAddress;
        private LinearLayout mGarageItemRootView;

        public GarageViewHolder(@NonNull View itemView) {
            super(itemView);
            mGarageName = itemView.findViewById(R.id.garageName);
            mGaragePhoneNumber = itemView.findViewById(R.id.garagePhoneNumber);
            mGarageType = itemView.findViewById(R.id.garageType);
            mGarageAddress = itemView.findViewById(R.id.garageAddress);
            mGarageItemRootView = itemView.findViewById(R.id.garageItemRootView);
        }
    }

}