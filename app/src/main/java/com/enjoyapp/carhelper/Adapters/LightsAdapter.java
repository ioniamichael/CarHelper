package com.enjoyapp.carhelper.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.enjoyapp.carhelper.Models.Light;
import com.enjoyapp.carhelper.R;
import com.enjoyapp.carhelper.Screens.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class LightsAdapter extends RecyclerView.Adapter<LightsAdapter.LightsViewHolder> {

    private Context context;
    private ArrayList<Light> lights;
    private OnLightImageTouchListener onLightImageTouchListener;
    private int selected = -1;
    private Light light;


    public LightsAdapter(Context context, ArrayList<Light> lights) {
        this.context = context;
        this.lights = lights;
    }

    public void updateData(ArrayList<Light> lights) {
        this.lights = lights;
        notifyDataSetChanged();
    }


    public interface OnLightImageTouchListener {
        void onLightImageTouch(int position, MotionEvent motionEvent, LightsViewHolder holder);
    }

    public void setOnLightImageTouchListener(OnLightImageTouchListener onLightImageTouchListener) {
        this.onLightImageTouchListener = onLightImageTouchListener;
    }

    @NonNull
    @Override
    public LightsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lights_item, parent, false);
        return new LightsViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final LightsViewHolder holder, final int position) {
        light = lights.get(position);
        holder.rootItemView.setTag(position);

        //Glide - image loader.
        if (context != null) {
            Glide.with(context)
                    .load(light.getLampImageUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .override(200, 200)
                    .into(holder.IVLightImage);
        }

        setLightColor(holder, position);

        //This method called from LightsFragment.
        holder.rootItemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int position = (int) view.getTag();
                selected = position;
                if (onLightImageTouchListener != null) {
                    onLightImageTouchListener.onLightImageTouch(position, motionEvent, holder);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return lights.size();
    }

    public class LightsViewHolder extends RecyclerView.ViewHolder {

        private ImageView IVLightImage;
        private CardView rootItemView;


        public LightsViewHolder(@NonNull View itemView) {
            super(itemView);
            IVLightImage = itemView.findViewById(R.id.lightImage);
            rootItemView = itemView.findViewById(R.id.rootItemView);
        }
    }

    //Open light information on bottom sheet dialog.
    public void openLightInfo(int position) {
        View modelBottomSheet = LayoutInflater.from(context).inflate(R.layout.lights_info_bottom_sheet, null);
        BottomSheetDialog dialog = new BottomSheetDialog(context);
        dialog.setContentView(modelBottomSheet);
        TextView TVlightTitle = dialog.findViewById(R.id.lightTitle);
        TextView TVLightDesc = dialog.findViewById(R.id.lightDesc);
        TextView TVLightType = dialog.findViewById(R.id.lightType);
        TVlightTitle.setText(lights.get(position).getLampTitle());
        TVLightDesc.setText(lights.get(position).getLampDesc());
        TVLightType.setText(String.valueOf(lights.get(position).getLampType()));
        dialog.show();
    }

    //Set light color according to warning priority (1-4)
    public void setLightColor(LightsViewHolder holder, int position) {
        switch (lights.get(position).getLampType()) {
            case 1:
                holder.IVLightImage.setColorFilter(Color.RED);
                break;
            case 2:
                holder.IVLightImage.setColorFilter(Color.YELLOW);
                break;
            case 3:
                holder.IVLightImage.setColorFilter(Color.GREEN);
                break;
            case 4:
                holder.IVLightImage.setColorFilter(Color.BLUE);
                break;
        }
    }
}