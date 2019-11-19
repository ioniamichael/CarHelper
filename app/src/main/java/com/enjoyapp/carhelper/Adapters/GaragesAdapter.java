package com.enjoyapp.carhelper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enjoyapp.carhelper.Models.Garage;
import com.enjoyapp.carhelper.R;

import java.util.ArrayList;

public class GaragesAdapter extends RecyclerView.Adapter<GaragesAdapter.GarageViewHolder> {

    private Context context;
    private ArrayList<Garage.GarageObject> garages;
    private Garage garage;

    public GaragesAdapter(Context context, Garage garage) {
        this.context = context;
        this.garages = garage.getGarage();
        this.garage = garage;
    }

    @NonNull
    @Override
    public GarageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.garage_item, parent, false);
        return new GarageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GarageViewHolder holder, int position) {
        holder.mGarageName.setText(garages.get(position).garageName);
        holder.mGarageAddress.setText(garages.get(position).garageAddress+" , "+garages.get(position).garageCity);
        holder.mGaragePhoneNumber.setText(garages.get(position).garagePhone);
        holder.mGarageType.setText(garages.get(position).garageType);
    }

    @Override
    public int getItemCount() {
        if (garages == null) {
            return 0;
        }
        return garages.size();
    }

    public class GarageViewHolder extends RecyclerView.ViewHolder {

        private TextView mGarageName;
        private TextView mGaragePhoneNumber;
        private TextView mGarageType;
        private TextView mGarageAddress;

        public GarageViewHolder(@NonNull View itemView) {
            super(itemView);
            mGarageName = itemView.findViewById(R.id.garageName);
            mGaragePhoneNumber = itemView.findViewById(R.id.garagePhoneNumber);
            mGarageType = itemView.findViewById(R.id.garageType);
            mGarageAddress = itemView.findViewById(R.id.garageAddress);
        }
    }

}
