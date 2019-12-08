package com.enjoyapp.carhelper.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enjoyapp.carhelper.R;
import com.enjoyapp.carhelper.models.Garage;

import java.util.List;

public class GaragesAdapter extends RecyclerView.Adapter<GaragesAdapter.GarageViewHolder> {

    private Context context;
    private List<Garage.GarageObject> garageObjects;
    private Garage garage;
    private OnPhoneNumberClickListener onPhoneNumberClickListener;
    private int selected = -1;


    public interface OnPhoneNumberClickListener {
        void onPhoneNumberClick(int position, String phone);

        void onRootViewClick(int position);
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
    public void onBindViewHolder(@NonNull final GarageViewHolder holder, final int position) {
        holder.mGarageItemRootView.setTag(position);
        holder.mGarageName.setText(garageObjects.get(position).garageName);
        holder.mGarageAddress.setText(garageObjects.get(position).garageAddress + context.getString(R.string.comma) + garageObjects.get(position).garageCity);
        holder.mGaragePhoneNumber.setText(garageObjects.get(position).garagePhone);
        holder.mGarageType.setText(garageObjects.get(position).garageType);
        holder.mIVPhoneButton.setTag(position);

        if (position == selected) {
            expandSelectedCard(holder);
        } else {
            collapseSelectedCard(holder);
        }

        holder.mGarageItemRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                selected = position;
                if (onPhoneNumberClickListener != null) {
                    onPhoneNumberClickListener.onRootViewClick(position);
                }
                expandSelectedCard(holder);
                notifyDataSetChanged();
            }
        });

        holder.mIVPhoneButton.setOnClickListener(new View.OnClickListener() {
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

        private LinearLayout mTopLine, mBottomLine, mActionLine;
        private TextView mGaragePhoneNumber, mGarageType, mGarageAddress, mGarageName;
        private RelativeLayout mGarageItemRootView;
        private ImageView mIVWazeButton, mIVPhoneButton;

        public GarageViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
            setOnStartVisibility();
        }

        public void initView(View itemView){
            mIVWazeButton = itemView.findViewById(R.id.IVWazeButton);
            mIVPhoneButton = itemView.findViewById(R.id.IVPhoneButton);
            mGarageName = itemView.findViewById(R.id.garageName);
            mGaragePhoneNumber = itemView.findViewById(R.id.garagePhoneNumber);
            mGarageType = itemView.findViewById(R.id.garageType);
            mGarageAddress = itemView.findViewById(R.id.garageAddress);
            mGarageItemRootView = itemView.findViewById(R.id.garageItemRootView);
            mTopLine = itemView.findViewById(R.id.topline);
            mBottomLine = itemView.findViewById(R.id.bottomLine);
            mActionLine = itemView.findViewById(R.id.actionLine);
        }

        private void setOnStartVisibility() {
            mBottomLine.setVisibility(View.GONE);
            mActionLine.setVisibility(View.GONE);
        }
    }

    public void expandSelectedCard(GarageViewHolder holder) {
        holder.mGarageItemRootView.animate().translationZ(30);
        holder.mTopLine.setVisibility(View.VISIBLE);
        holder.mBottomLine.setVisibility(View.VISIBLE);
        holder.mActionLine.setVisibility(View.VISIBLE);
    }

    public void collapseSelectedCard(GarageViewHolder holder) {
        holder.mGarageItemRootView.animate().translationZ(0);
        holder.mTopLine.setVisibility(View.VISIBLE);
        holder.mBottomLine.setVisibility(View.GONE);
        holder.mActionLine.setVisibility(View.GONE);
    }
}