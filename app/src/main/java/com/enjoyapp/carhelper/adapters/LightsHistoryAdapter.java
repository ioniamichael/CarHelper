package com.enjoyapp.carhelper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.enjoyapp.carhelper.R;
import com.enjoyapp.carhelper.models.LightsHistory;
import com.enjoyapp.carhelper.views.LightsHistoryView;

import java.util.ArrayList;

public class LightsHistoryAdapter extends RecyclerView.Adapter<LightsHistoryAdapter.LightsHistoryViewHolder> {

    private LightsHistory lightsHistory;
    private ArrayList<LightsHistory> lightsHistoryArrayList;
    private Context mContext;
    private ArrayList<String> keys;
    private final int mLimit = 3;
    private LightsHistoryView lightsHistoryView;


    public LightsHistoryAdapter(LightsHistory lightsHistory, ArrayList<LightsHistory> lightsHistoryArrayList, Context mContext, ArrayList<String> keys) {
        this.lightsHistory = lightsHistory;
        this.lightsHistoryArrayList = lightsHistoryArrayList;
        this.mContext = mContext;
        this.keys = keys;
    }

    @NonNull
    @Override
    public LightsHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lights_history_item, parent, false);
        return new LightsHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LightsHistoryViewHolder holder, int position) {
        lightsHistory = lightsHistoryArrayList.get(position);
        Glide.with(mContext)
                .load(lightsHistory.getmLightsIMG())
                .override(60)
                .into(holder.mLightsHistoryIMG);

    }

    @Override
    public int getItemCount() {

        if (lightsHistoryArrayList.size() > mLimit)
            return mLimit;
        else {
        return lightsHistoryArrayList.size();
        }
    }

    public class LightsHistoryViewHolder extends RecyclerView.ViewHolder {

        private ImageView mLightsHistoryIMG;


        public LightsHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            mLightsHistoryIMG = itemView.findViewById(R.id.lightsHistoryIMG);

        }
    }
}
