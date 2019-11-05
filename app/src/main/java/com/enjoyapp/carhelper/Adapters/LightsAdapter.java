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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.enjoyapp.carhelper.Fragments.LightsFragment;
import com.enjoyapp.carhelper.Fragments.LightsInfoFragment;
import com.enjoyapp.carhelper.Models.Light;
import com.enjoyapp.carhelper.R;

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

    public interface OnLightImageClickListener {
        void onLightImageClick(int position);
    }

    public void setOnLightImageClickListener(OnLightImageClickListener onLightImageClickListener) {
        this.onLightImageClickListener = onLightImageClickListener;
    }

    @NonNull
    @Override
    public LightsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lights_item, parent, false);
        return new LightsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LightsViewHolder holder, int position) {
        light = lights.get(position);
        holder.rootItemView.setTag(position);
        holder.TVlightTitle.setText(light.getLampTitle());
        holder.TVLightDesc.setText(light.getLampDesc());
        holder.TVLightType.setText(String.valueOf(light.getLampType()));
        Glide.with(context)
                .load(light.getLampImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .override(200, 200)
                .into(holder.IVLightImage);
//        setBackGround(holder);
        hideLightInfo(holder);

        if (position == selected) {
            showLightInfo(holder);
        } else {
            hideLightInfo(holder);
        }

        holder.rootItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                selected = position;
                if (onLightImageClickListener != null) {
                    onLightImageClickListener.onLightImageClick(position);
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lights.size();
    }

    public class LightsViewHolder extends RecyclerView.ViewHolder {

        private TextView TVlightTitle;
        private TextView TVLightDesc;
        private TextView TVLightType;
        private ImageView IVLightImage;
        private CardView rootItemView;

        public LightsViewHolder(@NonNull View itemView) {
            super(itemView);
            TVlightTitle = itemView.findViewById(R.id.lightTitle);
            TVLightDesc = itemView.findViewById(R.id.lightDesc);
            TVLightType = itemView.findViewById(R.id.lightType);
            IVLightImage = itemView.findViewById(R.id.lightImage);
            rootItemView = itemView.findViewById(R.id.rootItemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onLightImageClickListener != null) {
                        onLightImageClickListener.onLightImageClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public void hideLightInfo(LightsViewHolder holder) {
        holder.TVlightTitle.setVisibility(View.GONE);
        holder.TVLightDesc.setVisibility(View.GONE);
        holder.TVLightType.setVisibility(View.GONE);
        holder.IVLightImage.setVisibility(View.VISIBLE);
    }

    public void showLightInfo(LightsViewHolder holder) {
        holder.TVlightTitle.setVisibility(View.VISIBLE);
        holder.TVLightDesc.setVisibility(View.VISIBLE);
        holder.TVLightType.setVisibility(View.VISIBLE);
        holder.IVLightImage.setVisibility(View.GONE);
    }

    public void setBackGround(LightsViewHolder holder) {
        if (light.getLampType() == 1) {
            holder.rootItemView.setBackgroundColor(Color.GREEN);
        } else {
            holder.rootItemView.setBackgroundColor(Color.RED);
        }
    }
}