package com.enjoyapp.carhelper.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class LightsAdapter extends RecyclerView.Adapter<LightsAdapter.LightsViewHolder> {

    private Context context;
    private ArrayList<Light> lights;
    private OnLightImageClickListener onLightImageClickListener;
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

    public interface OnLightImageClickListener {
        void onLightImageClick(int position);
    }

    public void setOnLightImageClickListener(OnLightImageClickListener onLightImageClickListener) {
        this.onLightImageClickListener = onLightImageClickListener;
    }

    @NonNull
    @Override
    public LightsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lights_item, parent, false);
        return new LightsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LightsViewHolder holder, int position) {
        light = lights.get(position);
        holder.rootItemView.setTag(position);
        Glide.with(context)
                .load(light.getLampImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .override(200, 200)
                .into(holder.IVLightImage);
        setLightColor(holder, position);
        holder.rootItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                selected = position;
                if (onLightImageClickListener != null) {
                    onLightImageClickListener.onLightImageClick(position);
                }
                openLightInfo(position);
                notifyDataSetChanged();
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